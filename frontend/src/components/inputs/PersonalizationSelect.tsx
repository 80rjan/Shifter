import type {UserPersonalization} from "../../models/javaObjects/UserPersonalization.tsx";
import {toEnumFormat} from "../../utils/toEnumFormat.ts";
import React from "react";

function PersonalizationSelect({label, name, id, optionsShow, options, setUser, user}: {
    label: string;
    name: "companySize";
    id: string;
    optionsShow: string[];
    options: readonly string[];
    setUser: React.Dispatch<React.SetStateAction<UserPersonalization>>;
    user: UserPersonalization;
}) {

    return (
        <div
            className="w-full relative flex flex-col gap-1 px-6 py-1 border-2 border-shifter group focus-within:border-l-20 transition-all ease-in-out duration-300 items-start rounded-sm">
            <label
                htmlFor={id}
                className="text-shifter font-medium"
            >
                {label}
            </label>
            <div className="w-full">
                <select
                    id={id}
                    name={name}
                    className="w-full focus:outline-none text-lg cursor-pointer"
                    value={user[name] || ""}
                    onChange={e =>
                        setUser((prev: UserPersonalization) => ({
                            ...prev,
                            [name]: e.target.value
                        }))
                    }
                >
                    <option value="" className="text-black/10">
                        Select an option
                    </option>
                    {
                        optionsShow.map((o, index) => (
                            <option key={index} value={toEnumFormat(options[index])}>
                                {o}
                            </option>
                        ))
                    }
                </select>
                <hr className="border-t-2 border-black/5 rounded-full w-full"/>
            </div>
        </div>
    );
}

export default PersonalizationSelect;