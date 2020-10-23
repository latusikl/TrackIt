<template>
  <v-app>
    <v-card>
      <v-app-bar rounded="0" color="primary" flat>
        <h1>TrackIt</h1>
        <v-spacer></v-spacer>
        <v-tabs fixed-tabs centered background-color="primary">
          <v-tab to="/">
            HOME
          </v-tab>
          <v-tab to="/account">
            ACCOUNT
          </v-tab>
          <v-tab to="/devices">
            DEVICES
          </v-tab>
        </v-tabs>
        <v-btn center flat elevation="2" text to="/">
          LOG IN
        </v-btn>
      </v-app-bar>
    </v-card>

    <v-main>
      <!-- Provides the application the proper gutter -->
      <v-container fluid>
        <!-- If using vue-router -->
        <router-view></router-view>
      </v-container>
    </v-main>
  </v-app>
</template>
