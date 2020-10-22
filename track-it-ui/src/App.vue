<template>
  <v-app>
    <v-app-bar dense app>
      <v-row no-gutters>
        <v-col cols="4">
          <h1>TrackIt</h1>
        </v-col>
        <v-col cols="2">
          <v-btn elevation="2" to="/">
            Home
          </v-btn>
        </v-col>
        <v-col cols="2" to="/">
          <v-btn elevation="2" to="/">
            Devices
          </v-btn>
        </v-col>
        <v-col cols="2">
          <v-btn elevation="2" to="/">
            Account
          </v-btn>
        </v-col>
        <v-col cols="2">
          <v-btn elevation="2" to="/">
            Log in
          </v-btn>
        </v-col>
      </v-row>
    </v-app-bar>

    <v-main>
      <!-- Provides the application the proper gutter -->
      <v-container fluid>
        <!-- If using vue-router -->
        <router-view></router-view>
      </v-container>
    </v-main>

    <v-footer app>
      <!-- -->
    </v-footer>
  </v-app>
</template>
