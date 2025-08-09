export function toUrlFormat(title: string): string {
    return title
        .toLowerCase()
        .trim() // Trim leading and trailing spaces
        .replace(/\s+/g, "-") // Replace spaces with hyphens
        .replace(/-+/g, "-"); // Replace multiple hyphens with a single hyphen
}

export function fromUrlFormat(url: string): string {
    return url
        .toLowerCase()
        .replace(/-/g, " ") // Replace hyphens with spaces
        .replace(/\b\w/g, c => c.toUpperCase()); // Capitalize the first letter of each word
}