<template>
  <v-list color="secondary" outlined rounded :min-width="minWidth">
    <div class="text-h5 pt-5 pb-5">Tracker server</div>
    <div>
      <v-divider></v-divider>
      <v-simple-table class="secondary">
        <thead>
          <tr>
            <th class="text-left">
              Server IP
            </th>
            <th class="text-left">
              Server port
            </th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>
              {{ serverIp }}
            </td>
            <td>{{ serverPort }}</td>
          </tr>
        </tbody>
      </v-simple-table>
      <v-divider></v-divider>
    </div>
  </v-list>
</template>

<script lang="ts">
import { Component, Prop, Vue } from "vue-property-decorator";
import EnvInfo from "@/env-info";

@Component({})
export default class DeviceServerInfo extends Vue {
  @Prop(String) minWidth!: string;
  serverIp: string;
  serverPort: string;

  constructor() {
    super();
    this.serverIp = EnvInfo.baseServerIp;
    this.serverPort = EnvInfo.baseServerPort;
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
