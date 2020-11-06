<template>
  <div class="background fill-height d-flex flex-row">
    <device-submenu></device-submenu>
    <div class="scroll-container">
      <v-container>
        <v-row>
          <vcard-subtitle
            class="justify-start"
            img-path="manager.svg"
            subtitle="Manage your devices"
          ></vcard-subtitle>
        </v-row>
        <v-row>
          <v-divider class="mb-5"></v-divider>
        </v-row>
        <v-row justify="center">
          <device-access></device-access>
        </v-row>
        <v-row>
          <v-divider class="mb-5 mt-5"></v-divider>
        </v-row>

        <v-row justify="center">
          <device-list class="device-list" min-width="70%"></device-list>
        </v-row>
      </v-container>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import DeviceAccess from "@/components/devices/DeviceAccess.vue";
import DeviceSubmenu from "@/components/devices/DeviceSubmenu.vue";
import VcardSubtitle from "@/components/home/VcardSubtitle.vue";
import DeviceList from "@/components/devices/DeviceList.vue";

@Component({
  components: {
    DeviceAccess,
    DeviceSubmenu,
    DeviceList,
    VcardSubtitle
  }
})
export default class DevicesManager extends Vue {}
</script>

<style scoped lang="scss">
.scroll-container {
  width: 100%;
  max-height: 100vh;
  overflow-y: scroll;
  padding-bottom: 10px;
}
</style>
