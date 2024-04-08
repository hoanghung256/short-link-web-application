import { Grid, Toolbar, Box, Typography } from "@mui/material";
import Logo from "../../utils/Logo";

function Navbar() {
  return (
    <Box
      position="static"
      borderBottom={1}
      borderColor="primary.300"
      height="7vh"
    >
      <Toolbar>
        <Grid container justifyContent="center">
          <Grid item xs={10}>
            <Box
              sx={{
                width: "100%",
                display: "flex",
                alignItems: "center",
                justifyContent: "space-between",
              }}
            >
              <Logo size={70} />
              <Typography variant="h6" component="div">
                Profile
              </Typography>
            </Box>
          </Grid>
        </Grid>
      </Toolbar>
    </Box>
  );
}

export default Navbar;
