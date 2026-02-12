import {Check, X} from "lucide-react";
import logo from "../../public/Shifter-Transparent.png"
import {useTranslation} from "react-i18next";

type DifferenceRow = {
    challenge: string;
    shifterApproach: string;
    traditionalApproach: string;
};

export default function ShifterDifferenceTable() {
    const {t} = useTranslation("home");

    const rows = t("shifterDifferenceTable.rows", {
        returnObjects: true,
    }) as DifferenceRow[];

    return (
        <section className="px-horizontal py-vertical">
            <div className="relative max-w-screen-2xl mx-auto flex flex-col gap-12
                lg:gap-16">

                {/* MOBILE & TABLET: Modern Card Layout */}
                <div className="lg:hidden flex flex-col gap-10 sm:gap-12 md:gap-16">

                    {/* Section Header */}
                    <header className="text-center flex flex-col items-center gap-4">
                        {/*<img src={logo} alt="Shifter Logo" className="h-12 sm:h-14 md:h-16 object-contain"/>*/}
                        <h2 className="text-2xl font-bold text-black-text sm:text-3xl md:text-4xl">
                            {t("shifterDifferenceTable.heading.theShifterDifference")}
                        </h2>
                    </header>

                    {/* Cards Container */}
                    <div className="flex flex-col gap-6 sm:gap-8 md:gap-10">
                        {rows.map((row, i) => (
                            <article key={i} className="relative overflow-hidden rounded-2xl border border-black/10
                                shadow-lg bg-white">

                                {/* Challenge Header */}
                                <div className="bg-gradient-to-r from-cta/10 to-cta/5 px-6 py-5
                                    sm:px-7 sm:py-6 md:px-8 md:py-7 border-b border-cta/20">
                                    <h3 className="text-xl font-bold text-black-text sm:text-2xl md:text-3xl">
                                        {row.challenge}
                                    </h3>
                                </div>

                                {/* Comparison Content */}
                                <div className="flex flex-col sm:flex-row">

                                    {/* Shifter Approach - Left/Top */}
                                    <div className="flex-1 p-6 sm:p-7 md:p-8 bg-cta/5 sm:border-r sm:border-cta/10">
                                        <div className="flex items-center gap-2 mb-4 sm:mb-5">
                                            <div className="w-6 h-6 rounded-full bg-cta/20 flex items-center justify-center
                                                sm:w-7 sm:h-7 flex-shrink-0">
                                                <Check color="var(--color-cta)" className="w-3.5 h-3.5 sm:w-4 sm:h-4" strokeWidth={3}/>
                                            </div>
                                            <span className="text-xs font-bold text-cta uppercase tracking-wide
                                                sm:text-sm
                                                md:text-base">
                                                {t("shifterDifferenceTable.heading.withShifter")}
                                            </span>
                                        </div>
                                        <p className="text-lg font-semibold text-black-text leading-relaxed
                                            sm:text-xl md:text-2xl md:leading-relaxed">
                                            {row.shifterApproach}
                                        </p>
                                    </div>

                                    {/* Traditional Approach - Right/Bottom */}
                                    <div className="flex-1 p-6 sm:p-7 md:p-8 bg-black/[0.02]">
                                        <div className="flex items-center gap-2 mb-4 sm:mb-5">
                                            <div className="w-6 h-6 rounded-full bg-black/10 flex items-center justify-center
                                                sm:w-7 sm:h-7 flex-shrink-0">
                                                <X color="var(--color-black)" className="w-3.5 h-3.5 opacity-40 sm:w-4 sm:h-4" strokeWidth={3}/>
                                            </div>
                                            <span className="text-xs font-bold text-black/40 uppercase tracking-wide
                                                sm:text-sm
                                                md:text-base">
                                                {t("shifterDifferenceTable.heading.withoutShifter")}
                                            </span>
                                        </div>
                                        <p className="text-lg font-medium text-black/60 leading-relaxed
                                            sm:text-xl md:text-2xl md:leading-relaxed">
                                            {row.traditionalApproach}
                                        </p>
                                    </div>
                                </div>

                                {/* Accent Bar */}
                                <div className="h-1 bg-gradient-to-r from-cta via-cta/80 to-cta/50"/>
                            </article>
                        ))}
                    </div>
                </div>

                {/* DESKTOP: Table Layout */}
                <div className="hidden lg:block relative">
                    {/* Modern gradient background for Shifter column */}
                    <div className="absolute h-full w-1/3 bg-gradient-to-b from-cta/10 via-cta/15 to-cta/10
                        rounded-2xl right-1/2 translate-x-1/2 top-0 shadow-xl z-1
                        border-2 border-cta/20"/>

                    <table className="relative z-10 w-full text-left table-fixed">
                        <thead>
                        <tr className="border-b-2 border-black/5">
                            <th className="py-6 xl:py-8"></th>
                            <th className="py-6 text-center xl:py-8">
                                <div className="flex flex-col items-center gap-2
                                    xl:gap-3">
                                    <img src={logo} alt="Shifter Logo" className="h-14 object-contain
                                        xl:h-16
                                        2xl:h-20"/>
                                </div>
                            </th>
                            <th className="py-6 text-center xl:py-8">
                                <span className="text-xl font-semibold text-black/60
                                    xl:text-2xl
                                    2xl:text-3xl">
                                    {t("shifterDifferenceTable.heading.traditionalApproach")}
                                </span>
                            </th>
                        </tr>
                        </thead>
                        <tbody className="text-base
                            xl:text-lg
                            2xl:text-xl">
                        {
                            rows.map((row, i) => (
                                <tr key={i} className="border-b-2 border-black/5">
                                    <td className="pl-6 py-6 text-left font-semibold text-lg
                                        xl:pl-8 xl:py-8 xl:text-xl
                                        2xl:pl-10 2xl:py-10 2xl:text-2xl">
                                        {row.challenge}
                                    </td>
                                    <td className="pl-6 py-6 font-medium
                                        xl:pl-8 xl:py-8
                                        2xl:pl-10 2xl:py-10">
                                        <div className="flex gap-3 items-center font-semibold text-lg
                                            xl:gap-4 xl:text-xl
                                            2xl:gap-5 2xl:text-2xl">
                                            <Check color="var(--color-cta)" className="w-5 h-5 flex-shrink-0
                                                xl:w-6 xl:h-6
                                                2xl:w-7 2xl:h-7" strokeWidth={4}/>
                                            {row.shifterApproach}
                                        </div>
                                    </td>
                                    <td className="pl-6 py-6 text-black/60 font-medium
                                        xl:pl-8 xl:py-8
                                        2xl:pl-10 2xl:py-10">
                                        <div className="flex gap-3 items-center text-lg
                                            xl:gap-4 xl:text-xl
                                            2xl:gap-5 2xl:text-2xl">
                                            <X color="var(--color-black)" className="w-5 h-5 opacity-40 flex-shrink-0
                                                xl:w-6 xl:h-6
                                                2xl:w-7 2xl:h-7" strokeWidth={4}/>
                                            {row.traditionalApproach}
                                        </div>
                                    </td>
                                </tr>
                            ))
                        }
                        </tbody>
                    </table>
                </div>
            </div>
        </section>
    )
}