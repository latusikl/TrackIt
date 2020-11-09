/* eslint-disable */
import Vue from "vue";
import VueRouter, {RouteConfig} from "vue-router";
import Home from "../views/Home.vue";

Vue.use(VueRouter);

const routes: Array<RouteConfig> = [
    {
        path: "/",
        redirect: "/home"
    },
    {
        path: "/home",
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

router.beforeEach((to, from, next) => {
    const publicRoutes = ["/sign-in", "/sign-up", "/home"];
    const authenticatedNotAllowedRoutes = ["/sign-in", "/sign-up"];
    const currentPath = to.path;
    const needsAuthentication = !publicRoutes.includes(currentPath);
    const loggedIn = localStorage.getItem("userModel");
    if (needsAuthentication && !loggedIn) {
        next("/sign-in");
    } else {
        if (loggedIn && authenticatedNotAllowedRoutes.includes(currentPath)) {
            next("/")
        } else {
            next();
        }
    }
});

export default router;
