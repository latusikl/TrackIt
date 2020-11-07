<template>
  <v-container class="container mb-15">
    <v-row>
      <VcardSubtitle
        subtitle="Your account"
        img-path="account.svg"
      ></VcardSubtitle>
    </v-row>
    <v-divider></v-divider>
    <v-row class="justify-center mt-10">
      <user-menu></user-menu>
    </v-row>
  </v-container>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import Welcome from "@/components/home/Welcome.vue";
import VcardSubtitle from "@/components/home/VcardSubtitle.vue";
import UserMenu from "@/components/home/UserMenu.vue";

@Component({
  components: {
    UserMenu,
    Welcome,
    VcardSubtitle
  }
})
export default class Account extends Vue {}
</script>

<style scoped type="scss">
.container {
  max-width: 80%;
}
</style>
