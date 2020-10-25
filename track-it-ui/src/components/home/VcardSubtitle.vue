<template>
  <div class="d-flex flex-row justify-start pl-15">
    <div class="text-h4 text-center pt-15 pr-5">
      {{ subtitle }}
    </div>
    <v-img
      max-height="200"
      max-width="150"
      v-bind:src="require('../../assets/' + imgPath)"
    ></v-img>
  </div>
</template>

<script lang="ts">
import { Component, Prop, Vue } from "vue-property-decorator";

@Component
export default class VcardSubtitle extends Vue {
  @Prop(String) subtitle!: string;
  @Prop(String) imgPath!: string;
}
</script>
