import Image from "next/image";

export default function Home() {
  return (
    <header className="relative flex items-center justify-center h-screen mb-12 overflow-hidden">
      <div className="relative z-30 p-5 text-2xl text-white bg-sky-100 bg-opacity-50 rounded-xl text-center">
        <Image src={"/logo.png"} alt="Arking" width={500} height={400}></Image>
        <p className="text-sky-700 text-3xl mt-3">Constuyendo tu futuro</p>
      </div>
      <video
        autoPlay
        loop
        muted
        className="absolute z-10 w-auto min-w-full min-h-full max-w-none"
      >
        <source
          src="https://assets.mixkit.co/videos/preview/mixkit-set-of-plateaus-seen-from-the-heights-in-a-sunset-26070-large.mp4"
          type="video/mp4"
        />
        Your browser does not support the video tag.
      </video>
    </header>
  );
}
