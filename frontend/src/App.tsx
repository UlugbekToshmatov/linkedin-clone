import { StrictMode } from "react";
import {
  createBrowserRouter,
  createRoutesFromElements,
  Route,
  RouterProvider,
} from "react-router-dom";
import "./index.scss";
import Feed from "./services/feed/pages/Feed/Feed";
import Login from "./services/auth/pages/Login/Login";
import SignUp from "./services/auth/pages/SignUp/SignUp";
import VerifyEmail from "./services/auth/pages/VerifyEmail/VerifyEmail";
import ResetPassword from "./services/auth/pages/ResetPassword/ResetPassword";
import Layout from "./services/auth/components/Layout/Layout";

const router = createBrowserRouter(
  createRoutesFromElements(
    <>
      <Route path="/" element={<Feed />} />
      <Route path="/" element={<Layout />}>
        <Route path="login" element={<Login />} />
        <Route path="register" element={<SignUp />} />
        <Route path="verify-email" element={<VerifyEmail />} />
        <Route path="reset-password" element={<ResetPassword />} />
      </Route>
    </>
  )
);

export default function App() {
  return (
    <StrictMode>
      <RouterProvider router={router} />
    </StrictMode>
  );
}
