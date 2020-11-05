<template>
  <div class="background fill-height d-flex flex-row">
    <device-submenu></device-submenu>
    <v-container>
      <v-row>
        <vcard-subtitle
          class="justify-start"
          img-path="logs.svg"
          subtitle="Device information logs"
        ></vcard-subtitle>
      </v-row>
      <v-divider></v-divider>
      <v-row>
        <device-choice
          v-on:device-chosen="handleDeviceChosenEvent"
        ></device-choice>
      </v-row>
      <v-row class="justify-center">
        <device-info-logs :device-id="chosenDeviceId"></device-info-logs
      ></v-row>
    </v-container>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import DeviceSubmenu from "@/components/devices/DeviceSubmenu.vue";
import DeviceChoice from "@/components/devices/DeviceChoice.vue";
import VcardSubtitle from "@/components/home/VcardSubtitle.vue";
import DeviceInfoLogs from "@/components/devices/DeviceInfoLogs.vue";

@Component({
  components: {
    DeviceInfoLogs,
    DeviceSubmenu,
    DeviceChoice,
    VcardSubtitle
  }
})
export default class DevicesLogs extends Vue {
  private chosenDeviceId = "";

  private handleDeviceChosenEvent(userDeviceId: string) {
    this.chosenDeviceId = userDeviceId;
  }
}
</script>
