import { useTranslation } from "react-i18next";
import CoursesGrid from "../components/CoursesGrid.tsx";

import ShifterRocket from "../assets/images/Rocket-Blue-Fire.png";
import CoursesFiltersSkeleton from "../components/skeletons/CoursesFiltersSkeleton.tsx";
import CoursesGridSkeleton from "../components/skeletons/CoursesGridSkeleton.tsx";
import {useCourses} from "../hooks/useCourses.tsx";


function Courses() {
    const { t } = useTranslation("courses");
    const {
        authLoading,
        loading,
        filters,
        setFilters,
        courses
    } = useCourses();

    return (
        <main className="font-montserrat bg-white">
            <section className="relative flex flex-col items-center justify-center py-vertical pt-nav-top gap-4 px-horizontal shadow-sm">
                <img
                    src={ShifterRocket}
                    alt={t("rocketAlt")}
                    className="absolute top-nav-top left-20 rotate-45 w-16 h-auto"
                />
                <h1 className="text-6xl">
                    {t("title")}
                    <br/>
                    <strong className="text-shifter">{t("highlight")}</strong>
                </h1>
                <p>{t("description")}</p>
            </section>

            {(authLoading || loading) ? (
                <section className="flex gap-0 w-full">
                    <CoursesFiltersSkeleton />
                    <CoursesGridSkeleton />
                </section>
            ) : (
                <section className="grid grid-cols-6 gap-0 w-full">
                    {/*<CoursesFilters*/}
                    {/*    filters={filters}*/}
                    {/*    setFilters={setFilters}*/}
                    {/*    topics={topics}*/}
                    {/*    skills={skills}*/}
                    {/*/>*/}
                    <CoursesGrid
                        setFilters={setFilters}
                        filters={filters}
                        courses={courses}
                    />
                </section>
            )}
        </main>
    );
}

export default Courses;
