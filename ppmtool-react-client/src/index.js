import React from "react";
import ReactDOM from "react-dom";
import "./index.css";
import App from "./App";
import * as serviceWorker from "./serviceWorker";

//Flow: App.js (makes all components and routes) and sends data here in the <App/>
//Flow: From here the page is shown in index.html <div id="root">
//index.html is the entry point floowed by index.js -->app.js-->components inside

ReactDOM.render(<App />, document.getElementById("root"));

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
