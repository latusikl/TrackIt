<template>
  <v-list color="secondary" outlined rounded :min-width="minWidth">
    <alert
      :message="alertMessage"
      :is-visible="isAlertVisible"
      :alert-type="alertType"
      @invisible-event="makeAlertInvisible"
    ></alert>
    <v-icon large color="black" class="float-right" @click="fetchUserDevices">
      mdi-refresh
    </v-icon>
    <div class="text-h5 pt-5 pb-5">Your devices</div>

    <div v-for="device in userDeviceDtoList" :key="device.deviceId">
      <v-divider></v-divider>
      <v-list-item>
        <v-list-item-content>
          <v-list-item-title class="pb-2">
            <b>DEVICE NAME</b>
          </v-list-item-title>
          <v-list-item-title>
            {{ device.deviceName }}
          </v-list-item-title>
          <v-list-item-title class="pt-2 pb-2">
            <b>DEVICE ID</b>
          </v-list-item-title>
          <v-list-item-title>
            {{ device.deviceId }}
          </v-list-item-title>
          <v-list-item-title class="pt-2 pb-2">
            <b>DEVICE STATUS</b>
          </v-list-item-title>
          <v-list-item-title>
            {{ device.deviceStatus }}
          </v-list-item-title>
        </v-list-item-content>
        <v-btn
          elevation="2"
          outlined
          color="accent"
          @click="deleteDevice(device.deviceId)"
          >Delete
        </v-btn>
      </v-list-item>
      <v-divider></v-divider>
    </div>
  </v-list>
</template>

<script lang="ts">
import { Component, Prop, Vue } from "vue-property-decorator";
import UserService from "@/sevices/UserService";
import { UserDeviceDto } from "@/dto/UserDeviceDto";
import DeviceAccessService from "@/sevices/DeviceAccessService";
import Alert from "@/components/Alert.vue";

@Component({
  components: { Alert }
})
export default class DeviceList extends Vue {
  @Prop(String) minWidth!: string;

  alertType = "ifo";
  alertMessage = "";
  isAlertVisible = false;

  userDeviceDtoList: Array<UserDeviceDto> = [];

  constructor() {
    super();
    this.fetchUserDevices();
  }

  private fetchUserDevices() {
    UserService.getUserDevices()
      .then(response => {
        if (response.status == 200) {
          this.userDeviceDtoList = response.data;
          if (this.userDeviceDtoList.length === 0) {
            this.showInfoWhenNoDevices();
          }
        } else {
          this.showError("Unable to fetch your device list.");
        }
      })
      .catch(reason => {
        this.showError("Error occured when fetching your devices.");
        console.warn(reason);
      });
  }

  private deleteDevice(deviceId: string) {
    DeviceAccessService.deactivate(deviceId)
      .then(response => {
        if (response.status == 202) {
          this.showSuccess("Device removal process is in progress");
          this.removeDeviceFromUi(deviceId);
        } else {
          this.showError("Unable to remove your device");
        }
      })
      .catch(reason => {
        this.showError("Error occurred when trying to remove your devices.");
        console.warn(reason);
      });
  }

  private makeAlertInvisible() {
    this.alertMessage = "";
    this.alertType = "info";
    this.isAlertVisible = false;
  }

  private showInfoWhenNoDevices() {
    this.alertMessage = "Currently you have no devices added.";
    this.alertType = "info";
    this.isAlertVisible = true;
  }

  private showError(errorMessage: string) {
    this.alertMessage = errorMessage;
    this.alertType = "error";
    this.isAlertVisible = true;
  }

  private showSuccess(successMessage: string) {
    this.alertMessage = successMessage;
    this.alertType = "success";
    this.isAlertVisible = true;
  }

  private removeDeviceFromUi(deviceId: string) {
    const index = this.userDeviceDtoList.findIndex(
      userDeviceDto => userDeviceDto.deviceId === deviceId
    );
    this.userDeviceDtoList.splice(index, 1);
  }
}
</script>
