import React from "react";

export function TextInputContactForm({label, name, placeholder, rows, onChange}: {
    label: string;
    name: string;
    placeholder: string;
    rows: number;
    onChange: (e: React.ChangeEvent<HTMLTextAreaElement>) => void;
}) {
    return (
        <label className="w-full flex flex-col items-start gap-3">
            <span className="text-black-text/60 font-semibold text-base peer-focused:text-shifter text-left
                  md:text-lg
                  2xl:text-xl">{label}</span>
            <textarea
                required
                onChange={onChange}
                rows={rows}
                name={name}
                className="peer w-full bg-dark-blue/5 border-1 border-black/10 py-2.5 px-3 rounded-sm resize-none min-h-fit custom-scrollbar focus:outline-none focus:border-shifter/40 focus:border-2
                text-base
                md:text-lg
                2xl:text-xl"
                placeholder={placeholder}
            />
        </label>
    );
}