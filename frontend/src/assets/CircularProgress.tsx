import React from "react";

interface CircularProgressProps {
    percentage: number; // 0 - 100
    size?: number;
    strokeWidth?: number;
}

const CircularProgress: React.FC<CircularProgressProps> = ({
                                                               percentage,
                                                               size = 20,
                                                               strokeWidth = 2,
                                                           }) => {
    const radius = (size - strokeWidth) / 2;
    const circumference = 2 * Math.PI * radius;
    const offset = circumference - (percentage / 100) * circumference;

    return (
        <svg
            width={size}
            height={size}
            className="transform -rotate-90"
        >
            {/* Background circle */}
            <circle
                cx={size / 2}
                cy={size / 2}
                r={radius}
                stroke="var(--color-gray)" // Tailwind's gray-200
                strokeWidth={strokeWidth}
                fill="none"
            />
            {/* Progress circle */}
            <circle
                cx={size / 2}
                cy={size / 2}
                r={radius}
                stroke="var(--color-shifter)" // Tailwind's blue-500
                strokeWidth={strokeWidth}
                fill="none"
                strokeDasharray={circumference}
                strokeDashoffset={offset}
                strokeLinecap="round"
                style={{ transition: "stroke-dashoffset 0.35s ease" }}
            />
        </svg>
    );
};

export default CircularProgress;
