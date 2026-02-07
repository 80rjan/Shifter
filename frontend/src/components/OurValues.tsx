import {useTranslation} from "react-i18next";

export default function OurValues() {
    const {t} = useTranslation("about");

    const values = t("values", { returnObjects: true }) as {char: string, title: string, description: string}[]

    return (
        <section className="relative flex items-center overflow-clip py-vertical">
            <div className="absolute left-0 rounded-full w-6/10 min-h-full aspect-square bg-shifter"/>
            <div className="absolute left-0 w-1/4 h-full aspect-square bg-shifter"/>

            <div className="flex items-center justify-center min-w-6/10 z-1">
                <h2 className="text-6xl text-left text-white-text leading-tight"
                    dangerouslySetInnerHTML={{ __html: t("shiftBusinesses") }}></h2>
            </div>
            <div className="flex flex-col gap-10 z-1 pr-horizontal">
                {
                    values.map((value, index) => (
                        <div key={index}
                             className={`flex gap-8 items-center
                                 ${(index === 0 || index === 4) && "-translate-x-25"}
                                 ${(index === 1 || index === 3) && "-translate-x-15"}
                                 ${index === 2  && "-translate-x-10"}
                                 `}>
                            <div className="min-w-20 aspect-square flex items-center justify-center rounded-full bg-white
                                    border-1 border-black/10 shadow-sm font-bold text-3xl text-shifter">
                                {value.char}
                            </div>

                            <div className="flex flex-col gap-1 text-left">
                                <h3 className="text-xl font-semibold">{value.title}</h3>
                                <p className="font-light">{value.description}</p>
                            </div>
                        </div>
                    ))
                }
            </div>
        </section>
    )
}