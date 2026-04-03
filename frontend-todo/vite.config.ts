/// <reference types="vitest" />

import {defineConfig} from 'vitest/config'
import react, {reactCompilerPreset} from '@vitejs/plugin-react'
import babel from '@rolldown/plugin-babel'
import tailwindcss from "@tailwindcss/vite";

// https://vite.dev/config/
export default defineConfig({

    // NOTE: this ensures that the frontend knows where to look and access the backend
    server: {
        port: 3000,
        strictPort: true,
        hmr:{
            // Modifies the localhost config to 3000, has to run on a different port so that we can access the backend!
            clientPort: 3000,
        },

        proxy: {
            '/api': {
                target: 'http://localhost:8080',
                // Resolves the CORS error...
                changeOrigin: true,
            },
        },
    },
    plugins: [
        react(),
        tailwindcss(),
        babel({presets: [reactCompilerPreset()]})
    ],
    build: {
        outDir: 'build',
    },

    test: {
        globals: true, // Allows using `describe`, `it`, `expect` without imports
        environment: 'jsdom', // Simulates a browser environment
        setupFiles: './src/setupTests.ts', // File for test setup (see below)
        css: false, // Optional: Include CSS in tests if needed, if you get an "unable to find CSS stylesheet error, modify to false!"
    },
})


