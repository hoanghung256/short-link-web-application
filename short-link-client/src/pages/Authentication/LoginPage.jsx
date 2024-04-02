import {
  Avatar,
  Button,
  CssBaseline,
  Grid,
  Box,
  Typography,
  Container,
} from "@mui/material";
import { ReactComponent as Logo } from "../../images/logo.svg";

function LoginPage() {
  return (
    <Container
      component="main"
      maxWidth="xs"
      sx={{ display: "flex", justifyContent: "center" }}
      bgcolor="hello"
    >
      <CssBaseline />
      <Box
        sx={{
          marginTop: 30,
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
          border: 2,
          borderColor: "#F0F0F0",
          borderRadius: 3,
        }}
      >
        <Avatar
          sx={{
            m: 1,
            width: 70,
            height: 70,
            bgcolor: "transparent",
            borderRadius: 0,
          }}
        >
          <Logo />
        </Avatar>
        <Typography component="h1" variant="h5">
          Sign in
        </Typography>
        <Typography component="p" variant="p">
          Start creating short links with superpowers
        </Typography>
        <hr />
        <Box
          component="form"
          noValidate
          sx={{
            padding: 4,
            border: 1,
            borderRadius: 3,
            borderTopRightRadius: 0,
            borderTopLeftRadius: 0,
            borderColor: "#F0F0F0",
          }}
        >
          <Grid container spacing={2}>
            <Grid item xs={6}>
              <Button fullWidth size="medium" variant="contained">
                google
              </Button>
            </Grid>
            <Grid item xs={6}>
              <Button fullWidth size="medium" variant="contained">
                github
              </Button>
            </Grid>
            <Grid item xs={12}>
              <Button fullWidth size="medium" variant="contained" onClick={submit}>
                Continue with email
              </Button>
            </Grid>
          </Grid>
        </Box>
      </Box>
    </Container>
  );
}

export default LoginPage;
