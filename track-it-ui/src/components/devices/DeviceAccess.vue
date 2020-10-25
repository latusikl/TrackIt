<template>
  <v-container class="grey access-container">
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
    <v-row>
      <v-btn>Add</v-btn>
    </v-row>
  </v-container>

  <!--    <label class="text-h5" for="deviceId">Add new device</label>-->
  <!--    <br />-->
  <!--    <label for="deviceId">{{ deviceId }}</label>-->
  <!--    <br />-->
  <!--    <input id="deviceId" type="text" v-model="deviceId" />-->
  <!--    <button @click="activateDevice">Activate</button>-->
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import DeviceAccessService from "@/sevices/DeviceAccessService";
import { AccessForm } from "@/scripts/forms/AccessForm.ts";
import { maxLength, requiredField } from "@/scripts/forms/FormValidators";
import { AccessDto } from "@/dto/AccessDto";

@Component
export default class DeviceAccess extends Vue {
  private maxDeviceIdLength = 36;
  private maxDeviceNameLength = 30;

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

  activateDevice() {
    const accessDto: AccessDto = {
      deviceId: this.accessForm.fields.deviceId,
      deviceName: this.accessForm.fields.deviceName
    };
    DeviceAccessService.activate(accessDto).then(response => {
      console.log(response.data);
      console.log(response.status);
      console.log(response.statusText);
    });
  }
}
</script>

<style scoped type="scss">
.access-container {
  max-width: 70%;
}
</style>
