/* eslint-disable */
import axios from "axios";
import getAuthHeader from "@/sevices/AuthorizationHeaderService";
import AuthService from "@/sevices/AuthService";
import EnvInfo from "@/env-info";

const configuredAxios = axios.create({

    baseURL: EnvInfo.baseUrl,
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

configuredAxios.interceptors.response.use((response) => {
    return response;
}, (error) => {
    if (error.response.status === 401 || error.response.status === 403) {
        AuthService.triggerReLoginMutation();
    }
    return new Promise((resolve, reject) => {
        reject(error);
    });
});


export default configuredAxios
