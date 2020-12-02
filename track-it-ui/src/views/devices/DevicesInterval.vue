<template>
  <div class="background fill-height d-flex flex-row">
    <device-submenu></device-submenu>
    <div class="scroll-container" id="main-container">
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
            Tracks are limited up to one hour with length.
          </v-alert>
        </v-row>
        <v-row class="align-center justify-center">
          <alert
            style="width: 68.5%;"
            message="Unable to get location data"
            alert-type="error"
            :is-visible="isErrorAlertVisible"
            @invisible-event="makeErrorInvisible"
          ></alert>
          <device-choice
            v-on:device-chosen="handleDeviceChosenEvent"
          ></device-choice>
          <date-range-stepper
            :key="stepperKey"
            v-if="isStepperActive"
            v-on:range-chosen="handleRangeChoice"
            :device-id="chosenDeviceId"
          ></date-range-stepper>
        </v-row>
        <v-row class="align-center justify-center" v-if="isMapVisible">
          <range-info-row
            style="width: 100%"
            :start-range-date="rangeStart"
            :end-range-date="rangeEnd"
          ></range-info-row>
        </v-row>
        <v-row class="map-row" v-if="isMapVisible">
          <geo-json-map
            :map-data="mapData"
            :start-longitude="this.mapStartLongitude"
            :start-latitude="this.mapStartLatitude"
          ></geo-json-map>
        </v-row>
      </v-container>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import DeviceSubmenu from "@/components/devices/DeviceSubmenu.vue";
import DateRangeStepper from "@/components/location/DateRangeStepper.vue";
import VcardSubtitle from "@/components/home/VcardSubtitle.vue";
import Alert from "@/components/Alert.vue";
import { FeatureCollection } from "geojson";
import GeoJsonMap from "@/components/location/GeoJsonMap.vue";
import RangeInfoRow from "@/components/location/RangeInfoRow.vue";
import DeviceChoice from "@/components/devices/DeviceChoice.vue";
import LocationService from "@/sevices/LocationService";

@Component({
  components: {
    RangeInfoRow,
    GeoJsonMap,
    Alert,
    DateRangeStepper,
    DeviceSubmenu,
    VcardSubtitle,
    DeviceChoice
  }
})
export default class DevicesInterval extends Vue {
  private isMapVisible = false;
  private isStepperActive = false;
  private isErrorAlertVisible = false;
  private rangeStart = "";
  private rangeEnd = "";
  private chosenDeviceId = "";
  private mapData: FeatureCollection | undefined = undefined;
  private mapStartLongitude = 0.0;
  private mapStartLatitude = 0.0;
  private stepperKey = 0;

  private handleDeviceChosenEvent(userDeviceId: string) {
    this.chosenDeviceId = userDeviceId;
    this.refreshStepper();
    this.isStepperActive = true;
  }

  private handleRangeChoice(range: string) {
    console.debug("Handled new location range" + range);
    const rangeSplit = range.split("_");
    this.fetchData(rangeSplit[0], rangeSplit[1]);
    this.scrollDown();
  }

  private scrollDown() {
    // eslint-disable-next-line
    window.scrollTo(0, document.querySelector("#main-container")!.scrollHeight);
  }

  private makeErrorInvisible() {
    this.isErrorAlertVisible = false;
  }

  private refreshStepper() {
    this.stepperKey++;
  }

  private fetchData(rangeStart: string, rangeEnd: string) {
    LocationService.getLocationRange(this.chosenDeviceId, rangeStart, rangeEnd)
      .then(response => {
        const data = response.data;
        this.rangeStart = data.rangeStart;
        this.rangeEnd = data.rangeEnd;
        this.mapData = data.mapData;
        this.mapStartLongitude = data.mapStart[0];
        this.mapStartLatitude = data.mapStart[1];
        this.isMapVisible = true;
      })
      .catch(reason => {
        this.isErrorAlertVisible = true;
        this.isMapVisible = false;
        console.debug(reason);
      });
  }
}
</script>

<style scoped lang="scss">
.map-row {
  width: 100%;
  height: 700px;
  margin-top: 5px;
  margin-bottom: 20px;
}

.scroll-container {
  width: 100%;
  max-height: 100vh;
  overflow-y: scroll;
  padding-bottom: 10px;
}
</style>
