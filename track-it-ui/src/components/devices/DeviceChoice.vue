<template>
  <v-container class="container-width">
    <v-select
      v-model="chosenDevice"
      @change="deviceChosen"
      label="Choose device"
      :items="userDeviceDtoList"
      item-text="deviceName"
      item-value="deviceId"
      solo
    ></v-select>
  </v-container>
</template>

<script lang="ts">
import { Component, Emit, Vue } from "vue-property-decorator";
import { UserDeviceDto } from "@/dto/UserDeviceDto";
import UserService from "@/sevices/UserService";

@Component
export default class DeviceChoice extends Vue {
  userDeviceDtoList: Array<UserDeviceDto> = [];
  chosenDevice = "";

  constructor() {
    super();
    this.fetchUserDevices();
  }

  @Emit()
  deviceChosen() {
    return this.chosenDevice;
  }

  private fetchUserDevices() {
    UserService.getUserDevices()
      .then(response => {
        if (response.status == 200) {
          this.userDeviceDtoList = response.data;
        } else {
          console.warn("Unable to fetch your device list.");
        }
      })
      .catch(reason => {
        console.warn(reason);
      });
  }
}
</script>
