import React from "react";
import type {UserPersonalization} from "../../models/javaObjects/UserPersonalization.tsx";
import {parseStringToTagReq} from "../../utils/parseStringToTagReq.ts";

function PersonalizationSlider({label, id, allOptions, setUser, user}: {
    label: string;
    id: string;
    allOptions: string[];
    setUser: React.Dispatch<React.SetStateAction<UserPersonalization>>;
    user: UserPersonalization;
}) {
    const [options, setOptions] = React.useState<string[]>(allOptions);
    const [filterText, setFilterText] = React.useState("");

    const handleFilterChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const value = e.target.value;
        setFilterText(value);
        setOptions(allOptions.filter(option =>
            parseStringToTagReq(option).value.toLowerCase().includes(value.toLowerCase())
        ));
    };

    const handleOptionClick = (attrId: number) => {
        setUser((prev: UserPersonalization) => {
            const set = new Set(prev.tagIdList);
            if (set.has(attrId)) {
                set.delete(attrId);
            } else {
                set.add(attrId);
            }

            const newArr = Array.from(set);
            return {
                ...prev,
                tagIdList: newArr,
            };
        });
    };

    return (
        <div className="w-full flex flex-col justify-center gap-4 px-6 py-1 items-start w-full">
            <div className="flex justify-between w-full flex-wrap gap-2">
                <label htmlFor={id} className="text-shifter font-medium text-xl">
                    {label}
                </label>
                <input
                    type={"search"}
                    className="px-3 py-1 rounded-md border border-black/10 text-black text-sm focus:outline-none focus:ring-2 focus:ring-shifter/60 transition-all"
                    placeholder="Search options..."
                    value={filterText}
                    onChange={handleFilterChange}
                />
            </div>

            <div className="relative custom-scrollbar flex gap-2 flex-wrap w-full max-h-[30vh] items-center overflow-y-auto">
                {options.map((option, index) => {
                    const id = parseStringToTagReq(option).id;
                    const value = parseStringToTagReq(option).value;
                    const isSelected = user.tagIdList.includes(id) || false;

                    return (
                        <button
                            key={index}
                            id={`${id}-${index}`}
                            className={`${isSelected ? "bg-shifter text-white shadow-black/20" : "bg-black/10 text-black shadow-shifter/20"} 
                            px-4 py-1 rounded-md transition-all duration-200 ease-in-out hover:shadow-md
                            focus:outline-none cursor-pointer whitespace-nowrap`}
                            onClick={() => handleOptionClick(id)}
                        >
                            {
                                value
                                    .toLowerCase()
                                    .replace(/_/g, " ")
                                    .replace(/\b\w/g, (c) => c.toUpperCase())
                            }
                        </button>
                    );
                })}

                <div
                    className="pointer-events-none sticky bottom-0 left-0 w-full h-4 bg-gradient-to-t from-white to-transparent"></div>
            </div>
        </div>
    );
}

export default PersonalizationSlider;
