/* eslint-disable */
import Vue from "vue";
import VueRouter, {RouteConfig} from "vue-router";
import Home from "../views/Home.vue";

Vue.use(VueRouter);

const routes: Array<RouteConfig> = [
    {
        path: "/",
        name: "Home",
        component: Home
    },
    {
        path: "/devices/manager",
        name: "DevicesManager",
        component: () => import( "@/views/devices/DevicesManager.vue")
    },
    {
        path: "/devices/last",
        name: "DevicesLast",
        component: () => import( "@/views/devices/DevicesLast.vue")
    },
    {
        path: "/devices/interval",
        name: "DevicesInterval",
        component: () => import( "@/views/devices/DevicesInterval.vue")
    },
    {
        path: "/devices/logs",
        name: "DevicesLogs",
        component: () => import( "@/views/devices/DevicesLogs.vue")
    },
    {
        path: "/devices/live",
        name: "DevicesLive",
        component: () => import( "@/views/devices/DevicesLive.vue")
    },
    {
        path: "/sign-in",
        name: "SignIn",
        component: () => import( "@/views/SignIn.vue")
    },
    {
        path: "/sign-up",
        name: "SignUp",
        component: () => import( "@/views/SignUp.vue")
    },
    {
        path: "/account",
        name: "Account",
        component: () => import( "@/views/Account.vue")
    }
];

const router = new VueRouter({
    routes
});

export default router;
