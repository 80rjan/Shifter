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
        <div className="w-full flex flex-col items-start gap-2">
            <span className="text-black/40 font-semibold text-md">{label}</span>
            <div className="w-full flex flex-wrap gap-2">
                {options.map(option => (
                    <button
                        key={option}
                        type="button"
                        onClick={() => togglePill(option)}
                        className={`
                            px-4 py-1.5 rounded-md text-sm font-medium transition-all ease-in-out duration-200 cursor-pointer hover:scale-105
                            ${selectedPills.includes(option)
                            ? 'bg-shifter text-white shadow-md'
                            : 'bg-white border-1 border-black/20 text-black/70 hover:border-shifter/40 hover:text-shifter'
                        }
                        `}
                    >
                        {option}
                        {
                            selectedPills.includes(option) ?
                                <Minus className="inline-block ml-2" size={14} strokeWidth={2} /> :
                                <Plus className="inline-block ml-2" size={14} strokeWidth={2} />
                        }
                    </button>
                ))}
            </div>
        </div>
    );
}