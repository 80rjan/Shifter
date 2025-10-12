import { useTranslation } from "react-i18next";

function ShifterValues() {
    const { t } = useTranslation("home");

    const values = t("shifterValues.valuesList", { returnObjects: true }) as string[];

    return (
        <section className="flex px-horizontal-md py-vertical-lg gap-20">
            <div className="flex flex-col gap-12 w-1/2">
                <div className="flex flex-col gap-8 text-left">
                    <h2 className="text-5xl" dangerouslySetInnerHTML={{ __html: t("shifterValues.heading") }} />
                    <p className="text-xl">{t("shifterValues.description")}</p>
                </div>

                <div className="flex gap-2">
                    <div className="flex flex-col text-center gap-2 text-shifter text-5xl">
                        {values.map((v, i) => (
                            <strong key={i}>{v[0]}</strong>
                        ))}
                    </div>
                    <div className="flex flex-col text-2xl items-start font-light">
                        {values.map((v, i) => (
                            <span key={i} className="h-1/5 flex items-end pb-1">{v.slice(1)}</span>
                        ))}
                    </div>
                </div>

                <button className="hover:shadow-shifter/40 hover:shadow-lg transition-all duration-300 ease-in-out cursor-pointer
                    bg-shifter rounded-md border-3 border-white/40 py-2
                    text-white text-xl shadow-md shadow-shifter/20 font-medium w-7/10"
                >
                    {t("shifterValues.button")}
                </button>
            </div>

            <div className="relative w-1/2 flex items-center justify-start">
                <div className="rounded-full bg-dark-gray/30 w-70 h-70"></div>
                <div className="absolute left-70 top-1/5 rounded-full bg-dark-gray/30 w-35 h-35"></div>
                <div className="absolute left-70 bottom-1/5 rounded-full bg-dark-gray/30 w-35 h-35"></div>
                <div className="absolute right-20 top-1/2 transform -translate-x-1/2 -translate-y-1/2 rounded-full bg-dark-gray/30 w-20 h-20"></div>
                <div className="absolute left-1/2 top-1/10 transform -translate-x-1/2 -translate-y-1/2 rounded-full bg-dark-gray/30 w-20 h-20"></div>
                <div className="absolute left-2/5 -bottom-1/10 transform -translate-x-1/2 -translate-y-1/2 rounded-full bg-dark-gray/30 w-30 h-30"></div>
                <div className="absolute left-1/5 bottom-4 transform -translate-x-1/2 rounded-full bg-dark-gray/30 w-20 h-20"></div>
                <div className="absolute left-2/7 -top-2 transform -translate-x-1/2 rounded-full bg-dark-gray/30 w-30 h-30"></div>
            </div>
        </section>
    );
}

export default ShifterValues;
