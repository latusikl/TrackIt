<template>
  <v-simple-table>
    <thead>
      <tr>
        <th class="text-left">
          Range start
        </th>
        <th class="text-left">
          Range end
        </th>
      </tr>
    </thead>
    <tbody>
      <tr>
        <td>{{ startRangeDate }}</td>
        <td>
          {{ endRangeDate }}
        </td>
      </tr>
    </tbody>
  </v-simple-table>
</template>

<script lang="ts">
import { Component, Prop, Vue } from "vue-property-decorator";

@Component
export default class RangeInfoRow extends Vue {
  @Prop()
  startRangeDate!: string;
  @Prop()
  endRangeDate!: string;
}
</script>

<style scoped lang="scss">
tbody {
  tr:hover {
    background-color: transparent !important;
    width: 100%;
  }
}
</style>
