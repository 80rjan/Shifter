import {Check, X} from "lucide-react";
// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-expect-error
import logo from "../../public/Shifter-Transparent.png"
import {useTranslation} from "react-i18next";

type DifferenceRow = {
    challenge: string;
    shifterApproach: string;
    traditionalApproach: string;
};

export default function ShifterDifferenceTable() {
    const { t } = useTranslation("home");

    const rows = t("shifterDifferenceTable.rows", {
        returnObjects: true,
    }) as DifferenceRow[];

    return (
        <section className="px-horizontal py-vertical">
            <div className="relative max-w-screen-2xl mx-auto">

                {/* MOBILE & TABLET: Card Layout */}
                <div className="lg:hidden flex flex-col gap-6
                    sm:gap-8
                    md:gap-10">

                    {/* Header */}
                    <div className="flex justify-center mb-4">
                        <img src={logo} alt="Shifter Logo" className="h-12 object-contain
                            sm:h-14
                            md:h-16" />
                    </div>

                    {rows.map((row, i) => (
                        <div key={i} className="flex flex-col gap-4 border-2 border-black/10 rounded-xl p-4
                            sm:p-6
                            md:p-8">

                            {/* Challenge */}
                            <h3 className="text-lg font-bold text-black-text
                                sm:text-xl
                                md:text-2xl">
                                {row.challenge}
                            </h3>

                            {/* Shifter Approach */}
                            <div className="flex flex-col gap-2 bg-cta/10 rounded-lg p-4
                                sm:p-5
                                md:p-6">
                                <div className="flex items-center gap-2 text-xs font-semibold text-cta uppercase
                                    sm:text-sm
                                    md:text-base">
                                    <Check size={16} strokeWidth={4} className="sm:w-5 sm:h-5 md:w-6 md:h-6"/>
                                    Shifter
                                </div>
                                <p className="text-sm font-medium
                                    sm:text-base
                                    md:text-lg">
                                    {row.shifterApproach}
                                </p>
                            </div>

                            {/* Traditional Approach */}
                            <div className="flex flex-col gap-2 bg-black/5 rounded-lg p-4
                                sm:p-5
                                md:p-6">
                                <div className="flex items-center gap-2 text-xs font-semibold text-black/60 uppercase
                                    sm:text-sm
                                    md:text-base">
                                    <X size={16} strokeWidth={4} className="opacity-40 sm:w-5 sm:h-5 md:w-6 md:h-6"/>
                                    Traditional
                                </div>
                                <p className="text-sm font-medium text-black/60
                                    sm:text-base
                                    md:text-lg">
                                    {row.traditionalApproach}
                                </p>
                            </div>
                        </div>
                    ))}
                </div>

                {/* DESKTOP: Table Layout */}
                <div className="hidden lg:block relative">
                    <div className="absolute h-full w-1/3 bg-cta/10 rounded-md right-1/2 translate-x-1/2 top-0 shadow-xl z-1"/>

                    <table className="relative z-10 w-full text-left table-fixed">
                        <thead>
                        <tr className="border-b-2 border-black/5 text-xl
                            xl:text-2xl
                            2xl:text-3xl">
                            <th></th>
                            <th className="text-center font-semibold">
                                <div className="flex justify-center">
                                    <img src={logo} alt="Shifter Logo" className="h-14 object-contain
                                        xl:h-16
                                        2xl:h-20" />
                                </div>
                            </th>
                            <th className="py-6 text-center font-semibold text-black/60
                                xl:py-8">
                                Traditional Approach
                            </th>
                        </tr>
                        </thead>
                        <tbody className="text-base
                            xl:text-lg
                            2xl:text-xl">
                        {
                            rows.map((row, i) => (
                                <tr key={i} className="border-b-2 border-black/5">
                                    <td className="pl-6 py-6 text-left font-semibold
                                        xl:pl-8 xl:py-8
                                        2xl:pl-10 2xl:py-10">
                                        {row.challenge}
                                    </td>
                                    <td className="pl-6 py-6 font-medium
                                        xl:pl-8 xl:py-8
                                        2xl:pl-10 2xl:py-10">
                                        <div className="flex gap-3 items-center
                                            xl:gap-4
                                            2xl:gap-5">
                                            <Check color="var(--color-cta)" className="w-5 h-5
                                                xl:w-6 xl:h-6
                                                2xl:w-7 2xl:h-7" strokeWidth={4}/>
                                            {row.shifterApproach}
                                        </div>
                                    </td>
                                    <td className="pl-6 py-6 text-black/60 font-medium
                                        xl:pl-8 xl:py-8
                                        2xl:pl-10 2xl:py-10">
                                        <div className="flex gap-3 items-center
                                            xl:gap-4
                                            2xl:gap-5">
                                            <X color="var(--color-black)" className="w-5 h-5 opacity-40
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