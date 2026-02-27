import {defineConfig} from 'vite'
import react from '@vitejs/plugin-react'
import tailwindcss from '@tailwindcss/vite'


// https://vite.dev/config/
export default defineConfig({
    plugins: [
        react(),
        tailwindcss(),
        // visualizer({ open: true, gzipSize: true, brotliSize: true })     // visualizer for bundle analysis (stats.html)
    ],
    server: {
        allowedHosts: ["cira-odontoblastic-pseudoimpartially.ngrok-free.dev"]
    },
    build: {
        rollupOptions: {
            output: {
                manualChunks: {
                    'three': ['three', '@react-three/fiber', '@react-three/drei'],
                    'animations': ['gsap', 'framer-motion'],
                    'lottie': ['lottie-web']
                }
            }
        }
    }
    // build: {
    //     outDir: '../docs'
    // },
    // base: '/Shifter/'
})
