import RoadmapInput from "./RoadmapInput.tsx";
import ShifterArrow from "../../public/Shifter-Arrow.png";

function RoadmapAI() {
    const roadmapData = [
        {
            title: "Define The Challenge",
            description: "What is your biggest business challenge? (e.g., marketing, scaling, sales, operations, leadership, etc.)",
            textColor: "text-deep-green",
            bgColor: "bg-deep-green/80",
            bgColorPale: "bg-deep-green/40",
        },
        {
            title: "Set Clear Goals",
            description: "What do you want to achieve? (e.g., increase revenue, build a team, launch a product, improve strategy, etc.)",
            textColor: "text-shifter",
            bgColor: "bg-shifter/80",
            bgColorPale: "bg-shifter/40",
        },
        {
            title: "Assess Experience",
            description: "Where are you in your journey? (e.g., startup, growing business, established business, corporate level, etc.)",
            textColor: "text-teal",
            bgColor: "bg-teal/80",
            bgColorPale: "bg-teal/40",
        },
        {
            title: "Pick a Learning Style",
            description: "How do you prefer to learn? (e.g., one-on-one mentoring, group coaching, self-paced courses, workshops, etc.)",
            textColor: "text-dark-blue",
            bgColor: "bg-dark-blue/80",
            bgColorPale: "bg-dark-blue/40",
        },
    ]


    return (
        <section className="relative overflow-clip px-horizontal py-vertical flex flex-col items-center text-black gap-12">
            <img src={ShifterArrow} alt="Shifter Arrow"
                 className="absolute opacity-10 h-180 w-130 rotate-45 -top-20 right-30"/>
            <img src={ShifterArrow} alt="Shifter Arrow"
                 className="absolute opacity-10 h-180 w-130 -rotate-135 -bottom-20 left-30"/>

            {/*HEADER*/}
            <div className="flex flex-col gap-2">
                <h2 className="text-5xl">AI-Powered Roadmap</h2>
                <p className="font-light text-xl">Answer 4 questions, and let AI guide you with a personalized plan to
                    achieve your goals</p>
            </div>

            {/*INPUTS*/}
            <div className="grid grid-cols-[1fr_60px_1fr] w-full">
                <div
                    className="col-start-2 col-span-1 row-start-1 row-span-4 bg-black/10 rounded-full justify-self-center w-1"
                />

                {
                    roadmapData.map((data, idx) => {
                        return (
                            <>
                                <div
                                    className={`col-start-2 row-start-${idx + 1} w-8 aspect-square place-self-center rounded-full overflow-clip bg-white `}>
                                    <div className={`w-full h-full ${data.bgColorPale}`}/>
                                </div>

                                <RoadmapInput
                                    key={idx}
                                    title={data.title}
                                    description={data.description}
                                    textColor={data.textColor}
                                    bgColor={data.bgColor}
                                    index={idx}
                                    isLeft={idx % 2 === 0}
                                />
                            </>
                        )
                    })
                }
            </div>

            <button className="hover:shadow-shifter/40 hover:shadow-lg transition-all duration-300 ease-in-out cursor-pointer
                px-40 py-2 bg-shifter border-3 border-white/40 rounded-md w-fit text-white text-xl font-semibold shadow-lg shadow-shifter/20
                ">Generate roadmap
            </button>
        </section>
    );
}

export default RoadmapAI