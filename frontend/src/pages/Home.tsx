import Hero from "../components/Hero.tsx";
import CollaborationSteps from "../components/CollaborationSteps.tsx";
import RoadmapAI from "../components/RoadmapAI.tsx";
import ShifterValues from "../components/ShifterValues.tsx";
import CoursesCarousel from "../components/CoursesCarousel.tsx";

function Home() {

    return (
        <>
            <Hero />
            <CollaborationSteps />
            <RoadmapAI />
            <CoursesCarousel />
            <ShifterValues />
        </>
    )
}

export default Home