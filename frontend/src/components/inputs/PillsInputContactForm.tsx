import React from "react";
import {Minus, Plus} from "lucide-react";

export function PillsInputContactForm({label, onChange, options}: {
    label: string;
    onChange: (values: string[]) => void;
    options: string[];
}) {
    const [selectedPills, setSelectedPills] = React.useState<string[]>([]);

    const togglePill = (value: string) => {
        const newSelected = selectedPills.includes(value)
            ? selectedPills.filter(v => v !== value)
            : [...selectedPills, value];

        setSelectedPills(newSelected);
        onChange(newSelected);
    };

    return (
        <div className="w-full flex flex-col items-start gap-3">
            <span className="text-black-text/60 font-semibold text-base peer-focused:text-shifter text-left
                  md:text-lg
                  2xl:text-xl">{label}</span>
            <div className="w-full flex flex-wrap gap-3
                sm:gap-3">
                {options.map(option => (
                    <button
                        key={option}
                        type="button"
                        onClick={() => togglePill(option)}
                        className={`
                            px-5 py-2.5 rounded-md font-medium transition-all ease-in-out duration-200 cursor-pointer hover:scale-105
                            flex items-center gap-2
                            text-base
                            md:text-lg
                            2xl:text-xl
                            ${selectedPills.includes(option)
                            ? 'bg-shifter text-white shadow-md'
                            : 'bg-white border-1 border-black/20 text-black/70 hover:border-shifter/40 hover:text-shifter'
                        }
                        `}
                    >
                        <span>{option}</span>
                        {
                            selectedPills.includes(option) ?
                                <Minus className="flex-shrink-0" size={14} strokeWidth={2} /> :
                                <Plus className="flex-shrink-0" size={14} strokeWidth={2} />
                        }
                    </button>
                ))}
            </div>
        </div>
    );
}