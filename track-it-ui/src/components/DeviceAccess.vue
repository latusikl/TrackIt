<template>
  <v-card>
    <div class="text-h5">Add new device</div>

    <!--    <label class="text-h5" for="deviceId">Add new device</label>-->
    <!--    <br />-->
    <!--    <label for="deviceId">{{ deviceId }}</label>-->
    <!--    <br />-->
    <!--    <input id="deviceId" type="text" v-model="deviceId" />-->
    <!--    <button @click="activateDevice">Activate</button>-->
  </v-card>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import DeviceAccessService from "@/sevices/DeviceAccessService";

@Component
export default class DeviceAccess extends Vue {
  deviceId = "";

  activateDevice() {
    console.log("Activate triggered");
    DeviceAccessService.activate(this.deviceId).then(response => {
      console.log(response.data);
      console.log(response.status);
      console.log(response.statusText);
    });
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss"></style>
