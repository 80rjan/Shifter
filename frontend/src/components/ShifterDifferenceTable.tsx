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

                {/* MOBILE & TABLET: Card Layout */}
                <div className="lg:hidden flex flex-col gap-between">

                    {/* Section Header - Mobile Only */}
                    <header className="text-center">
                        <h2 className="text-2xl font-bold text-black-text
                            sm:text-3xl
                            md:text-4xl">
                            {t("shifterDifferenceTable.heading.theShifterDifference")}
                        </h2>
                    </header>

                    <div className="flex flex-col gap-8
                         sm:gap-10
                         md:gap-12">
                        {rows.map((row, i) => (
                            <article key={i} className="flex flex-col gap-6
                            sm:gap-6">

                                {/* Challenge Title with accent */}
                                <div className="flex items-center gap-3
                                sm:gap-4">
                                    <div className="w-1 h-12 bg-cta rounded-full
                                    sm:h-14
                                    md:h-16"/>
                                    <h3 className="text-xl font-bold text-black-text
                                    sm:text-2xl
                                    md:text-3xl">
                                        {row.challenge}
                                    </h3>
                                </div>

                                {/* Comparison Grid */}
                                <div className="grid grid-cols-1 gap-4
                                sm:grid-cols-2 sm:gap-4">

                                    {/* Shifter Approach */}
                                    <div className="relative p-5 rounded-2xl bg-gradient-to-br from-cta/15 to-cta/5
                                    border-2 border-cta/30 shadow-md
                                    sm:p-6
                                    md:p-8">
                                        {/* Badge */}
                                        <div className="absolute -top-3 left-4 bg-cta text-white px-3 py-1 rounded-full
                                        text-xs font-bold uppercase shadow-md
                                        sm:px-4
                                        md:text-sm">
                                            ✓ {t("shifterDifferenceTable.heading.withShifter")}
                                        </div>

                                        <div className="flex h-full justify-center items-center">
                                        <p className="text-base font-semibold text-black-text
                                            sm:text-lg
                                            md:text-xl">
                                            {row.shifterApproach}
                                        </p>
                                        </div>
                                    </div>

                                    {/* Without Shifter */}
                                    <div className="relative p-5 rounded-2xl bg-black/5
                                    border-2 border-black/10
                                    sm:p-6
                                    md:p-8">
                                        {/* Badge */}
                                        <div className="absolute -top-3 left-4 bg-black/20 text-black/60 px-3 py-1 rounded-full
                                        text-xs font-bold uppercase
                                        sm:px-4
                                        md:text-sm">
                                            ✗ {t("shifterDifferenceTable.heading.withoutShifter")}
                                        </div>

                                        <div className="flex h-full justify-center items-center">
                                        <p className="text-base font-medium text-black/60 vertical-align-center
                                            sm:text-lg
                                            md:text-xl">
                                            {row.traditionalApproach}
                                        </p>
                                        </div>
                                    </div>
                                </div>
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