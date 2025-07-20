import {useGlobalContext} from "../context/GlobalContext.tsx";
import React, {useState} from "react";
import {updateUserApi} from "../api/userApi.ts";

function ProfileMyProfile() {
    const [loading, setLoading] = useState(false);
    const {user, setUser, accessToken} = useGlobalContext();

    const [formData, setFormData] = useState({
        name: user?.name || "",
        workPosition: user?.workPosition || "",
        companyType: user?.companyType || ""
    });

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();

        setLoading(true);
        updateUserApi(formData, accessToken || "")
            .then(() => {
                console.log("User updated successfully:");
                setUser(prev => {
                    if (!prev) return prev;
                    return {
                        ...prev,
                        name: formData.name,
                        workPosition: formData.workPosition,
                        companyType: formData.companyType
                    };
                });

            })
            .catch(error => {
                console.error("Error updating user:", error);
            })
            .finally(() => {
                setLoading(false);
            })
    };

    return (
        <section className="flex flex-col gap-4 items-start">
            <h2 className="text-3xl font-semibold">My Profile</h2>
            <form
                onSubmit={handleSubmit}
                className="flex flex-col gap-6 px-24 py-6 border-1 border-dark-blue/10 bg-white rounded-lg w-full ">
                <Input name={"name"} value={formData.name} func={setFormData} />
                <Input name={"workPosition"} value={formData.workPosition} func={setFormData} />
                <select
                    className="hover:border-1 hover:border-shifter focus:outline-none focus:border-2 focus:border-shifter
                border-1 border-black/40 rounded-sm py-2 px-4 text-lg text-black w-full cursor-pointer"
                    name={"companyType"} value={formData.companyType}
                    onChange={e => setFormData(prev => ({...prev, companyType: e.target.value}))}
                >
                    <option value="FREELANCE">Freelance</option>
                    <option value="STARTUP">Start Up</option>
                    <option value="SME">SME</option>
                    <option value="MID_MARKET">Mid Market</option>
                    <option value="ENTERPRISE">Enterprise</option>
                    <option value="OTHER">Other</option>
                </select>
                <div className="flex gap-4 items-center">
                    <button
                        className="shadow-md shadow-shifter/30 hover:shadow-lg hover:shadow-shifter/50 transition-all duration-200 ease-in-out cursor-pointer
                    bg-shifter px-12 py-2 w-fit text-white rounded-sm font-semibold border-2 border-white/40"
                        type="submit">
                        {loading ? "Saving..." : "Save Changes"}
                    </button>
                    {
                        loading && (
                            <div className="h-full loader"></div>
                        )
                    }
                </div>
            </form>
        </section>
    )
}

function Input({name, value, func}: {
    name: string;
    value: string;
    func: React.Dispatch<React.SetStateAction<{ name: string; workPosition: string; companyType: string; }>>;
}) {

    return (
        <input
            className="hover:border-1 hover:border-shifter focus:outline-none focus:border-2 focus:border-shifter
                border-1 border-black/40 rounded-sm py-2 px-4 text-lg text-black w-full"
            value={value}
            onChange={e => func(prev => ({...prev, [name]: e.target.value}))}
            name={name}
        />
    )
}

export default ProfileMyProfile;