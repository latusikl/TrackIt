<template>
  <v-card class="fill-height" max-width="300">
    <v-navigation-drawer color="secondary" permanent width="300">
      <v-list-item>
        <v-list-item-content>
          <v-list-item-title class="title">
            Devices
          </v-list-item-title>
          <v-list-item-subtitle>
            Manage your devices from here.
          </v-list-item-subtitle>
        </v-list-item-content>
      </v-list-item>

      <v-divider></v-divider>

      <v-list nav>
        <v-list-item link to="/devices/manager">
          Device manager
        </v-list-item>
        <v-divider></v-divider>
        <v-list-item link to="/devices/last">
          Last known location
        </v-list-item>

        <v-list-item link to="/devices/interval">
          Location from time interval
        </v-list-item>

        <v-list-item link to="/devices/logs">
          Device logs
        </v-list-item>

        <v-list-item link to="/devices/live">
          Live mode
        </v-list-item>
      </v-list>
    </v-navigation-drawer>
  </v-card>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";

@Component
export default class DeviceSubmenu extends Vue {}
</script>
