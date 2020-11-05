import {InfoLevel} from "@/dto/InfoLevel";

export interface DeviceInfoDto{
    message: string;
    serverDateTime: string;
    infoLevel: InfoLevel;
}
