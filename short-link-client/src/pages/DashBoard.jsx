import Navbar from "./Layout/NavBar";
import {
  Box,
  Grid,
  Paper,
  Typography,
  Button,
  Container,
  Avatar,
  Dialog,
  DialogTitle,
  DialogContent,
  TextField,
} from "@mui/material";
import { ReactComponent as NetworkIcon } from "../images/network-icon.svg";
import { ReactComponent as LinkIcon } from "../images/link-icon.svg";
import { ReactComponent as StatisticBarIcon } from "../images/statistics-bars-icon.svg";
import Logo from "../utils/Logo";
import { useEffect, useState } from "react";
import axios from "axios";
import { Link } from "react-router-dom";
import { toast } from "react-toastify";
import { useNavigate } from "react-router-dom";

function DashBoard() {
  const [projectList, setProjectList] = useState([]);
  const [isCreated, setIsCreated] = useState(false);

  useEffect(() => {
    axios
      .get("http://localhost:8080/dashboard/get-project-list")
      .then((res) => setProjectList(res.data))
      .catch((err) => console.log(err));
  }, [isCreated]);

  return (
    <>
      <Navbar />
      <Box>
        <ProjectList projectList={projectList} setIsCreated={setIsCreated} />
      </Box>
    </>
  );
}

export default DashBoard;

function ProjectList({ projectList, setIsCreated }) {
  const navigate = useNavigate();

  function goToProjectDashboard(project) {
    navigate(`/${project.projectSlug}`, { state: { project: project } });
  }

  return (
    <Box>
      <Box
        height="15vh"
        display="flex"
        flexDirection="row"
        justifyContent="space-around"
        borderBottom={1}
        borderColor="primary.300"
      >
        <Box display="flex" alignItems="center">
          <Typography variant="h1" color="primary.700" sx={{ fontSize: 30 }}>
            My Projects
          </Typography>
        </Box>
        <Box display="flex" alignItems="center">
          <CreateProject setIsCreated={setIsCreated} />
        </Box>
      </Box>
      <Box bgcolor="primary.100" height="78vh" marginTop={3}>
        <Container>
          <Grid container spacing={3}>
            {projectList.map((project) => (
              <Grid item xs={12} sm={4} key={project.projectID} marginTop={2}>
                <Paper
                  elevation={3}
                  onClick={() => goToProjectDashboard(project)}
                  sx={{ cursor: "pointer" }}
                >
                  <Box padding={3}>
                    <Box display="flex">
                      <Avatar sx={{ bgcolor: "secondary.300" }}>
                        {project.projectName.substring(0, 2).toUpperCase()}
                      </Avatar>
                      <Box marginLeft={2}>
                        <Typography
                          variant="h2"
                          sx={{ fontSize: 20 }}
                          color="primary.900"
                        >
                          {project.projectName}
                        </Typography>
                        <Typography
                          variant="p"
                          align="center"
                          color="primary.700"
                        >
                          {project.domain}
                        </Typography>
                      </Box>
                    </Box>
                    <Box marginTop={4} display="flex" gap={1}>
                      <Typography
                        variant="p"
                        sx={{ fontSize: 18 }}
                        color="primary.700"
                        display="flex"
                        alignItems="center"
                      >
                        <NetworkIcon /> 1 domains
                      </Typography>{" "}
                      <Typography
                        variant="p"
                        sx={{ fontSize: 18 }}
                        color="primary.700"
                        display="flex"
                        alignItems="center"
                      >
                        <LinkIcon />
                        {project.totalLink !== null
                          ? project.totalLink
                          : 0}{" "}
                        links
                      </Typography>{" "}
                      <Typography
                        variant="p"
                        sx={{ fontSize: 18 }}
                        color="primary.700"
                        display="flex"
                        alignItems="center"
                      >
                        <StatisticBarIcon />
                        {project.totalClick !== null
                          ? project.totalClick
                          : 0}{" "}
                        click
                      </Typography>
                    </Box>
                  </Box>
                </Paper>
              </Grid>
            ))}
          </Grid>
        </Container>
      </Box>
    </Box>
  );
}

function CreateProject({ setIsCreated }) {
  const [data, setData] = useState({});
  const [isOpen, setIsOpen] = useState(false);
  const handleOpen = () => setIsOpen(true);
  const handleClose = () => setIsOpen(false);

  const submit = async () => {
    const res = await axios.post(
      "http://localhost:8080/dashboard/create-project",
      data
    );

    if (
      res.data.projectName === data.name &&
      res.data.projectSlug === data.slug
    ) {
      toast.success("Create new project success");
      setIsCreated(true);
      handleClose();
    } else {
      toast.error("Create new project failed");
    }
  };

  const handleChange = (event) => {
    setData((prevData) => ({
      ...prevData,
      [event.target.name]: event.target.value,
    }));
  };

  return (
    <>
      <Button onClick={handleOpen} variant="contained" size="medium">
        Create Project
      </Button>
      <Dialog open={isOpen} onClose={handleClose} fullWidth maxWidth="xs">
        <DialogTitle>
          <Box display="flex" flexDirection="column" alignItems="center">
            <Logo size={70} />
            <Typography variant="h6">Create a new project</Typography>
          </Box>
        </DialogTitle>
        <Box bgcolor="primary.100" borderTop={1} borderColor="primary.300">
          <DialogContent>
            <Container>
              <Grid container spacing={5}>
                <Grid item xs={12}>
                  <TextField
                    autoFocus
                    required
                    name="name"
                    label="Project name"
                    type="text"
                    size="small"
                    fullWidth
                    variant="outlined"
                    onChange={handleChange}
                  />
                </Grid>
                <Grid item xs={12}>
                  <TextField
                    required
                    name="slug"
                    label="Project Slug"
                    type="text"
                    size="small"
                    fullWidth
                    variant="outlined"
                    onChange={handleChange}
                  />
                </Grid>
                <Grid item xs={12}>
                  <Button
                    type="button"
                    variant="contained"
                    size="medium"
                    fullWidth
                    onClick={submit}
                  >
                    Create project
                  </Button>
                </Grid>
              </Grid>
            </Container>
          </DialogContent>
        </Box>
      </Dialog>
    </>
  );
}
