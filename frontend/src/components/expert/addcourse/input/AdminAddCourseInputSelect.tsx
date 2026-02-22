import React from "react";

function AdminAddCourseInputSelect({label, value, options, onChange}: {
    label: string;
    value?: string | number;
    options: {value: string, name: string}[];
    onChange?: (e: React.ChangeEvent<HTMLSelectElement>) => void;
}) {
    return (
        <div className="flex flex-col gap-1 items-start">
            <label
                className="text-md font-medium text-black/60 whitespace-nowrap">{label}</label>
            <select
                value={value}
                onChange={onChange}
                className="border-2 border-transparent focus:outline-none focus:border-black/40
                                                    text-lg px-2 py-2 bg-[#FFF] rounded-sm shadow-sm shadow-black/10"
            >
                {
                    options.map((o, index) => (
                        <option value={o.value} key={index}>{o.name}</option>
                    ))
                }
            </select>
        </div>
    )
}

export default AdminAddCourseInputSelect;