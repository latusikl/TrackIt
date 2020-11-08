<template>
  <v-container class="green background container-width">
    <alert
      message="Invalid credentials. Unable to sign in."
      :is-visible="isAlertVisible"
      alert-type="error"
      @invisible-event="makeAlertInvisible"
    ></alert>
    <v-row>
      <vcard-subtitle
        img-path="account.svg"
        subtitle="Sign In"
      ></vcard-subtitle>
    </v-row>
    <v-divider class="mb-15"></v-divider>
    <v-row justify="center">
      <v-col cols="12" md="4">
        <v-form ref="form" v-model="signInForm.valid">
          <v-text-field
            label="E-mail"
            v-model="signInForm.fields.email"
            :rules="signInForm.rules.email"
          ></v-text-field>
          <v-text-field
            label="Password"
            v-model="signInForm.fields.password"
            :rules="signInForm.rules.password"
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
        @click="logIn()"
        :disabled="!signInForm.valid && !isLogged"
        v-if="!isLoading"
      >
        Sign In
      </v-btn>
      <v-progress-circular
        v-if="isLoading"
        indeterminate
        color="primary"
      ></v-progress-circular>
    </v-row>
  </v-container>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import Alert from "@/components/Alert.vue";
import VcardSubtitle from "@/components/home/VcardSubtitle.vue";
import { SignInForm } from "@/scripts/forms/SignInForm";
import { emailFormat, requiredField } from "@/scripts/forms/FormValidators";
import { namespace } from "vuex-class";
import { SignInDto } from "@/dto/SignInDto";
const Authentication = namespace("Authentication");

@Component({
  components: { VcardSubtitle, Alert }
})
export default class SignIn extends Vue {
  private passwordVisible = false;
  private isLoading = false;
  private isAlertVisible = false;

  private signInForm: SignInForm = {
    valid: false,
    fields: {
      email: "",
      password: ""
    },
    rules: {
      email: [
        requiredField("E-mail is required."),
        emailFormat("Provided value is not valid e-mail.")
      ],
      password: [requiredField("Password is required.")]
    }
  };

  @Authentication.Getter
  private isLogged!: boolean;

  @Authentication.Action
  private signIn!: (signInDto: SignInDto) => Promise<any>;

  private logIn() {
    this.isLoading = true;
    this.signIn({
      email: this.signInForm.fields.email,
      password: this.signInForm.fields.password
    }).then(
      user => {
        this.isLoading = false;
        this.$router.push("/account");
      },
      error => {
        this.isLoading = false;
        this.isAlertVisible = true;
        console.debug(error);
      }
    );
  }

  private makeAlertInvisible() {
    this.isAlertVisible = false;
  }
}
</script>
