/* eslint-disable */
import http from "../http-commons";
import {AxiosResponse} from "axios";
import {LastLocationDto} from "@/dto/LastLocationDto";
import LocationRangeDto from "@/dto/LocationRangeDto";
import {TrackDto} from "@/dto/TrackDto";


class LocationService {
    getLastLocation(deviceId: string): Promise<AxiosResponse<LastLocationDto>> {
        return http.get(`${this.locationUrlWithId(deviceId)}/last`);
    }

    getLocationRange(deviceId: string, rangeStart: string, rangeEnd: string): Promise<AxiosResponse<LocationRangeDto>> {
        return http.get(`${this.locationUrlWithId(deviceId)}/range`, {params: this.createRangeParams(rangeStart, rangeEnd)});
    }

    getTracksForPage(deviceId: string, rangeStart: string, rangeEnd: string): Promise<AxiosResponse<Array<TrackDto>>> {
        return http.get(`${this.locationUrlWithId(deviceId)}/tracks`, {params: this.createRangeParams(rangeStart, rangeEnd)});
    }

    private locationUrlWithId(deviceId: string) {
        return `/locations/${deviceId}`
    }

    private createRangeParams(rangeStart: string, rangeEnd: string): URLSearchParams {
        const requestParams = new URLSearchParams()
        requestParams.set("rangeStart", rangeStart);
        requestParams.set("rangeEnd", rangeEnd);
        return requestParams
    }

}

export default new LocationService();
