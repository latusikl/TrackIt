import {FormDefinition} from "@/scripts/forms/FormDefinition";

export interface SignInForm extends FormDefinition {
    fields: {
        email: string;
        password: string;
    },
    rules: {
        email: ((message?: string) => {})[];
        password: ((message?: string) => {})[];
    }
}
