
export default function GoogleLoginButton({text = "Sign in with Google"}) {
    const handleLogin = () => {
        // Redirect to backend OAuth2 login
        window.location.href = "http://localhost:8080/oauth2/authorization/google";
    };

    return (
        <button
            onClick={handleLogin}
            className="cursor-pointer flex items-center justify-center w-full max-w-sm px-4 py-2 border
            border-gray-300 rounded shadow-sm bg-white hover:bg-gray-100 transition duration-200"
        >
            <img
                src="https://upload.wikimedia.org/wikipedia/commons/c/c1/Google_%22G%22_logo.svg"
                alt="Google logo"
                className="w-6 h-6 mr-3"
            />
            <span className="text-gray-700 font-medium">{text}</span>
        </button>
    );
}
