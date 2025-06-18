import {useRef} from "react";

interface RoadmapInputProps {
    title: string;
    description: string;
    textColor: string;
    bgColor: string;
    marginAutoSide: string;
    isLeft: boolean;
}


function RoadmapInput({ title, description, textColor, bgColor, marginAutoSide, isLeft }: RoadmapInputProps) {
    const textareaRef = useRef<HTMLTextAreaElement | null>(null);

    const handleInput = () => {
        const el = textareaRef.current;
        if (el) {
            el.style.height = 'auto';
            el.style.height = `${el.scrollHeight}px`;
        }
    };

    return (
        <div
            className={`overflow-clip flex gap-16 w-fit ${isLeft ? 'pr-4' : 'pl-4'} bg-[#FFF] border-3 border-black/20 rounded-xl ${marginAutoSide}`}>
            {/*LEFT CIRCLE*/}
            {
                isLeft && <Circle bgColor={bgColor} isLeft={isLeft}/>
            }

            {/*TEXT AND INPUT*/}
            <div className="flex flex-col gap-10 py-4 max-w-xl">
                <div className="flex flex-col gap-4 text-start">
                    <h3 className={`${textColor} text-3xl font-semibold`}>{title}</h3>
                    <hr className="border-t-2 border-black border-gray"/>
                    <p>{description}</p>
                </div>
                <textarea
                    ref={textareaRef}
                    rows={1}
                    placeholder="Type your answer here..."
                    onInput={handleInput}
                    className=" bg-gray border-2 py-1 px-2 border-black/10 rounded-sm
                    font-medium resize-none overflow-hidden min-h-[30px] max-h-50"
                />
            </div>

            {/*RIGHT CIRCLE*/}
            {
                !isLeft && <Circle bgColor={bgColor} isLeft={isLeft}/>
            }
        </div>
    )

}

function Circle({bgColor, isLeft}: { bgColor: string, isLeft: boolean }) {

    return (
        <div className={`flex items-stretch w-2/6 ${isLeft ? 'justify-end' : 'justify-start'}`}>
            <div className={`${bgColor} h-full aspect-square rounded-full `}></div>
        </div>
    )
}

export default RoadmapInput;