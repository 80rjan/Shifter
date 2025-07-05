import type {Course} from "../types/Course.tsx";
import React from "react";
import StarFilled from "../assets/icons/StarFilled.tsx";
import {hexToRgb} from "../utils/hexToRGB.ts";



// interface CardInterface {
//     title: string;
//     skills: string[];
//     price: number;
//     duration_hours: number;
//     number_modules: number;
//     image: string;
//     imageAlt: string;
//     color: string;
// }


function CourseCard({ card }: {card: Course}) {
    const [isHovered, setIsHovered] = React.useState<boolean>(false);
    const bgColor = "bg-[var(--card-color)]";
    const shadowColor = `rgba(${hexToRgb(card.color)}, 0.6)`;

    return (
        <article
            style={{"--card-color": card.color} as React.CSSProperties}
            className="border-1 border-black/10 shadow-md shadow-black/10
                flex flex-col w-full rounded-xl gap-4 py-4 px-4 bg-[#FFFFFF]">

            {/*IMAGE*/}
            <div className="overflow-clip rounded-md rounded-br-4xl rounded-tl-4xl">
                <img src={card.imageUrl} alt={card.title}
                     className="w-full h-[180px] object-cover"/>
            </div>


            {/*TITLE AND TOPICS LEARNED*/}
            <div className="flex flex-col gap-2 items-start text-left h-full">
                {/*Title*/}
                <h3 className="font-semibold text-xl">{card.titleShort}</h3>

                {/*What will be learned*/}
                <p className="text-black/60">{
                    card.whatWillBeLearned.map(item =>
                        item
                            .toLowerCase()
                            .replace(/_/g, " ")
                            .replace(/\b\w/g, c => c.toUpperCase())
                    )
                        .join(" • ")
                }</p>
            </div>

            {/*INFO*/}
            <div className="flex flex-wrap gap-2 whitespace-nowrap">
                <div className="flex items-center gap-1 px-2 border-1 border-black/20 rounded-sm text-black/60">
                    <StarFilled className="w-4 h-4 text-gold"/> {card.rating / card.ratingCount}
                </div>
                <div className="flex items-center gap-1 px-2 border-1 border-black/20 rounded-sm text-black/60">
                    {card.ratingCount} reviews
                </div>
                <div className="flex items-center gap-1 px-2 border-1 border-black/20 rounded-sm text-black/60">
                    {card.durationHours} hours
                </div>
                <div className="flex items-center gap-1 px-2 border-1 border-black/20 rounded-sm text-black/60">
                    {0} modules
                </div>
                <div className="flex items-center gap-1 px-2 border-1 border-black/20 rounded-sm text-black/60">
                    {card.difficulty.charAt(0) + card.difficulty.slice(1).toLowerCase()}
                </div>
            </div>

            {/*BUTTON AND PRICE*/}
            <div className="flex justify-between items-center mt-0">
                <p className={`font-bold text-black/80 text-lg ${card.price == 0 && "font-normal"}`}>{card.price > 0 ? "$"+card.price : "Free"}</p>
                <button
                    style={isHovered ?
                        {boxShadow: `0 4px 6px -1px ${shadowColor},  0 2px 4px -2px ${shadowColor}`} :
                        {}
                    }
                    className={`transition-all duration-200 ease-in-out cursor-pointer
                    px-8 py-1 ${bgColor} text-white rounded-md border-3 border-white/40 }`}
                    onMouseEnter={() => setIsHovered(true)}
                    onMouseLeave={() => setIsHovered(false)}
                >
                    Explore
                </button>
            </div>

        </article>
    );
}

export default CourseCard;