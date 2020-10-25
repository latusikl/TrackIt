<template>
  <v-list color="secondary" outlined rounded :min-width="minWidth">
    <v-list-item>
      <v-list-item-content>
        <v-list-item-title>
          <b>DEVICE NAME</b>
        </v-list-item-title>
        <v-list-item-subtitle>
          Lokalizator adam
        </v-list-item-subtitle>
        <v-list-item-title>
          <b>DEVICE ID</b>
        </v-list-item-title>
        <v-list-item-subtitle>
          1234456662
        </v-list-item-subtitle>
        <v-list-item-title>
          <b>DEVICE STATUS</b>
        </v-list-item-title>
        <v-list-item-subtitle>
          CONNECTED
        </v-list-item-subtitle>
      </v-list-item-content>
      <v-btn elevation="2" outlined color="accent">Delete</v-btn>
    </v-list-item>
    <v-divider></v-divider>
    <v-list-item>
      <v-list-item-content>
        <v-list-item-title>
          <b>DEVICE NAME</b>
        </v-list-item-title>
        <v-list-item-subtitle>
          Lokalizator adam
        </v-list-item-subtitle>

        <v-list-item-title>
          <b>DEVICE ID</b>
        </v-list-item-title>
        <v-list-item-subtitle>
          1234456662
        </v-list-item-subtitle>

        <v-list-item-title>
          <b>DEVICE STATUS</b>
        </v-list-item-title>
        <v-list-item-subtitle>
          CONNECTED
        </v-list-item-subtitle>
      </v-list-item-content>
      <v-btn elevation="2" outlined color="accent">Delete</v-btn>
    </v-list-item>
  </v-list>
</template>

<script lang="ts">
import { Component, Prop, Vue } from "vue-property-decorator";

@Component
export default class DeviceList extends Vue {
  @Prop(String) minWidth!: string;
}
</script>
