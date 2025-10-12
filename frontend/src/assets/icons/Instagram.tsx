export default function Instagram({ className = "w-6 h-6 text-black" }) {
    return (
        <svg
            xmlns="http://www.w3.org/2000/svg"
            viewBox="0 0 24 24"     // Reset full view
            fill="currentColor"
            className={className}
        >
            <path d="M7 2h10a5 5 0 0 1 5 5v10a5 5 0 0 1-5 5H7a5 5 0 0 1-5-5V7a5 5 0 0 1 5-5zm5 7a4 4 0 1 0 4 4 4 4 0 0 0-4-4zm4.5-3.5a1.25 1.25 0 1 0 1.25 1.25A1.25 1.25 0 0 0 16.5 5.5z" />
        </svg>
    );
}
