import ProfileInfo from "../components/ProfileInfo.tsx";
import ProfileSkillsInterests from "../components/ProfileSkills&Interests.tsx";
import {useGlobalContext} from "../context/GlobalContext.tsx";
import ProfileMyProfile from "../components/ProfileMyProfile.tsx";
import ProfileSkeleton from "../components/skeletons/ProfileSkeleton.tsx";
import React from "react";
import ProfileModalAddSkillsInterests from "../components/ProfileModalAddSkills&Interests.tsx";

function Profile() {
    const {user, loading} = useGlobalContext();
    const [showModalDesiredSkills, setShowModalDesiredSkills] = React.useState(false);
    const [showModalInterests, setShowModalInterests] = React.useState(false);

    if (loading) {
        return <ProfileSkeleton/>;
    }

    return (
        <main className="grid grid-cols-4 gap-x-12 py-vertical-lg pt-top-nav-lg px-horizontal-md bg-dark-blue/5">
            <div className="col-span-1 flex flex-col gap-6">
                <ProfileInfo/>
                {user?.skills && user.skills.length > 0 && (
                    <ProfileSkillsInterests title="Learned Skills" pills={user?.skills || []} />)}
                <ProfileSkillsInterests title="Desired Skills" pills={user?.desiredSkills || []} openModal={() => setShowModalDesiredSkills(true)}/>
                <ProfileSkillsInterests title="Interests" pills={user?.interests || []} openModal={() => setShowModalInterests(true)}/>
            </div>

            <div className="col-span-3">
                <ProfileMyProfile/>
            </div>

            {
                showModalDesiredSkills && (
                    <ProfileModalAddSkillsInterests
                        type={"desiredSkills"}
                        label={"Identify Skills You’d Like to Improve"}
                        closeModal={() => setShowModalDesiredSkills(false)}
                    />
                )
            }
            {
                showModalInterests && (
                    <ProfileModalAddSkillsInterests
                        type={"interests"}
                        label={"Choose Topics You’re Interested In"}
                        closeModal={() => setShowModalInterests(false)}
                    />
                )
            }
        </main>
    )
}

export default Profile;