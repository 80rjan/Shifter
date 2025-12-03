import {capitalize} from "./capitalize.ts";

export function toEnumFormat(str: string): string {
    return str.trim().replace(/\s+/g, '_').toUpperCase();
}

export function fromEnumFormat(str: string): string {
    return capitalize(
        str
            .trim()
            .toLowerCase()
            .replace(/_/g, " ")
    );
}