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
        <section className="bg-dark-blue text-white py-vertical-md px-horizontal flex flex-col gap-12 items-center">
            <h2 className="text-5xl font-light">
                How to Start Your Journey to <strong className="font-bold">Success</strong>
            </h2>

            <div className="relative flex w-full mt-4">
                {/* LINE AND DOTS */}
                <div className="absolute w-[104%] top-1/2 -translate-y-1/2 left-1/2 -translate-x-1/2 -rotate-16">
                    <hr className="border-t-4 border-white rounded-full w-full absolute top-2"
                    />

                    {[-0.2, 25, 50, 75, 99.8].map((percent, idx) => (
                        <div
                            key={idx}
                            className="absolute w-5 h-5 bg-white rounded-full z-20 border-2 border-black/20 top-0"
                            style={{left: `${percent}%`, transform: 'translateX(-50%)'}}
                        />
                    ))}
                </div>


                {steps.map((step, i) => (
                    <section
                        key={i}
                        className={`flex flex-col items-center gap-2 justify-between w-1/4 px-4 
                                border-l-2 ${i === 3 ? 'border-r-2' : ''} ${i < 2 ? 'pb-60' : 'pt-60'} border-white/60
                            `}
                    >
                        {/* Number */}
                        <strong className="text-4xl font-bold text-white/60">{step.number}</strong>
                        {/* Title and description */}
                        <div className="flex flex-col justify-start h-full gap-2">
                            <div className="flex justify-center items-center min-h-[3.5rem]">
                                <h3 className="text-2xl font-semibold text-center line-clamp-2">{step.title}</h3>
                            </div>
                            <p className="font-light text-white/70">{step.description}</p>
                        </div>
                    </section>
                ))}
            </div>

            {/* Button */}
            <button className="hover:shadow-white/40 hover:shadow-lg transition-all duration-300 ease-in-out cursor-pointer
                w-3/10 whitespace-nowrap py-2 bg-white text-xl text-dark-blue rounded-sm font-semibold shadow-md shadow-white/20">
                Start Now
            </button>
        </section>
    );
}
