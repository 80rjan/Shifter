import React from "react";
import { Circle, CircleCheckBig } from "lucide-react";

export function RadioInputContactForm({
                                          label,
                                          name,
                                          options,
                                          onChange
                                      }: {
    label: string;
    name: string;
    options: string[];
    onChange: (value: string) => void;
}) {
    const [selectedValue, setSelectedValue] = React.useState<string>("");

    const handleSelect = (value: string) => {
        setSelectedValue(value);
        onChange(value);
    };

    return (
        <div className="w-full flex flex-col items-start gap-2">
            <span className="text-black/40 font-semibold text-md">{label}</span>
            <div className="w-full flex flex-col gap-2">
                {options.map(option => (
                    <label
                        key={option}
                        className="flex items-center gap-3 cursor-pointer group"
                    >
                        <input
                            type="radio"
                            name={name}
                            value={option}
                            checked={selectedValue === option}
                            onChange={() => handleSelect(option)}
                            className="sr-only"
                            required
                        />
                        <div className="relative flex items-center justify-center">
                            {selectedValue === option ? (
                                <CircleCheckBig
                                    size={20}
                                    className="text-shifter transition-all duration-200"
                                    strokeWidth={2}
                                />
                            ) : (
                                <Circle
                                    size={20}
                                    className="text-black/20 group-hover:text-shifter/60 transition-all duration-200"
                                    strokeWidth={2}
                                />
                            )}
                        </div>
                        <span className={`font-medium transition-all duration-200 ${
                            selectedValue === option
                                ? 'text-shifter'
                                : 'text-black-text/60 group-hover:text-shifter/80'
                        }`}>
                            {option}
                        </span>
                    </label>
                ))}
            </div>
        </div>
    );
}