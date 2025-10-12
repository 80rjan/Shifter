// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-expect-error
import ShifterArrow from "../../public/Shifter-Arrow-White.png";
// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-expect-error
import ShifterLogo from "../../public/Shifter-S2W-Transparent.png";
import {Link, useNavigate} from "react-router-dom";
import { usePersonalizeHook } from "../hooks/usePersonalizeHook.tsx";
import { Box, Step, StepLabel, Stepper } from "@mui/material";
import { CustomStepperConnector, CustomStepperStepIcon } from "../components/registerSteps/CustomStepper.tsx";
import { AnimatePresence, motion } from "framer-motion";
import { useTranslation } from "react-i18next";

function Personalize() {
    const { t } = useTranslation("personalize");
    const {
        verificationChecked,
        isVerified,
        isLoading,
        activeStep,
        showError,
        error,
        direction,
        variants,
        stepsContent,
        handleNext,
        handleBack,
        handlePersonalize
    } = usePersonalizeHook();
    const navigate = useNavigate();

    if (!verificationChecked || !isVerified) {
        navigate("/")
        return undefined;
    }

    return (
        <main className="flex font-montserrat h-screen bg-beige">

            {/* LEFT HEADER AND BACKGROUND */}
            <section className="relative bg-black w-[55%] overflow-hidden">
                <div className="absolute w-full h-full bg-shifter/80 z-0 text-white px-16 flex flex-col gap-4 justify-center text-start">
                    <img src={ShifterArrow} alt="Shifter Arrow" className="absolute -top-20 right-20 -rotate-130 w-auto h-100 opacity-70 z-2"/>
                    <img src={ShifterArrow} alt="Shifter Arrow" className="absolute -bottom-20 left-20 rotate-50 w-auto h-100 opacity-70 z-2"/>
                    <h1 className="text-7xl font-semibold z-2 whitespace-nowrap">{t("title")}</h1>
                    <p className="text-2xl font-light z-2">{t("subtitle")}</p>
                </div>
            </section>

            {/* RIGHT FORM CONTAINER */}
            <section className="relative flex flex-col justify-center items-center flex-1 px-horizontal-md gap-6">
                <div className="absolute top-0 px-4 py-4 flex w-full justify-between items-center">
                    <Link to={"/"} >
                        <img src={ShifterLogo} alt="Shifter Logo" className="w-40 h-auto object-contain"/>
                    </Link>
                    <Link to={"/"} className="hover:bg-shifter/20 hover:text-shifter underline decoration-current font-semibold text-black/80 rounded-sm px-4 py-2">
                        {t("backToMain")}
                    </Link>
                </div>

                {isVerified ? (
                    <div className="flex flex-col items-center justify-center">
                        <div className="flex flex-col gap-4 bg-white rounded-2xl shadow-lg p-8 w-fit text-center">
                            <h2 className="text-2xl font-bold text-red">{t("verificationFailedTitle")}</h2>
                            <p className="font-medium text-black-text/80 max-w-xl mx-auto">
                                {t("verificationFailedMessage1")}
                                <br/>
                                {t("verificationFailedMessage2")}
                            </p>
                        </div>
                    </div>
                ) : (
                    <Box className="w-full flex flex-col">
                        <Stepper activeStep={activeStep} alternativeLabel connector={<CustomStepperConnector/>}>
                            {stepsContent.map((_, index) => (
                                <Step key={index}>
                                    <StepLabel StepIconComponent={CustomStepperStepIcon} className="text-shifter font-semibold"/>
                                </Step>
                            ))}
                        </Stepper>

                        <Box className="flex flex-col overflow-hidden gap-2">
                            <AnimatePresence mode="wait" initial={false} custom={direction}>
                                <motion.div
                                    key={activeStep}
                                    custom={direction}
                                    variants={variants}
                                    initial="enter"
                                    animate="center"
                                    exit="exit"
                                    transition={{ x: { type: "spring", stiffness: 500, damping: 40 }, opacity: { duration: 0.2 } }}
                                    className="h-80 flex flex-col justify-center"
                                >
                                    {stepsContent[activeStep]}
                                </motion.div>
                            </AnimatePresence>

                            {showError && <p className="text-red-500 text-sm">{error}</p>}

                            <Box className="flex flex-col justify-center items-center gap-2">
                                <div className="flex justify-center gap-4 w-full">
                                    <button
                                        disabled={activeStep === 0}
                                        onClick={handleBack}
                                        className="disabled:opacity-50 disabled:cursor-not-allowed disabled:hover:shadow-none hover:shadow-sm hover:shadow-black/20 transition-all duration-200 ease-in-out border-3 border-white/50 w-1/3 py-1 bg-black/10 text-black/60 cursor-pointer rounded-sm"
                                    >
                                        {t("back")}
                                    </button>

                                    {activeStep === stepsContent.length - 1 ? (
                                        <button
                                            onClick={handlePersonalize}
                                            className={`hover:shadow-md hover:shadow-shifter/60 transition-all duration-200 ease-in-out px-8 border-3 border-white/50 bg-shifter text-white cursor-pointer rounded-md ${isLoading ? "opacity-50 cursor-not-allowed" : ""}`}
                                        >
                                            {isLoading ? t("settingUp") : t("startUsing")}
                                        </button>
                                    ) : (
                                        <button
                                            onClick={handleNext}
                                            className="disabled:opacity-50 disabled:cursor-not-allowed disabled:hover:shadow-none hover:shadow-md hover:shadow-shifter/60 transition-all duration-200 ease-in-out w-1/3 border-3 border-white/50 bg-shifter text-white cursor-pointer rounded-md"
                                        >
                                            {t("next")}
                                        </button>
                                    )}

                                    {isLoading && <div className="h-full loader"></div>}
                                </div>
                            </Box>
                        </Box>
                    </Box>
                )}
            </section>
        </main>
    );
}

export default Personalize;
