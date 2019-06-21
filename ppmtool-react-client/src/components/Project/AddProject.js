import React, { Component } from "react";

class AddProject extends Component {
  constructor() {
    super();

    this.state = {
      description: " ",
      projectIdentifier: "",
      projectName: "",
      start_date: "",
      end_date: ""
    };
  }

  onChange(e) {
    //this.setState({ projectName: e.target.value }); long way
    this.setState({ [e.target.name]: e.target.value });
  }

  onSubmit(e) {
    e.preventDefault();
    const newProject = {
      description: this.state.description,
      projectIdentifier: this.state.projectIdentifier,
      projectName: this.state.projectName,
      start_date: this.state.start_date,
      end_date: this.state.end_date
    };
  }

  render() {
    return (
      <div>
        {
          //check name attribute input fields
          //create constructor
          //sset state
          //set value of inpit field
          //create onChange function
          //set onChange on input field
          //bind on construnctor
          //check state change in the react extensiion
        }
        <div className="project">
          <div className="container">
            <div className="row">
              <div className="col-md-8 m-auto">
                <h5 className="display-4 text-center">Create Project form</h5>
                <hr />
                <form onSubmit={this.onSubmit.bind(this)}>
                  <div className="form-group">
                    <input
                      type="text"
                      className="form-control form-control-lg "
                      placeholder="Project Name"
                      name="projectName"
                      value={this.state.projectName}
                      onChange={this.onChange.bind(this)}
                    />
                  </div>
                  <div className="form-group">
                    <input
                      type="text"
                      className="form-control form-control-lg"
                      placeholder="Unique Project ID"
                      name="projectIdentifier"
                      value={this.state.projectIdentifier}
                      onChange={this.onChange.bind(this)}
                    />
                  </div>
                  <div className="form-group">
                    <textarea
                      className="form-control form-control-lg"
                      placeholder="Project Description"
                      name="description"
                      value={this.state.description}
                      onChange={this.onChange.bind(this)}
                    />
                  </div>
                  <h6>Start Date</h6>
                  <div className="form-group">
                    <input
                      type="date"
                      className="form-control form-control-lg"
                      name="start_date"
                      value={this.state.start_date}
                      onChange={this.onChange.bind(this)}
                    />
                  </div>
                  <h6>Estimated End Date</h6>
                  <div className="form-group">
                    <input
                      type="date"
                      className="form-control form-control-lg"
                      name="end_date"
                      value={this.state.end_date}
                      onChange={this.onChange.bind(this)}
                    />
                  </div>

                  <input
                    type="submit"
                    className="btn btn-primary btn-block mt-4"
                  />
                </form>
              </div>
            </div>
          </div>
        </div>

        <h1>Add Project Form</h1>
      </div>
    );
  }
}

export default AddProject;

//"name" attributes in <form>  should exactly match java attribute name in backend
