import "./styles/App.css";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import LoginPage from "./pages/Authentication/LoginPage";
import DashBoard from "./pages/DashBoard";
import ProjectDashBoard from "./pages/Project/ProjectDashBoard";
import { ThemeProvider, createTheme } from "@mui/material/styles";
import { green, orange, grey, red, yellow } from "@mui/material/colors";
import { useCookies } from "react-cookie";
import axios from "axios";

const theme = createTheme({
  palette: {
    primary: grey,
    secondary: orange,
    error: red,
    warning: yellow,
    info: green,
    text: {
      primary: "#000000", // your primary text color
      secondary: "#757575", // your secondary text color
    },
  },
  components: {
    MuiButton: {
      styleOverrides: {
        outlined: {
          borderWidth: "1px",
          borderColor: "primary.100",
          "&:hover": {
            borderWidth: "1px",
            borderColor: "secondary",
          },
        },
      },
    },
  },
});

function App() {
  const [cookie, setCookie] = useCookies(["auth-token"]);
  // Token static assign for development
  setCookie(
    "auth-token",
    "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNzExOTgyOTg0LCJleHAiOjE3MTI4NDY5ODR9.NxFxQk76_0fmBwdZUhem8xN8A5hkDl9CW9-kMTuj89o"
  );

  axios.defaults.headers.common["Authorization"] =
    "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNzExOTgyOTg0LCJleHAiOjE3MTI4NDY5ODR9.NxFxQk76_0fmBwdZUhem8xN8A5hkDl9CW9-kMTuj89o";

  return (
    <div className="App">
      <ThemeProvider theme={theme}>
        <BrowserRouter>
          <Routes>
            <Route path="/login" Component={LoginPage} />
            <Route path="/" Component={DashBoard} />
            <Route path="/:projectSlug" Component={ProjectDashBoard} />
          </Routes>
        </BrowserRouter>
      </ThemeProvider>
    </div>
  );
}

export default App;
