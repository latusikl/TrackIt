<template>
  <v-container>
    <alert
      :message="alertMessage"
      :is-visible="isAlertVisible"
      :alert-type="alertType"
      @invisible-event="makeAlertInvisible"
    ></alert>
    <v-row justify="center">
      <v-col cols="12" md="4">
        <v-form ref="form" v-model="passwordChangeForm.valid">
          <v-text-field
            label="New password"
            v-model="passwordChangeForm.fields.password"
            :rules="passwordChangeForm.rules.password"
            :append-icon="passwordVisible ? 'mdi-eye' : 'mdi-eye-off'"
            @click:append="() => (passwordVisible = !passwordVisible)"
            :type="passwordVisible ? 'text' : 'password'"
          ></v-text-field>
          <v-text-field
            label="Confirm password"
            v-model="passwordChangeForm.fields.confirmPassword"
            :rules="[
              this.passwordChangeForm.fields.password ===
                this.passwordChangeForm.fields.confirmPassword ||
                'Passwords must match'
            ]"
            :append-icon="passwordVisible ? 'mdi-eye' : 'mdi-eye-off'"
            @click:append="() => (passwordVisible = !passwordVisible)"
            :type="passwordVisible ? 'text' : 'password'"
          ></v-text-field>
        </v-form>
      </v-col>
    </v-row>

    <v-row justify="center" class="pa-5">
      <v-btn
        elevation="2"
        outlined
        color="accent"
        @click="changePassword()"
        :disabled="!passwordChangeForm.valid"
      >
        change
      </v-btn>
    </v-row>
  </v-container>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import Alert from "@/components/Alert.vue";
import VcardSubtitle from "@/components/home/VcardSubtitle.vue";
import { minLength, requiredField } from "@/scripts/forms/FormValidators";
import { PasswordChangeFrom } from "@/scripts/forms/PasswordChangeFrom";
import UserService from "@/sevices/UserService";
import { namespace } from "vuex-class";
const Authentication = namespace("Authentication");
@Component({
  components: { VcardSubtitle, Alert }
})
export default class SignIn extends Vue {
  private passwordVisible = false;
  private alertType = "info";
  private alertMessage = "";
  private isAlertVisible = false;

  @Authentication.Action
  private reLogin!: () => void;

  passwordChangeForm: PasswordChangeFrom = {
    valid: false,
    fields: {
      password: "",
      confirmPassword: ""
    },
    rules: {
      password: [
        requiredField("Password is required."),
        minLength("Password is to short", 8)
      ]
    }
  };

  private changePassword() {
    UserService.changePassword(this.passwordChangeForm.fields.password)
      .then(() => this.showSuccess())
      .catch(() => this.showError());
  }

  private makeAlertInvisible() {
    this.alertMessage = "";
    this.alertType = "";
    this.isAlertVisible = false;
  }

  private showError() {
    this.makeAlertInvisible();
    this.alertMessage =
      "Internal server error occurred. Password has not been changed. Close alert to login again.";
    this.alertType = "error";
    this.isAlertVisible = true;
  }

  private showSuccess() {
    this.makeAlertInvisible();
    this.alertMessage = "Password changed successfully";
    this.alertType = "success";
    this.isAlertVisible = true;
  }
}
</script>
