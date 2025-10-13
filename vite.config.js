import { defineConfig } from 'vite'
import svelte from '@sveltejs/vite-plugin-svelte'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [svelte({
    compilerOptions: {
      customElement: true
    }
  })],
  build: {
    outDir: 'src/main/resources/web/assets',
    emptyOutDir: true,
    rollupOptions: {
      input: {
        dashboard: 'src/main/frontend/dashboard.js',
        settings: 'src/main/frontend/settings.js',
        upload: 'src/main/frontend/upload.js'
      },
      output: {
        entryFileNames: '[name].js',
        chunkFileNames: '[name].js',
        assetFileNames: '[name].[ext]'
      }
    }
  }
})
