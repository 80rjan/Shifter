import {useGlobalContext} from "../context/GlobalContext.tsx";
import {useCourseStorage} from "../context/CourseStorage.ts";

function HeroProfile({selectedTab, setSelectedTab} : {
    selectedTab: string;
    setSelectedTab: (tab: string) => void;
}) {
    const {user} = useGlobalContext();
    const {enrollments} = useCourseStorage();
    console.log(user)
    console.log(enrollments)

    return (
        <section className="flex flex-col gap-8 pt-40">
            {/*USER INFO*/}
            <div className="flex items-center justify-start gap-12 px-horizontal-lg">
                <div className="w-40 h-60 bg-black/20 rounded-sm"/>
                <div className="flex flex-col gap-2 items-start">
                    <h1 className="text-4xl font-semibold">{user?.name}</h1>
                    <span className="text-xl font-regular">{user?.workPosition} | {user?.companyType && (user.companyType.charAt(0) + user.companyType.toLowerCase().slice(1))}</span>
                    <div className="flex gap-4">
                        {
                            user?.skills.map((skill, index) => (
                                <span
                                    key={index}
                                    className="py-1 px-6 rounded-lg bg-black/40 text-white border-2 border-white/60"
                                >
                                    {
                                        skill
                                            .charAt(0).toUpperCase() + skill.slice(1).toLowerCase()
                                    }
                                </span>
                            ))
                        }
                    </div>
                </div>
            </div>

            {/*TAB NAVIGATION*/}
            <ul className="flex gap-12 px-horizontal-lg">
                <li
                    onClick={() => setSelectedTab("myCourses")}
                    className={`${selectedTab === "myCourses" && "border-b-2 border-shifter text-shifter"}
                        text-lg font-semibold cursor-pointer py-1 px-4 text-black/40
                    `}
                >
                    My Courses</li>
                <li
                    onClick={() => setSelectedTab("ratings")}
                    className={`${selectedTab === "ratings" && "border-b-2 border-shifter text-shifter"}
                        text-lg font-semibold cursor-pointer py-1 px-4 text-black/40
                    `}
                >Ratings</li>
                <li
                    onClick={() => setSelectedTab("profile")}
                    className={`${selectedTab === "profile" && "border-b-2 border-shifter text-shifter"}
                        text-lg font-semibold cursor-pointer py-1 px-4 text-black/40
                    `}
                >Profile</li>
                <li
                    onClick={() => setSelectedTab("settings")}
                    className={`${selectedTab === "settings" && "border-b-2 border-shifter text-shifter"}
                        text-lg font-semibold cursor-pointer py-1 px-4 text-black/40
                    `}
                >Settings</li>
            </ul>
        </section>
    )
}

export default HeroProfile;