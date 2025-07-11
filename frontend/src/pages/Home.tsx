import HeroHome from "../components/HeroHome.tsx";
import CollaborationSteps from "../components/CollaborationSteps.tsx";
import RoadmapAI from "../components/RoadmapAI.tsx";
import ShifterValues from "../components/ShifterValues.tsx";
import CoursesCarouselHome from "../components/CoursesCarouselHome.tsx";

function Home() {

    return (
        <main className="bg-white">
            <div className="bg-dark-blue">
                <HeroHome/>
                <CollaborationSteps/>
            </div>
            <RoadmapAI/>
            <CoursesCarouselHome/>
            <ShifterValues/>
        </main>
    )
}

export default Home