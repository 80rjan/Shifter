import {useEffect, useState} from "react";
import {useAuthContext} from "../context/AuthContext.tsx";
import type {UserPersonalization} from "../models/javaObjects/UserPersonalization.tsx";
import {useNavigate} from "react-router-dom";
import PersonalizeStepOne from "../components/registerSteps/PersonalizeStepOne.tsx";
import PersonalizationStepTwo from "../components/registerSteps/PersonalizationStepTwo.tsx";
import PersonalizationStepThree from "../components/registerSteps/PersonalizationStepThree.tsx";

export function usePersonalizeHook() {
    const {verify, personalize} = useAuthContext();
    const [verificationChecked, setVerificationChecked] = useState<boolean>(false);
    const [isVerified, setIsVerified] = useState(false);
    const [isLoading, setIsLoading] = useState<boolean>(false);
    const [activeStep, setActiveStep] = useState(0);
    const [showError, setShowError] = useState(false);
    const [error, setError] = useState("");
    const [direction, setDirection] = useState(0); // 1 for next, -1 for back
    const [user, setUser] = useState<UserPersonalization>({
        name: "",
        email: "",
        workPosition: "",
        companySize: "",
        interests: [],
        desiredSkills: [],
    });
    const navigate = useNavigate();

    const handleNext = async () => {
        if (error.length > 0) {
            setShowError(true);
            return;
        }

        setError("");
        setShowError(false);
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

    const stepsContent = [
        <PersonalizeStepOne setUser={setUser} user={user} setError={setError}/>,
        <PersonalizationStepTwo setUser={setUser} user={user} setError={setError}/>,
        <PersonalizationStepThree setUser={setUser} user={user} setError={setError}/>
    ];


    const handleVerify = async () => {
        const params = new URLSearchParams(window.location.search);
        const verificationToken = params.get("token");

        verify(verificationToken || "")
            .then((userEmail) => {
                setUser({
                    ...user,
                    email: userEmail,
                })
                setIsVerified(true);
            })
            .catch(err => {
                setIsVerified(false);
                setError("Verification failed. Please try again.");
                setShowError(true);
                console.error("Error verifying user " + err);
            })
            .finally(() => setVerificationChecked(true))
    }

    const handlePersonalize = async () => {
        if (!isVerified) {
            setError("Verification failed. Please try again.");
            setShowError(true);
            return;
        }
        setIsLoading(true);
        personalize(user)
            .then(() => {
                navigate("/");
            })
            .catch(err => {
                setError("Personalization failed. Please try again.");
                setShowError(true);
                console.error("Error personalizing account for user: ", err);
            })
            .finally(() => setIsLoading(false));
    }


    useEffect(() => {
        handleVerify()
    }, [])

    return {
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
    }
}