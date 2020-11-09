<template>
  <v-card min-width="70%">
    <alert
      alert-type="warning"
      :message="message"
      :is-visible="isAlertVisible"
      @invisible-event="makeAlertInvisible"
    ></alert>
    <alert
      alert-type="info"
      message="Your account was removed. After closing that info you will be redirected to home page."
      :is-visible="isDeleteAlertVisible"
      @invisible-event="finishRemoval"
    ></alert>
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
              <v-text-field disabled outlined :value="email"></v-text-field>
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
              <v-col>
                <v-card-title>Delete Your account</v-card-title>
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
                ></v-switch>
              </v-col>
            </v-row>
          </v-container>

          <v-btn
            block
            outlined
            color="accent"
            class="pa-5 mb-2"
            :disabled="!isDangerEnabled"
            @click="removeAccount"
            >delete account
          </v-btn>
        </v-card>
      </v-tab-item>
    </v-tabs>
  </v-card>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import PasswordChange from "@/components/home/PasswordChange.vue";
import { UserModel } from "@/dto/UserModel";
import { namespace } from "vuex-class";
import Alert from "@/components/Alert.vue";
import UserService from "@/sevices/UserService";
import DeviceService from "@/sevices/DeviceService";

const Authentication = namespace("Authentication");
@Component({
  components: { Alert, PasswordChange }
})
export default class UserMenu extends Vue {
  private isAlertVisible = false;
  private email = "";
  private createdAt = "";
  private totalDevices = 0;
  private isDangerEnabled = false;
  private isDeleteAlertVisible = false;
  private message = "";

  @Authentication.Getter
  private isLogged!: boolean;

  @Authentication.Getter
  private user!: UserModel;

  @Authentication.Action
  private signOut!: () => void;

  mounted() {
    if (this.isLogged) {
      this.loadUserData();
      this.loadNumberOfDevices();
    } else {
      this.isAlertVisible = true;
    }
    return;
  }

  private makeAlertInvisible() {
    this.isAlertVisible = false;
  }

  private loadUserData() {
    UserService.getUserData()
      .then(response => {
        this.email = response.data.email;
        this.createdAt = response.data.accountCreation;
      })
      .catch(reason => {
        console.debug(reason);
        this.isAlertVisible = true;
      });
  }

  private loadNumberOfDevices() {
    DeviceService.getDeviceCount()
      .then(response => {
        this.totalDevices = response.data;
      })
      .catch(reason => {
        console.debug(reason);
        this.makeAlertInvisible();
        this.isAlertVisible = true;
        this.message = "Unable to retrieve data from server";
      });
  }

  private removeAccount() {
    UserService.deleteAccount()
      .then(() => (this.isDeleteAlertVisible = true))
      .catch(reason => {
        console.debug(reason);
        this.makeAlertInvisible();
        this.isAlertVisible = true;
        this.message = "Internal server error occurred";
      });
  }

  private finishRemoval() {
    this.signOut();
    this.$router.push("/");
  }
}
</script>
