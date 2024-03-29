<template>
  <v-container class="secondary container-width">
    <alert
      :message="successMessage"
      :is-visible="isSuccessVisible"
      alert-type="success"
      @invisible-event="makeSuccessInvisible"
    ></alert>

    <alert
      :message="errorMessage"
      :is-visible="isFailedVisible"
      alert-type="error"
      @invisible-event="makeFailedInvisible"
    ></alert>

    <v-row justify="space-between">
      <v-col cols="12" md="4">
        <div class="text-h5">Add device</div>
      </v-col>
    </v-row>

    <v-row justify="space-between">
      <v-col cols="12" md="4">
        <v-form ref="form" v-model="accessForm.valid">
          <v-text-field
            label="Device ID"
            v-model="accessForm.fields.deviceId"
            :rules="accessForm.rules.deviceId"
            :counter="maxDeviceIdLength"
          ></v-text-field>
          <v-text-field
            label="Device Name"
            v-model="accessForm.fields.deviceName"
            :rules="accessForm.rules.deviceName"
            :counter="maxDeviceNameLength"
          ></v-text-field>
        </v-form>
      </v-col>
    </v-row>

    <v-row justify="end" class="pa-5">
      <v-btn
        elevation="2"
        outlined
        color="accent"
        @click="activateDevice"
        :disabled="!accessForm.valid"
      >
        Add new device
      </v-btn>
    </v-row>
  </v-container>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import DeviceAccessService from "@/sevices/DeviceService";
import Alert from "@/components/Alert.vue";
import { AccessForm } from "@/scripts/forms/AccessForm.ts";
import { maxLength, requiredField } from "@/scripts/forms/FormValidators";
import { AccessDto } from "@/dto/AccessDto";

@Component({
  components: { Alert, SuccessAlert: Alert }
})
export default class DeviceAccess extends Vue {
  private maxDeviceIdLength = 145;
  private maxDeviceNameLength = 80;
  private isSuccessVisible = false;
  private isFailedVisible = false;
  private successMessage =
    "Request is in progress. Device should be added within few minutes. Refresh device list to check.";
  private errorMessage =
    "We are sorry. There some errors on server side. Please try again later or contact administrator.";

  accessForm: AccessForm = {
    valid: false,
    fields: {
      deviceId: "",
      deviceName: ""
    },
    rules: {
      deviceId: [
        requiredField("Device ID is required."),
        maxLength("Device ID is too long.", this.maxDeviceNameLength)
      ],
      deviceName: [
        requiredField("Device name is required."),
        maxLength("Device name is too long.", this.maxDeviceNameLength)
      ]
    }
  };

  private successVisible() {
    this.isSuccessVisible = true;
  }

  private errorVisible() {
    this.isFailedVisible = true;
  }

  private makeSuccessInvisible() {
    this.isSuccessVisible = false;
  }

  private makeFailedInvisible() {
    this.isFailedVisible = false;
  }

  private clearStatuses() {
    this.makeSuccessInvisible();
    this.makeFailedInvisible();
  }

  private buildSuccessMessage(id: string) {
    this.successMessage = "Device ID: " + id + " " + this.successMessage;
  }

  private activateDevice() {
    this.clearStatuses();
    const accessDto: AccessDto = {
      deviceId: this.accessForm.fields.deviceId,
      deviceName: this.accessForm.fields.deviceName
    };
    DeviceAccessService.activate(accessDto)
      .then(response => {
        if (response.status == 202) {
          this.buildSuccessMessage(accessDto.deviceId);
          this.clearStatuses();
          this.successVisible();
        } else {
          this.clearStatuses();
          this.errorVisible();
        }
      })
      .catch(reason => {
        console.debug("Error in device activation. Value: " + reason);
        this.clearStatuses();
        this.errorVisible();
      });
  }
}
</script>
