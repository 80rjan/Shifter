import React from "react";
import type {UserRegister} from "../../types/UserRegister.tsx";
import type {SliderProps} from "../../types/SliderProps.tsx";
import {toEnumFormat} from "../../utils/toEnumFormat.ts";

function RegisterSlider(sliderProps: SliderProps) {
    const [allOptions] = React.useState<string[]>(sliderProps.options || []);
    const [options, setOptions] = React.useState<string[]>(allOptions);
    const [filterText, setFilterText] = React.useState("");

    const handleFilterChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const value = e.target.value;
        setFilterText(value);
        setOptions(allOptions.filter(option =>
            option.toLowerCase().includes(value.toLowerCase())
        ));
    };

    const handleOptionClick = (option: string) => {
        sliderProps.setUser((prev: UserRegister) => {
            const arr = prev[sliderProps.name] as string[] || [];
            const newArr = arr.includes(option)
                ? arr.filter(item => item !== option)
                : [...arr, option];

            return {
                ...prev,
                [sliderProps.name]: newArr,
            };
        });

        // Reset filter input
        setFilterText("");
        setOptions(allOptions); // Show all options again
    };

    return (
        <div className="flex flex-col justify-center gap-4 px-6 py-1 items-start w-full">
            <div className="flex justify-between w-full flex-wrap gap-2">
                <label htmlFor={sliderProps.id} className="text-shifter font-medium text-xl">
                    {sliderProps.label}
                </label>
                <input
                    className="px-3 py-1 rounded-md border border-black/10 text-black text-sm focus:outline-none focus:ring-2 focus:ring-shifter/60 transition-all"
                    placeholder="Search options..."
                    value={filterText}
                    onChange={handleFilterChange}
                />
            </div>

            <div className="relative custom-scrollbar flex gap-2 flex-wrap w-full max-h-[30vh] items-center overflow-y-auto">
                {options.map((option, index) => {
                    const isSelected = sliderProps.user[sliderProps.name]?.includes(toEnumFormat(option)) || false;

                    return (
                        <button
                            key={index}
                            name={sliderProps.name}
                            id={`${sliderProps.id}-${index}`}
                            className={`${isSelected ? "bg-shifter text-white shadow-black/20" : "bg-black/10 text-black shadow-shifter/20"} 
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
                    );
                })}

                <div
                    className="pointer-events-none sticky bottom-0 left-0 w-full h-4 bg-gradient-to-t from-white to-transparent"></div>
            </div>
        </div>
    );
}

export default RegisterSlider;
