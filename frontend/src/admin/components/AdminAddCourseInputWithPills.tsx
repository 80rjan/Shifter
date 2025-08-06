import {X} from "lucide-react";
import AdminAddCourseInput from "./AdminAddCourseInput.tsx";

function InputWithPills({label, name, options, addPill, removePill, width}: {
    name: string;
    label: string;
    options: string[];
    addPill: (option: string) => void;
    removePill: (index: number) => void;
    width: "full" | "fit";
}) {
    return (
        <div className="flex flex-col gap-4 ">
            <form
                onSubmit={e => {
                    e.preventDefault()
                    const form = e.target as HTMLFormElement;
                    const input = form.elements.namedItem(name) as HTMLInputElement;
                    const option = input.value.trim();
                    addPill(option)
                    form.reset()
                }}
                className="flex gap-8 items-end w-full">
                <AdminAddCourseInput
                    type={"text"}
                    label={label}
                    name={name}
                    width={width}
                />
                <button
                    type="submit"
                    className="hover:shadow-lg shadow-md shadow-shifter/40 transition-all ease-out duration-300
                                border-2 border-white/40 px-12 py-2 bg-shifter rounded-sm text-white text-lg w-fit cursor-pointer "
                >
                    Add
                </button>
            </form>

            <div className="flex flex-wrap gap-2 items-center justify-start">
                {
                    options.length > 0 && options.map((option, index) => (
                        <button
                            onClick={() => removePill(index)}
                            className="hover:opacity-60 transition-all ease-out duration-300 cursor-pointer
                                        flex items-center gap-2 px-4 py-2 rounded-md bg-black/40 text-white shadow-sm"
                            key={index}
                        >
                            <span>{option}</span>
                            <X size={20}/>
                        </button>
                    ))
                }
            </div>
        </div>
    )
}

export default InputWithPills;