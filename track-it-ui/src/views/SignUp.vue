<template>
  <v-container class="green background container-width">
    <alert
      :message="successMessage"
      :is-visible="isSuccessVisible"
      alert-type="success"
      @invisible-event="makeSuccessInvisible"
    ></alert>

    <alert
      :message="errorMessage"
      :is-visible="isFailedVisible"
      alert-type="error"
      @invisible-event="makeFailedInvisible"
    ></alert>
    <v-row>
      <vcard-subtitle
        img-path="sign_in.svg"
        subtitle="Sign Up"
      ></vcard-subtitle>
    </v-row>
    <v-divider class="mb-15"></v-divider>
    <v-row justify="center">
      <v-col cols="12" md="4">
        <v-form ref="form" v-model="signUpForm.valid">
          <v-text-field
            label="E-mail"
            v-model="signUpForm.fields.email"
            :rules="signUpForm.rules.email"
          ></v-text-field>
          <v-text-field
            label="Password"
            v-model="signUpForm.fields.password"
            :rules="signUpForm.rules.password"
            :append-icon="passwordVisible ? 'mdi-eye' : 'mdi-eye-off'"
            @click:append="() => (passwordVisible = !passwordVisible)"
            :type="passwordVisible ? 'text' : 'password'"
          ></v-text-field>
          <v-text-field
            label="Confirm password"
            v-model="signUpForm.fields.confirmPassword"
            :rules="[
              this.signUpForm.fields.password ===
                this.signUpForm.fields.confirmPassword || 'Passwords must match'
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
        @click="createAccount()"
        :disabled="!signUpForm.valid"
      >
        sign up
      </v-btn>
    </v-row>
  </v-container>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import Alert from "@/components/Alert.vue";
import VcardSubtitle from "@/components/home/VcardSubtitle.vue";
import { SignUpForm } from "@/scripts/forms/SignUpForm";
import {
  emailFormat,
  minLength,
  requiredField
} from "@/scripts/forms/FormValidators";
import UserService from "@/sevices/UserService";

@Component({
  components: { VcardSubtitle, Alert }
})
export default class SignIn extends Vue {
  private passwordVisible = false;
  private isSuccessVisible = false;
  private isFailedVisible = false;
  private successMessage =
    "Account was created successfully. Close this message and Sing In.";
  private errorMessage =
    "Something went wrong. Unable to create account with given credentials.";

  signUpForm: SignUpForm = {
    valid: false,
    fields: {
      email: "",
      password: "",
      confirmPassword: ""
    },
    rules: {
      email: [
        requiredField("E-mail is required."),
        emailFormat("Provided value is not valid e-mail.")
      ],
      password: [
        requiredField("Password is required."),
        minLength("Password is to short", 8)
      ]
    }
  };

  private createAccount() {
    UserService.createAccount({
      userEmail: this.signUpForm.fields.email,
      password: this.signUpForm.fields.password
    })
      .then(() => {
        this.makeFailedInvisible();
        this.successVisible();
      })
      .catch(reason => {
        console.debug(reason);
        this.errorVisible();
      });
  }

  private successVisible() {
    this.isSuccessVisible = true;
  }

  private errorVisible() {
    this.isFailedVisible = true;
  }

  private makeSuccessInvisible() {
    this.$router.push("/sign-in");
    this.isSuccessVisible = false;
  }

  private makeFailedInvisible() {
    this.isFailedVisible = false;
  }
}
</script>
