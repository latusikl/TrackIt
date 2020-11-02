<template>
  <div ref="map-root" class="map"></div>
</template>

<script lang="ts">
import { Component, Emit, Prop, Ref, Vue, Watch } from "vue-property-decorator";
import TileLayer from "ol/layer/Tile";
import OSM from "ol/source/OSM";
import Map from "ol/Map";
import View from "ol/View";
import { transform } from "ol/proj.js";
import VectorLayer from "ol/layer/Vector";
import { FeatureCollection } from "geojson";
import GeoJSON from "ol/format/GeoJSON";
import VectorSource from "ol/source/Vector";

@Component
export default class GeoJsonMap extends Vue {
  @Prop({ default: 10 }) readonly startZoom!: number;
  @Prop({ default: 5 }) readonly minZoom!: number;
  @Prop({ default: 0 }) readonly startLongitude!: number;
  @Prop({ default: 0 }) readonly startLatitude!: number;
  @Prop() mapData!: FeatureCollection;
  @Ref("map-root")
  readonly mapDiv!: HTMLDivElement;
  private olMap!: Map;
  private dataLayer!: VectorLayer;

  mounted() {
    console.log("Inside mounted");
    this.dataLayer = this.initDataLayer();
    this.olMap = this.initMap(this.dataLayer);
    this.updateMapData(this.mapData);
  }

  private initDataLayer(): VectorLayer {
    return new VectorLayer({ source: new VectorSource({ features: [] }) });
  }

  private initMap(dataLayer: VectorLayer): Map {
    return new Map({
      target: this.mapDiv,
      layers: [this.initOsmLayer(), dataLayer],
      view: this.initView()
    });
  }

  private initOsmLayer(): TileLayer {
    return new TileLayer({
      source: new OSM()
    });
  }

  private initView(): View {
    return new View({
      zoom: this.startZoom,
      center: transform(
        [this.startLongitude, this.startLatitude],
        "EPSG:4326",
        "EPSG:3857"
      ),
      constrainResolution: true,
      minZoom: this.minZoom
    });
  }

  @Watch("mapData")
  onMapDataChanged(mapData: FeatureCollection) {
    console.log("Inside watch");
    this.updateMapData(mapData);
  }

  @Emit("mapUpdate")
  private updateMapData(mapData: FeatureCollection) {
    const view = this.olMap.getView();
    const source = this.dataLayer.getSource();

    const newFeatures = new GeoJSON({
      featureProjection: "EPSG:3857"
    }).readFeatures(JSON.stringify(mapData));

    source.clear();
    source.addFeatures(newFeatures);

    view.fit(source.getExtent());
  }
}
</script>

<style scoped lang="scss">
@import "~ol/ol.css";

.map {
  width: 100%;
  height: 100%;
}
</style>
