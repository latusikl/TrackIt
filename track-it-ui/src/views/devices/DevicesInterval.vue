<template>
  <div class="background fill-height d-flex flex-row">
    <device-submenu></device-submenu>
    <v-container>
      <v-row>
        <vcard-subtitle
          class="justify-start"
          img-path="logo.svg"
          subtitle="Check location from interval"
        ></vcard-subtitle>
      </v-row>
      <v-divider></v-divider>
      <v-row class="align-center justify-center mt-5">
        <v-alert type="info" width="68.5%">
          Please remember! <br />
          Date range will be limited if there is for full range.
        </v-alert>
      </v-row>
      <v-row>
        <date-range-stepper
          v-on:range-chosen="handleRangeChoice"
        ></date-range-stepper>
      </v-row>
    </v-container>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import DeviceSubmenu from "@/components/devices/DeviceSubmenu.vue";
import DateRangeStepper from "@/components/location/DateRangeStepper.vue";
import VcardSubtitle from "@/components/home/VcardSubtitle.vue";
import Alert from "@/components/Alert.vue";

@Component({
  components: {
    Alert,
    DateRangeStepper,
    DeviceSubmenu,
    VcardSubtitle
  }
})
export default class DevicesInterval extends Vue {
  private handleRangeChoice(range: string) {
    const rangeSplit = range.split("_");
    console.log(rangeSplit[0]);
    console.log(rangeSplit[1]);
  }
}
</script>
