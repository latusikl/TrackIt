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
      <v-row class="mb-15">
        <date-range-stepper
          v-on:range-chosen="handleRangeChoice"
        ></date-range-stepper>
      </v-row>
      <v-divider class="mb-15"></v-divider>
      <v-row class="align-center justify-center" v-if="isMapVisible">
        <range-info-row
          style="width: 100%"
          start-range-date="2020-11-11T20:15"
          end-range-date="2020-11-11T22:15"
        ></range-info-row>
      </v-row>
      <v-row class="map-row" v-if="isMapVisible">
        <geo-json-map
          :map-data="mapData"
          start-latitude="50.110611614863444"
          start-longitude="18.97786714476294"
        ></geo-json-map>
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
import { FeatureCollection } from "geojson";
import GeoJsonMap from "@/components/location/GeoJsonMap.vue";
import RangeInfoRow from "@/components/location/RangeInfoRow.vue";

@Component({
  components: {
    RangeInfoRow,
    GeoJsonMap,
    Alert,
    DateRangeStepper,
    DeviceSubmenu,
    VcardSubtitle
  }
})
export default class DevicesInterval extends Vue {
  private isMapVisible = false;
  private mapData: FeatureCollection;

  private handleRangeChoice(range: string) {
    const rangeSplit = range.split("_");
    this.fetchData(rangeSplit[0], rangeSplit[1]);
  }

  fetchData(rangeStart: string, rangeEnd: string) {
    this.mapData = {
      type: "FeatureCollection",
      features: [
        {
          type: "Feature",
          geometry: {
            type: "Point",
            coordinates: [18.97786714476294, 50.110611614863444]
          },
          id: "1",
          properties: []
        },
        {
          type: "Feature",
          geometry: {
            type: "LineString",
            coordinates: [
              [18.97786714476294, 50.110611614863444],
              [18.9703723998913, 50.111070081467105],
              [18.9693368265111, 50.11780012229904]
            ]
          },
          id: "2",
          properties: []
        }
      ]
    };
    this.isMapVisible = true;
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
</style>
