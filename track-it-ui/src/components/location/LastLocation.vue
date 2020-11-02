<template>
  <v-container>
    <v-btn @click="change">click</v-btn>
    <geo-json-map
      start-longitude="18.9845"
      start-latitude="50.1266"
      :map-data="mapFeatureCollection"
      v-on:mapUpdate="echoChange"
    ></geo-json-map>
  </v-container>
</template>

<script lang="ts">
import { Component, Prop, Vue } from "vue-property-decorator";
import GeoJsonMap from "@/components/location/GeoJsonMap.vue";
import { FeatureCollection } from "geojson";
@Component({
  components: { GeoJsonMap }
})
export default class DevicesLast extends Vue {
  @Prop() deviceId!: string;

  private mapFeatureCollection: FeatureCollection = DevicesLast.prepareMapData();

  private echoChange() {
    console.warn("Change received");
  }

  private change() {
    this.mapFeatureCollection = {
      type: "FeatureCollection",
      features: [
        {
          type: "Feature",
          geometry: {
            type: "Point",
            coordinates: [18.8, 50.1566]
          },
          properties: [],
          id: "133"
        }
      ]
    };
  }

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

<style scoped lang="scss">
@import "~ol/ol.css";
</style>
