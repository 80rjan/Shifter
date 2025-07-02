import ShifterLogo from "../assets/shifterImg/Shifter-Logo-S2W-Transparent.png";
import ShifterArrow from "../assets/shifterImg/Shifter-Arrow-White.png";
import React from "react";
import { Eye, EyeOff } from "lucide-react";
import { Link, useNavigate } from "react-router-dom";
import { useGlobalContext } from "../GlobalContext.tsx";

interface InputProps {
    placeholder: string;
    label: string;
    name: string;
    type: string;
    id: string;
    value: string;
    onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
}

function Login() {
    const { login } = useGlobalContext();
    const [email, setEmail] = React.useState<string>("");
    const [password, setPassword] = React.useState<string>("");
    const [error, setError] = React.useState<string>("");
    const [isLoading, setIsLoading] = React.useState<boolean>(false);
    const navigate = useNavigate();

    const handleLogin = async (e: React.FormEvent) => {
        setIsLoading(true);
        e.preventDefault();
        setError("");

        try {
            await login(email, password);
            navigate("/");
        } catch (err: any) {
            if (err.response?.status === 401) {
                setError("Invalid email or password.");
            } else {
                setError("Something went wrong. Please try again.");
            }
        } finally {
            setIsLoading(false);
        }
    };

    return (
        <section className="flex font-montserrat h-screen bg-white">
            {/* LEFT HEADER AND BACKGROUND */}
            <section className="relative bg-black w-[55%] overflow-hidden">
                <div className="absolute w-full h-full bg-shifter/80 z-0 text-white px-20 flex flex-col gap-4 justify-center text-start">
                    <img
                        src={ShifterArrow}
                        alt="Shifter Arrow"
                        className="absolute -top-20 right-20 -rotate-130 w-auto h-100 opacity-70 z-2"
                    />
                    <img
                        src={ShifterArrow}
                        alt="Shifter Arrow"
                        className="absolute -bottom-20 left-20 rotate-50 w-auto h-100 opacity-70 z-2"
                    />

                    <h1 className="text-7xl font-semibold z-2">Welcome Back!</h1>
                    <p className="text-2xl font-light z-2">
                        Let's unlock your next breakthrough in business!
                    </p>
                </div>
            </section>

            {/* RIGHT FORM CONTAINER */}
            <section className="relative flex flex-col justify-center items-center flex-1 px-30">
                <img
                    src={ShifterLogo}
                    alt="Shifter Logo"
                    className="absolute top-4 left-4 w-40 h-auto object-contain"
                />

                <form
                    onSubmit={handleLogin}
                    className="flex flex-col justify-center items-center gap-4 w-full"
                >

                    <Input
                        placeholder="name@email.com"
                        label="Email address"
                        name="email"
                        type="email"
                        id="login-email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                    />
                    <Input
                        placeholder="********"
                        label="Password"
                        name="password"
                        type="password"
                        id="login-password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                    />

                    {/* Error Message */}
                    {error && <p className="text-red-500 text-sm">{error}</p>}

                    {/* Buttons */}
                    <div className="flex gap-2 justify-start text-md w-full text-lg mt-4">
                        <button
                            type="submit"
                            disabled={isLoading}
                            className={`hover:shadow-md hover:shadow-shifter/60 transition-all duration-200 ease-in-out cursor-pointer
              rounded-md border-3 border-white/50 text-white w-1/3 py-1 bg-shifter font-medium
              whitespace-nowrap ${isLoading ? "opacity-50 cursor-not-allowed" : ""}`}
                        >
                            {
                                isLoading ? "Logging in..." : "Log In"
                            }
                        </button>
                        <Link
                            to="/register"
                            className="hover:shadow-md hover:shadow-shifter/20 transition-all duration-200 ease-in-out cursor-pointer
              rounded-md text-shifter/80 w-1/3 py-1 bg-white border-3 border-shifter/20 font-medium
              whitespace-nowrap"
                        >
                            Register
                        </Link>

                        {/* Loading Animation */}
                        {
                            isLoading && (
                                <div className="h-full loader"></div>
                            )
                        }
                    </div>
                </form>
            </section>
        </section>
    );
}

function Input(inputProps: InputProps) {
    const [showPassword, setShowPassword] = React.useState(false);

    return (
        <div className="relative flex flex-col gap-1 px-6 py-1.5 border-2 border-shifter group focus-within:border-l-20 transition-all ease-in-out duration-300 items-start rounded-sm w-full">
            <label htmlFor={inputProps.id} className="text-shifter text-light">
                {inputProps.label}
            </label>
            <div className="flex gap-2 w-full">
                <div className="w-full">
                    <input
                        id={inputProps.id}
                        type={
                            inputProps.name !== "password"
                                ? "text"
                                : showPassword
                                    ? "text"
                                    : "password"
                        }
                        name={inputProps.name}
                        placeholder={inputProps.placeholder}
                        className="w-full focus:outline-none text-lg"
                        value={inputProps.value}
                        onChange={inputProps.onChange}
                    />
                    <hr className="border-t-2 border-black/5 rounded-full w-full" />
                </div>
                {inputProps.name === "password" && (
                    <button
                        type="button"
                        onClick={() => setShowPassword((prev) => !prev)}
                        className="text-black cursor-pointer hover:bg-black/5 rounded-full p-1"
                        aria-label={showPassword ? "Hide password" : "Show password"}
                    >
                        {!showPassword ? (
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

export default Login;
