export function requiredField(message: string): (value: any) => boolean | string {
    return (value) => (!!value || value === 0) || message;
}

export function maxLength(message: string, maxLength: number): (value: any) => boolean | string {
    return (value) => (value.length < maxLength) || message;
}
