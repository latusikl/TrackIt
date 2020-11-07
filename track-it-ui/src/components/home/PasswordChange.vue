<template>
  <v-container>
    <v-row justify="center">
      <v-col cols="12" md="4">
        <v-form ref="form" v-model="passwordChangeForm.valid">
          <v-text-field
            label="Password"
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

@Component({
  components: { VcardSubtitle, Alert }
})
export default class SignIn extends Vue {
  private passwordVisible = false;

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
    //TODO implement
    console.error("Not implemented");
    console.log(this.passwordChangeForm.fields.password);
    console.log(this.passwordChangeForm.fields.confirmPassword);
  }
}
</script>
