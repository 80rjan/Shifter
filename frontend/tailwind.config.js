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
                'horizontal-xl': '12rem',
                'horizontal-lg': '10rem',
                'horizontal-md': '7rem',
                'horizontal-sm': '4rem',

                'vertical-xl': '5rem',
                'vertical-lg': '3rem',
                'vertical-md': '2rem',
                'vertical-sm': '1rem',

                'top-nav-xl': '9rem',
                'top-nav-lg': '8rem',
                'top-nav-md': '7rem',
                'top-nav-sm': '6rem',

                'between-lg': '3rem',
                'between-md': '2rem',
                'between-sm': '1rem',
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
                'black-text': '#333333',
                'white-text': '#EEEEEE',
                'warm': '#E67E50',
                'secondary-warm': '#D4A373',
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
    plugins: []
}