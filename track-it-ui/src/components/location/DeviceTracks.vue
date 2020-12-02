<template>
  <v-container>
    <v-row class="mb-5 justify-center">
      <v-card class="secondary text-h6 pa-2">
        {{ pageStartRange + " - " + pageEndRange }}
      </v-card>
    </v-row>
    <v-row class="justify-center">
      <v-simple-table
        v-if="tableVisible"
        class="scroll-container"
        style="width: 90%"
      >
        <thead>
          <tr>
            <th class="text-center">
              Range start
            </th>
            <th class="text-center">
              Range end
            </th>
            <th class="text-center">
              Show
            </th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="item in trackDtos" :key="item.name">
            <td class="text-center">{{ beautifyDateTime(item.start) }}</td>
            <td class="text-center">
              {{ beautifyDateTime(item.end) }}
            </td>
            <td>
              <v-btn
                class="ma-2"
                color="primary"
                @click="emitRange(item.start, item.end)"
              >
                Show
              </v-btn>
            </td>
          </tr>
        </tbody>
      </v-simple-table>
    </v-row>
    <v-row class="justify-center mt-5">
      <v-pagination
        v-model="currentPage"
        circle
        v-on:input="loadPage()"
        :length="lastPage"
      ></v-pagination>
    </v-row>
  </v-container>
</template>

<script lang="ts">
import { Component, Emit, Prop, Vue } from "vue-property-decorator";
import { TrackDto } from "@/dto/TrackDto";
import timeService from "@/sevices/TimeService";
import locationService from "@/sevices/LocationService";

@Component
export default class DeviceTracks extends Vue {
  @Prop({ default: "" })
  readonly deviceId!: string;
  @Prop({ default: "" })
  readonly startRangeDate!: string;
  @Prop({ default: "" })
  readonly startRangeTime!: string;
  @Prop({ default: "" })
  readonly endRangeDate!: string;
  @Prop({ default: "" })
  readonly endRangeTime!: string;

  private static HOURS_PER_PAGE = 2;

  private currentPage = 1;
  private lastPage = 1;

  private tableVisible = false;

  private trackDtos: TrackDto[] | undefined;
  private pageStartRange: string | undefined;
  private pageEndRange: string | undefined;

  @Emit("track-chosen")
  private emitRange(rangeStart: string, rangeEnd: string) {
    return rangeStart + "_" + rangeEnd;
  }

  created() {
    console.log("Created");
    console.log(this.startRangeTime + " " + this.startRangeDate);
    console.log(this.endRangeTime + " " + this.endRangeDate);
    this.currentPage = 1;
    this.evaluateAmountOfPages();
    this.loadPage();
  }

  private evaluateAmountOfPages() {
    const amountOfHours = timeService.getHoursInRange(
      this.startRangeDate,
      this.startRangeTime,
      this.endRangeDate,
      this.endRangeTime
    );
    this.lastPage = Math.ceil(amountOfHours / DeviceTracks.HOURS_PER_PAGE);
  }

  private loadPage() {
    this.tableVisible = false;
    const hoursToAddStart = (this.currentPage - 1) * 2;
    const hoursToAddEnd = hoursToAddStart + 2;
    const pageStartRangeTime = timeService.withAddedHours(
      this.startRangeDate,
      this.startRangeTime,
      hoursToAddStart
    );

    let pageEndRangeTime = timeService.withAddedHours(
      this.startRangeDate,
      this.startRangeTime,
      hoursToAddEnd
    );
    const endOfRange = timeService.getDate(
      this.endRangeDate,
      this.endRangeTime
    );

    if (new Date(pageEndRangeTime) > endOfRange) {
      pageEndRangeTime = timeService.convertToDateTimeIsoStringAsInUtc(
        this.endRangeDate,
        this.endRangeTime
      );
    }
    this.pageStartRange = this.beautifyDateTime(
      pageStartRangeTime.substr(0, 16)
    );
    this.pageEndRange = this.beautifyDateTime(pageEndRangeTime.substr(0, 16));
    console.debug(this.pageStartRange + " " + this.pageEndRange);
    this.fetchDataForPage(pageStartRangeTime, pageEndRangeTime);
  }

  private fetchDataForPage(pageRangeStart: string, pageRangeEnd: string) {
    locationService
      .getTracksForPage(this.deviceId, pageRangeStart, pageRangeEnd)
      .then(response => {
        this.trackDtos = response.data;
        this.tableVisible = true;
        console.log(this.trackDtos);
      })
      .catch(reason => {
        console.debug(reason);
        //TODO Add dialog window
      });
  }

  private beautifyDateTime(dateTime: string): string {
    return dateTime
      .split("T")
      .reverse()
      .join(" ");
  }
}
</script>

<style scoped lang="scss">
tbody {
  tr:hover {
    background-color: transparent !important;
    width: 100%;
  }
}
.scroll-container {
  width: 100%;
  max-height: 400px;
  overflow-y: scroll;
  padding-bottom: 10px;
}
</style>
