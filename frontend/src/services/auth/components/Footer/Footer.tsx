import { Link } from "react-router-dom";
import classes from "./Footer.module.scss";

export default function Footer() {
  return (
    <footer>
      <ul className={classes.root}>
        <li className={classes.footerLogo}>
          <img src="public/logo-dark.svg" alt="dark logo" />
          <span>© 2025</span>
        </li>
        <li>
          <Link to="">User Agreement</Link>
        </li>
        <li>
          <Link to="">Privacy Policy</Link>
        </li>
        <li>
          <Link to="">Community Guidelines</Link>
        </li>
        <li>
          <Link to="">Cookie Policy</Link>
        </li>
        <li>
          <Link to="">Copyright Policy</Link>
        </li>
        <li>
          <Link to="">Send Feedback</Link>
        </li>
        <li className={classes.languageOption}>
          <label htmlFor="language">Language</label>
          <select id="language" name="language" defaultValue="english" required>
            <option value="english">English</option>
            <option value="russian">Russian</option>
            <option value="uzbek">Uzbek</option>
          </select>
        </li>
      </ul>
    </footer>
  );
}
