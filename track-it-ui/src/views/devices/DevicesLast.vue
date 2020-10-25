<template>
  <div class="background fill-height d-flex flex-row">
    <device-submenu></device-submenu>
    <v-card class="flat flex-grow-1 red rounded-0">Hello Devices Last</v-card>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import DeviceSubmenu from "@/components/devices/DeviceSubmenu.vue";

@Component({
  components: {
    DeviceSubmenu
  }
})
export default class DevicesLast extends Vue {}
</script>
