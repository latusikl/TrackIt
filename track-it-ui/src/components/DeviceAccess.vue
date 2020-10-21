<template>
  <div id="deviceAccess">
    <label for="deviceId">Enter device ID</label>
    <br/>
    <label for="deviceId">{{ deviceId }}</label>
    <br/>
    <input id="deviceId" type="text" v-model="deviceId">
    <button @click="activateDevice">Activate</button>
  </div>
</template>

<script lang="ts">
import {Component, Vue} from "vue-property-decorator"
import DeviceAccessService from "@/sevices/DeviceAccessService"

@Component
export default class DeviceAccess extends Vue {
  deviceId = ""

  activateDevice() {
    console.log('Activate triggered');
    DeviceAccessService.activate(this.deviceId).then(response => {
      console.log(response.data)
      console.log(response.status)
      console.log(response.statusText)
    })
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">
</style>
