import {FeatureCollection} from "geojson";

export default interface LocationRangeDto {

    rangeStart: string;
    rangeEnd: string;
    mapStart: number[];
    mapData: FeatureCollection;

}
