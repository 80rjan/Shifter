import HeroHome from "../components/HeroHome.tsx";
import CollaborationSteps from "../components/CollaborationSteps.tsx";
import CoursesCarouselHome from "../components/CoursesCarouselHome.tsx";
import OurServices from "../components/OurServices.tsx";
import AcoSection from "../components/AcoSection.tsx";

function Home() {

    return (
        <main className="bg-beige">
            <div className="px-4 py-vertical-sm">
                <HeroHome/>
            </div>
            <OurServices />
            <CollaborationSteps/>
            {/*<CoursesCarouselHome/>*/}
            <AcoSection />
        </main>
    )
}

export default Home