/* eslint-disable */
import http from "../http-commons";
import {AxiosResponse} from "axios";
import {LastLocationDto} from "@/dto/LastLocationDto";
import LocationRangeDto from "@/dto/LocationRangeDto";


class LocationService {
    getLastLocation(deviceId: String): Promise<AxiosResponse<LastLocationDto>> {
        const url = "/locations/" + deviceId + "/last";
        return http.get(url);
    }

    getLocationRange(deviceId: String, rangeStart: string, rangeEnd: string): Promise<AxiosResponse<LocationRangeDto>> {
        const url = "/locations/" + deviceId;
        const requestParams = new URLSearchParams()
        requestParams.set("rangeStart", rangeStart);
        requestParams.set("rangeEnd", rangeEnd);
        return http.get(url, {params: requestParams});
    }

}

export default new LocationService();
