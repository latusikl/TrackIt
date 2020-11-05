import {DeviceInfoDto} from "@/dto/DeviceInfoDto";

export interface DeviceInfoPage {
    content: Array<DeviceInfoDto>;
    totalPages: number;
    last: boolean;
    first: boolean;
    number: number;
}
