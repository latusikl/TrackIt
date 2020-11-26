<template>
  <v-container class="container-width">
    <v-stepper v-model="stepNumber">
      <v-stepper-header>
        <v-stepper-step :complete="stepNumber > 1" step="1">
          Choose start timestamp
        </v-stepper-step>

        <v-divider></v-divider>

        <v-stepper-step
          :complete="stepNumber > 2"
          step="2"
          :rules="[startEndDateRule()]"
        >
          Choose end timestamp
          <small v-if="isStartBiggerThanEnd()"
            >Start date is bigger than end date</small
          >
        </v-stepper-step>

        <v-divider></v-divider>

        <v-stepper-step :complete="stepNumber > 3" step="3">
          Choose track
        </v-stepper-step>
      </v-stepper-header>

      <v-stepper-items>
        <v-stepper-content step="1">
          <v-card class="mb-10" color="secondary">
            <v-container>
              <v-row>
                <v-col>
                  <v-row class="justify-center text-h6">
                    Choose date
                  </v-row>
                </v-col>
                <v-col>
                  <v-row class="justify-center text-h6">
                    Choose time
                  </v-row>
                </v-col>
              </v-row>
              <v-divider></v-divider>
              <v-row align="center" justify="center">
                <v-col>
                  <v-row justify="center">
                    <v-date-picker v-model="startDatePicker"></v-date-picker>
                  </v-row>
                </v-col>
                <v-col>
                  <v-row justify="center">
                    <v-time-picker
                      format="24hr"
                      v-model="startTime"
                    ></v-time-picker>
                  </v-row>
                </v-col>
              </v-row>
            </v-container>
          </v-card>

          <v-btn text @click="cancelStepper()">
            Cancel
          </v-btn>
          <v-btn color="primary" @click="stepNumber = 2" class="float-right">
            Next
          </v-btn>
        </v-stepper-content>

        <v-stepper-content step="2">
          <v-card class="mb-10" color="secondary">
            <v-container>
              <v-row>
                <v-col>
                  <v-row class="justify-center text-h6">
                    Choose date
                  </v-row>
                </v-col>
                <v-col>
                  <v-row class="justify-center text-h6">
                    Choose time
                  </v-row>
                </v-col>
              </v-row>
              <v-divider></v-divider>
              <v-row align="center" justify="center">
                <v-col>
                  <v-row justify="center">
                    <v-date-picker v-model="endDatePicker"></v-date-picker>
                  </v-row>
                </v-col>
                <v-col>
                  <v-row justify="center">
                    <v-time-picker
                      format="24hr"
                      v-model="endTime"
                    ></v-time-picker>
                  </v-row>
                </v-col>
              </v-row>
            </v-container>
          </v-card>
          <v-btn text @click="stepNumber = 1">
            Back
          </v-btn>

          <v-btn text @click="cancelStepper()">
            Cancel
          </v-btn>
          <v-btn
            :disabled="isStartBiggerThanEnd()"
            color="primary"
            @click="handleStartThirdStep"
            class="float-right"
          >
            Next
          </v-btn>
        </v-stepper-content>

        <v-stepper-content step="3">
          <v-card class="mb-10" color="secondary" v-if="isThirdContentVisible">
            <device-tracks
              v-on:track-chosen="emitRange"
              :key="trackKey"
              :device-id="deviceId"
              :start-range-date="startDatePicker"
              :start-range-time="startTime"
              :end-range-date="endDatePicker"
              :end-range-time="endTime"
            ></device-tracks>
          </v-card>
          <v-btn text @click="handleBackFromThird()">
            Back
          </v-btn>
          <v-btn text @click="cancelStepper()">
            Cancel
          </v-btn>
        </v-stepper-content>
      </v-stepper-items>
    </v-stepper>
  </v-container>
</template>

<script lang="ts">
import { Component, Emit, Prop, Vue } from "vue-property-decorator";
import DeviceTracks from "@/components/location/DeviceTracks.vue";
import timeService from "@/sevices/TimeService";

@Component({
  components: { DeviceTracks }
})
export default class DeviceRangeStepper extends Vue {
  @Prop({ default: "" })
  readonly deviceId!: string;

  private startDatePicker = timeService.currentDate();
  private startTime = timeService.currentUserTime();
  private endDatePicker = timeService.currentDate();
  private endTime = timeService.currentUserTimePlusMinute();

  private isThirdContentVisible = false;
  private stepNumber = 1;
  private trackKey = 0;

  private handleStartThirdStep() {
    this.isThirdContentVisible = true;
    this.stepNumber = 3;
    this.trackKey++;
  }

  private handleBackFromThird() {
    this.isThirdContentVisible = false;
    this.stepNumber = 2;
  }

  @Emit("range-chosen")
  private emitRange(rangeValue: string) {
    console.debug("Emitted range-chosen" + rangeValue);
  }

  private cancelStepper() {
    this.isThirdContentVisible = false;
    this.endTime = timeService.currentUserTimePlusMinute();
    this.startTime = timeService.currentUserTime();
    this.startDatePicker = timeService.currentDate();
    this.endDatePicker = timeService.currentDate();
    this.stepNumber = 1;
  }

  private startEndDateRule(): (value: unknown) => boolean | string {
    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    return value => !this.isStartBiggerThanEnd();
  }

  private isStartBiggerThanEnd(): boolean {
    return timeService.isFirstDateTimeBiggerOrEqual(
      this.startDatePicker,
      this.startTime,
      this.endDatePicker,
      this.endTime
    );
  }
}
</script>
