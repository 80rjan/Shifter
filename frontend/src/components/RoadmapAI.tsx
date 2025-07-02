import RoadmapInput from "./RoadmapInput.tsx";

function RoadmapAI() {
    const roadmapData = [
        {
            title: "Define The Challenge",
            description: "What is your biggest business challenge? (e.g., marketing, scaling, sales, operations, leadership, etc.)",
            textColor: "text-deep-green",
            bgColor: "bg-deep-green/80",
            marginAutoSide: "mr-auto"
        },
        {
            title: "Set Clear Goals",
            description: "What do you want to achieve? (e.g., increase revenue, build a team, launch a product, improve strategy, etc.)",
            textColor: "text-shifter",
            bgColor: "bg-shifter/80",
            marginAutoSide: "ml-auto"
        },
        {
            title: "Assess Experience",
            description: "Where are you in your journey? (e.g., startup, growing business, established business, corporate level, etc.)",
            textColor: "text-teal",
            bgColor: "bg-teal/80",
            marginAutoSide: "mr-auto"
        },
        {
            title: "Pick a Learning Style",
            description: "How do you prefer to learn? (e.g., one-on-one mentoring, group coaching, self-paced courses, workshops, etc.)",
            textColor: "text-dark-blue",
            bgColor: "bg-dark-blue/80",
            marginAutoSide: "ml-auto"
        },
    ]


    return (
        <section className="px-horizontal py-vertical-md flex flex-col py-vertical-md text-black gap-12">
            {/*HEADER*/}
            <div className="flex flex-col gap-2">
                <h2 className="text-5xl">AI-Powered Roadmap</h2>
                <p className="font-light text-xl">Answer 4 questions, and let AI guide you with a personalized plan to
                    achieve your goals</p>
            </div>

            {/*INPUTS*/}
            <div className="flex flex-col">
                {
                    roadmapData.map((data, i) => {
                        return (
                            <RoadmapInput
                                key={i}
                                title={data.title}
                                description={data.description}
                                textColor={data.textColor}
                                bgColor={data.bgColor}
                                marginAutoSide={data.marginAutoSide}
                                isLeft={data.marginAutoSide !== "mr-auto"}
                                />
                        )
                    })
                }
            </div>
        </section>
    );
}

export default RoadmapAI