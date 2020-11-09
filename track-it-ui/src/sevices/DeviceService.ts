/* eslint-disable */
import http from "../http-commons";
import {AccessDto} from "@/dto/AccessDto";
import {AxiosResponse} from "axios";
import {DeviceInfoPage} from "@/dto/DeviceInfoPage";


class DeviceService {
    activate(deviceId: AccessDto) {
        const url = "/devices/access/" + "test" + "/activate";
        return http.post(url, deviceId);
    }

    deactivate(deviceId: string) {
        const url = "/devices/access/" + "test" + "/deactivate";
        return http.post(url, deviceId);
    }

    getDeviceInfo(deviceId: string, pageNumber: number, pageSize: number): Promise<AxiosResponse<DeviceInfoPage>> {
        const url = "/devices/" + deviceId + "/logs";
        const requestParams = new URLSearchParams()
        requestParams.set("page", String(pageNumber));
        requestParams.set("size", String(pageSize));
        return http.get(url, {params: requestParams})
    }

    getDeviceCount(): Promise<AxiosResponse<number>>{
        return http.get("/devices/count");
    }
}

export default new DeviceService();
