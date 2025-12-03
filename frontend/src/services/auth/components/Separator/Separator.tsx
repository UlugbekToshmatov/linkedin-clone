import classes from "./Separator.module.scss";
import type { ReactNode } from "react";

export default function Separator({ children }: { children: ReactNode }) {
  return <div className={classes.root}>{children}</div>;
}
