import React, { useEffect, useState } from "react";

export function LightBeams({tilt, beamCount, initialHeight, spikeMoveDown}: {
    tilt: number,
    beamCount: number,
    initialHeight: number,
    spikeMoveDown: number
}): React.ReactElement {
    const [windowWidth, setWindowWidth] = useState(typeof window !== 'undefined' ? window.innerWidth : 1440);

    useEffect(() => {
        const handleResize = () => setWindowWidth(window.innerWidth);
        window.addEventListener('resize', handleResize);
        return () => window.removeEventListener('resize', handleResize);
    }, []);

    // Calculate viewBox width based on actual window width
    const viewBoxWidth = Math.max(windowWidth, 1440);

    const beams = Array.from({ length: beamCount }, (_, i) => {
        const spacing = (viewBoxWidth + 320) / beamCount;
        const x = i * spacing;
        const width = 10 + Math.random() * 4; // thin but varied

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
                <animate
                    attributeName="y"
                    values={`${320 - initialHeight}; ${320 - (initialHeight - spikeMoveDown)}; ${320 - initialHeight}`}
                    dur={`${1 + Math.random() * 2}s`}
                    repeatCount="indefinite"
                />
            </rect>
        );
    });

    return (
        <div className="absolute bottom-0 left-0 w-full h-[60vh]
            sm:h-[65vh]
            md:h-[40vh]
            lg:h-[45vh]
            xl:h-[70vh]
            2xl:h-[70vh]
            overflow-hidden">
            <svg viewBox={`0 0 ${viewBoxWidth} 320`} preserveAspectRatio="none" className="w-full h-full">
                <defs>
                    <linearGradient id="grad" x1="0%" y1="0%" x2="0%" y2="100%">
                        <stop offset="0%" stopColor="var(--color-main)" />
                        <stop offset="100%" stopColor="var(--color-shifter)" stopOpacity="0.8" />
                    </linearGradient>
                    <filter id="blur" x="-20%" y="-20%" width="140%" height="140%">
                        <feGaussianBlur stdDeviation={windowWidth < 640 ? "6" : windowWidth < 1024 ? "8" : "10"} />
                    </filter>
                </defs>

                <g filter="url(#blur)">{beams}</g>
            </svg>
        </div>
    );
}