<template>
  <div class="background fill-height d-flex flex-row">
    <device-submenu></device-submenu>
    <v-card class="flat flex-grow-1 red rounded-0">Hello Device Manager</v-card>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import DeviceAccess from "@/components/devices/DeviceAccess.vue";
import DeviceSubmenu from "@/components/devices/DeviceSubmenu.vue";

@Component({
  components: {
    DeviceAccess,
    DeviceSubmenu
  }
})
export default class DevicesManager extends Vue {}
</script>
