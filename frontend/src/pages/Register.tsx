import React, {useEffect} from "react";
import {Eye, EyeOff} from "lucide-react";
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
import { motion, AnimatePresence } from "framer-motion";


function StepOne({setUser, user, setCanContinue}: { setUser: React.Dispatch<React.SetStateAction<User>>, user: User, setCanContinue: React.Dispatch<React.SetStateAction<boolean>>}) {
    const [showPassword, setShowPassword] = React.useState(false);
    const [showError, setShowError] = React.useState(false);
    const [error, setError] = React.useState("");

    useEffect(() => {
        function hasUppercase(str: string) {
            return /[A-Z]/.test(str);
        }
        function hasDigit(str: string) {
            return /\d/.test(str);
        }
        function hasSpecialChar(str: string) {
            return /[^A-Za-z0-9]/.test(str);
        }

        console.log(user.password, user.passwordConfirmation);
        if (!user.password || !user.passwordConfirmation) {

        } else if (user.password && user.passwordConfirmation && user.password !== user.passwordConfirmation) {
            setShowError(true);
            setError("Passwords do not match");
            setCanContinue(false);
        } else if (!hasUppercase(user.password)) {
            setShowError(true);
            setError("Password must contain at least one uppercase letter");
            setCanContinue(false);
        } else if (!hasDigit(user.password)) {
            setShowError(true);
            setError("Password must contain at least one digit");
            setCanContinue(false);
        } else if (!hasSpecialChar(user.password)) {
            setShowError(true);
            setError("Password must contain at least one special character");
            setCanContinue(false);
        } else {
            setError("");
            setShowError(false);
            setCanContinue(true);
        }

    }, [user.password, user.passwordConfirmation])

    return (
        <div className="flex flex-col gap-4 w-full items-center">
            <Input
                placeholder={"name@email.com"}
                label={"Email address"}
                name={"email"}
                type={"email"}
                id={"email"}
                setUser={setUser}
                user={user}
            />
            <Input
                placeholder={"********"}
                label={"Password"}
                name={"password"}
                type={"password"}
                id={"password"}
                showPassword={showPassword}
                setShowPassword={setShowPassword}
                setUser={setUser}
                user={user}
            />
            <Input
                placeholder={"********"}
                label={"Confirm password"}
                name={"passwordConfirmation"}
                type={"password"}
                id={"password-confirmation"}
                showPassword={showPassword}
                setShowPassword={setShowPassword}
                setUser={setUser}
                user={user}
            />
            {
                showError && (
                    <p className="text-red-500 font-semibold text-sm">
                        {error}
                    </p>
                )
            }
        </div>
    );
}

function StepTwo({setUser, user}: { setUser: React.Dispatch<React.SetStateAction<User>>, user: User }) {
    return (
        <div className="flex flex-col gap-4 w-full items-center">
            <Input
                placeholder={"John Doe"}
                label={"Full Name"}
                name={"fullName"}
                type={"text"}
                id={"full-name"}
                setUser={setUser}
                user={user}
            />
            <Input
                placeholder={"Your Position"}
                label={"Work Position"}
                name={"workPosition"}
                type={"text"}
                id={"work-position"}
                setUser={setUser}
                user={user}
            />
            <SelectInput
                label={"Company Type"}
                name={"companyType"}
                id={"company-type"}
                options={["Freelance", "Startup", "SME", "Mid Market", "Enterprise", "Other"]}
                setUser={setUser}
                user={user}
            />
        </div>
    );
}

function StepThree({setUser, user}: { setUser: React.Dispatch<React.SetStateAction<User>>, user: User }) {
    const interests = [
        "Sales Strategies",
        "Marketing",
        "Leadership",
        "Management",
        "Digital Transformation",
        "Business Transformation",
        "Entrepreneurship",
        "Startup",
        "Sales",
        "Negotiation",
        "Finance For Business"
    ]

    return (
        <div className="flex flex-col gap-4 w-full items-center">
            <SliderInput
                label={"Select topics that inspire you"}
                name={"interests"}
                id={"interests"}
                options={interests}
                setUser={setUser}
                user={user}
            />
        </div>
    )
}

function StepFour({setUser, user}: { setUser: React.Dispatch<React.SetStateAction<User>>, user: User }) {
    const skills = [
        "Strategic Thinking",
        "Strategic Planning",
        "Business Development",
        "Project Management",
        "Operation Management",
        "Logistics",
        "Business Model Creation",
        "Risk Management",
        "Startup Methodologies",

        "Marketing",
        "Digital Marketing",
        "Traditional Marketing",
        "Branding",
        "Sales",
        "Sales Techniques",
        "B2b Tactics",
        "B2c Tactics",
        "Seo",
        "Google Analytics",
        "Ecommerce Platforms",

        "Leadership",
        "People Management",
        "Team Building",
        "Delegation",
        "Conflict Resolution",
        "Decision Making",
        "Performance Evaluation",
        "Change Management",

        "Communication",
        "Emotional Intelligence",
        "Time Management",
        "Adaptability",
        "Critical Thinking",
        "Creativity",
        "Problem Solving",

        "Opportunity Identification",
        "Fundraising Capital",
        "Innovation Management",

        "Ai Tools",

        "Business Law",
        "Business Ethics"
    ];


    return (
        <div className="flex flex-col gap-4 w-full">
            <SliderInput
                label={"Identify your strengths"}
                name={"skills"}
                id={"skills"}
                options={skills}
                setUser={setUser}
                user={user}
            />
        </div>
    )
}

function StepFive({setUser, user}: { setUser: React.Dispatch<React.SetStateAction<User>>, user: User }) {
    const skills = [
        "Strategic Thinking",
        "Strategic Planning",
        "Business Development",
        "Project Management",
        "Operation Management",
        "Logistics",
        "Business Model Creation",
        "Risk Management",
        "Startup Methodologies",

        "Marketing",
        "Digital Marketing",
        "Traditional Marketing",
        "Branding",
        "Sales",
        "Sales Techniques",
        "B2b Tactics",
        "B2c Tactics",
        "Seo",
        "Google Analytics",
        "Ecommerce Platforms",

        "Leadership",
        "People Management",
        "Team Building",
        "Delegation",
        "Conflict Resolution",
        "Decision Making",
        "Performance Evaluation",
        "Change Management",

        "Communication",
        "Emotional Intelligence",
        "Time Management",
        "Adaptability",
        "Critical Thinking",
        "Creativity",
        "Problem Solving",

        "Opportunity Identification",
        "Fundraising Capital",
        "Innovation Management",

        "Ai Tools",

        "Business Law",
        "Business Ethics"
    ];


    return (
        <div className="flex flex-col gap-4 w-full">
            <SliderInput
                label={"Select skills you want to develop"}
                name={"skillsGap"}
                id={"skills-gap"}
                options={skills}
                setUser={setUser}
                user={user}
            />
        </div>
    )
}


function Register() {
    const [activeStep, setActiveStep] = React.useState(0);
    const [canContinue, setCanContinue] = React.useState(true);
    const [direction, setDirection] = React.useState(0); // 1 for next, -1 for back
    const [user, setUser] = React.useState<User>({
        email: "",
        password: "",
        passwordConfirmation: "",
        fullName: "",
        workPosition: "",
        companyType: "",
        interests: [],
        skills: [],
        skillsGap: [],
    });


    const stepsContent = [<StepOne setUser={setUser} user={user} setCanContinue={setCanContinue}/>, <StepTwo setUser={setUser} user={user}/>,
        <StepThree setUser={setUser} user={user}/>, <StepFour setUser={setUser} user={user}/>,
        <StepFive setUser={setUser} user={user}/> ];

    const handleNext = () => {
        setDirection(1);
        setActiveStep((prev) => prev + 1);
    };
    const handleBack = () => {
        setDirection(-1);
        setActiveStep((prev) => prev - 1);
    };
    const handleReset = () => {
        setDirection(0);
        setActiveStep(0);
    }
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
                                    x: { type: "spring", stiffness: 500, damping: 40 },
                                    opacity: { duration: 0.2 },
                                }}
                                className="h-80 flex flex-col justify-center"
                            >
                                {stepsContent[activeStep]}
                            </motion.div>
                        </AnimatePresence>



                        {/*STEPPER BUTTONS*/}
                        <Box className="flex flex-col justify-center items-center gap-2">
                            <div className="flex justify-center gap-4 ">
                                <button
                                    disabled={activeStep === 0}
                                    onClick={handleBack}
                                    className="hover:shadow-sm hover:shadow-black/20 transition-all duration-200 ease-in-out
                                border-3 border-white/50 px-10 py-2 bg-black/10 text-black/60 cursor-pointer rounded-sm "
                                >
                                    Back
                                </button>
                                {activeStep === stepsContent.length - 1 ? (
                                    <button
                                        onClick={handleReset}
                                        className="hover:shadow-md hover:shadow-shifter/60 transition-all duration-200 ease-in-out
                                    px-20 border-3 border-white/50 bg-shifter text-white cursor-pointer rounded-md"
                                    >
                                        Start Your Journey
                                    </button>
                                ) : (
                                    <button
                                        onClick={() => {
                                            if (canContinue) {
                                                handleNext();
                                            }
                                        }}
                                        className="hover:shadow-md hover:shadow-shifter/60 transition-all duration-200 ease-in-out
                                    px-20 border-3 border-white/50 bg-shifter text-white cursor-pointer rounded-md"
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

function Input(inputProps: InputProps) {

    return (
        <div
            className="w-8/10 relative flex flex-col items- gap-1 px-6 py-1 border-2 border-shifter group focus-within:border-l-20 transition-all ease-in-out duration-300 items-start rounded-sm">
            <label
                htmlFor={inputProps.id}
                className="text-shifter font-medium"
            >
                {inputProps.label}
            </label>
            <div className="flex gap-2 w-full">
                <div className="w-full">
                    <input
                        id={inputProps.id}
                        type={!inputProps.name.includes("password") ? "text" : inputProps.showPassword ? "text" : "password"}
                        name={inputProps.name}
                        placeholder={inputProps.placeholder}
                        className="w-full focus:outline-none text-lg"
                        value={inputProps.user[inputProps.name] || ""}
                        onChange={e =>
                            inputProps.setUser((prev: User) => ({
                                ...prev,
                                [inputProps.name]: e.target.value
                            }))
                        }
                    />
                    <hr className="border-t-2 border-black/5 rounded-full w-full"/>
                </div>
                {inputProps.name.includes("password") && (
                    <button
                        type="button"
                        onClick={() => inputProps.setShowPassword?.((prev: boolean) => !prev)}
                        className="text-black cursor-pointer hover:bg-black/5 rounded-full p-1"
                        aria-label={inputProps.showPassword ? "Hide password" : "Show password"}
                    >
                        {!inputProps.showPassword ? (
                            <EyeOff size={20} opacity={0.6}/>
                        ) : (
                            <Eye size={20} opacity={0.6}/>
                        )}
                    </button>
                )}
            </div>
        </div>
    );
}

function SelectInput(selectProps: SelectProps) {

    return (
        <div
            className="w-8/10 relative flex flex-col gap-1 px-6 py-1 border-2 border-shifter group transition-all ease-in-out duration-300 items-start rounded-sm">
            <label
                htmlFor={selectProps.id}
                className="text-shifter font-medium"
            >
                {selectProps.label}
            </label>
            <div className="w-full">
                <select
                    id={selectProps.id}
                    name={selectProps.name}
                    className="w-full focus:outline-none text-lg cursor-pointer"
                    onChange={e =>
                        selectProps.setUser((prev: User) => ({
                            ...prev,
                            [selectProps.name]: e.target.value
                        }))
                    }
                >
                    {
                        selectProps.options?.map((option, index) => (
                            <option key={index} value={option}>
                                {option}
                            </option>
                        ))
                    }
                </select>
                <hr className="border-t-2 border-black/5 rounded-full w-full"/>
            </div>
        </div>
    );
}

function SliderInput(sliderProps: SliderProps) {

    return (
        <div
            className="flex flex-col justify-center gap-4 px-6 py-1 items-start w-full">
            <label
                htmlFor={sliderProps.id}
                className="text-shifter font-medium text-xl"
            >
                {sliderProps.label}
            </label>
            <div className="custom-scrollbar flex gap-2 flex-wrap w-full max-h-[30vh] items-center  overflow-y-auto">
                {
                    sliderProps.options?.map((option, index) => {
                        const isSelected = sliderProps.user[sliderProps.name]?.includes(option) || false;

                        return (
                            <button
                                key={index}
                                name={sliderProps.name}
                                id={`${sliderProps.id}-${index}`}
                                className={`${isSelected ? "bg-shifter text-white" : "bg-black/10 text-black"} px-4 py-1 rounded-md 
                                transition-all duration-200 ease-in-out hover:shadow-md ${isSelected ? "shadow-black/20" : "shadow-shifter/20"}
                                focus:outline-none cursor-pointer whitespace-nowrap`}
                                onClick={() => {
                                    sliderProps.setUser((prev: User) => {
                                        const arr = prev[sliderProps.name] as string[] || [];

                                        const newArr = arr.includes(option)
                                            ? arr.filter(item => item !== option)
                                            : [...arr, option];

                                        return {
                                            ...prev,
                                            [sliderProps.name]: newArr,
                                        };
                                    });
                                }}

                            >
                                {option}
                            </button>
                        )
                    })
                }
            </div>

        </div>
    );
}

interface User {
    email: string;
    password: string;
    passwordConfirmation: string;
    fullName: string;
    workPosition: string;
    companyType: string;
    interests: string[];
    skills: string[];
    skillsGap: string[];
}

type UserStrFields = 'email' | 'password' | 'passwordConfirmation' | 'fullName' | 'workPosition' | 'companyType';
interface InputProps {
    placeholder: string;
    label: string;
    name: UserStrFields;
    type: string;
    id: string;
    showPassword?: boolean;
    setShowPassword?: React.Dispatch<React.SetStateAction<boolean>>;
    setUser: React.Dispatch<React.SetStateAction<User>>;
    user: User;
}

interface SelectProps {
    label: string;
    name: string;
    id: string;
    options?: string[];
    setUser: React.Dispatch<React.SetStateAction<User>>;
    user: User;
}

type UserArrayFields = 'interests' | 'skills' | 'skillsGap';

interface SliderProps {
    label: string;
    name: UserArrayFields;
    id: string;
    options?: string[];
    setUser: React.Dispatch<React.SetStateAction<User>>;
    user: User;
}

export default Register;
