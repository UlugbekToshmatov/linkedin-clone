import classes from "./Box.module.scss";
import type { ReactNode } from "react";

export default function Box({ children }: {children: ReactNode}) {
  return (
    <div className={classes.root}>{children}</div>
  );
}
