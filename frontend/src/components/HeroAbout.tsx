import { ArrowRight } from "lucide-react";
import CountUp from "react-countup";
import {LocalizedLink} from "./links/LocalizedLink.tsx";

function HeroAbout() {

    return (
        <section className="grid grid-cols-2 gap-x-20 px-horizontal py-vertical pt-nav-top rounded-3xl bg-dark-blue/5
                shadow-md shadow-black/20">

            <div className="col-start-1 col-span-1 flex flex-col text-left gap-12
                text-black-text">
                <div className="flex flex-col gap-4 w-full">
                    <h1 className=" text-5xl font-medium">About <span className="text-shifter font-black">Shifter</span></h1>
                    <p>
                        Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the
                        industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type
                        and scrambled it to make a type specimen book. It has survived not only five centuries, but also the
                        leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s
                        with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop
                        publishing software like Aldus PageMaker including versions of Lorem Ipsum.
                    </p>
                    <LocalizedLink to={"/free-consultation"}
                        className="flex gap-4 items-center text-white bg-shifter px-8 py-2 w-fit rounded-sm">
                        Schedule a Free Consultation
                        <ArrowRight size={20} strokeWidth={1.5} />
                    </LocalizedLink>
                </div>

                <hr className="border-t-2 border-black/20 w-full" />

                <div className="flex justify-between w-full">
                    <div>
                        <h2 className="text-4xl font-bold"><CountUp start={0} end={250} duration={4} separator={"."}  />+</h2>
                        <p className="font-light whitespace-nowrap">Clients Empowered</p>
                    </div>
                    <div>
                        <h2 className="text-4xl font-bold"><CountUp start={0} end={2000} duration={4} separator={"."}  />+</h2>
                        <p className="font-light whitespace-nowrap">Mentoring Hours</p>
                    </div>
                    <div>
                        <h2 className="text-4xl font-bold"><CountUp start={0} end={4} duration={4} separator={"."}  />+</h2>
                        <p className="font-light whitespace-nowrap">Years of Shifter</p>
                    </div>
                </div>
            </div>

            <div className="col-start-2 col-span-1 h-full bg-black/20 rounded-2xl"/>
        </section>
    )
}

export default HeroAbout;