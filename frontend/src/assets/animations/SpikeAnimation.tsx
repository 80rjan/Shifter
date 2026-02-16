import React, { useEffect, useState, useMemo } from "react";

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

    const viewBoxWidth = Math.max(windowWidth, 1440);

    beamCount = windowWidth < 768 ? Math.floor(beamCount / 1.5) : beamCount;

    const beams = useMemo(() => {
        return Array.from({ length: beamCount }, (_, i) => {
            const spacing = (viewBoxWidth + 400) / beamCount;
            const x = i * spacing;
            const width = 20 + Math.random() * 12;
            const duration = 1 + Math.random() * 2;

            return (
                <rect
                    key={i}
                    x={x}
                    y={320 - initialHeight}
                    width={width}
                    height={initialHeight}
                    fill="url(#grad)"
                    transform={`skewX(${tilt})`}
                    // Add opacity here instead of in gradient
                    opacity="0.95"
                >
                    <animate
                        attributeName="y"
                        values={`${320 - initialHeight}; ${320 - (initialHeight - spikeMoveDown)}; ${320 - initialHeight}`}
                        dur={`${duration}s`}
                        repeatCount="indefinite"
                    />
                </rect>
            );
        });
    }, [beamCount, viewBoxWidth, initialHeight, spikeMoveDown, tilt]);

    const blurAmount = "4";

    return (
        <div
            className="absolute bottom-0 left-0 w-full h-[60vh]
            sm:h-[65vh]
            md:h-[40vh]
            lg:h-[45vh]
            xl:h-[70vh]
            2xl:h-[70vh]
            overflow-hidden"
            style={{
                willChange: 'transform',
                transform: 'translateZ(0)',
                filter: 'brightness(1.0) blur(4px)',
                // Create a mask to hide bottom portion
                WebkitMaskImage: 'linear-gradient(to bottom, black 0%, black 100%, transparent 100%)',
                maskImage: 'linear-gradient(to bottom, black 0%, black 100%, transparent 100%)',
            }}
        >
            <svg
                viewBox={`0 0 ${viewBoxWidth} 320`}
                preserveAspectRatio="none"
                className="w-full h-full"
                style={{
                    transform: 'translateZ(0)',
                    backfaceVisibility: 'hidden',
                }}
            >
                <defs>
                    <linearGradient
                        id="grad"
                        x1="0%"
                        y1="0%"
                        x2="0%"
                        y2="100%"
                    >
                        {/* Remove opacity from gradient stops */}
                        <stop offset="0%" stopColor="var(--color-main)" />
                        <stop offset="100%" stopColor="var(--color-shifter)" />
                    </linearGradient>
                    {/* Keep filter but try different settings */}
                    <filter
                        id="blur"
                        x="-50%"
                        y="-50%"
                        width="200%"
                        height="200%"
                    >
                        <feGaussianBlur
                            in="SourceGraphic"
                            stdDeviation={blurAmount}
                            edgeMode="duplicate"
                            result="blur"
                        />
                    </filter>
                </defs>

                {/* Don't apply SVG filter, use CSS instead */}
                <g>{beams}</g>
            </svg>
        </div>
    );
}