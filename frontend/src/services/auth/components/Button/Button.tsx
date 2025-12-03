import type { ButtonHTMLAttributes } from "react";
import classes from "./Button.module.scss";

type ButtonProps = ButtonHTMLAttributes<HTMLButtonElement> & {
  children: React.ReactNode
  outline?: boolean;
}

export default function Button({ children, outline, ...otherProps }: ButtonProps) {
  return (
    <button
      {...otherProps}
      className={`${classes.button} ${outline ? classes.outline : ""}`}
    >
      {children}
    </button>
  );
}
