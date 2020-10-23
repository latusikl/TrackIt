<template>
  <v-container>
    <Welcome></Welcome>
    <v-card class="background d-flex flex-column" flat tile>
      <div class="text-h2 pa-10 mb-10">Try it out!</div>
      <div class="d-flex flex-row justify-start pl-15">
        <div class="text-h4 text-center pt-15 pr-5">
          Create account
        </div>
        <v-img
          max-height="200"
          max-width="150"
          src="@/assets/account.svg"
        ></v-img>
      </div>
      <div class="d-flex flex-row justify-end pr-15">
        <div class="text-h4 text-center pt-15 pr-5">
          Add device
        </div>
        <v-img
          max-height="200"
          max-width="150"
          src="@/assets/device.svg"
        ></v-img>
      </div>
      <div class=" d-flex flex-row justify-start pl-15">
        <div class="text-h4 text-center pt-15 pr-5">
          Check connection
        </div>
        <v-img
          max-height="150"
          max-width="200"
          src="@/assets/check.svg"
        ></v-img>
      </div>
      <div class="d-flex flex-row justify-end pr-15">
        <div class="text-h4 text-center pt-15 pr-5">
          All set!
        </div>
        <v-img max-height="150" max-width="200" src="@/assets/done.svg"></v-img>
      </div>
    </v-card>
  </v-container>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import Welcome from "@/components/Welcome.vue";

@Component({
  components: {
    Welcome
  }
})
export default class Home extends Vue {}
</script>
