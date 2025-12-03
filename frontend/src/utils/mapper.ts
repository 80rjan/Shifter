
export function priceToQueryMapper(option: string): string {
    switch (option) {
        case "Free":
            return "free";
        case "< $20":
            return "low";
        case "$20 - $50":
            return "medium";
        case "$50+":
            return "high";
        default:
            return "";
    }
}

export function queryToPriceMapper(option: string): string {
    switch (option) {
        case "free":
            return "Free";
        case "low":
            return "< $20";
        case "medium":
            return "$20 - $50";
        case "high":
            return "$50+";
        default:
            return "";
    }
}

export function durationToQueryMapper(option: string): string {
    switch (option) {
        case "< 3h":
            return "extraShort";
        case "3h - 6h":
            return "short";
        case "6h - 10h":
            return "medium";
        case "10h + ":
            return "long";
        default:
            return "";
    }
}

export function queryToDurationMapper(option: string): string {
    switch (option) {
        case "extraShort":
            return "< 3h";
        case "short":
            return "3h - 6h";
        case "medium":
            return "6h - 10h";
        case "long":
            return "10h + ";
        default:
            return "";
    }
}

export function indexToDifficultyMapper(index: number, arr: string[]): string {
    return arr[index];
}
