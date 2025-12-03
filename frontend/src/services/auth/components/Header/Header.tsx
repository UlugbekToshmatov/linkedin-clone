import { Link } from "react-router-dom";
import classes from "./Header.module.scss";

export default function Header({ isDisabled }: { isDisabled: boolean }) {
  return (
    <header className={classes.root}>
      <Link to="/register" className={isDisabled ? classes.disabled : ""}>
        <img src="public/logo.svg" alt="logo" />
      </Link>
    </header>
  );
}
