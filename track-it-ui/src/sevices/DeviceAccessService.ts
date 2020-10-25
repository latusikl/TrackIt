import http from "../http-commons";
import {AccessDto} from "@/dto/AccessDto";

class DeviceAccessService {
  activate(deviceId: AccessDto) {
    return http.post("/devices/access/activate", deviceId);
  }

  deactivate(deviceId: string) {
    return http.post("/devices/access/deactivate", deviceId);
  }
}

export default new DeviceAccessService();
