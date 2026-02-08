// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-expect-error
import ShifterArrow from "../../public/Shifter-Arrow-White.png";
// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-expect-error
import ShifterLogo from "../../public/Shifter-S2W-Transparent.png";
import { usePersonalizeHook } from "../hooks/usePersonalizeHook.tsx";
import { Box, Step, StepLabel, Stepper } from "@mui/material";
import { CustomStepperConnector, CustomStepperStepIcon } from "../components/registerSteps/CustomStepper.tsx";
import { AnimatePresence, motion } from "framer-motion";
import { useTranslation } from "react-i18next";
import LoadingScreen from "../layout/LoadingScreen.tsx";

function Personalize() {
    const { t } = useTranslation("personalize");
    const {
        error,
        showError,
        isNewVerificationEmailSent,
        isVerified,
        isLoading,
        activeStep,
        direction,
        variants,
        stepsContent,
        handleNext,
        handleBack,
        handlePersonalize,
        verifyEmail,
        verificationChecked
    } = usePersonalizeHook();

    if (!verificationChecked.current)
        return <LoadingScreen />


    return (
        <main className="flex flex-col font-montserrat min-h-screen bg-main
            lg:flex-row lg:h-screen">

            {/* LEFT HEADER AND BACKGROUND - Hidden on mobile, visible on desktop */}
            <section className="hidden lg:block relative bg-black w-full overflow-hidden
                lg:w-[55%]">
                <div className="absolute w-full h-full bg-shifter/80 z-0 text-white px-12 flex flex-col gap-3 justify-center text-start
                    xl:px-20 xl:gap-4">
                    <img src={ShifterArrow} alt="Shifter Arrow"
                         className="absolute -top-16 right-12 -rotate-130 w-auto h-80 opacity-70 z-2
                            xl:-top-20 xl:right-20 xl:h-100" />
                    <img src={ShifterArrow} alt="Shifter Arrow"
                         className="absolute -bottom-16 left-12 rotate-50 w-auto h-80 opacity-70 z-2
                            xl:-bottom-20 xl:left-20 xl:h-100" />

                    <h1 className="text-5xl font-semibold z-2 whitespace-nowrap
                        xl:text-6xl
                        2xl:text-7xl">
                        {t("title")}
                    </h1>
                    <p className="text-xl font-light z-2
                        xl:text-2xl
                        2xl:text-3xl">
                        {t("subtitle")}
                    </p>
                </div>
            </section>

            {/* RIGHT FORM CONTAINER */}
            <section className="relative flex flex-col justify-center items-center flex-1 px-horizontal py-vertical gap-4
                sm:py-20 sm:gap-6">

                {/* Top Navigation */}
                <div className="absolute top-0 px-4 py-4 flex w-full justify-between items-center
                    sm:px-6
                    md:px-8
                    lg:px-8">
                    <div>
                        <img src={ShifterLogo} alt="Shifter Logo"
                             className="w-20 h-auto object-contain
                                sm:w-26
                                md:w-30
                                lg:w-26
                                xl:w-30
                                2xl:w-40" />
                    </div>
                </div>

                {/* Mobile Header - Only visible on mobile */}
                <div className="lg:hidden mb-4 text-center
                    sm:mb-6
                    md:mb-8">
                    <h1 className="text-3xl font-semibold text-shifter
                        sm:text-4xl
                        md:text-5xl">
                        {t("title")}
                    </h1>
                    <p className="text-base font-light text-black/60 mt-2
                        sm:text-lg
                        md:text-xl">
                        {t("subtitle")}
                    </p>
                </div>

                {!isVerified ? (
                    <div className="flex flex-col gap-6 items-center justify-center w-full max-w-md
                        sm:gap-8
                        md:max-w-lg
                        lg:max-w-md
                        xl:max-w-lg">
                        <div className="flex flex-col gap-3 bg-white rounded-2xl shadow-lg p-6 w-full text-center
                            sm:gap-4 sm:p-8">
                            <h2 className="text-xl font-bold text-red
                                sm:text-2xl">{t("verificationFailedTitle")}</h2>
                            <p className="font-medium text-black-text/80 text-sm
                                sm:text-base">
                                {t("verificationFailedMessage1")}
                                <br/>
                                {t("verificationFailedMessage2")}
                            </p>
                        </div>
                        <div className="flex gap-3 items-center
                            sm:gap-4">
                            <button
                                onClick={verifyEmail}
                                className={`${isNewVerificationEmailSent ? "font-semibold border-0 text-base sm:text-xl" : "border-1 hover:shadow-md hover:bg-shifter hover:text-white cursor-pointer"} 
                                transition-all duration-200 ease-in-out py-2 px-8 rounded-md border-shifter text-shifter text-sm
                                sm:px-12 sm:text-base`}
                            >
                                {
                                    isNewVerificationEmailSent ?
                                        t("verificationFailedButtonSuccess") :
                                        isLoading ? t("verificationFailedButtonLoading") : t("verificationFailedButton")
                                }
                            </button>
                            {isLoading && <div className="loader" />}
                        </div>
                    </div>
                ) : (
                    <Box className="w-full flex flex-col max-w-md
                        md:max-w-lg
                        lg:max-w-md
                        xl:max-w-lg">
                        {
                            stepsContent.length > 1 && (
                                <Stepper activeStep={activeStep} alternativeLabel connector={<CustomStepperConnector/>}>
                                    {stepsContent.map((_, index) => (
                                        <Step key={index}>
                                            <StepLabel StepIconComponent={CustomStepperStepIcon} className="text-shifter font-semibold"/>
                                        </Step>
                                    ))}
                                </Stepper>
                            )
                        }


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
                                    className="h-60 flex flex-col justify-center
                                        sm:h-72
                                        md:h-80"
                                >
                                    {stepsContent[activeStep]}
                                </motion.div>
                            </AnimatePresence>

                            {showError && (
                                <p className="text-red-500 text-xs text-center
                                    sm:text-sm">
                                    {error}
                                </p>
                            )}

                            <Box className="flex flex-col justify-center items-center gap-2">
                                <div className="flex flex-col gap-3 justify-center w-full
                                    sm:flex-row sm:gap-4">
                                    <button
                                        disabled={activeStep === 0}
                                        onClick={handleBack}
                                        className="disabled:opacity-50 disabled:cursor-not-allowed disabled:hover:shadow-none hover:shadow-sm hover:shadow-black/20 transition-all duration-200 ease-in-out border-3 border-white/50 w-full py-2 bg-black/10 text-black/60 cursor-pointer rounded-sm text-sm
                                            sm:w-1/3 sm:py-1 sm:text-base"
                                    >
                                        {t("back")}
                                    </button>

                                    {activeStep === stepsContent.length - 1 ? (
                                        <button
                                            onClick={handlePersonalize}
                                            className={`hover:shadow-md hover:shadow-shifter/60 transition-all duration-200 ease-in-out px-6 py-2 border-3 border-white/50 bg-shifter text-white cursor-pointer rounded-md text-sm
                                                sm:px-8 sm:py-1 sm:text-base
                                                ${isLoading ? "opacity-50 cursor-not-allowed" : ""}`}
                                        >
                                            {isLoading ? t("settingUp") : t("startUsing")}
                                        </button>
                                    ) : (
                                        <button
                                            onClick={handleNext}
                                            className="disabled:opacity-50 disabled:cursor-not-allowed disabled:hover:shadow-none hover:shadow-md hover:shadow-shifter/60 transition-all duration-200 ease-in-out w-full py-2 border-3 border-white/50 bg-shifter text-white cursor-pointer rounded-md text-sm
                                                sm:w-1/3 sm:py-1 sm:text-base"
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
