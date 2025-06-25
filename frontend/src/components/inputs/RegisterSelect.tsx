import type {User} from "../../types/User.tsx";
import type {SelectProps} from "../../types/SelectProps.tsx";
import {toEnumFormat} from "../../utils/toEnumFormat.tsx";

function RegisterSelect(selectProps: SelectProps) {

    return (
        <div
            className="w-8/10 relative flex flex-col gap-1 px-6 py-1 border-2 border-shifter group transition-all ease-in-out duration-300 items-start rounded-sm">
            <label
                htmlFor={selectProps.id}
                className="text-shifter font-medium"
            >
                {selectProps.label}
            </label>
            <div className="w-full">
                <select
                    id={selectProps.id}
                    name={selectProps.name}
                    className="w-full focus:outline-none text-lg cursor-pointer"
                    onChange={e =>
                        selectProps.setUser((prev: User) => ({
                            ...prev,
                            [selectProps.name]: toEnumFormat(e.target.value)
                        }))
                    }
                >
                    <option value="" className="text-black/10">
                        Select an option
                    </option>
                    {
                        selectProps.options?.map((option, index) => (
                            <option key={index} value={option}>
                                {option}
                            </option>
                        ))
                    }
                </select>
                <hr className="border-t-2 border-black/5 rounded-full w-full"/>
            </div>
        </div>
    );
}

export default RegisterSelect;