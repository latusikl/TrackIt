/* eslint-disable */
import http from "../http-commons";
import {AxiosResponse} from "axios";
import {UserDeviceDto} from "@/dto/UserDeviceDto";
import userInfo from "@/scripts/UserInfo";

class UserService {
    getUserDevices(): Promise<AxiosResponse<Array<UserDeviceDto>>> {
        return http.get("/users/" + userInfo.userId + "/devices")
    }
}

export default new UserService();
