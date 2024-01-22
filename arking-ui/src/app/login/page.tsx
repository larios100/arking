import Image from "next/image";
import AcmeLogo from "../ui/acme-logo";
import LoginForm from "../ui/login-form";

export default function LoginPage() {
  return (
    <main className="flex items-center justify-center md:h-screen">
      <div className="relative mx-auto flex w-full max-w-[400px] flex-col space-y-2.5 p-4 md:-mt-32">
        <div className="flex h-20 w-full items-end rounded-lg bg-white md:h-36 text-center">
          <Image
            alt="Arking"
            src="/logo-simple.jpg"
            width={150}
            height={50}
            style={{ margin: "0 auto" }}
            className="relative dark:drop-shadow-[0_0_0.3rem_#ffffff70] dark:invert"
          ></Image>
        </div>
        <LoginForm />
      </div>
    </main>
  );
}
