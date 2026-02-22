
function Input({label, name, value, width}: {
    label: string;
    name?: string;
    value?: string | number;
    width: "fit" | "full";
}) {
    return (
        <div className={`flex flex-col gap-1 items-start ${width === "full" ? "w-full" : "w-fit"}`}>
            <label className="text-md font-medium text-black/60 whitespace-nowrap">{label}</label>
            <input
                disabled={true}
                value={value}
                name={name}
                className={`disabled:bg-black/5 disabled:shadow-none
                border-2 border-transparent focus:outline-none focus:border-black/40
                text-lg px-2 py-2 bg-[#FFF] w-full rounded-sm shadow-sm shadow-black/10
                file:mr-4 file:py-2 file:px-4
                file:rounded-sm file:border-0
                file:text-sm file:font-semibold
                file:bg-black/10 file:text-black/70
                hover:file:bg-black/20`}
            />
        </div>
    )
}

export default Input;