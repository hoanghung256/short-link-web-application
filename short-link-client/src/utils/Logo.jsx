import { ReactComponent as Image } from "../images/logo.svg";
import { Avatar } from "@mui/material";

function Logo({ size }) {
  return (
    <Avatar
      sx={{
        m: 1,
        width: size,
        height: size,
        bgcolor: "transparent",
        borderRadius: 0,
      }}
    >
      <Image />
    </Avatar>
  );
}

export default Logo;
