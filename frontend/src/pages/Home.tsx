import HeroHome from "../components/HeroHome.tsx";
import CollaborationSteps from "../components/CollaborationSteps.tsx";
import CoursesCarouselHome from "../components/CoursesCarouselHome.tsx";
import OurServices from "../components/OurServices.tsx";
import AcoSection from "../components/AcoSection.tsx";
import ShifterDifferenceTable from "../components/ShifterDifferenceTable.tsx";

function Home() {

    return (
        <main className="bg-main">
            <div className="px-4 py-vertical-sm">
                <HeroHome/>
            </div>
            <OurServices />
            <CollaborationSteps/>
            {/*<CoursesCarouselHome/>*/}
            <ShifterDifferenceTable />
            <AcoSection />
        </main>
    )
}

export default Home