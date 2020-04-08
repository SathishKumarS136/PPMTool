import Axios from "axios";
import {
  GET_ERRORS,
  GET_BACKLOG,
  GET_PROJECT_TASK,
  DELETE_PROJECT_TASK,
} from "./types";

export const addProjectTask = (backlog_id, projectTask, history) => async (
  dispatch
) => {
  try {
    await Axios.post(
      `http://localhost:8080/api/backlog/${backlog_id}`,
      projectTask
    );
    dispatch({
      type: GET_ERRORS,
      payload: {},
    });
    history.push(`/projectBoard/${backlog_id}`);
  } catch (error) {
    dispatch({
      type: GET_ERRORS,
      payload: error.response.data,
    });
  }
};
export const getBacklog = (backlog_id) => async (dispatch) => {
  try {
    const res = await Axios.get(
      `http://localhost:8080/api/backlog/${backlog_id}`
    );
    dispatch({
      type: GET_ERRORS,
      payload: {},
    });
    dispatch({
      type: GET_BACKLOG,
      payload: res.data,
    });
  } catch (error) {
    dispatch({
      type: GET_ERRORS,
      payload: error.response.data,
    });
  }
};

export const getProjectTask = (backlog_id, project_sequence, history) => async (
  dispatch
) => {
  try {
    const res = await Axios.get(
      `http://localhost:8080/api/backlog/${backlog_id}/${project_sequence}`
    );
    dispatch({
      type: GET_ERRORS,
      payload: {},
    });
    dispatch({
      type: GET_PROJECT_TASK,
      payload: res.data,
    });
  } catch (error) {
    history.push("/dashboard");
    dispatch({
      type: GET_ERRORS,
      payload: error.response.data,
    });
  }
};

export const updateProjectTask = (
  backlog_id,
  project_sequence,
  projectTask,
  history
) => async (dispatch) => {
  try {
    await Axios.patch(
      `http://localhost:8080/api/backlog/${backlog_id}/${project_sequence}`,
      projectTask
    );
    history.push(`/projectBoard/${backlog_id}`);
    dispatch({
      type: GET_ERRORS,
      payload: {},
    });
  } catch (error) {
    dispatch({
      type: GET_ERRORS,
      payload: error.response.data,
    });
  }
};

export const deleteProjectTask = (backlog_id, project_sequence) => async (
  dispatch
) => {
  if (window.confirm("Are you sure?")) {
    await Axios.delete(
      `http://localhost:8080/api/backlog/${backlog_id}/${project_sequence}`
    );
    dispatch({
      type: DELETE_PROJECT_TASK,
      payload: project_sequence,
    });
  }
};
