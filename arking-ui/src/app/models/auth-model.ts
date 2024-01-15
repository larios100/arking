export interface AuthModel {
  token: string;
  //user: User;
}
export interface User {
  id: string;
  name: string;
  email: string;
  role: "Admin" | "Operator";
}
