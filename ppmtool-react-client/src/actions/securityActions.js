import Axios from "axios";
import { GET_ERRORS, SET_CURRENT_USER } from "./types";
import setJwtToken from "../securityUtils/setJwtToken";
import jwt_decode from "jwt-decode";

export const registerUser = (user, history) => async (dispatch) => {
  try {
    await Axios.post("http://localhost:8080/api/users/register", user);
    dispatch({
      type: GET_ERRORS,
      payload: {},
    });
    history.push("/login");
  } catch (error) {
    dispatch({
      type: GET_ERRORS,
      payload: error.response.data,
    });
  }
};

export const loginUser = (user) => async (dispatch) => {
  try {
    const res = await Axios.post("http://localhost:8080/api/users/login", user);
    dispatch({
      type: GET_ERRORS,
      payload: {},
    });
    const { token } = res.data;
    window.localStorage.setItem("JWT_TOKEN", token);
    setJwtToken(token);
    const decodedUser = jwt_decode(token);
    dispatch({
      type: SET_CURRENT_USER,
      payload: decodedUser,
    });
  } catch (error) {
    dispatch({
      type: GET_ERRORS,
      payload: error.response.data,
    });
  }
};
export const logoutUser = () => async () => {
  window.localStorage.removeItem("JWT_TOKEN");
  setJwtToken(false);
  window.location.href = "/";
};
