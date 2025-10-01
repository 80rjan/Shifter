import React, {useEffect} from "react";
import type {UserRegister} from "../../models/javaObjects/UserRegister.tsx";
import RegisterInput from "../inputs/RegisterInput.tsx";
import RegisterSelect from "../inputs/RegisterSelect.tsx";

function RegisterStepTwo({setUser, user, setError}: {
    setUser: React.Dispatch<React.SetStateAction<UserRegister>>,
    user: UserRegister,
    setError: React.Dispatch<React.SetStateAction<string>>,
}) {

    useEffect(() => {
        if (!user.name || !user.workPosition || !user.companySize) {
            setError("Please ensure all inputs are completed.");
        } else {
            setError("");
        }
    }, [user.name, user.workPosition, user.companySize]);

    return (
        <section
            className="flex flex-col gap-4 w-full items-center">
            <RegisterInput
                placeholder={"John Doe"}
                label={"Full Name"}
                name={"name"}
                type={"text"}
                id={"full-name"}
                setUser={setUser}
                user={user}
            />
            <RegisterInput
                placeholder={"Your Position"}
                label={"Work Position"}
                name={"workPosition"}
                type={"text"}
                id={"work-position"}
                setUser={setUser}
                user={user}
            />
            <RegisterSelect
                label={"Company Size"}
                name={"companySize"}
                id={"company-size"}
                options={["Freelance", "Micro", "Small", "Medium", "Mid Market", "Enterprise", "Other"]}
                setUser={setUser}
                user={user}
            />
        </section>
    );
}

export default RegisterStepTwo;