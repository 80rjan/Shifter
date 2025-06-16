export default function CollaborationSteps() {
    const steps = [
        {
            number: '01',
            title: 'Schedule a Free Consultation',
            description:
                'Schedule a no-obligation consultation to discuss your challenges, goals, and expectations.',
        },
        {
            number: '02',
            title: 'Share Your Needs & Goals',
            description:
                'Provide clear insights about your business struggles and aspirations to shape the right strategy.',
        },
        {
            number: '03',
            title: 'Review Your Personalized Plan',
            description:
                'Receive a tailored roadmap with clear, actionable steps to help you achieve business success.',
        },
        {
            number: '04',
            title: 'Start Implementing & Collaborating',
            description:
                'Apply expert guidance and proven strategies with continuous support throughout the process.',
        },
    ];


    return (
        <div className="bg-dark-blue text-white py-10 px-20 flex flex-col gap-20 items-center">
            <h1 className="text-3xl font-regular">
                How to Start Your Journey to <strong>Success</strong>
            </h1>

            <div className="flex flex-col gap-1 items-center w-full">
                {/* NUMBERS */}
                <div className="flex justify-between relative w-82/100 text-white/60">
                    {steps.map((step) => (
                        <strong className="text-4xl font-bold">{step.number}</strong>
                    ))}
                </div>

                {/* PROGRESS BAR AND DOTS */}
                <div className="flex justify-between relative w-8/10">
                    {steps.map(() => (
                        <div className="w-5 h-5 bg-white rounded-full z-20 border-2 border-black/20"/>
                    ))}
                    <div
                        className="absolute top-1/2 left-1/2 border-t-4 border-white rounded-full transform -translate-y-1/2 -translate-x-1/2 w-full"
                    ></div>
                </div>

                {/*STEPS*/}
                <div className="flex w-full gap-4 items-stretch mt-4">

                    {steps.map((step, i) => (
                        <div
                            key={i}
                            className="flex flex-col items-center gap-4 justify-between w-1/4"
                        >
                            {/* Title */}
                            <div className="h-full flex items-center justify-center">
                                <h3 className="text-xl font-semibold text-center">{step.title}</h3>
                            </div>
                            {/* Description */}
                            <p className="text-sm text-white/70">{step.description}</p>
                        </div>
                    ))}
                </div>
            </div>

            {/* Button */}
            <button className="px-20 py-2 bg-white text-xl text-dark-blue rounded-sm font-semibold shadow-md shadow-white/20">
                Start Now
            </button>
        </div>
    );
}
