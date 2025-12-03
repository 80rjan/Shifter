import {useEffect, useRef, useState} from "react";
import {useAuthContext} from "../context/AuthContext.tsx";
import type {UserPersonalization} from "../models/javaObjects/UserPersonalization.tsx";
import {useNavigate} from "react-router-dom";
import PersonalizeStepOne from "../components/registerSteps/PersonalizeStepOne.tsx";
import PersonalizationStepTwo from "../components/registerSteps/PersonalizationStepTwo.tsx";
import {verifyEmailApi} from "../api/verificationTokenApi.ts";

export function usePersonalizeHook() {
    const {verify, personalize, accessToken } = useAuthContext();
    const verificationChecked = useRef(false);
    const [isVerified, setIsVerified] = useState(false);
    const [isNewVerificationEmailSent, setIsNewVerificationEmailSent] = useState(false);
    const [error, setError] = useState("");
    const [showError, setShowError] = useState(false);
    const [isLoading, setIsLoading] = useState<boolean>(false);
    const [activeStep, setActiveStep] = useState(0);
    const [direction, setDirection] = useState(0); // 1 for next, -1 for back
    const [user, setUser] = useState<UserPersonalization>({
        name: "",
        email: "",
        workPosition: "",
        companySize: "",
        language: "EN",
        attributeIdList: []
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
        // <PersonalizationStepTwo setUser={setUser} user={user} setError={setError}/>
    ];


    const handleVerify = async () => {
        const params = new URLSearchParams(window.location.search);
        const verificationToken = params.get("token");

        verify(verificationToken || "")
            .then(userMail => {
                setIsVerified(true);
                setUser(prev => ({
                    ...prev,
                    email: userMail
                }))
            })
            .catch(err => {
                setIsVerified(false);
                console.error("Error verifying user " + err);
            })
            .finally(() => verificationChecked.current = true)
    }

    const handlePersonalize = async () => {
        if (!isVerified) {
            return;
        }
        if (error.length > 0) {
            setShowError(true)
            return;
        }

        setIsLoading(true);
        personalize(user)
            .then(() => {
                navigate("/");
            })
            .catch(err => {
                console.error("Error personalizing account for user: ", err);
            })
            .finally(() => setIsLoading(false));
    }

    const sendNewVerificationEmail = async () => {
        setIsLoading(true);

        verifyEmailApi(accessToken || "")
            .then(() => {
                setIsNewVerificationEmailSent(true);
            })
            .catch(err => {
                setIsNewVerificationEmailSent(false);
                console.error("Error sending verification token to email ", err);
            })
            .finally(() => {
                setIsLoading(false)
            })
    }


    useEffect(() => {
        if (!verificationChecked.current) {
            handleVerify();
            verificationChecked.current = true; // <-- SET REF HERE SYNCHRONOUSLY
        }
    }, [])

    return {
        error,
        showError,
        isNewVerificationEmailSent,
        verificationChecked,
        isVerified,
        isLoading,
        activeStep,
        direction,
        variants,
        stepsContent,
        handleNext,
        handleBack,
        handlePersonalize,
        verifyEmail: sendNewVerificationEmail,
    }
}