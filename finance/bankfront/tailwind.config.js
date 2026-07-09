/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{vue,js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      fontFamily: {
        'youshebiaotihhei': ['YouSheBiaoTiHei', 'sans-serif'], // 定义字体名称
      },
    },
  },
  plugins: [],
} 