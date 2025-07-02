import React from "react";
import ShifterLogo from "../assets/shifterImg/Shifter-Logo-S2W-Transparent.png";
import ShifterArrow from "../assets/shifterImg/Shifter-Arrow-White.png";
import {
    Stepper,
    Step,
    StepLabel,
    Box,
} from "@mui/material";
import {CustomStepperConnector, CustomStepperStepIcon} from "../components/CustomStepper";
import {Link, useNavigate} from "react-router-dom";
import {motion, AnimatePresence} from "framer-motion";
import type {UserRegister} from "../types/UserRegister.tsx";
import RegisterStepOne from "../components/steps/RegisterStepOne.tsx";
import RegisterStepTwo from "../components/steps/RegisterStepTwo.tsx";
import RegisterStepThree from "../components/steps/RegisterStepThree.tsx";
import RegisterStepFour from "../components/steps/RegisterStepFour.tsx";
import RegisterStepFive from "../components/steps/RegisterStepFive.tsx";
import {useGlobalContext} from "../GlobalContext.tsx";
import {isValidEmail} from "../utils/validation.ts";
import {checkEmailExistsApi} from "../api/user.ts";

function Register() {
    const {register} = useGlobalContext();
    const [isLoading, setIsLoading] = React.useState<boolean>(false);
    const [activeStep, setActiveStep] = React.useState(0);
    const [showError, setShowError] = React.useState(false);
    const [error, setError] = React.useState("");
    const [direction, setDirection] = React.useState(0); // 1 for next, -1 for back
    const [user, setUser] = React.useState<UserRegister>({
        email: "",
        password: "",
        passwordConfirmation: "",
        name: "",
        workPosition: "",
        companyType: "",
        interests: [],
        skills: [],
        skillsGap: [],
    });
    const navigate = useNavigate();

    const handleNext = async () => {
        if (activeStep === 0) {
            const existsEmail: boolean = isValidEmail(user.email) ? await checkEmailExistsApi(user.email) : false;
            if (existsEmail) {
                setError("Email already exists");
                setShowError(true)
                return
            }
        }

        if (error.length > 0) {
            setShowError(true)
        } else {
            setError("");
            setShowError(false);
            setDirection(1);
            setActiveStep((prev) => prev + 1);
        }
    };
    const handleBack = () => {
        setDirection(-1);
        setActiveStep((prev) => prev - 1);
    };
    const variants = {
        enter: (dir: number) => ({
            x: dir > 0 ? 100 : -100,
            opacity: 0,
        }),
        center: {
            x: 0,
            opacity: 1,
        },
        exit: (dir: number) => ({
            x: dir > 0 ? -100 : 100,
            opacity: 0,
        }),
    };

    const handleRegister = async () => {
        setIsLoading(true);

        try {
            await register(user);
            navigate("/");
        } catch (err: any) {
            setError("Registration failed. Please try again.");
            console.log("Registration error: ", err);
        } finally {
            setIsLoading(false);
        }
    }

    const stepsContent = [
        <RegisterStepOne setUser={setUser} user={user} setError={setError} />,
        <RegisterStepTwo setUser={setUser} user={user} setError={setError} />,
        <RegisterStepThree setUser={setUser} user={user} setError={setError} />,
        <RegisterStepFour setUser={setUser} user={user} setError={setError} />,
        <RegisterStepFive setUser={setUser} user={user} setError={setError} />
    ];

    return (
        <section className="flex font-montserrat h-screen bg-white">

            {/* LEFT HEADER AND BACKGROUND */}
            <section className="relative bg-black w-[55%] overflow-hidden">
                <div
                    className="absolute w-full h-full bg-shifter/80 z-0 text-white px-16 flex flex-col gap-4 justify-center text-start">
                    {/*Arrows*/}
                    <img src={ShifterArrow} alt="Shifter Arrow"
                         className="absolute -top-20 right-20 -rotate-130 w-auto h-100 opacity-70 z-2"/>
                    <img src={ShifterArrow} alt="Shifter Arrow"
                         className="absolute -bottom-20 left-20 rotate-50 w-auto h-100 opacity-70 z-2"/>
                    <h1 className="text-7xl font-semibold z-2 whitespace-nowrap">Shift into Success</h1>
                    <p className="text-2xl font-light z-2">Start your journey toward smarter, scalable business
                        growth.</p>
                </div>
            </section>

            {/* RIGHT FORM CONTAINER */}
            <section className="relative flex flex-col justify-center items-center flex-1 px-20 gap-6">
                <img
                    src={ShifterLogo}
                    alt="Shifter Logo"
                    className="absolute top-4 left-4 w-40 h-auto object-contain"
                />

                {/* STEPPER */}
                <Box className="w-full flex flex-col">
                    <Stepper
                        activeStep={activeStep}
                        alternativeLabel
                        connector={<CustomStepperConnector/>}
                    >
                        {stepsContent.map((_, index) => (
                            <Step key={index}>
                                <StepLabel StepIconComponent={CustomStepperStepIcon}
                                           className="text-shifter font-semibold"
                                />
                            </Step>
                        ))}
                    </Stepper>

                    <Box className="flex flex-col overflow-hidden gap-2">

                        {/*STEPPER CONTENT*/}
                        <AnimatePresence mode="wait" initial={false} custom={direction}>
                            <motion.div
                                key={activeStep}
                                custom={direction}
                                variants={variants}
                                initial="enter"
                                animate="center"
                                exit="exit"
                                transition={{
                                    x: {type: "spring", stiffness: 500, damping: 40},
                                    opacity: {duration: 0.2},
                                }}
                                className="h-80 flex flex-col justify-center"
                            >
                                {stepsContent[activeStep]}
                            </motion.div>
                        </AnimatePresence>

                        {/* Error Message */}
                        {showError && <p className="text-red-500 text-sm">{error}</p>}


                        {/*STEPPER BUTTONS*/}
                        <Box className="flex flex-col justify-center items-center gap-2">
                            <div className="flex justify-center gap-4 ">
                                <button
                                    disabled={activeStep === 0}
                                    onClick={handleBack}
                                    className="disabled:opacity-50 disabled:cursor-not-allowed disabled:hover:shadow-none
                                    hover:shadow-sm hover:shadow-black/20 transition-all duration-200 ease-in-out
                                border-3 border-white/50 px-10 py-1 bg-black/10 text-black/60 cursor-pointer rounded-sm "
                                >
                                    Back
                                </button>
                                {activeStep === stepsContent.length - 1 ? (
                                    <button
                                        onClick={handleRegister}
                                        className={`hover:shadow-md hover:shadow-shifter/60 transition-all duration-200 ease-in-out
                                    px-20 border-3 border-white/50 bg-shifter text-white cursor-pointer rounded-md 
                                    ${isLoading ? "opacity-50 cursor-not-allowed" : ""}`}
                                    >
                                        {
                                            isLoading ? "Setting up..." : "Start Your Journey"
                                        }
                                    </button>
                                ) : (
                                    <button
                                        onClick={handleNext}
                                        // disabled={!canContinue}
                                        className={`disabled:opacity-50 disabled:cursor-not-allowed disabled:hover:shadow-none
                                        hover:shadow-md hover:shadow-shifter/60 transition-all duration-200 ease-in-out
                                    px-20 border-3 border-white/50 bg-shifter text-white cursor-pointer rounded-md`}
                                    >
                                        Next
                                    </button>
                                )}


                                {/* Loading Animation */}
                                {
                                    isLoading && (
                                        <div className="h-full loader"></div>
                                    )
                                }
                            </div>
                            <p
                                className="text-black/40"
                            >
                                Already have an account?
                                <Link to={"/login"}
                                      className="relative text-shifter font-medium w-fit hover:font-semibold"
                                >
                                    {" "}Log In
                                </Link>
                            </p>
                        </Box>
                    </Box>
                </Box>
            </section>
        </section>
    );
}

export default Register;
