
const TextWithUnderline = ({label, className, fromRightToLeft}: { label: string, className?: string; fromRightToLeft?: boolean }) => (
    <div className={`flex flex-col gap-0 overflow-clip p-1 group ${className}`}>
        {label}
        <hr className={`relative ${fromRightToLeft ? "-right-50 group-hover:-right-6" : "-left-50 group-hover:-left-6"} border-t-2 rounded-full transition-all duration-300 ease-in-out`}/>
    </div>
);

export default TextWithUnderline;