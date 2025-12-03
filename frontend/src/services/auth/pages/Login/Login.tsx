import { Link } from "react-router-dom";
import Button from "../../components/Button/Button";
import Input from "../../components/Input/Input";
import classes from "./Login.module.scss";
import Separator from "../../components/Separator/Separator";
import { useState } from "react";

export default function Login() {
  const [errorMessage, setErrorMessage] = useState("");

  return (
    <div className={classes.root}>
      <div className={classes.title}>
        <h1>Sign in</h1>
        <p>Stay updated on your professional world</p>
      </div>

      <form>
        <Input
          type="email"
          id="email"
          label="Email"
          onFocus={() => setErrorMessage("")}
        />
        <Input
          type="password"
          id="password"
          label="Password"
          onFocus={() => setErrorMessage("")}
        />
        {errorMessage && <p className={classes.error}>{errorMessage}</p>}
        <Button type="submit">Sign in</Button>
      </form>
      <Link to="/reset-password" className={classes.forgotPasswordPrompt}>
        Forgot password?
      </Link>
      <Separator>Or</Separator>
      <div className={classes.registerPrompt}>
        New to LinkedIn? <Link to="/register">Join now</Link>
      </div>
    </div>
  );
}
