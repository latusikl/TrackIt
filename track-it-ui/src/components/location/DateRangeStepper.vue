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

          <v-btn color="primary" @click="stepNumber = 2">
            Next
          </v-btn>
          <v-btn text @click="cancelStepper()" class="float-right">
            Cancel
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
          <v-btn
            :disabled="isStartBiggerThanEnd()"
            color="primary"
            @click="emitRange()"
          >
            Get data
          </v-btn>

          <v-btn text @click="cancelStepper()" class="float-right">
            Cancel
          </v-btn>
        </v-stepper-content>
      </v-stepper-items>
    </v-stepper>
  </v-container>
</template>

<script lang="ts">
import { Component, Emit, Vue } from "vue-property-decorator";

@Component
export default class DeviceRangeStepper extends Vue {
  private stepNumber = 1;
  private startDatePicker = this.getCurrentDate();
  private startTime = this.getCurrentTime();
  private endDatePicker = this.getCurrentDate();
  private endTime = this.getCurrentTime();

  @Emit("range-chosen")
  private emitRange() {
    return (
      this.convertToDateTimeIsoString(this.startDatePicker, this.startTime) +
      "_" +
      this.convertToDateTimeIsoString(this.endDatePicker, this.endTime)
    );
  }

  private getCurrentDate(): string {
    return new Date().toISOString().substr(0, 10);
  }

  private getCurrentTime(): string {
    return new Date().toISOString().substr(11, 5);
  }

  private cancelStepper() {
    this.endTime = this.getCurrentTime();
    this.startTime = this.getCurrentTime();
    this.startDatePicker = this.getCurrentDate();
    this.endDatePicker = this.getCurrentDate();
    this.stepNumber = 1;
  }

  private convertToDateTimeIsoString(
    dateString: string,
    timeString: string
  ): string {
    return dateString + "T" + timeString + ":00";
  }

  private startEndDateRule(): (value: unknown) => boolean | string {
    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    return value => !this.isStartBiggerThanEnd();
  }

  private isStartBiggerThanEnd(): boolean {
    const startDate = new Date(
      this.convertToDateTimeIsoString(this.startDatePicker, this.startTime)
    );
    const endDate = new Date(
      this.convertToDateTimeIsoString(this.endDatePicker, this.endTime)
    );
    return startDate > endDate;
  }
}
</script>
