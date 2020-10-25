<template>
  <v-container class="mb-15">
    <Welcome></Welcome>
    <v-card class="background d-flex flex-column" flat tile>
      <div class="text-h2 pa-10 mb-10">Try it out!</div>

      <VcardSubtitle
        subtitle="Create account"
        img-path="account.svg"
      ></VcardSubtitle>

      <VcardSubtitle
        class="justify-end"
        subtitle="Add device"
        img-path="device.svg"
      ></VcardSubtitle>

      <VcardSubtitle
        subtitle="Check connection"
        img-path="check.svg"
      ></VcardSubtitle>

      <VcardSubtitle
        class="justify-end"
        subtitle="All set!"
        img-path="done.svg"
      ></VcardSubtitle>
    </v-card>
  </v-container>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import Welcome from "@/components/Welcome.vue";
import VcardSubtitle from "@/components/VcardSubtitle.vue";

@Component({
  components: {
    Welcome,
    VcardSubtitle
  }
})
export default class Home extends Vue {}
</script>
