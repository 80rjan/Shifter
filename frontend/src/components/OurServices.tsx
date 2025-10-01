import {ArrowBigUpDash, BookOpen, GraduationCap, Handshake} from "lucide-react";
import {Link} from "react-router-dom";

function OurServices() {
    const cardContent = [
        {
            title: "Mentoring",
            description: "Personalized mentorship to help you overcome challenges and achieve your goals.",
            icon: Handshake,
            link: "/mentoring"
        },
        {
            title: "Courses",
            description: "On-demand courses to guide your journey at every stage.",
            icon: BookOpen,
            link: "/courses"
        },
        {
            title: "Consulting",
            description: "Targeted consulting to solve specific challenges and deliver immediate results.",
            icon: ArrowBigUpDash ,
            link: "/consulting"
        },
        {
            title: "Academies",
            description: "Structured group learning designed to help you grow and succeed together.",
            icon: GraduationCap,
            link: "/academies"
        }
    ]

    return (
        <section className="grid grid-cols-3 gap-20 justify-between py-vertical-lg pt-top-nav-lg px-horizontal-md">

            {/*TEXT*/}
            <div className="col-span-1 col-start-1 flex flex-col gap-2 text-black-text text-left w-full">
                <h2 className="text-5xl font-bold">Solutions That Deliver Real Results</h2>
                <p className="text-xl font-light">
                    Shifter focuses on clear, practical steps that drive measurable growth.
                </p>
            </div>

            {/*CARDS*/}
            <div className="col-span-2 col-start-2 grid grid-cols-2 grid-rows-2 gap-8">
                {
                    cardContent.map((card, index) => (
                        <div key={index}
                             className={`shadow-md shadow-black/20
                             ${index === 0 ? "bg-shifter/100 -rotate-3" : "bg-black/20"}
                             ${index === 0 ? "text-white" : "text-black-text"}
                            flex flex-col gap-8 items-start text-left border-2 border-white/40 rounded-2xl p-8 `}>
                            <card.icon size={40} strokeWidth={1.5}
                                       color={index === 0 ? "var(--color-white)" : "var(--color-black-text)"} className=""/>
                            <div className="flex flex-col gap-2 justify-between">
                                <h2 className="text-2xl font-bold">{card.title}</h2>
                                <p className="text-lg font-light">{card.description}</p>
                                <Link to={card.link} className="underline mt-4" >Show Service</Link>
                            </div>
                        </div>
                    ))
                }
            </div>
        </section>
    )
}

export default OurServices;