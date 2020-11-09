/* eslint-disable */
import axios from "axios";
import getAuthHeader from "@/sevices/AuthorizationHeaderService";

const configuredAxios = axios.create({
    baseURL: "http://localhost:8090",
    headers: {
        "Content-type": "application/json"
    }
});


configuredAxios.interceptors.request.use(function (config) {
    config.headers = getAuthHeader();

    return config;
}, function (err) {
    return Promise.reject(err);
});

export default configuredAxios
