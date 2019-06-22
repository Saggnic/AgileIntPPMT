import { GET_PROJECTS } from "../actions/types";

const initialState = {
  projects: [],
  project: {}
};

export default function(state = initialState, actions) {
  switch (actions.type) {
    case GET_PROJECTS:
      return { ...state, projects: actions.payload };

    default:
      return state;
  }
}
