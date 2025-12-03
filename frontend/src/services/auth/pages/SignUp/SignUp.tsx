import { Link } from "react-router-dom";
import Button from "../../components/Button/Button";
import Input from "../../components/Input/Input";
import Separator from "../../components/Separator/Separator";
import classes from "./SignUp.module.scss";
import { useState } from "react";

export default function SignUp() {
  const [errorMessage, setErrorMessage] = useState("");

  return (
    <div className={classes.root}>
      <div className={classes.title}>
        <h1>Sign up</h1>
        <p>Make the most of your professional life</p>
      </div>

      <form>
        <Input type="email" id="email" label="Email" />
        <Input type="password" id="password" label="Password" />
        {errorMessage && <p className={classes.error}>{errorMessage}</p>}
        <p className={classes.disclaimer}>
          By clicking Agree & Join or Continue, you agree to LinkedIn's{" "}
          <a href="">User Agreement</a>, <a href="">Privacy Policy</a>, and{" "}
          <a href="">Cookie Policy</a>.
        </p>
        <Button type="submit">Agree & join</Button>
      </form>
      <Separator>Or</Separator>
      <div className={classes.signInPrompt}>
        Already on LinkedIn? <Link to="/login">Sign in</Link>
      </div>
    </div>
  );
}
