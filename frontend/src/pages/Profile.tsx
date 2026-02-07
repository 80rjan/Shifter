import ProfileInfo from "../components/ProfileInfo.tsx";
import ProfileSkillsInterests from "../components/ProfileSkills&Interests.tsx";
import {useAuthContext} from "../context/AuthContext.tsx";
import ProfileMyProfile from "../components/ProfileMyProfile.tsx";
import ProfileSkeleton from "../components/skeletons/ProfileSkeleton.tsx";
import React from "react";
import ProfileModalAddSkillsInterests from "../components/ProfileModalAddSkills&Interests.tsx";
import {useTranslation} from "react-i18next";
import {useUserContext} from "../context/UserContext.tsx";

function Profile() {
    const {logout} = useAuthContext();
    const {user, isUserLoading} = useUserContext();
    const [showModalInterests, setShowModalInterests] = React.useState(false);
    const {t} = useTranslation("profile");

    if (isUserLoading) {
        return <ProfileSkeleton/>;
    }

    return (
        <main className="grid grid-cols-4 gap-x-12 py-vertical pt-nav-top px-horizontal bg-main flex-1">
            <div className="col-span-1 flex flex-col gap-6">
                <ProfileInfo/>
                {/*{*/}
                {/*    user && (user.skillsGained || []).length > 0 &&*/}
                {/*    <ProfileSkillsInterests*/}
                {/*        title={t("skillsGained.title")}*/}
                {/*        pills={user.skillsGained}*/}
                {/*    />*/}
                {/*}*/}
                {/*<ProfileSkillsInterests*/}
                {/*    title={t("interests.title")}*/}
                {/*    pills={user?.interests || []}*/}
                {/*    openModal={() => setShowModalInterests(true)}*/}
                {/*/>*/}
                <button
                    onClick={logout}
                    className="hover:border-white/40 hover:bg-red hover:text-white hover:shadow-lg hover:shadow-red/50 transition-all duration-200 ease-in-out cursor-pointer
                    py-2 rounded-sm border-2 border-red/40 text-red/80 font-semibold"
                >
                    {t("logout")}
                </button>
            </div>

            <div className="col-span-3">
                <ProfileMyProfile/>
            </div>

            {/*{showModalInterests && (*/}
            {/*    <ProfileModalAddSkillsInterests*/}
            {/*        label={t("modal.chooseTopics")}*/}
            {/*        closeModal={() => setShowModalInterests(false)}*/}
            {/*    />*/}
            {/*)}*/}
        </main>
    );
}

export default Profile;
