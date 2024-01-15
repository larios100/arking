import NextAuth, { User } from "next-auth";
import { authConfig } from "./auth.config";
import Credentials from "next-auth/providers/credentials";
import { z } from "zod";
import { fetchWithInterceptor } from "@/app/lib/fetch-client";
import { AuthModel } from "@/app/models/auth-model";

export async function getUser(
  userName: string | null,
  password: string | null
): Promise<AuthModel | null> {
  try {
    const user = await fetchWithInterceptor(
      "https://localhost:7258/api/user/login",
      {
        method: "POST",
        body: JSON.stringify({ userName: userName, password: password }),
        headers: {
          "cache-control": "no-cache",
          "content-type": "application/json",
        },
      }
    );
    return user.json();
  } catch (error) {
    console.error("Failed to fetch user:", error);
    return null;
  }
}

export const { auth, signIn, signOut } = NextAuth({
  ...authConfig,
  providers: [
    Credentials({
      async authorize(credentials) {
        console.log("credentials", credentials);
        const parsedCredentials = z
          .object({ email: z.string().email(), password: z.string().min(6) })
          .safeParse(credentials);

        if (parsedCredentials.success) {
          const { email, password } = parsedCredentials.data;

          const user = await getUser(email, password);
          if (!user) return null;
          return {
            id: "",
            email: "",
          };
        }

        console.log("Invalid credentials");
        return null;
      },
    }),
  ],
});
