<template>
  <v-container>
    <alert
      :is-visible="isAlertVisible"
      :message="alertMessage"
      :alert-type="alertType"
      @invisible-event="makeInvisible"
    ></alert>
    <v-icon
      large
      color="black"
      class="float-right mt-2 mr-2"
      @click="updateLocationRefreshClick"
      v-if="isMapVisible"
    >
      mdi-refresh
    </v-icon>
    <last-location-info
      v-if="isMapVisible"
      :latitude="latitude"
      :longitude="longitude"
      :date-time="dateTime"
    ></last-location-info>
    <geo-json-map
      v-if="isMapVisible"
      :start-longitude="longitude"
      :start-latitude="latitude"
      :map-data="mapData"
    ></geo-json-map>
  </v-container>
</template>

<script lang="ts">
import { Component, Prop, Vue, Watch } from "vue-property-decorator";
import GeoJsonMap from "@/components/location/GeoJsonMap.vue";
import { FeatureCollection } from "geojson";
import LastLocationInfo from "@/components/location/LastLocationInfo.vue";
import LocationService from "@/sevices/LocationService";
import { LastLocationDto } from "@/dto/LastLocationDto";
import Alert from "@/components/Alert.vue";
import { transformGeom2D } from "ol/geom/SimpleGeometry";

@Component({
  components: { Alert, LastLocationInfo, GeoJsonMap }
})
export default class DevicesLast extends Vue {
  @Prop() deviceId!: string;
  private isMapVisible = false;
  private isAlertVisible = false;
  private alertMessage = "";
  private alertType = "info";

  private mapData!: FeatureCollection;
  private latitude = "";
  private longitude = "";
  private dateTime = "";

  @Watch("deviceId")
  private onDeviceChange(deviceId: string) {
    this.makeInvisible();
    this.updateLocation(deviceId);
  }

  private updateLocation(deviceId: string) {
    LocationService.getLastLocation(deviceId)
      .then(response => {
        if (response.status == 200) {
          this.assignData(response.data);
          this.isMapVisible = true;
        } else {
          this.showError();
        }
      })
      .catch(reason => {
        console.warn(reason);
        this.showError();
      });
  }
  private updateLocationRefreshClick() {
    this.makeInvisible();
    this.updateLocation(this.deviceId);
  }

  private assignData(responseData: LastLocationDto) {
    this.latitude = responseData.locationData.latitude;
    this.longitude = responseData.locationData.longitude;
    this.dateTime = responseData.locationData.positionTimestamp;
    this.mapData = responseData.mapFeatures;
  }

  private makeInvisible() {
    this.isAlertVisible = false;
  }

  private showError() {
    this.alertMessage = "Unable to get location data for chosen device";
    this.alertType = "warning";
    this.isAlertVisible = true;
  }
}
</script>
