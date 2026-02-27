import { useEffect, useRef } from "react";
import lottie, {type AnimationItem } from "lottie-web";
import { useTranslation } from "react-i18next";
import logoAnimation from "../assets/animations/shifter_animation.json";

export default function LoadingScreen() {
    const { t } = useTranslation("loading");
    const containerRef = useRef<HTMLDivElement>(null);
    const animationRef = useRef<AnimationItem | null>(null);

    useEffect(() => {
        if (containerRef.current && !animationRef.current) {
            animationRef.current = lottie.loadAnimation({
                container: containerRef.current,
                renderer: 'svg',
                loop: true,
                autoplay: true,
                animationData: logoAnimation
            });
        }

        // Cleanup
        return () => {
            if (animationRef.current) {
                animationRef.current.destroy();
                animationRef.current = null;
            }
        };
    }, []);

    return (
        <div className="flex flex-col gap-4 items-center justify-center h-screen bg-main">
            <div className="w-20" ref={containerRef} />
            <p className="text-2xl font-medium text-black-text">
                {t("loadingScreen")}
            </p>
        </div>
    )
}