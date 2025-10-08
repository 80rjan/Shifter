import {Eye, EyeOff} from "lucide-react";
import React from "react";
import type { UserPersonalization } from "../../models/javaObjects/UserPersonalization.tsx";

type UserStrFields = 'email' | 'password' | 'passwordConfirmation' | 'name' | 'workPosition' | 'companyType';
interface InputProps {
    placeholder: string;
    label: string;
    name: UserStrFields;
    type: string;
    id: string;
    showPassword?: boolean;
    setShowPassword?: React.Dispatch<React.SetStateAction<boolean>>;
    setUser: React.Dispatch<React.SetStateAction<UserPersonalization>>;
    user: UserPersonalization;
}

function PersonalizationInput(inputProps: InputProps) {

    return (
        <div
            className="w-full relative flex flex-col items- gap-1 px-6 py-1 border-2 border-shifter group focus-within:border-l-20 transition-all ease-in-out duration-300 items-start rounded-sm">
            <label
                htmlFor={inputProps.id}
                className="text-shifter font-medium"
            >
                {inputProps.label}
            </label>
            <div className="flex gap-2 w-full">
                <div className="w-full">
                    <input
                        id={inputProps.id}
                        type={!inputProps.name.includes("password") ? "text" : inputProps.showPassword ? "text" : "password"}
                        name={inputProps.name}
                        placeholder={inputProps.placeholder}
                        className="w-full focus:outline-none text-lg"
                        value={inputProps.user[inputProps.name] || ""}
                        onChange={e =>
                            inputProps.setUser((prev: UserPersonalization) => ({
                                ...prev,
                                [inputProps.name]: e.target.value
                            }))
                        }
                    />
                    <hr className="border-t-2 border-black/5 rounded-full w-full"/>
                </div>
                {inputProps.name.includes("password") && (
                    <button
                        type="button"
                        onClick={() => inputProps.setShowPassword?.((prev: boolean) => !prev)}
                        className="text-black cursor-pointer hover:bg-black/5 rounded-full p-1"
                        aria-label={inputProps.showPassword ? "Hide password" : "Show password"}
                    >
                        {!inputProps.showPassword ? (
                            <EyeOff size={20} opacity={0.6}/>
                        ) : (
                            <Eye size={20} opacity={0.6}/>
                        )}
                    </button>
                )}
            </div>
        </div>
    );
}

export default PersonalizationInput;