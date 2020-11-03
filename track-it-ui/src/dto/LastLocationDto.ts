import {LocationDto} from "@/dto/LocationDataDto";
import {FeatureCollection} from "geojson";

export interface LastLocationDto {
    locationData: LocationDto
    mapFeatures: FeatureCollection
}
