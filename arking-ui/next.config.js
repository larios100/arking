/** @type {import('next').NextConfig} */
const nextConfig = {
  images: {
    remotePatterns: [
      {
        protocol: "https",
        hostname: "localhost",
        port: "7258",
        pathname: "/api/file/**",
      },
    ],
  },
  assetPrefix: "/arking",
  allowedOrigins: ["www.arking.com.mx", "localhost:3000"],
  experimental: {
    allowedOrigins: ["www.arking.com.mx", "localhost:3000"],
    serverActions: {
      allowedOrigins: ["www.arking.com.mx", "localhost:3000"],
    },
  },
};

module.exports = nextConfig;
