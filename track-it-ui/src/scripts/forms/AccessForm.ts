import {FormDefinition} from "@/scripts/forms/FormDefinition";

export interface AccessForm extends FormDefinition {
    fields: {
        deviceId: string;
        deviceName: string;
    },
    rules: {
        deviceId: ((message?: string) => {})[];
        deviceName: ((message?: string) => {})[];
    }
}
