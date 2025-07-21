/** @type {import('tailwindcss').Config} */
export default {
    content: [
        "./index.html",
        "./src/**/*.{js,ts,jsx,tsx}",
    ],
    theme: {
        extend: {
            spacing: {
                // Map your spacing variables as custom spacing tokens
                'horizontal-lg': '10rem',   // --spacing-horizontal-lg
                'horizontal-md': '7rem',    // --spacing-horizontal-md
                'horizontal-sm': '4rem',    // --spacing-horizontal-sm

                'vertical-lg': '3rem',      // --spacing-vertical-lg
                'vertical-md': '2rem',      // --spacing-vertical-md
                'vertical-sm': '1rem',      // --spacing-vertical-sm

                'top-nav-lg': '8rem',       // --spacing-top-nav-lg
                'top-nav-md': '7rem',       // --spacing-top-nav-md
                'top-nav-sm': '6rem',       // --spacing-top-nav-sm
            },
            colors: {
                beige: '#F0CFB5',
                shifter: '#008CC2',
                red: '#FF6F61',
                'dark-blue': '#002E5D',
                'deep-green': '#2C6B3D',
                gold: '#FFB300',
                teal: '#009688',
                'dark-gray': '#666666',
                'bright-gray': '#DDDDDD',
                gray: '#E5E7EB',
            },
            boxShadow: {
                'up-sm': '0 -1px 2px 0 rgba(0, 0, 0, 0.05)',
                up: '0 -1px 3px 0 rgba(0, 0, 0, 0.1), 0 -1px 2px 0 rgba(0, 0, 0, 0.06)',
                'up-md': '0 -4px 6px -1px rgba(0, 0, 0, 0.1), 0 -2px 4px -2px rgba(0, 0, 0, 0.06)',
                'up-lg': '0 -10px 15px -3px rgba(0, 0, 0, 0.1), 0 -4px 6px -4px rgba(0, 0, 0, 0.05)',
                'up-xl': '0 -20px 25px -5px rgba(0, 0, 0, 0.1), 0 -10px 10px -5px rgba(0, 0, 0, 0.04)',
                'up-2xl': '0 -25px 50px -12px rgba(0, 0, 0, 0.25)',
            },
            fontFamily: {
                sans: ['"Source Sans 3"', 'sans-serif'],
                montserrat: ['Montserrat', 'sans-serif'],
            },
        },
    },
    plugins: [],
}
