<template>
  <v-container class="green container-width">
    {{ deviceId }}
  </v-container>
</template>

<script lang="ts">
import { Component, Prop, Vue } from "vue-property-decorator";

@Component
export default class DevicesLast extends Vue {
  @Prop() deviceId!: string;
}
</script>
