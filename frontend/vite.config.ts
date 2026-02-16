import {defineConfig} from 'vite'
import react from '@vitejs/plugin-react'
import tailwindcss from '@tailwindcss/vite'


// https://vite.dev/config/
export default defineConfig({
    plugins: [
        react(),
        tailwindcss(),
    ],
    server: {
        allowedHosts: ["cira-odontoblastic-pseudoimpartially.ngrok-free.dev"]
    }
    // build: {
    //     outDir: '../docs'
    // },
    // base: '/Shifter/'
})
