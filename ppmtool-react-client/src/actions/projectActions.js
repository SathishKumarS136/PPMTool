import Axios from "axios";
import { GET_ERRORS, GET_PROJECTS, GET_PROJECT, DELETE_PROJECT } from "./types";

export const createProject = (project, history) => async (dispatch) => {
  try {
    await Axios.post("http://localhost:8080/api/project", project);
    dispatch({
      type: GET_ERRORS,
      payload: {},
    });
    history.push("/dashboard");
  } catch (error) {
    dispatch({
      type: GET_ERRORS,
      payload: error.response.data,
    });
  }
};

export const getProjects = () => async (dispatch) => {
  const res = await Axios.get("http://localhost:8080/api/project/all");
  dispatch({
    type: GET_ERRORS,
    payload: {},
  });
  dispatch({
    type: GET_PROJECTS,
    payload: res.data,
  });
};

export const getProject = (id, history) => async (dispatch) => {
  try {
    const res = await Axios.get(`http://localhost:8080/api/project/${id}`);
    dispatch({
      type: GET_ERRORS,
      payload: {},
    });
    dispatch({
      type: GET_PROJECT,
      payload: res.data,
    });
  } catch (error) {
    history.push("/dashboard");
  }
};
export const deleteProject = (id) => async (dispatch) => {
  if (window.confirm("Are you sure?")) {
    await Axios.delete(`http://localhost:8080/api/project/${id}`);
    dispatch({
      type: DELETE_PROJECT,
      payload: id,
    });
  }
};
