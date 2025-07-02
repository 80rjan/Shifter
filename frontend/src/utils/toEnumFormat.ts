
export function toEnumFormat(str: string): string {
    return str.trim().replace(/\s+/g, '_').toUpperCase();
}