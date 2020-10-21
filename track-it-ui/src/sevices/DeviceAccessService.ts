import http from "../http-commons"


class DeviceAccessService {

    activate(deviceId: string) {
        return http.post('/devices/access/activate', deviceId)
    }

    deactivate(deviceId: string) {
        return http.post('/devices/access/deactivate', deviceId)
    }

}

export default new DeviceAccessService()
