import {defineConfig} from 'vite'
import react from '@vitejs/plugin-react'
import tailwindcss from '@tailwindcss/vite'


// https://vite.dev/config/
export default defineConfig({
    plugins: [
        react(),
        tailwindcss(),
        // visualizer({
        //     open: true,
        //     filename: 'bundle-analysis.html',
        //     gzipSize: true,
        //     brotliSize: true,
        //     template: 'treemap', // or 'sunburst' for different view
        // })
    ],
    server: {
        allowedHosts: ["cira-odontoblastic-pseudoimpartially.ngrok-free.dev"]
    },
    resolve: {
        alias: {
            // Use the light version of lottie-web
            'lottie-web': 'lottie-web/build/player/lottie_light.js'
        }
    },
    build: {
        // sourcemap: true, // Helps with debugging production issues
        rollupOptions: {
            output: {
                manualChunks: {
                    // Three.js - your biggest dependency
                    'three-core': ['three'],
                    'three-fiber': ['@react-three/fiber'],

                    // Animation libraries
                    'animations': ['gsap', 'framer-motion', 'motion-dom', 'motion-utils'],

                    // Lottie
                    'lottie': ['lottie-web'],

                    // Icons - keep together since they're similar
                    'icons': ['lucide-react', '@tabler/icons-react'],

                    // Router
                    'router': ['react-router-dom', 'react-router'],

                    // i18n
                    'i18n': ['i18next', 'react-i18next'],

                    // Other vendor deps
                    'vendor': [
                        'axios',
                        'zustand',
                        'react-countup',
                        'react-country-flag',
                        'react-slick',
                        'slick-carousel',
                        'react-toastify'
                    ]
                }
            }
        }
    }
    // build: {
    //     outDir: '../docs'
    // },
    // base: '/Shifter/'
})
