import {FormDefinition} from "@/scripts/forms/FormDefinition";

export interface PasswordChangeFrom extends FormDefinition {
    fields: {
        password: string;
        confirmPassword: string;
    },
    rules: {
        password: ((message?: string) => {})[];
    }
}
