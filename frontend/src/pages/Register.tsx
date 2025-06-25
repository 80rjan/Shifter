import React from "react";
import ShifterLogo from "../assets/Shifter-Logo-S2W-Transparent.png";
import ShifterArrow from "../assets/Shifter-Arrow-White.png";
import {
    Stepper,
    Step,
    StepLabel,
    Box,
} from "@mui/material";
import {CustomStepperConnector, CustomStepperStepIcon} from "../components/CustomStepper";
import {Link} from "react-router-dom";
import {motion, AnimatePresence} from "framer-motion";
import axios from "axios";
import type { User } from "../types/User.tsx";
import RegisterStepOne from "../components/steps/RegisterStepOne.tsx";
import RegisterStepTwo from "../components/steps/RegisterStepTwo.tsx";
import RegisterStepThree from "../components/steps/RegisterStepThree.tsx";
import RegisterStepFour from "../components/steps/RegisterStepFour.tsx";
import RegisterStepFive from "../components/steps/RegisterStepFive.tsx";

function Register() {
    const [activeStep, setActiveStep] = React.useState(0);
    const [canContinue, setCanContinue] = React.useState(true);
    const [showError, setShowError] = React.useState(false);
    const [error, setError] = React.useState("");
    const [direction, setDirection] = React.useState(0); // 1 for next, -1 for back
    const [user, setUser] = React.useState<User>({
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


    const stepsContent = [
        <RegisterStepOne setUser={setUser} user={user} setCanContinue={setCanContinue} setError={setError}/>,
        <RegisterStepTwo setUser={setUser} user={user} setCanContinue={setCanContinue} setError={setError}/>,
        <RegisterStepThree setUser={setUser} user={user} setCanContinue={setCanContinue} setError={setError}/>,
        <RegisterStepFour setUser={setUser} user={user} setCanContinue={setCanContinue} setError={setError}/>,
        <RegisterStepFive setUser={setUser} user={user} setCanContinue={setCanContinue} setError={setError}/>];

    const handleNext = () => {
        setDirection(1);
        setActiveStep((prev) => prev + 1);
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

    const handleRegister = () => {
        console.log("user", user);
        console.log(import.meta.env.VITE_BACKEND_URL)
        axios.post(`http://localhost:8080/api/auth/register`, user)
            .then(() => {
                console.log("User registered successfully");
            })
            .catch(err => {
                console.log("Error registering user:", err);
            })
    }

    return (
        <section className="flex font-montserrat h-screen">

            {/* LEFT HEADER AND BACKGROUND */}
            <div className="relative bg-black w-[55%] overflow-hidden">
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
            </div>

            {/* RIGHT FORM CONTAINER */}
            <div className="relative flex flex-col justify-center items-center flex-1 px-20 gap-6">
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

                    <Box className="flex flex-col overflow-hidden">

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


                        {/*STEPPER BUTTONS*/}
                        <Box className="flex flex-col justify-center items-center gap-2">
                            {
                                showError && (
                                    <p className="text-red-500 font-medium text-sm">
                                        {error}
                                    </p>
                                )
                            }
                            <div className="flex justify-center gap-4 ">
                                <button
                                    disabled={activeStep === 0}
                                    onClick={handleBack}
                                    className="disabled:opacity-50 disabled:cursor-not-allowed disabled:hover:shadow-none
                                    hover:shadow-sm hover:shadow-black/20 transition-all duration-200 ease-in-out
                                border-3 border-white/50 px-10 py-2 bg-black/10 text-black/60 cursor-pointer rounded-sm "
                                >
                                    Back
                                </button>
                                {activeStep === stepsContent.length - 1 ? (
                                    <button
                                        onClick={handleRegister}
                                        className="hover:shadow-md hover:shadow-shifter/60 transition-all duration-200 ease-in-out
                                    px-20 border-3 border-white/50 bg-shifter text-white cursor-pointer rounded-md"
                                    >
                                        Start Your Journey
                                    </button>
                                ) : (
                                    <button
                                        onClick={() => {
                                            if (canContinue) {
                                                setShowError(false);
                                                handleNext();
                                            } else {
                                                setShowError(true)
                                            }
                                        }}
                                        // disabled={!canContinue}
                                        className={`disabled:opacity-50 disabled:cursor-not-allowed disabled:hover:shadow-none
                                        hover:shadow-md hover:shadow-shifter/60 transition-all duration-200 ease-in-out
                                    px-20 border-3 border-white/50 bg-shifter text-white cursor-pointer rounded-md`}
                                    >
                                        Next
                                    </button>
                                )}
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
            </div>
        </section>
    );
}

export default Register;
