import ShifterArrow from "../../public/Shifter-Arrow-White.png";
import ShifterLogo from "../../public/Shifter-S2W-Transparent.png";
import React from "react";
import { Eye, EyeOff } from "lucide-react";
import { useRegisterHook } from "../hooks/useRegisterHook.tsx";
import GoogleLoginButton from "../components/GoogleLoginButton.tsx";
import { useTranslation } from "react-i18next";
import {LocalizedLink} from "../components/links/LocalizedLink.tsx";

function Register() {
    const { t } = useTranslation("register");
    const {
        email,
        setEmail,
        password,
        setPassword,
        passwordConfirmation,
        setPasswordConfirmation,
        showPassword,
        setShowPassword,
        showError,
        error,
        isSuccess,
        isLoading,
        handleRegister
    } = useRegisterHook();

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
            <section className="relative flex flex-col justify-center items-center flex-1 px-horizontal py-vertical
                sm:py-20">

                {/* Top Navigation */}
                <div className="absolute top-0 px-4 py-4 flex w-full justify-between items-center
                    sm:px-6
                    md:px-8
                    lg:px-8">
                    <LocalizedLink to={"/"}>
                        <img src={ShifterLogo} alt="Shifter Logo"
                             className="w-20 h-auto object-contain
                                sm:w-26
                                md:w-30
                                lg:w-26
                                xl:w-30
                                2xl:w-40" />
                    </LocalizedLink>
                    <LocalizedLink to={"/"}
                                   className="hover:bg-shifter/20 hover:text-shifter underline decoration-current font-semibold text-black/80 rounded-sm
                                   px-3 py-1.5 text-xs
                                   sm:text-sm
                                   md:px-4 md:py-2 md:text-base
                                   lg:text-sm
                                   2xl:text-base">
                        {t("backToMain")}
                    </LocalizedLink>
                </div>

                {/* Mobile Header - Only visible on mobile */}
                <div className="lg:hidden mb-8 text-center
                    sm:mb-10
                    md:mb-12">
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

                {isSuccess ? (
                    <div className="flex flex-col items-center justify-center w-full max-w-md
                        md:max-w-lg
                        lg:max-w-md
                        xl:max-w-lg">
                        <div className="flex flex-col gap-3 bg-white rounded-2xl shadow-lg p-6 w-full text-center
                            sm:gap-4 sm:p-8">
                            <h2 className="text-xl font-bold text-shifter
                                sm:text-2xl">{t("verifyEmail")}</h2>
                            <p className="font-medium text-black-text/80 text-sm
                                sm:text-base">
                                {t("verificationSent1")} <span className='font-semibold text-shifter'>{email}</span> <span dangerouslySetInnerHTML={{ __html: t("verificationSent2") }}/>
                            </p>
                        </div>
                    </div>
                ) : (
                    <>
                        <form onSubmit={handleRegister}
                              className="flex flex-col justify-center items-center gap-3 w-full max-w-md
                                sm:gap-4
                                md:max-w-lg
                                lg:max-w-md
                                xl:max-w-lg">
                            <Input
                                placeholder={t("emailPlaceholder")}
                                label={t("emailLabel")}
                                name="email"
                                type="email"
                                id="email"
                                value={email}
                                onChange={e => setEmail(e.target.value)}
                                showPassword={showPassword}
                                setShowPassword={setShowPassword}
                            />
                            <Input
                                placeholder={t("passwordPlaceholder")}
                                label={t("passwordLabel")}
                                name="password"
                                type="password"
                                id="password"
                                value={password}
                                onChange={e => setPassword(e.target.value)}
                                showPassword={showPassword}
                                setShowPassword={setShowPassword}
                            />
                            <Input
                                placeholder={t("passwordConfirmationPlaceholder")}
                                label={t("passwordConfirmationLabel")}
                                name="passwordConfirmation"
                                type="password"
                                id="password-confirmation"
                                value={passwordConfirmation}
                                onChange={e => setPasswordConfirmation(e.target.value)}
                                showPassword={showPassword}
                                setShowPassword={setShowPassword}
                            />

                            {showError && (
                                <p className="text-red-500 text-xs
                                    sm:text-sm">
                                    {error}
                                </p>
                            )}

                            <div className="flex flex-col gap-3 justify-center w-full mt-4
                                sm:flex-row sm:gap-2 sm:text-base
                                md:text-lg">
                                <button
                                    type="submit"
                                    disabled={isLoading}
                                    className={`hover:shadow-md hover:shadow-shifter/60 transition-all duration-200 ease-in-out cursor-pointer rounded-md border-3 border-white/50 text-white w-full py-2 bg-shifter font-medium whitespace-nowrap text-sm
                                        sm:w-1/2 sm:py-1 sm:text-base
                                        ${isLoading ? "opacity-50 cursor-not-allowed" : ""}`}
                                >
                                    {isLoading ? t("creatingAccount") : t("createAccount")}
                                </button>
                                <LocalizedLink
                                    to="/login"
                                    className="hover:shadow-md hover:shadow-shifter/20 transition-all duration-200 ease-in-out cursor-pointer rounded-md text-shifter/80 w-full py-2 bg-white border-3 border-shifter/20 font-medium whitespace-nowrap opacity-80 text-center text-sm
                                        sm:w-1/2 sm:py-1 sm:text-base"
                                >
                                    {t("logIn")}
                                </LocalizedLink>

                                {isLoading && <div className="h-full loader"></div>}
                            </div>
                        </form>

                        <div className="my-6 flex items-center gap-2 w-full max-w-md text-black opacity-20
                            sm:my-4
                            md:max-w-lg
                            lg:max-w-md
                            xl:max-w-lg">
                            <hr className="border-t-2 border-black w-full" />
                            <p className="text-sm
                                sm:text-base">
                                {t("or")}
                            </p>
                            <hr className="border-t-2 border-black w-full" />
                        </div>

                        <GoogleLoginButton text={t("googleLogin")} />
                    </>
                )}
            </section>
        </main>
    )
}

interface InputProps {
    placeholder: string;
    label: string;
    name: string;
    type: string;
    id: string;
    value: string;
    onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
    showPassword: boolean;
    setShowPassword: React.Dispatch<React.SetStateAction<boolean>>;
}

function Input(inputProps: InputProps) {
    return (
        <div className="relative flex flex-col gap-1 px-4 py-1.5 border-2 border-shifter group focus-within:border-l-8 transition-all ease-in-out duration-300 items-start rounded-sm w-full
            sm:px-5
            md:px-6
            lg:focus-within:border-l-12
            xl:focus-within:border-l-20">
            <label htmlFor={inputProps.id}
                   className="text-shifter text-light text-xs
                    sm:text-sm
                    md:text-base">
                {inputProps.label}
            </label>
            <div className="flex gap-2 w-full">
                <div className="w-full">
                    <input
                        id={inputProps.id}
                        type={inputProps.type === "password" && !inputProps.showPassword ? "password" : "text"}
                        name={inputProps.name}
                        placeholder={inputProps.placeholder}
                        className="w-full focus:outline-none text-base
                            sm:text-lg
                            md:text-lg"
                        value={inputProps.value}
                        onChange={inputProps.onChange}
                    />
                    <hr className="border-t-2 border-black/5 rounded-full w-full" />
                </div>
                {inputProps.type === "password" && (
                    <button
                        type="button"
                        onClick={() => inputProps.setShowPassword(prev => !prev)}
                        className="text-black cursor-pointer hover:bg-black/5 rounded-full p-1"
                        aria-label={inputProps.showPassword ? "Hide password" : "Show password"}
                    >
                        {!inputProps.showPassword ? (
                            <EyeOff className="w-5 h-5
                                sm:w-5 sm:h-5
                                md:w-6 md:h-6"
                                    opacity={0.6} />
                        ) : (
                            <Eye className="w-5 h-5
                                sm:w-5 sm:h-5
                                md:w-6 md:h-6"
                                 opacity={0.6} />
                        )}
                    </button>
                )}
            </div>
        </div>
    );
}

export default Register;
