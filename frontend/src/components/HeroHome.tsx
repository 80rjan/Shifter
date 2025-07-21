import StarFilled from "../assets/icons/StarFilled.tsx";
import ShifterArrow from "../../public/Shifter-Arrow.png";
import {Link} from 'react-router-dom';

function HeroHome() {

    return (
        <section
            className="relative flex flex-col gap-0
        items-center pt-top-nav-md bg-white w-full rounded-b-[60px]">
            <h1 className="text-6xl">
                Business <strong className="text-shifter">Excellence</strong>
                <br/>
                Powered by <strong className="text-shifter">Expertise</strong>
            </h1>

            {/*<img src={ShifterRocket} alt="Shifter Rocket Image"*/}
            {/*     className="absolute left-30 rotate-50 w-20 h-auto"/>*/}

            <div className="relative z-1 px-horizontal-md bg-white rounded-b-[60px] flex justify-between items-center w-full">

                {/*LEFT TEXT*/}
                <div className="relative max-w-sm text-left">
                    <img src={ShifterArrow} alt="Shifter Arrow"
                         className="absolute left-5 -top-30 h-35 w-30 rotate-40 opacity-20"/>
                    <img src={ShifterArrow} alt="Shifter Arrow"
                         className="absolute -left-25 top-5 h-35 w-30 -rotate-140 opacity-20"/>

                    <p className="text-lg text-black leading-relaxed">
                        We guide businesses from the basics of planning to complete transformations, offering
                        expert
                        mentoring, consulting, and e-learning. Whether you're starting small or aiming for major
                        growth,
                        we provide the support and tools to achieve lasting success!
                    </p>
                </div>

                {/*CENTER IMAGE*/}
                <div className="flex justify-center items-center w-fit h-fit overflow-clip">
                    <div className="relative -bottom-20 bg-dark-gray/20 w-100 h-100 rounded-full"></div>

                    {/*CTA BUTTONS*/}
                    <div
                        className="absolute bottom-5 flex gap-2 bg-gray/20 backdrop-blur-lg p-1 rounded-full border-3 border-black/5 text-md">
                        <Link
                            to="/"
                            className="hover:shadow-lg hover:shadow-shifter/50 transition-all duration-200 ease-in-out cursor-pointer
                        rounded-full text-white px-8 py-3 bg-shifter border-3 border-white/50 font-semibold
                        shadow-md shadow-shifter/30">
                            Book a Free Consultation
                        </Link>
                        <Link
                            to="/courses"
                            className="hover:shadow-lg hover:shadow-shifter/50 transition-all duration-200 ease-in-out cursor-pointer
                        rounded-full text-shifter px-8 py-3 bg-white border-3 border-shifter/50 font-bold
                        shadow-md shadow-shifter/30">Explore
                            Our Courses
                        </Link>
                    </div>
                </div>

                {/*RIGHT STATISTICS*/}
                <div className="flex flex-col gap-4 items-center">
                    <div className=" grid grid-cols-2 grid-rows-2 gap-x-12 gap-y-6">
                        <p className="text-right min-w-fit">
                            <span className="text-3xl font-bold">20+</span> <br/>
                            <span className="whitespace-nowrap font-light">Years Experience</span>
                        </p>
                        <p className="text-right">
                            <span className="text-3xl font-bold">300+</span> <br/>
                            <span className="whitespace-nowrap font-light">Clients Empowered</span>
                        </p>
                        <p className="text-right">
                            <span className="text-3xl font-bold">10+</span> <br/>
                            <span className="whitespace-nowrap font-light">Courses Available</span>
                        </p>
                        <p className="text-right">
                            <span className="text-3xl font-bold">5000+</span> <br/>
                            <span className="whitespace-nowrap font-light">Mentoring Hours</span>
                        </p>
                        {/*<p className="text-right">*/}
                        {/*    <span className="text-3xl font-bold">2</span> <br/>*/}
                        {/*    <span className="whitespace-nowrap font-light">Expert Mentors</span>*/}
                        {/*</p>*/}
                    </div>
                    <div className="flex gap-1 text-gold">
                        <StarFilled className="w-10 h-10 opacity-80"/>
                        <StarFilled className="w-10 h-10 opacity-80"/>
                        <StarFilled className="w-10 h-10 opacity-80"/>
                        <StarFilled className="w-10 h-10 opacity-80"/>
                        <StarFilled className="w-10 h-10 opacity-80"/>
                    </div>
                </div>
            </div>

            <div
                className="shadow-md shadow-black/80
                absolute bottom-0 w-full h-40 z-0 rounded-b-[60px]"
            />
        </section>
    )
}

export default HeroHome