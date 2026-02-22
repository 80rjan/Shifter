import React from "react";
import {Link} from "react-router-dom";
import type {CoursePreview} from "../../models/javaObjects/course/CoursePreview.tsx";
import {fromEnumFormat} from "../../utils/toEnumFormat.ts";
import StarFilled from "../../assets/icons/StarFilled.tsx";
import {capitalize} from "../../utils/capitalize.ts";
import {hexToRgb} from "../../utils/hexToRGB.ts";

export default function ExpertCourseCard({card}: { card: CoursePreview }) {
    const [isHoveredButton, setIsHoveredButton] = React.useState<boolean>(false);
    const bgColor = "bg-[var(--card-color)]";
    const shadowColor = `rgba(${hexToRgb(card.color)}, 0.6)`;


    return (
        <article
            style={{"--card-color": card.color} as React.CSSProperties}
            className="relative border-1 border-black/10 shadow-md shadow-black/10
                flex flex-col w-full rounded-xl gap-4 py-4 px-4 bg-[#FFFFFF]">

            <div className="absolute top-5 right-5 text-xs font-semibold p-2 border-1
                bg-white/60 border-black/20 rounded-sm text-black-text shadow-xs">
                {
                    card.translatedLanguages
                        // .map(lang => lang.toLowerCase())
                        .join(" / ")
                }
            </div>

            {/* IMAGE */}
            <div className="overflow-clip rounded-md rounded-br-4xl rounded-tl-4xl">
                <img src={card.imageUrl} alt={card.title}
                     className="w-full h-[180px] object-cover"/>
            </div>

            {/* TITLE & TOPICS */}
            <div className="flex flex-col gap-2 items-start text-left justify-between h-full">
                <h3 className="font-semibold text-xl">{card.titleShort}</h3>

                <p className="text-black/60">{(() => {
                    const MAX_CHARS = 80;
                    const fullStr = (card.topicsCovered ?? [])
                        .map(item =>
                            fromEnumFormat(item)
                                .replace(/\b\w/g, c => c.toUpperCase())
                        )
                        .join(" • ");
                    if (fullStr.length > MAX_CHARS)
                        return fullStr.slice(0, 80).trim() + "...";
                    return fullStr;
                })()}</p>
            </div>

            {/* INFO */}
            <div className="flex flex-wrap gap-2 whitespace-nowrap">
                <div className="flex items-center gap-1 px-2 border-1 border-black/20 rounded-sm text-black/60">
                    <StarFilled className="w-4 h-4 text-gold"/> {card.averageRating}
                </div>
                <div className="flex items-center gap-1 px-2 border-1 border-black/20 rounded-sm text-black/60">
                    {(card.durationMinutes / 60).toFixed(1)} Hours
                </div>
                <div className="flex items-center gap-1 px-2 border-1 border-black/20 rounded-sm text-black/60">
                    {card.courseContentCount} Modules
                </div>
                <div className="flex items-center gap-1 px-2 border-1 border-black/20 rounded-sm text-black/60">
                    {capitalize(card.difficulty.toLowerCase())}
                </div>
            </div>

            {/* BUTTON & PRICE */}
            <div className="flex justify-between items-center mt-0">
                <p className={`font-bold text-black/80 text-lg ${card.price == 0 && "font-normal"}`}>
                    {card.price > 0 ? `$${card.price}` : "Free"}
                </p>

                <div className="flex items-center gap-2">
                    {
                        card.translatedLanguages.includes("MK") ? (
                            <div className={`cursor-not-allowed transition-all duration-200 ease-in-out opacity-40
                            px-8 py-1 ${bgColor} text-white rounded-md border-3 border-white/40`}>
                                Course Translated
                            </div>
                        ) : (
                            <Link
                                to={"/expert/courses/" + `${card.id}` + "/translate"}
                                style={isHoveredButton ?
                                    {boxShadow: `0 4px 6px -1px ${shadowColor},  0 2px 4px -2px ${shadowColor}`} :
                                    {}
                                }
                                className={`transition-all duration-200 ease-in-out cursor-pointer
                                px-8 py-1 ${bgColor} text-white rounded-md border-3 border-white/40`}
                                onMouseEnter={() => setIsHoveredButton(true)}
                                onMouseLeave={() => setIsHoveredButton(false)}
                            >
                                Translate Course
                            </Link>
                        )
                    }
                </div>
            </div>
        </article>
    );
}
