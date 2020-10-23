import Vue from "vue";
import Vuetify from "vuetify";

Vue.use(Vuetify);

const vuetify = new Vuetify({
    theme: {
        themes: {
            light: {
                primary: "#009688",
                secondary: "#00bcd4",
                accent: "#3f51b5",
                error: "#f44336",
                warning: "#ff9800",
                info: "#607d8b",
                success: "#8bc34a"
            },
        },
    },
})

export default vuetify;
