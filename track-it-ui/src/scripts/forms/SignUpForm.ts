import {FormDefinition} from "@/scripts/forms/FormDefinition";

export interface SignUpForm extends FormDefinition {
    fields: {
        email: string;
        password: string;
        confirmPassword: string;
    },
    rules: {
        email: ((message?: string) => {})[];
        password: ((message?: string) => {})[];
    }
}
