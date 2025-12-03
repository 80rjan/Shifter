
export interface FilterParams {
    showOnlyFavoriteCourses?: boolean;
    search?: string[];
    difficulty?: string[]; // but digits for mapping for languages
    price?: string[];
    duration?: string[];
    skill?: string[];
    topic?: string[];
}