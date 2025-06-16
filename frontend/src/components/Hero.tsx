import ShifterRocket from "../assets/Shifter-Rocket-Blue.png";
import StarFilled from "../assets/icons/StarFilled.tsx";
import ShifterArrow from "../assets/Shifter-Arrow.png";

function Hero() {
    return (
        <section className=" bg-dark-blue w-full pb-2 font-montserrat">
            <div className="pt-30 shadow-md shadow-black/80 flex flex-col gap-20 items-center py-10 px-20 w-full bg-white rounded-b-4xl">
                <h1 className="font-regular text-5xl">
                    Business <strong className="text-deep-green">Excellence</strong>
                    <br/>
                    Powered by <strong className="text-deep-green">Expertise</strong>
                </h1>

                <div className="flex flex-col items-center w-full">
                    <div className="flex justify-between items-center w-full">
                        {/*LEFT TEXT*/}
                        <div className="relative max-w-xs font-medium">
                            <img src={ShifterArrow} alt="Shifter Arrow"
                                 className="absolute left-5 -top-30 h-50 w-40 rotate-40 opacity-50"/>
                            <img src={ShifterArrow} alt="Shifter Arrow"
                                 className="absolute -left-35 top-15 h-50 w-40 -rotate-140 opacity-50"/>

                            <p className="text-bold">
                                We guide businesses from the basics of planning to complete transformations, offering
                                expert
                                mentoring, consulting, and e-learning. Whether you're starting small or aiming for major
                                growth,
                                we provide the support and tools to achieve lasting success.
                            </p>
                        </div>

                        {/*CENTER IMAGE*/}
                        <div className="flex justify-center items-center">
                            <img src={ShifterRocket} alt="Shifter Rocket Image" className="w-40 h-auto"/>
                        </div>

                        {/*RIGHT STATISTICS*/}
                        <div className="flex flex-col gap-8 items-center">
                            <div className="grid grid-cols-2 grid-rows-2 gap-4">
                                <p className="text-right min-w-fit">
                                    <span className="text-3xl font-bold">20+</span> <br/>
                                    <span className="whitespace-nowrap">Years Experience</span>
                                </p>
                                <p className="text-right">
                                    <span className="text-3xl font-bold">300+</span> <br/>
                                    <span className="whitespace-nowrap">Clients Empowered</span>
                                </p>
                                <p className="text-right">
                                    <span className="text-3xl font-bold">10+</span> <br/>
                                    <span className="whitespace-nowrap">Courses Available</span>
                                </p>
                                <p className="text-right">
                                    <span className="text-3xl font-bold">2</span> <br/>
                                    <span className="whitespace-nowrap">Expert Mentors</span>
                                </p>
                            </div>
                            <div className="flex gap-1">
                                <StarFilled className="w-8 h-8 text-deep-green" />
                                <StarFilled className="w-8 h-8 text-deep-green" />
                                <StarFilled className="w-8 h-8 text-deep-green" />
                                <StarFilled className="w-8 h-8 text-deep-green" />
                                <StarFilled className="w-8 h-8 text-deep-green" />
                            </div>
                        </div>
                    </div>

                    {/*CTA BUTTONS*/}
                    <div className="flex gap-2 bg-gray-200 p-1 rounded-full border-3 border-black/5 w-fit">
                        <button
                            className="hover:scale-105 transition-transform duration-200 ease-in-out cursor-pointer
                            rounded-full text-white px-6 py-1 bg-deep-green border-3 border-white/50 font-medium shadow-md shadow-deep-green/30">
                            Book a Free Consultation
                        </button>
                        <button
                            className="hover:scale-105 transition-transform duration-200 ease-in-out cursor-pointer
                            rounded-full text-deep-green px-6 py-1 bg-white border-3 border-deep-green/50 font-semibold shadow-md shadow-deep-green/30">Explore
                            Our Courses
                        </button>
                    </div>
                </div>

            </div>
        </section>
    )
}

export default Hero