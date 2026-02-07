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
            <div className="relative">
                <div className="absolute h-full w-1/3 bg-cta/10 rounded-md right-1/2 translate-x-1/2 top-0 shadow-xl z-1"/>

                <table className="relative z-10 w-full text-left table-fixed">
                    <thead>
                    <tr className="border-b-2 border-black/5 text-2xl">
                        <th></th>
                        <th className="text-center font-semibold">
                            <div className="flex justify-center">
                                <img src={logo} alt={"Shifter Logo"} className="h-16 object-contain" />
                            </div>
                        </th>
                        <th className="py-6 text-center font-semibold text-black/60">Traditional Approach</th>
                    </tr>
                    </thead>
                    <tbody className="text-lg">
                    {
                        rows.map((row, i) => (
                            <tr key={i} className="border-b-2 border-black/5">
                                <td className="pl-6 py-6 text-left font-semibold">
                                    {row.challenge}
                                </td>
                                <td className="pl-6 py-6 font-medium">
                                    <div className="flex gap-4 items-center">
                                        <Check color="var(--color-cta)" size={20} strokeWidth={4}/>
                                        {row.shifterApproach}
                                    </div>
                                </td>
                                <td className="pl-6 py-6 text-black/60 font-medium">
                                    <div className="flex gap-4 items-center">
                                        <X color="var(--color-black)" size={20} strokeWidth={4} className="opacity-40"/>
                                        {row.traditionalApproach}
                                    </div>
                                </td>
                            </tr>
                        ))
                    }
                    </tbody>
                </table>
            </div>
        </section>
    )
}