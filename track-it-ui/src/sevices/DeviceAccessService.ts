/* eslint-disable */
import http from "../http-commons";
import {AccessDto} from "@/dto/AccessDto";
import userInfo from "@/scripts/UserInfo";
import {AxiosResponse} from "axios";
import {DeviceInfoPage} from "@/dto/DeviceInfoPage";


class DeviceAccessService {
    activate(deviceId: AccessDto) {
        const url = "/devices/access/" + userInfo.userId + "/activate";
        return http.post(url, deviceId);
    }

    deactivate(deviceId: string) {
        const url = "/devices/access/" + userInfo.userId + "/deactivate";
        return http.post(url, deviceId);
    }

    getDeviceInfo(deviceId: string, pageNumber: number, pageSize: number): Promise<AxiosResponse<DeviceInfoPage>> {
        const url = "/devices/" + deviceId + "/logs";
        const requestParams = new URLSearchParams()
        requestParams.set("page", String(pageNumber));
        requestParams.set("size", String(pageSize));
        return http.get(url, {params: requestParams})
    }
}

export default new DeviceAccessService();
