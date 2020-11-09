/* eslint-disable */
import Vue from "vue";
import Vuex from "vuex";

import Authentication from "./modules/authentication.module";

Vue.use(Vuex);
const store =  new Vuex.Store({
    modules: {
        Authentication
    }
});


export default store
