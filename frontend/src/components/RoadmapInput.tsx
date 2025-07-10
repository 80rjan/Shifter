import {useRef} from "react";

interface RoadmapInputProps {
    title: string;
    description: string;
    textColor: string;
    bgColor: string;
    isLeft: boolean;
    index: number;
}


function RoadmapInput({ title, description, textColor, bgColor, isLeft, index }: RoadmapInputProps) {
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
            className={`${
                index % 2 === 0 ? 'col-start-1 col-span-1' : 'col-start-3 col-span-1'
            }
            ${
                index === 0 ? 'row-start-1' : 
                    index === 1 ? 'row-start-2' :
                    index === 2 ? 'row-start-3' :
                    index === 3 ? 'row-start-4' : ''
            }
            ${isLeft ? 'pl-4' : 'pr-4'}
            overflow-clip flex gap-16 bg-[#FFF] border-3 border-black/20 rounded-xl h-fit z-10`}>
            {/*LEFT CIRCLE*/}
            {
                !isLeft && <Circle bgColor={bgColor} isLeft={isLeft}/>
            }

            {/*TEXT AND INPUT*/}
            <div className="flex flex-col gap-10 py-4 max-w-xl h-fit">
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
                    className="bg-gray border-2 py-1 px-2 border-black/10 rounded-sm
                    font-medium resize-none overflow-hidden min-h-fit max-h-[5rem]"
                />
            </div>

            {/*RIGHT CIRCLE*/}
            {
                isLeft && <Circle bgColor={bgColor} isLeft={isLeft}/>
            }
        </div>
    )

}

function Circle({bgColor, isLeft}: { bgColor: string, isLeft: boolean }) {

    return (
        <div className={`flex items-stretch w-2/6 ${isLeft ? 'justify-start' : 'justify-end'}`}>
            <div className={`${bgColor} h-full aspect-square rounded-full `}></div>
        </div>
    )
}

export default RoadmapInput;