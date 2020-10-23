<template>
  <v-content>
    <v-card class="background d-flex flex-row justify-space-between" flat tile>
      <div class="pa-10">
        <div class="text-h2 m-b-5">Welcome to TrackIt!</div>
        <div class="text-h6 pl-2">We make tracking as easy as pie</div>
        <div class="text-h5 pl-2 pt-5">
          <br />
          Easy to use, reliable, user friendly - that's what defines us.
          <br />
          Try it out with our app or locating device.
        </div>
      </div>
      <div class="pa-10">
        <v-img max-height="280" max-width="350" src="@/assets/map.svg"></v-img>
      </div>
    </v-card>
    <v-card class="background d-flex flex-column" flat tile>
      <div class="d-flex flex-row justify-space-between pa-10">
        <div class="text-h2 m-b-5">Try it out!</div>
        <v-img
          max-height="280"
          max-width="350"
          src="@/assets/try_it.svg"
        ></v-img>
      </div>
      <div class="d-flex flex-row justify-start pl-15">
        <div class="text-h3 text-center pt-15 pr-5">
          Create account
        </div>
        <v-img
          max-height="200"
          max-width="150"
          src="@/assets/account.svg"
        ></v-img>
      </div>
      <div class="d-flex flex-row justify-end pr-15">
        <div class="text-h3 text-center pt-15 pr-5">
          Add device
        </div>
        <v-img
          max-height="200"
          max-width="150"
          src="@/assets/device.svg"
        ></v-img>
      </div>
      <div class=" d-flex flex-row justify-start pl-15">
        <div class="text-h3 text-center pt-15 pr-5">
          Check connection
        </div>
        <v-img
          max-height="150"
          max-width="200"
          src="@/assets/check.svg"
        ></v-img>
      </div>
      <div class="d-flex flex-row justify-end pr-15">
        <div class="text-h3 text-center pt-15 pr-5">
          All set!
        </div>
        <v-img max-height="150" max-width="200" src="@/assets/done.svg"></v-img>
      </div>
    </v-card>
  </v-content>
</template>

<style>
.same-size {
  flex-grow: 1;
}
</style>
