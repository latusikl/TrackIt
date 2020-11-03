/* eslint-disable */
import http from "../http-commons";
import {AxiosResponse} from "axios";
import {LastLocationDto} from "@/dto/LastLocationDto";


class LocationService {
    getLastLocation(deviceId: String): Promise<AxiosResponse<LastLocationDto>> {
        const url = "/locations/" + deviceId + "/last";
        return http.get(url);
    }

}

export default new LocationService();
