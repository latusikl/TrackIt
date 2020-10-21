<template>
  <div class="about">
    <DeviceAccess></DeviceAccess>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import DeviceAccess from "@/components/DeviceAccess.vue";

@Component({
  components: {
    DeviceAccess
  }
})
export default class Home extends Vue {}
</script>
