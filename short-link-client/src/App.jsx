import "./styles/App.css";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import LoginPage from "./pages/Authentication/LoginPage";
// import { ThemeProvider, createTheme } from '@mui/material'
import { ThemeProvider, createTheme } from "@mui/material/styles";
import { green, orange, grey, red, yellow } from "@mui/material/colors";

const theme = createTheme({
  palette: {
    primary: grey,
    secondary: orange,
    error: red,
    warning: yellow,
    info: green,
  },
  components: {
    MuiButton: {
      styleOverrides: {
        outlined: {
          borderWidth: "1px",
          borderColor: "primary",
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
  return (
    <div className="App">
      <ThemeProvider theme={theme}>
        <BrowserRouter>
          <Routes>
            <Route path="/login" Component={LoginPage} />
          </Routes>
        </BrowserRouter>
      </ThemeProvider>
    </div>
  );
}

export default App;
