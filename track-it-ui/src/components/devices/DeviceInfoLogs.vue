<template>
  <v-container class=" container-width">
    <alert
      :message="message"
      :alert-type="alertType"
      :is-visible="isAlertVisible"
      @invisible-event="makeAlertInvisible"
    ></alert>
    <div v-if="isTableVisible">
      <v-simple-table style="width: 100%">
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
          <tr v-for="(item, index) in deviceInfoList" v-bind:key="index">
            <td class="text-center">{{ item.serverDateTime }}</td>
            <td class="text-center" :style="getColor(item.infoLevel)">
              {{ item.infoLevel }}
            </td>
            <td class="text-center">
              {{ item.message }}
            </td>
          </tr>
        </tbody>
      </v-simple-table>
      <div class="text-center">
        <v-pagination
          v-model="currentPage"
          circle
          v-on:input="changePage()"
          :length="lastPage"
        ></v-pagination>
      </div>
    </div>
  </v-container>
</template>

<script lang="ts">
import { Component, Prop, Vue, Watch } from "vue-property-decorator";
import DeviceAccessService from "@/sevices/DeviceService";
import { DeviceInfoDto } from "@/dto/DeviceInfoDto";
import Alert from "@/components/Alert.vue";
import { InfoLevel } from "@/dto/InfoLevel";

@Component({
  components: { Alert }
})
export default class DevicesLast extends Vue {
  @Prop({ default: "" }) deviceId!: string;
  private currentPage = 1;
  private lastPage: number | undefined;
  private pageSize = 15;
  private deviceInfoList: Array<DeviceInfoDto> | undefined;
  private isTableVisible = false;
  private isAlertVisible = false;
  private alertType = "";
  private message = "";

  @Watch("deviceId")
  private onDeviceChange(deviceId: string) {
    this.deviceId = deviceId;
    this.isTableVisible = false;
    this.makeAlertInvisible();
    this.getData();
  }

  getData() {
    DeviceAccessService.getDeviceInfo(
      this.deviceId,
      this.currentPage - 1,
      this.pageSize
    )
      .then(response => {
        console.debug(response);
        this.deviceInfoList = response.data.content;
        this.currentPage = response.data.number + 1;
        this.lastPage = response.data.totalPages;
        if (response.data.content.length === 0) {
          this.showInfoNoLogs();
          this.isTableVisible = false;
        } else {
          this.isTableVisible = true;
        }
      })
      .catch(reason => {
        console.debug(reason);
        this.showError();
      });
  }

  private changePage() {
    this.isTableVisible = false;
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
    this.isAlertVisible = false;
  }

  private showError() {
    this.makeAlertInvisible();
    this.alertType = "error";
    this.message = "Unable to fetch logs for device.";
    this.isAlertVisible = true;
  }

  private showInfoNoLogs() {
    this.makeAlertInvisible();
    this.alertType = "info";
    this.message = "No logs found for given device.";
    this.isAlertVisible = true;
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
