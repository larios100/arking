export interface AuthModel {
  token: string;
  firstName: string;
  lastName: string;
  userName: string;
  //user: User;
}
export interface User {
  id: string;
  name: string;
  email: string;
  role: "Admin" | "Operator";
}
