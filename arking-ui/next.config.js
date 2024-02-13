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
};

module.exports = nextConfig;
