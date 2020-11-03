<template>
  <div class="background fill-height d-flex flex-row">
    <device-submenu></device-submenu>
    <v-container>
      <v-row>
        <vcard-subtitle
          class="justify-start"
          img-path="logo.svg"
          subtitle="Check location from time interval"
        ></vcard-subtitle>
      </v-row>
      <v-divider></v-divider>
      <v-row>
        <date-range-stepper></date-range-stepper>
      </v-row>
    </v-container>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import DeviceSubmenu from "@/components/devices/DeviceSubmenu.vue";
import DateRangeStepper from "@/components/location/DateRangeStepper.vue";
import VcardSubtitle from "@/components/home/VcardSubtitle.vue";

@Component({
  components: {
    DateRangeStepper,
    DeviceSubmenu,
    VcardSubtitle
  }
})
export default class DevicesInterval extends Vue {}
</script>
