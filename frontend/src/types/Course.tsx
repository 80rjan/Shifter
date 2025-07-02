export interface Course {
    id: number,
    imageUrl: string;
    color: string;
    titleShort: string;
    title: string;
    topic: string;
    difficulty: Difficulty;
    durationHours: number;
    price: number;
    rating: number;
    ratingCount: number;
    descriptionShort: string;
    description: string;
    descriptionLong: string;
    skillsGained: string[];
    whatWillBeLearned: string[];
}

type Difficulty = "Beginner" | "Intermediate" | "Advanced" | "Expert";


