import { useNavigate } from "react-router-dom";
import Button from "../../components/Button/Button";
import Input from "../../components/Input/Input";
import classes from "./ResetPassword.module.scss";
import { useState } from "react";

export default function ResetPassword() {
  const navigate = useNavigate();
  const [emailSent, setEmailSent] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");

  function getPasswordResetForm() {
    return (
      <>
        <p>Enter your email and we will send a verification code if it matches an existing LinkedIn account</p>
        <form>
          <Input type="email" id="email" label="Email" />
          <Button onClick={() => setEmailSent(true)}>Next</Button>
          <Button type="button" outline onClick={() => navigate("/login")}>Back</Button>
        </form>
      </>
    )
  }

  function getCodeVerificationForm() {
    return (
      <>
        <p>Enter the verification code we sent to your email and your new password</p>
        <form>
          <Input type="text" key="code" name="code" label="Verification code" />
          <Input type="password" key="password" name="password" label="New password" />
          {errorMessage && <p style={{color: "red"}}>{errorMessage}</p>}
          <Button>Reset password</Button>
          <Button type="button" outline onClick={() => setEmailSent(false)}>Back</Button>
        </form>
      </>
    )
  }

  return (
      <div className={classes.root}>
        <h1>Reset password</h1>
        {
          emailSent ? getCodeVerificationForm() : getPasswordResetForm()
        }
      </div>
  );
}
