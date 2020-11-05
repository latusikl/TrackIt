<template>
  <v-container class=" container-width">
    <alert
      message="Unable to fetch logs for device."
      alert-type="error"
      :is-visible="isErrorVisible"
      @invisible-event="makeAlertInvisible"
    ></alert>
    <v-simple-table style="width: 100%" v-if="isTableVisible">
      <template v-slot:default>
        <thead>
          <tr>
            <th class="text-center">
              Server time
            </th>
            <th class="text-center">
              Level
            </th>
            <th class="text-center">
              Message
            </th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="item in deviceInfoList" :key="item.serverDateTime">
            <td class="text-center">{{ item.serverDateTime }}</td>
            <td class="text-center" :style="getColor(item.infoLevel)">
              {{ item.infoLevel }}
            </td>
            <td class="text-center">
              {{ item.message }}
            </td>
          </tr>
        </tbody>
      </template>
    </v-simple-table>
  </v-container>
</template>

<script lang="ts">
import { Component, Prop, Vue, Watch } from "vue-property-decorator";
import DeviceAccessService from "@/sevices/DeviceAccessService";
import { DeviceInfoDto } from "@/dto/DeviceInfoDto";
import Alert from "@/components/Alert.vue";
import { InfoLevel } from "@/dto/InfoLevel";
@Component({
  components: { Alert }
})
export default class DevicesLast extends Vue {
  @Prop({ default: "" }) deviceId!: string;
  private currentPage = 1;
  private lastPage: number;
  private pageRequested = 1;
  private pageSize = 15;
  private deviceInfoList: Array<DeviceInfoDto>;
  private isTableVisible = false;
  private isErrorVisible = false;

  getData() {
    DeviceAccessService.getDeviceInfo(
      this.deviceId,
      this.pageRequested - 1,
      this.pageSize
    )
      .then(response => {
        console.log(response);
        this.deviceInfoList = response.data.content;
        this.currentPage = response.data.number;
        this.lastPage = response.data.totalPages;
        this.isTableVisible = true;
      })
      .catch(reason => {
        console.log(reason);
        this.isErrorVisible = true;
      });
  }

  @Watch("deviceId")
  private onDeviceChange(deviceId: string) {
    this.deviceId = deviceId;
    this.getData();
  }

  private getColor(infoLevel: InfoLevel): string {
    switch (infoLevel) {
      case InfoLevel.ERROR:
        return "color: red;";
      case InfoLevel.WARN:
        return "color: orange;";
      case InfoLevel.INFO:
        return "color: green;";
    }
  }

  private makeAlertInvisible() {
    this.isErrorVisible = false;
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
