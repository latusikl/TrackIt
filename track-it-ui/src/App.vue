<template>
  <v-app :style="{ background: $vuetify.theme.themes['light'].background }">
    <v-card>
      <v-app-bar rounded="0" color="primary" flat>
        <h1>TrackIt</h1>
        <v-spacer></v-spacer>
        <v-tabs fixed-tabs centered background-color="primary">
          <v-tab to="/">
            HOME
          </v-tab>
          <v-tab to="/account" :disabled="!isLogged">
            ACCOUNT
          </v-tab>
          <v-tab to="/devices/manager" :disabled="!isLogged">
            DEVICES
          </v-tab>
        </v-tabs>
        <div class="mr-10 text-center" v-if="isLogged">
          {{ `Welcome! ${user.email.split("@")[0].toUpperCase()}` }}
        </div>
        <v-btn
          center
          elevation="2"
          class="mr-5"
          text
          to="/sign-up"
          v-if="!isLogged"
        >
          SIGN UP
        </v-btn>
        <v-btn
          center
          elevation="2"
          class="mr-5"
          text
          to="/sign-in"
          v-if="!isLogged"
        >
          SIGN IN
        </v-btn>
        <v-btn
          center
          elevation="2"
          class="mr-5"
          text
          to="/"
          v-if="isLogged"
          @click="signOut"
        >
          SIGN OUT
        </v-btn>
      </v-app-bar>
    </v-card>
    <v-main>
      <!-- If using vue-router -->
      <router-view></router-view>
    </v-main>
  </v-app>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import { namespace } from "vuex-class";
import { UserModel } from "@/dto/UserModel";
import AuthService from "@/sevices/AuthService";

const Authentication = namespace("Authentication");
@Component
export default class App extends Vue {
  @Authentication.Getter
  private isLogged!: boolean;

  @Authentication.Getter
  private user!: UserModel;

  @Authentication.Action
  private signOut!: () => void;

  @Authentication.Action
  private reLogin!: () => void;

  click() {
    AuthService.triggerReLoginMutation();
  }
}
</script>
