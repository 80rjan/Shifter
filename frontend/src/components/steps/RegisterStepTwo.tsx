import React, {useEffect} from "react";
import type {User} from "../../types/User.tsx";
import RegisterInput from "../inputs/RegisterInput.tsx";
import RegisterSelect from "../inputs/RegisterSelect.tsx";

function RegisterStepTwo({setUser, user, setCanContinue, setError}: {
    setUser: React.Dispatch<React.SetStateAction<User>>,
    user: User,
    setCanContinue: React.Dispatch<React.SetStateAction<boolean>>,
    setError: React.Dispatch<React.SetStateAction<string>>
}) {

    useEffect(() => {
        if (!user.name || !user.workPosition || !user.companyType) {
            setError("Please ensure all inputs are completed.");
            setCanContinue(false);
        } else {
            setError("");
            setCanContinue(true);
        }
    }, [user.name, user.workPosition, user.companyType]);

    return (
        <div className="flex flex-col gap-4 w-full items-center">
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

export default RegisterStepTwo;