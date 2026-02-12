import {useTranslation} from "react-i18next";

export default function OurValues() {
    const {t} = useTranslation("about");

    const values = t("values", { returnObjects: true }) as {char: string, title: string, description: string}[]

    return (
        <section className="relative bg-shifter text-white py-vertical overflow-hidden">
            {/* Gradient overlays for smooth blending */}
            <div className="absolute top-0 w-full h-[30%]" style={{
                background: "linear-gradient(to top, rgba(0,113,158,0) 0%, rgba(250,251,252,0.03) 15%, rgba(250,251,252,0.08) 30%, rgba(250,251,252,0.2) 50%, rgba(250,251,252,0.5) 70%, rgba(250,251,252,0.8) 85%, rgba(250,251,252,1) 100%)",
                pointerEvents: "none",
            }} />
            <div className="absolute bottom-0 w-full h-[30%]" style={{
                background: "linear-gradient(to bottom, rgba(0,113,158,0) 0%, rgba(250,251,252,0.03) 15%, rgba(250,251,252,0.08) 30%, rgba(250,251,252,0.2) 50%, rgba(250,251,252,0.5) 70%, rgba(250,251,252,0.8) 85%, rgba(250,251,252,1) 100%)",
                pointerEvents: "none",
            }} />

            <div className="max-w-screen-2xl mx-auto flex flex-col gap-12 z-10 relative px-horizontal
                sm:gap-16
                md:gap-20
                lg:gap-24">

                {/* Section Header */}
                <header className="flex flex-col gap-4 text-center
                    sm:gap-5
                    md:gap-6">
                    <h2 className="text-3xl font-bold
                        sm:text-4xl
                        md:text-5xl
                        lg:text-6xl
                        xl:text-7xl"
                        dangerouslySetInnerHTML={{ __html: t("shiftBusinesses") }}></h2>
                </header>

                {/* Values Grid */}
                <div className="grid grid-cols-1 gap-6
                    sm:grid-cols-2 sm:gap-8
                    md:gap-10
                    lg:grid-cols-6 lg:gap-8
                    xl:gap-10
                    2xl:gap-12">
                    {values.map((value, index) => (
                        <article key={index}
                                 className={`
                                     flex flex-col gap-4 items-center text-center p-6 rounded-2xl
                                     bg-white/10 backdrop-blur-sm border-2 border-white/20
                                     hover:bg-white/15 transition-all duration-300
                                     shadow-md hover:shadow-lg hover:-translate-y-1
                                     sm:gap-5 sm:p-7
                                     md:gap-6 md:p-8
                                     lg:p-8 lg:col-span-2
                                     xl:p-10
                                     ${index === values.length - 1 ? 'sm:col-span-2 sm:justify-self-center sm:w-[calc(50%-1rem)] md:w-[calc(50%-1.25rem)] lg:w-auto lg:col-span-2' : ''}
                                     ${index === values.length - 2 ? 'lg:col-start-2' : ''}
                                 `}>

                            {/* Character Badge */}
                            <div className="w-16 h-16 flex items-center justify-center rounded-full
                                bg-white text-shifter font-bold text-2xl
                                border-2 border-white/40 shadow-md
                                sm:w-18 sm:h-18 sm:text-3xl
                                md:w-20 md:h-20 md:text-4xl
                                lg:w-20 lg:h-20 lg:text-3xl
                                xl:w-24 xl:h-24 xl:text-4xl">
                                {value.char}
                            </div>

                            {/* Content */}
                            <div className="flex flex-col gap-2">
                                <h3 className="text-xl font-bold
                                    sm:text-2xl
                                    md:text-2xl
                                    lg:text-xl
                                    xl:text-2xl
                                    2xl:text-3xl">
                                    {value.title}
                                </h3>
                                <p className="text-sm font-light text-white/80
                                    sm:text-base
                                    md:text-base
                                    lg:text-sm
                                    xl:text-base
                                    2xl:text-lg">
                                    {value.description}
                                </p>
                            </div>
                        </article>
                    ))}
                </div>
            </div>
        </section>
    )
}