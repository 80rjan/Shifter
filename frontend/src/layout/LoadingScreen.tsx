import Lottie from "lottie-react";
import logoAnimation from "../assets/animations/shifter_animation.json";
import {useTranslation} from "react-i18next";

export default function LoadingScreen() {
    const { t } = useTranslation("loading");

    return (
        <div className="flex flex-col gap-4 items-center justify-center h-screen bg-main">
            <div className="w-20">
                <Lottie animationData={logoAnimation} loop={true} />
            </div>
            <p className="text-2xl font-medium text-black-text">
                {t("loadingScreen")}
            </p>
        </div>
    )
}