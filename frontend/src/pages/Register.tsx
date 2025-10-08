// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-expect-error
import ShifterArrow from "../../public/Shifter-Arrow-White.png";
// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-expect-error
import ShifterLogo from "../../public/Shifter-S2W-Transparent.png";
import {Link} from "react-router-dom";
import React from "react";
import {Eye, EyeOff} from "lucide-react";
import {useRegisterHook} from "../hooks/useRegisterHook.tsx";
import GoogleLoginButton from "../components/GoogleLoginButton.tsx";

function Register() {
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
        <main className="flex font-montserrat h-screen bg-beige">

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
            <section className="relative flex flex-col justify-center items-center flex-1 px-horizontal-md">
                <div className="absolute top-0 px-4 py-4 flex w-full justify-between items-center">
                    <Link to={"/"} >
                        <img
                            src={ShifterLogo}
                            alt="Shifter Logo"
                            className="w-40 h-auto object-contain"
                        />
                    </Link>
                    <Link
                        to={"/"}
                        className="hover:bg-shifter/20 hover:text-shifter underline decoration-current
                             font-semibold text-black/80 rounded-sm px-4 py-2"
                    >
                        Back to Main Page
                    </Link>
                </div>

                {
                    isSuccess ? (
                            <div className="flex flex-col items-center justify-center">
                                <div className="flex flex-col gap-4 bg-white rounded-2xl shadow-lg p-8 w-fit text-center">
                                    <h2 className="text-2xl font-bold text-shifter">Verify your email to continue</h2>
                                    <p className="font-medium text-black-text/80 max-w-xl mx-auto">
                                        A verification link has been sent to <span className='font-semibold text-shifter'>{email}</span>.
                                        Please check your inbox and click the link to activate your account.
                                    </p>
                                </div>
                            </div>
                    ) : (
                        <>
                            <form
                                onSubmit={handleRegister}
                                className="flex flex-col gap-4 w-full items-center">
                                <Input
                                    placeholder={"name@email.com"}
                                    label={"Email address"}
                                    name={"email"}
                                    type={"email"}
                                    id={"email"}
                                    value={email}
                                    onChange={e => setEmail(e.target.value)}
                                    showPassword={showPassword}
                                    setShowPassword={setShowPassword}
                                />
                                <Input
                                    placeholder={"********"}
                                    label={"Password"}
                                    name={"password"}
                                    type={"password"}
                                    id={"password"}
                                    value={password}
                                    onChange={e => setPassword(e.target.value)}
                                    showPassword={showPassword}
                                    setShowPassword={setShowPassword}
                                />
                                <Input
                                    placeholder={"********"}
                                    label={"Confirm password"}
                                    name={"passwordConfirmation"}
                                    type={"password"}
                                    id={"password-confirmation"}
                                    value={passwordConfirmation}
                                    onChange={e => setPasswordConfirmation(e.target.value)}
                                    showPassword={showPassword}
                                    setShowPassword={setShowPassword}
                                />

                                {/* Error Message */}
                                {showError && <p className="text-red-500 text-sm">{error}</p>}

                                {/* Buttons */}
                                <div className="flex gap-2 justify-center text-md w-full text-lg mt-4">
                                    <button
                                        type="submit"
                                        disabled={isLoading}
                                        className={`hover:shadow-md hover:shadow-shifter/60 transition-all duration-200 ease-in-out cursor-pointer
                                    rounded-md border-3 border-white/50 text-white px-8 py-1 bg-shifter font-medium
                                    whitespace-nowrap ${isLoading ? "opacity-50 cursor-not-allowed" : ""}`}
                                    >
                                        {
                                            isLoading ? "Creating account..." : "Create Account"
                                        }
                                    </button>
                                    <Link
                                        to="/login"
                                        className="hover:shadow-md hover:shadow-shifter/20 transition-all duration-200 ease-in-out cursor-pointer
                                    rounded-md text-shifter/80 w-1/3 py-1 bg-white border-3 border-shifter/20 font-medium
                                    whitespace-nowrap opacity-80"
                                    >
                                        Log In
                                    </Link>

                                    {/* Loading Animation */}
                                    {
                                        isLoading && (
                                            <div className="h-full loader"></div>
                                        )
                                    }
                                </div>
                            </form>

                            <div className="my-4 flex items-center gap-2 w-9/10 text-black opacity-20">
                                <hr className="border-t-2 border-black w-full"/>
                                <p>or</p>
                                <hr className="border-t-2 border-black w-full"/>
                            </div>

                            <GoogleLoginButton text="Sign in with Google"/>
                        </>
                    )
                }
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
        <div
            className="relative flex flex-col gap-1 px-6 py-1.5 border-2 border-shifter group focus-within:border-l-20 transition-all ease-in-out duration-300 items-start rounded-sm w-full">
            <label htmlFor={inputProps.id} className="text-shifter text-light">
                {inputProps.label}
            </label>
            <div className="flex gap-2 w-full">
                <div className="w-full">
                    <input
                        id={inputProps.id}
                        type={inputProps.showPassword ? "text" : inputProps.type}
                        name={inputProps.name}
                        placeholder={inputProps.placeholder}
                        className="w-full focus:outline-none text-lg"
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
                            <EyeOff size={20} opacity={0.6} />
                        ) : (
                            <Eye size={20} opacity={0.6} />
                        )}
                    </button>
                )}
            </div>
        </div>
    );
}

export default Register;