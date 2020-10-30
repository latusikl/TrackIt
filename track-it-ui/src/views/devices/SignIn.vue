<template>
  <v-container class="green background container-width">
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
        :disabled="!signInForm.valid"
      >
        Sign In
      </v-btn>
    </v-row>
  </v-container>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import Alert from "@/components/Alert.vue";
import VcardSubtitle from "@/components/home/VcardSubtitle.vue";
import { SignInForm } from "@/scripts/forms/SignInForm";
import { emailFormat, requiredField } from "@/scripts/forms/FormValidators";

@Component({
  components: { VcardSubtitle, Alert }
})
export default class SignIn extends Vue {
  passwordVisible = false;

  signInForm: SignInForm = {
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

  private logIn() {
    //TODO implement
    console.log(this.signInForm.fields.email);
    console.log(this.signInForm.fields.password);
  }
}
</script>
