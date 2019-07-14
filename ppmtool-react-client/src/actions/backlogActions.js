import axios from "axios";
import {
  GET_BACKLOG,
  GET_PROJECT_TASK,
  DELETE_PROJECT_TASK
} from "../actions/types";

export const addProjectTask = (
  backlog_id,
  project_task,
  history
) => async dispatch => {
  await axios.post(
    `http://localhost:8080/api/backlog/${backlog_id}`,
    project_task
  );
  history.push(`http://localhost:8080/api/projectBoard/${backlog_id}`);
};
