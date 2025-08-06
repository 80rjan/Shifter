import ReactDom from "react-dom";
import {toEnumFormat} from "../utils/toEnumFormat.ts";
import React, {useEffect} from "react";
import {fetchCoursesSkillsApi, fetchCoursesTopicsApi, fetchRecommendedCoursesApi} from "../api/courseApi.ts";
import {useAuthContext} from "../context/AuthContext.tsx";
import {X} from "lucide-react";
import ProfileModalAddSkillsInterestsSkeleton from "./skeletons/ProfileModalAddSkills&InterestsSkeleton.tsx";
import {updateUserDesiredSkillsApi, updateUserInterestsApi} from "../api/userApi.ts";
import {useCourseStorage} from "../context/CourseStorage.ts";

function ProfileModalAddSkillsInterests({type, label, closeModal}: {
    type: "desiredSkills" | "interests";
    label: string;
    closeModal: () => void;
}) {
    const {user, setUser, accessToken} = useAuthContext();
    const {setRecommendedCourses} = useCourseStorage();
    const [allOptions, setAllOptions] = React.useState<string[]>([]);
    const [options, setOptions] = React.useState<string[]>([]);
    const [selected, setSelected] = React.useState<string[]>(user![type] || []);
    const [filterText, setFilterText] = React.useState("");
    const [loading, setLoading] = React.useState(false);
    const [componentRenderLoading, setComponentRenderLoading] = React.useState(true);
    const [error, setError] = React.useState<string | null>(null);

    useEffect(() => {
        if (type === "desiredSkills") {
            fetchCoursesSkillsApi()
                .then(skills => {
                    setAllOptions(skills);
                    setOptions(skills);
                })
                .catch(err => {
                    console.error("Failed to fetch course skills", err);
                })
                .finally(() => setComponentRenderLoading(false));
        } else if (type === "interests") {
            fetchCoursesTopicsApi()
                .then(topics => {
                    setAllOptions(topics);
                    setOptions(topics);
                })
                .catch(err => {
                    console.error("Failed to fetch course topics", err);
                })
                .finally(() => setComponentRenderLoading(false));
        }
    }, []);

    useEffect(() => {
        // Disable scroll
        document.body.classList.add("overflow-hidden");

        // Cleanup: re-enable scroll when modal unmounts
        return () => {
            document.body.classList.remove("overflow-hidden");
        };
    }, []);


    const handleFilterChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const value = e.target.value;
        setFilterText(value);
        setOptions(allOptions.filter(option =>
            option.toLowerCase().includes(value.toLowerCase())
        ));
    };

    const handleOptionClick = (option: string) => {
        setSelected((prev: string[]) => (
            prev.includes(option)
                ? prev.filter(item => item !== option)
                : [...prev, option]
        ));
    };

    const handleSubmit = () => {
        if (selected.length === 0) {
            setError("Help us tailor your experience — please select at least one option");
            return;
        }

        setLoading(true)
        const updateFn = type === "interests" ? updateUserInterestsApi : updateUserDesiredSkillsApi;
        updateFn(selected, accessToken!)
            .then(() => {
                setUser(prev => ({
                    ...prev!,
                    [type]: selected
                }))

                fetchRecommendedCoursesApi(accessToken!)
                    .then(courses => {
                        setRecommendedCourses(courses);
                    })
                    .catch(err => {
                        console.error("Failed to fetch recommended courses after update", err);
                    })

                closeModal();
            })
            .catch(err => {
                console.error("Failed to update user interests", err);
            })
            .finally(() => setLoading(false));
    }


    if (componentRenderLoading)
        return <ProfileModalAddSkillsInterestsSkeleton closeModal={closeModal} />;

    return ReactDom.createPortal(
        <>
            {/*OVERFLOW*/}
            <div className="fixed top-0 bottom-0 left-0 right-0 bg-black/70 z-1000"/>

            <section className="fixed top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 bg-white shadow-lg py-4 px-6 z-1000
                     max-w-2/3 min-w-1/2 max-h-5/6 rounded-lg flex flex-col gap-8">
                <div className="flex flex-col gap-2">
                    <button
                        onClick={closeModal}
                        className="hover:bg-black/10 transition-all duration-300 ease-out ml-auto px-2 w-fit rounded-sm cursor-pointer">
                        <X size={32} />
                    </button>

                    <div className="flex justify-between w-full flex-wrap ">
                        <label className="text-shifter font-medium text-2xl">
                            {label}
                        </label>
                        <input
                            type={"search"}
                            className="px-3 py-1 rounded-md border border-black/10 text-black text-md focus:outline-none focus:ring-2 focus:ring-shifter/60 transition-all"
                            placeholder={`Search ${type === "interests" ? "interests" : "skills"}...`}
                            value={filterText}
                            onChange={handleFilterChange}
                        />
                    </div>
                </div>

                <div
                    className="relative custom-scrollbar flex gap-2 flex-wrap w-full  items-center overflow-y-auto">
                    {options.map((option, index) => (
                        <button
                            key={index}
                            name={option}
                            className={`${selected.includes(toEnumFormat(option)) ? "bg-shifter text-white shadow-black/20" : "bg-black/10 text-black shadow-shifter/20"} 
                            px-4 py-1 rounded-md transition-all duration-200 ease-in-out hover:shadow-md
                            focus:outline-none cursor-pointer whitespace-nowrap`}
                            onClick={() => handleOptionClick(option)}
                        >
                            {
                                option
                                    .toLowerCase()
                                    .replace(/_/g, " ")
                                    .replace(/\b\w/g, (c) => c.toUpperCase())
                            }
                        </button>
                    ))}
                </div>

                <div className="flex flex-col gap-2 items-center">
                    {
                        error && error.length > 0 && (
                            <p className="text-red-500 text-center">
                                {error}
                            </p>
                        )
                    }
                    <div className="flex items-center gap-2">
                        <button
                            onClick={handleSubmit}
                            className="shadow-md shadow-shifter/30 hover:shadow-lg hover:shadow-shifter/50 transition-all duration-200 ease-in-out cursor-pointer
                    bg-shifter px-20 py-2 w-fit text-white rounded-sm font-semibold border-2 border-white/40"
                        >
                            {
                                loading ? "Submitting..." : "Submit Changes"
                            }
                        </button>
                        {loading && <div className="h-full loader"></div>}
                    </div>
                </div>
            </section>
        </>,
        document.getElementById("portal")!
    )
}

export default ProfileModalAddSkillsInterests;