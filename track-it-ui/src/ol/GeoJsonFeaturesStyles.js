import Stroke from "ol/style/Stroke";
import CircleStyle from "ol/style/Circle";
import Style from "ol/style/Style";
import Fill from "ol/style/Fill";

const image = new CircleStyle({
  radius: 4,
  fill: new Fill({
    color: "red"
  }),
  stroke: new Stroke({ color: "red", width: 3 })
});

const styles = {
  Point: new Style({
    image: image
  }),
  LineString: new Style({
    stroke: new Stroke({
      color: "red",
      width: 1
    })
  }),
  MultiLineString: new Style({
    stroke: new Stroke({
      color: "red",
      width: 1
    })
  }),
  MultiPoint: new Style({
    image: image
  })
};

export default function styleFunction(feature) {
  return styles[feature.getGeometry().getType()];
}
