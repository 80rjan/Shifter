import HeroProfile from "../components/HeroProfile.tsx";
import {useState} from "react";

function Profile() {
    const [selectedTab, setSelectedTab] = useState("myCourses");

    return (
        <main>
            <HeroProfile selectedTab={selectedTab} setSelectedTab={setSelectedTab}/>
        </main>
    )
}

export default Profile;