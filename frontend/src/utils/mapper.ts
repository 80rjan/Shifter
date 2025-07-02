
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
        case "< 3 hours":
            return "extraShort";
        case "3-6 hours":
            return "short";
        case "6-10 hours":
            return "medium";
        case "10+ hours":
            return "long";
        default:
            return "";
    }
}

export function queryToDurationMapper(option: string): string {
    switch (option) {
        case "extraShort":
            return "< 3 hours";
        case "short":
            return "3-6 hours";
        case "medium":
            return "6-10 hours";
        case "long":
            return "10+ hours";
        default:
            return "";
    }
}
