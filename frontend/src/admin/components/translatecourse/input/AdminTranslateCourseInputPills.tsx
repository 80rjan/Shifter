import React from "react";
import InitialValueForTranslation from "./InitialValueForTranslation.tsx";
import {parseStringToAttributeReq} from "../../../../utils/parseStringToAttributeReq.ts";

function InputPills({label, onChange, translateFromList, isAttributeEncoded, display}: {
    label: string;
    onChange: (e: React.ChangeEvent<HTMLInputElement>, index: number) => void;
    translateFromList: string[];
    isAttributeEncoded: boolean;
    display: "flex" | "grid"
}) {
    return (
        <div className={`flex flex-col gap-1 items-start w-full`}>
            <label className="text-md font-medium text-black/60 whitespace-nowrap">{label}</label>
            <div className={`${display === "flex" ? "flex flex-wrap" : "grid grid-cols-3"} gap-4 w-full`}>
                {
                    translateFromList.map((str, index) => (
                        <div className={`flex flex-col gap-1 w-full`}>
                            <input
                                data-translate-from={str}
                                onChange={(e) => onChange(e, index)}
                                className={`
                                border-2 border-transparent focus:outline-none focus:border-black/40
                                text-lg px-2 py-2 bg-[#FFF] w-full rounded-sm shadow-sm shadow-black/10
                                file:mr-4 file:py-2 file:px-4
                                file:rounded-sm file:border-0
                                file:text-sm file:font-semibold
                                file:bg-black/10 file:text-black/70
                                hover:file:bg-black/20
                            `}
                            />
                            <InitialValueForTranslation
                                value={
                                    isAttributeEncoded ?
                                        parseStringToAttributeReq(str).value :
                                        str
                                }
                            />
                        </div>
                    ))
                }
            </div>
        </div>
    )
}

export default InputPills;