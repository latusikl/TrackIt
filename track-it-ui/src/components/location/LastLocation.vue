<template>
  <div ref="map-div" style="width: 100%; height: 100%"></div>
</template>

<script lang="ts">
import { Component, Prop, Ref, Vue } from "vue-property-decorator";
import TileLayer from "ol/layer/Tile";
import OSM from "ol/source/OSM";
import Map from "ol/Map";
import View from "ol/View";
import { transform } from "ol/proj.js";

@Component
export default class DevicesLast extends Vue {
  @Prop() deviceId!: string;
  @Ref("map-div") readonly mapDiv!: HTMLDivElement;

  private START_ZOOM = 10;
  private MIN_ZOOM = 5;

  mounted() {
    this.initMap();
  }

  private initMap() {
    return new Map({
      target: this.mapDiv,
      layers: [this.prepareOsmLayer()],
      view: this.prepareView()
    });
  }

  private prepareOsmLayer() {
    return new TileLayer({
      source: new OSM()
    });
  }

  private prepareView() {
    return new View({
      zoom: this.START_ZOOM,
      center: transform([18.9845, 50.1266], "EPSG:4326", "EPSG:3857"),
      constrainResolution: true,
      minZoom: this.MIN_ZOOM
    });
  }
}
</script>

<style scoped lang="scss">
@import "~ol/ol.css";
</style>
