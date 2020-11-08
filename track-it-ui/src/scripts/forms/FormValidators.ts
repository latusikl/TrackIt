/* eslint-disable */
export function requiredField(message: string): (value: any) => boolean | string {
    return (value) => (!!value || value === 0) || message;
}

export function maxLength(message: string, maxLength: number): (value: any) => boolean | string {
    return (value) => (value.length < maxLength) || message;
}

export function minLength(message: string, minLength: number): (value: any) => boolean | string {
    return (value) => (value.length >= minLength) || message;
}

const emailRegex = new RegExp(/^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/);

export function emailFormat(message: string): (value: any) => boolean | string {
    return (value) => (emailRegex.test(value)) || message;
}
