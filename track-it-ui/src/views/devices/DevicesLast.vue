<template>
  <div class="background fill-height d-flex flex-row">
    <device-submenu></device-submenu>
    <v-container>
      <v-row>
        <vcard-subtitle
          class="justify-start"
          img-path="logo.svg"
          subtitle="Check last location"
        ></vcard-subtitle>
      </v-row>
      <v-divider></v-divider>
      <v-row>
        <device-choice
          v-on:device-chosen="handleDeviceChosenEvent"
        ></device-choice>
      </v-row>
      <v-row class="map-row justify-center">
        <last-location :device-id="chosenDeviceId"></last-location>
      </v-row>
    </v-container>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import DeviceSubmenu from "@/components/devices/DeviceSubmenu.vue";
import VcardSubtitle from "@/components/home/VcardSubtitle.vue";
import DeviceChoice from "@/components/devices/DeviceChoice.vue";
import LastLocation from "@/components/location/LastLocation.vue";

@Component({
  components: {
    DeviceSubmenu,
    VcardSubtitle,
    DeviceChoice,
    LastLocation
  }
})
export default class DevicesLast extends Vue {
  chosenDeviceId = "";

  handleDeviceChosenEvent(userDeviceId: string) {
    this.chosenDeviceId = userDeviceId;
  }
}
</script>

<style scoped>
.map-row {
  min-width: 100%;
  height: 70%;
}
</style>
