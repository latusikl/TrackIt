/* eslint-disable */
import http from "../http-commons";
import {AccessDto} from "@/dto/AccessDto";
import userInfo from "@/scripts/UserInfo";


class DeviceAccessService {
    activate(deviceId: AccessDto) {
        const url = "/devices/access/" + userInfo.userId + "/activate";
        return http.post(url, deviceId);
    }

    deactivate(deviceId: string) {
        const url = "/devices/access/" + userInfo.userId + "/deactivate";
        return http.post(url, deviceId);
    }
}

export default new DeviceAccessService();
