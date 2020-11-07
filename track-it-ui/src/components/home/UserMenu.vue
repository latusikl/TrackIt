<template>
  <v-card min-width="70%">
    <v-toolbar flat color="primary" dark>
      <v-toolbar-title>Manage your account</v-toolbar-title>
    </v-toolbar>
    <v-tabs vertical>
      <v-tab>
        <v-icon left>
          mdi-account
        </v-icon>
        Account data
      </v-tab>
      <v-tab>
        <v-icon left>
          mdi-lock
        </v-icon>
        Change password
      </v-tab>
      <v-tab>
        <v-icon left>
          mdi-alert-octagon
        </v-icon>
        Danger zone
      </v-tab>

      <v-tab-item>
        <v-card flat>
          <v-card-text>
            <div>
              <div class="text-h6">E-mail address</div>
              <v-text-field disabled outlined :value="email"> </v-text-field>
            </div>
            <div>
              <div class="text-h6">Created at</div>
              <v-text-field disabled outlined :value="createdAt">
              </v-text-field>
            </div>
            <div>
              <div class="text-h6">
                Number of devices in system
              </div>
              <v-text-field disabled outlined :value="totalDevices">
              </v-text-field>
            </div>
          </v-card-text>
        </v-card>
      </v-tab-item>
      <v-tab-item>
        <v-card flat>
          <password-change></password-change>
        </v-card>
      </v-tab-item>
      <v-tab-item>
        <v-card flat v-bind:class="{ secondary: !isDangerEnabled }">
          <v-container>
            <v-row>
              <v-col
                ><v-card-title>Delete Your account</v-card-title>
                <v-card-text
                  ><p>
                    Deleting account will remove all saved devices and devices
                    data from server. After deletion you will lose access to
                    account and rest of our services
                  </p></v-card-text
                >
              </v-col>
              <v-col>
                <v-switch
                  class="float-right"
                  v-model="isDangerEnabled"
                  :label="isDangerEnabled ? 'Disable' : 'Enable'"
                ></v-switch
              ></v-col>
            </v-row>
          </v-container>

          <v-btn
            block
            outlined
            color="accent"
            class="pa-5 mb-2"
            :disabled="!isDangerEnabled"
            >delete account</v-btn
          >
        </v-card>
      </v-tab-item>
    </v-tabs>
  </v-card>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import PasswordChange from "@/components/home/PasswordChange.vue";
@Component({
  components: { PasswordChange }
})
export default class UserMenu extends Vue {
  private email = "email@email.com";
  private createdAt = "2020-11-05T15:48:00";
  private totalDevices = 10;
  private isDangerEnabled = false;
}
</script>
