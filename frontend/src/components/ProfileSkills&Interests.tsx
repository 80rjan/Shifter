import React from "react";

function ProfileSkillsInterests({ title, pills }: {
    title: string;
    pills: string[];
}) {
    const [showAll, setShowAll] = React.useState(false);

    const visiblePills = showAll ? pills : pills.slice(0, 4); // Show 6 by default

    return (
        <section className="flex flex-col gap-2 items-start w-full rounded-xl">
            <h2 className="text-2xl font-semibold">{title}</h2>
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
