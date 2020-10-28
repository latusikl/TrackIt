<template>
  <v-alert dense :type="alertType" v-if="isVisible">
    {{ message }}
    <v-icon color="white" style="float:right" @click="emitInvisible">
      mdi-close
    </v-icon>
  </v-alert>
</template>

<script lang="ts">
import { Component, Prop, Vue } from "vue-property-decorator";

@Component
export default class SuccessAlert extends Vue {
  @Prop({ default: false }) readonly isVisible!: boolean;
  @Prop({ default: "" }) readonly message!: string;
  @Prop(String) readonly alertType!: string;

  emitInvisible() {
    this.$emit("invisible-event");
  }
}
</script>
