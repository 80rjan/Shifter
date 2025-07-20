import ProfileInfo from "../components/ProfileInfo.tsx";
import ProfileSkillsInterests from "../components/ProfileSkills&Interests.tsx";
import {useGlobalContext} from "../context/GlobalContext.tsx";
import ProfileMyProfile from "../components/ProfileMyProfile.tsx";
import ProfileSkeleton from "../components/skeletons/ProfileSkeleton.tsx";

function Profile() {
    const {user, loading} = useGlobalContext();

    if (loading) {
        return <ProfileSkeleton />;
    }

    return (
        <main className="grid grid-cols-4 gap-x-12 py-vertical-lg pt-30 px-horizontal-md bg-dark-blue/5">
            <div className="col-span-1 flex flex-col gap-6">
                <ProfileInfo />
                {user?.skills && user.skills.length > 0 && (<ProfileSkillsInterests title="Learned Skills" pills={user?.skills || []} />)}
                <ProfileSkillsInterests title="Desired Skills" pills={user?.skillGap || []} />
                <ProfileSkillsInterests title="Interests" pills={user?.interests || []} />
            </div>

            <div className="col-span-3">
                <ProfileMyProfile />
            </div>
        </main>
    )
}

export default Profile;