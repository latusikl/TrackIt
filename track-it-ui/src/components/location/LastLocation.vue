<template>
  <v-container>
    <last-location-info
      v-if="isVisible"
      latitude="50.1266"
      longitude="18.9845"
      date-time="25.10.2020 15:00"
    ></last-location-info>
    <geo-json-map
      v-if="isVisible"
      start-longitude="18.9845"
      start-latitude="50.1266"
      :map-data="mapFeatureCollection"
    ></geo-json-map>
  </v-container>
</template>

<script lang="ts">
import { Component, Prop, Vue, Watch } from "vue-property-decorator";
import GeoJsonMap from "@/components/location/GeoJsonMap.vue";
import { FeatureCollection } from "geojson";
import LastLocationInfo from "@/components/location/LastLocationInfo.vue";

@Component({
  components: { LastLocationInfo, GeoJsonMap }
})
export default class DevicesLast extends Vue {
  @Prop() deviceId!: string;
  private isVisible = false;

  @Watch("deviceId")
  private onDeviceChange(deviceId: string) {
    console.error(deviceId);
    this.isVisible = true;
  }

  private mapFeatureCollection: FeatureCollection = DevicesLast.prepareMapData();

  static prepareMapData(): FeatureCollection {
    return {
      type: "FeatureCollection",
      features: [
        {
          type: "Feature",
          geometry: {
            type: "Point",
            coordinates: [18.9845, 50.1266]
          },
          properties: [],
          id: "123"
        }
      ]
    };
  }
}
</script>
