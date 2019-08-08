import axios from "axios";
import { GET_ERRORS, SET_CURRENT_USER } from "./types";
import setJWTToken from "../securityUtils/setJWTToken";
import jwt_decode from "jwt-decode";

export const createNewUser = (newUser, history) => async dispatch => {
  try {
    const res = await axios.post(
      "http://localhost:8080/api/users/register",
      newUser
    );
    history.push("/login");
    dispatch({
      type: GET_ERRORS,
      payload: {}
    });
  } catch (err) {
    dispatch({
      type: GET_ERRORS,
      payload: err.res.data
    });
  }
};

export const login = LoginRequest => async dispatch => {
  try {
    //post=>Login Request

    const res = await axios.post(
      "http://localhost:8080/api/users/login",
      LoginRequest
    );

    //extract the token from res.data
    const { token } = res.data;

    //store the token in the local storage
    localStorage.setItem("jwtToken", token);

    //set our token in the headers
    setJWTToken(token);

    //Decode the token in react
    const decoded = jwt_decode(token);

    //Dispatch  to our security reducer

    dispatch({
      type: SET_CURRENT_USER,
      payload: decoded
    });
  } catch (err) {
    dispatch({
      type: GET_ERRORS,
      payload: err.res.data
    });
  }
};

export const logOut = () => dispatch => {
  localStorage.removeItem("jwtToken");
  setJWTToken(false);
  dispatch({
    type: SET_CURRENT_USER,
    payload: {}
  });
};
