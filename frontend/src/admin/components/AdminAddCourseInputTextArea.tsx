import React from "react";

function InputTextArea({label, name, value, placeholder, rows, onChange}: {
    label: string;
    name: string;
    value: string;
    placeholder: string;
    rows: number;
    onChange: (e: React.ChangeEvent<HTMLTextAreaElement>) => void;
}) {
    return (
        <div className="flex flex-col gap-1 items-start">
            <label className="text-md font-medium text-black/60">{label}</label>
            <textarea
                name={name}
                value={value}
                rows={rows}
                placeholder={placeholder}
                onChange={onChange}
                className="resize-none overflow-hidden min-h-fit
                border-2 border-transparent focus:outline-none focus:border-black/40
                text-lg px-2 py-2 bg-[#FFF] w-full rounded-sm shadow-sm shadow-black/10 overflow-y-auto scrollable-show"
            />
        </div>
    )
}

export default InputTextArea;