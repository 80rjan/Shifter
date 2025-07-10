
export function slugify(text: string): string {
    return text
        .toLowerCase()
        .replace(/ /g, '-')
        .replace("&", "and")
        .replace(/[^\w-]+/g, "") // Remove non-word characters (not including hyphens)
}

export function unslugify(text: string): string {
    return text
        .replace(/-/g, ' ')
        .replace("and", "&")
        .trim();
}