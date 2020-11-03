<template>
  <v-simple-table>
    <thead>
      <tr>
        <th class="text-left">
          Date and time
        </th>
        <th class="text-left">
          Approximate location
        </th>
        <th class="text-left">
          Decimal degrees
        </th>
      </tr>
    </thead>
    <tbody>
      <tr>
        <td>{{ dateTime }}</td>
        <td>
          {{ getFormattedLatitude() + " " + getFormattedLongitude() }}
        </td>
        <td>{{ this.latitude + " " + this.longitude }}</td>
      </tr>
    </tbody>
  </v-simple-table>
</template>

<script lang="ts">
import { Component, Prop, Vue } from "vue-property-decorator";
import DecimalDegreesConverter from "@/sevices/DecimalDegreesConverter";

@Component
export default class DevicesLast extends Vue {
  @Prop()
  longitude!: string;
  @Prop()
  latitude!: string;
  @Prop()
  dateTime!: string;

  private getFormattedLatitude() {
    return DecimalDegreesConverter.convertLatitude(this.latitude);
  }

  private getFormattedLongitude() {
    return DecimalDegreesConverter.convertLongitude(this.longitude);
  }
}
</script>

<style scoped lang="scss">
tbody {
  tr:hover {
    background-color: transparent !important;
  }
}
</style>
