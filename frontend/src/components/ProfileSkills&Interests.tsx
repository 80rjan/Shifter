import React from "react";
import {Plus} from "lucide-react";

function ProfileSkillsInterests({ title, pills, openModal }: {
    title: string;
    pills: string[];
    openModal?: () => void;
}) {
    const [showAll, setShowAll] = React.useState(false);

    const visiblePills = showAll ? pills : pills.slice(0, 4); // Show 6 by default

    return (
        <section className="flex flex-col gap-2 items-start w-full rounded-xl">
            <div className="flex justify-between items-center w-full">
                <h2 className="text-2xl font-semibold">{title}</h2>
                {
                    openModal && (
                        <button
                            onClick={openModal}
                            className="hover:bg-shifter/20 px-2 rounded-sm transition-all duration-300 ease-out
                            cursor-pointer text-shifter"
                        >
                            <Plus size={24} />
                        </button>
                    )
                }
            </div>
            <div className="flex gap-2 w-full flex-wrap border-1 border-dark-blue/10 bg-white rounded-lg p-4">
                {
                    visiblePills.map((pill, index) => (
                        <span
                            key={index}
                            className="font-medium text-white px-4 py-1 bg-shifter/60 border-2 border-white/40 rounded-md"
                        >
                            {pill
                                .toLowerCase()
                                .replace(/_/g, " ")
                                .replace(/\b\w/g, char => char.toUpperCase())
                            }
                        </span>
                    ))
                }

                {pills.length > 4 && (
                    <button
                        onClick={() => setShowAll(prev => !prev)}
                        className="p-2 rounded-sm underline hover:bg-shifter/10 hover:text-shifter cursor-pointer"
                    >
                        {showAll ? "Show less" : `Show ${pills.length - 4} more`}
                    </button>
                )}
            </div>
        </section>
    );
}

export default ProfileSkillsInterests;
