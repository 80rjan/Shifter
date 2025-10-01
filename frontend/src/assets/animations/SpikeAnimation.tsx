import React from "react";

export function LightBeams(): React.ReactElement {
    const beamCount = 120; // more beams for denser effect
    const beams = Array.from({ length: beamCount }, (_, i) => {
        const spacing = (window.innerWidth + 300) / beamCount; // tighter spacing
        const x = i * spacing;
        const width = 10 + Math.random() * 4; // thin but varied
        const tilt = -50; // tilt to the right
        const initialHeight = 120 + Math.random() * 80; // starting height

        return (
            <rect
                key={i}
                x={x}
                y={320 - initialHeight}
                width={width}
                height={initialHeight}
                fill="url(#grad)"
                transform={`skewX(${tilt})`}
            >
                {/*<animate*/}
                {/*    attributeName="height"*/}
                {/*    values={`${initialHeight}; ${initialHeight + 60}; ${initialHeight}`}*/}
                {/*    dur={`${2 + Math.random() * 2}s`}*/}
                {/*    repeatCount="indefinite"*/}
                {/*/>*/}
                <animate
                    attributeName="y"
                    values={`${320 - initialHeight}; ${320 - (initialHeight - 40)}; ${320 - initialHeight}`}
                    dur={`${1 + Math.random() * 2}s`}
                    repeatCount="indefinite"
                />

            </rect>
        );
    });

    return (
        <div className="absolute bottom-0 left-0 w-full h-[80vh] overflow-hidden">
            <svg viewBox="0 0 1440 320" preserveAspectRatio="none" className="w-full h-full">
                <defs>
                    <linearGradient id="grad" x1="0%" y1="0%" x2="0%" y2="100%">
                        <stop offset="0%" stopColor="var(--color-beige)" />
                        <stop offset="100%" stopColor="var(--color-shifter)" stopOpacity="0.8" />
                    </linearGradient>
                    <filter id="blur" x="-20%" y="-20%" width="140%" height="140%">
                        <feGaussianBlur stdDeviation="10" />
                    </filter>
                </defs>

                <g filter="url(#blur)">{beams}</g>
            </svg>
        </div>
    );
}
