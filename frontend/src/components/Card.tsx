import {BookOpen, Clock3} from "lucide-react";

type ColorKey = "shifter" | "deep-green" | "teal" | "dark-blue" | "red";

interface CardInterface {
    title: string;
    skills: string[];
    price: number;
    duration_hours: number;
    number_modules: number;
    image: string;
    imageAlt: string;
    color: ColorKey;
}


type Props = {
    card: CardInterface
}

function Card({ card }: Props) {
    const colorMap = {
        shifter: {
            bgColor: 'bg-shifter',
            bgColorOpacity: 'bg-shifter/20',
            textColor: 'text-shifter',
            hoverShadow: 'hover:shadow-shifter/50',
            shadow: 'shadow-shifter/50',
        },
        'deep-green': {
            bgColor: 'bg-deep-green',
            bgColorOpacity: 'bg-deep-green/20',
            textColor: 'text-deep-green',
            hoverShadow: 'hover:shadow-deep-green/50',
            shadow: 'shadow-deep-green/50',
        },
        teal: {
            bgColor: 'bg-teal',
            bgColorOpacity: 'bg-teal/20',
            textColor: 'text-teal',
            hoverShadow: 'hover:shadow-teal/50',
            shadow: 'shadow-teal/50',
        },
        'dark-blue': {
            bgColor: 'bg-dark-blue',
            bgColorOpacity: 'bg-dark-blue/20',
            textColor: 'text-dark-blue',
            hoverShadow: 'hover:shadow-dark-blue/50',
            shadow: 'shadow-dark-blue/50',
        },
        red: {
            bgColor: 'bg-red',
            bgColorOpacity: 'bg-red/20',
            textColor: 'text-red',
            hoverShadow: 'hover:shadow-red/50',
            shadow: 'shadow-red/50',
        }
    }
    const { bgColor, bgColorOpacity, textColor, hoverShadow, shadow } = colorMap[card.color];


    return (
        <article className="flex flex-col w-fit max-w-xs rounded-xl gap-4 py-4 px-4 bg-[#FFFFFF] h-full">
            {/*IMAGE*/}
            <div className="overflow-clip rounded-md rounded-br-4xl rounded-tl-4xl">
                <img src={card.image} alt={card.imageAlt}
                     className="w-[300px] h-[150px] object-cover" />
            </div>

            {/*SKILLS*/}
            <ul className="flex flex-wrap gap-2 items-start">
                {card.skills.map((skill, i) => (
                    <li
                        key={i}
                        className={`px-6 py-1 ${bgColorOpacity} ${textColor} text-sm font-medium rounded-md whitespace-nowrap`}
                    >
                        {skill}
                    </li>
                ))}
            </ul>

            {/*TITLE AND INFO*/}
            <div className="flex flex-col justify-between gap-2 items-start text-left h-full">
                {/*Title*/}
                <h3 className="font-semibold text-lg">{card.title}</h3>

                {/*Info*/}
                <div className="flex flex-col gap-2 w-full">
                    <div className="flex gap-4 font-light text-sm">
                        <div className="flex gap-2 items-center">
                            <BookOpen size={24} strokeWidth={1.5} color={`var(--color-${card.color})`} />
                            {card.number_modules} Modules
                        </div>
                        <div className="flex gap-2 items-center">
                            <Clock3 size={24} strokeWidth={1.5} color={`var(--color-${card.color})`} />
                            {card.duration_hours} Hours
                        </div>
                    </div>
                    <hr className="border-t-2 border-dark-gray/30 w-full" />
                </div>
            </div>

            {/*BUTTON AND PRICE*/}
            <div className="flex justify-between mt-0">
                <p className="font-semibold text-lg">$ {card.price}</p>
                <button
                    className={`hover:shadow-lg ${hoverShadow} hover:scale-95 transition-all duration-300 cursor-pointer
                    px-8 py-1 ${bgColor} text-white rounded-md border-3 border-white/30 shadow-md ${shadow}`}
                >
                    Explore
                </button>
            </div>
        </article>
    );
}

export default Card;