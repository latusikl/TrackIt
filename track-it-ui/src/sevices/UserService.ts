/* eslint-disable */
import http from "../http-commons";
import {AxiosResponse} from "axios";
import {UserDeviceDto} from "@/dto/UserDeviceDto";
import userInfo from "@/scripts/UserInfo";
import {UserData} from "@/dto/UserData";
import {AccountCreationDto} from "@/dto/AccountCreationDto";

class UserService {
    getUserDevices(): Promise<AxiosResponse<Array<UserDeviceDto>>> {
        return http.get("/devices");
    }

    getUserData(): Promise<AxiosResponse<UserData>>{
        return http.get("/users/me");
    }

    deleteAccount(){
        return http.delete("/users");
    }

    createAccount(accountCreationDto : AccountCreationDto){
        return http.post("/users", accountCreationDto);
    }

    changePassword(password : string){
        return http.post("users/password", {password: password});
    }
}

export default new UserService();
