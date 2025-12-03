import classes from "./Layout.module.scss";
import Header from "../Header/Header";
import Footer from "../Footer/Footer";
import { Outlet, useLocation } from "react-router-dom";
import Box from "../Box/Box";

export default function Layout() {
  const pathname = useLocation().pathname as string;

  return (
    <section className={classes.root}>
      <div className={classes.container}>
        <Header isDisabled={pathname.includes("register")} />
        <main>
          <Box>
            <Outlet />
          </Box>
        </main>
      </div>
      <Footer />
    </section>
  )
}
