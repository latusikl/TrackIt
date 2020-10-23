<template>
  <v-card class="background d-flex flex-row justify-space-between" flat tile>
    <div class="pa-10">
      <div class="text-h2 m-b-5">Welcome to TrackIt!</div>
      <div class="text-h6 pl-2">We make tracking as easy as pie</div>
      <div class="text-h5 pl-2 pt-5">
        <br />
        Easy to use, reliable, user friendly - that's what defines us.
        <br />
        Try it out with our app or locating device.
      </div>
    </div>
    <div class="pa-10">
      <v-img max-height="280" max-width="350" src="@/assets/map.svg"></v-img>
    </div>
  </v-card>
</template>

<script lang="ts">
import { Vue, Component } from "vue-property-decorator";

@Component
export default class Welcome extends Vue {}
</script>
