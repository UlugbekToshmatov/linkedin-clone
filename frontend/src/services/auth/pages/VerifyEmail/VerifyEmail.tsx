import { useState } from "react";
import Button from "../../components/Button/Button";
import Input from "../../components/Input/Input";
import classes from "./VerifyEmail.module.scss";

export default function VerifyEmail() {
  const [errorMessage, setErrorMessage] = useState("");
  const [message, setMessage] = useState("");

  return (
      <div className={classes.root}>
        <h1>Verify your email</h1>
        <p>Only one step left to complete your registration. Verify your email address.</p>
        <Input type="text" key="code" name="code" label="Verification code" />
        {message && <p style={{color: "green"}}>{message}</p>}
        {errorMessage && <p style={{color: "red"}}>{errorMessage}</p>}
        <Button>Validate email</Button>
        <Button outline>Send again</Button>
      </div>
  );
}
