<template>
  <v-container>
    <DeviceAccess></DeviceAccess>
    Hello from devices
  </v-container>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import DeviceAccess from "@/components/DeviceAccess.vue";

@Component({
  components: {
    DeviceAccess
  }
})
export default class Devices extends Vue {}
</script>
