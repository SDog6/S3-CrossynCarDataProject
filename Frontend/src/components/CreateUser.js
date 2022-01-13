import axios from 'axios'
import React, { Component } from 'react'
import "../styles/form.css"
import { Button, Form, FormGroup, Label, Input, FormText } from 'reactstrap';



class CreateUser extends Component {
    constructor(props) {
        super(props)
        this.state = {         
            username: '',
            password: '',
            role:''
        }
        this.changeUsernameHandler = this.changeUsernameHandler.bind(this);
        this.changePasswordHandler = this.changePasswordHandler.bind(this);
        this.changeRoleHandler = this.changeRoleHandler.bind(this);

        this.saveMember = this.saveMember.bind(this);
    }


    saveMember = (hndl) => {
        hndl.preventDefault();
        var tok = localStorage.getItem('token');
        let member = { username: this.state.username, password: this.state.password , role : this.state.role};
        axios.post("http://localhost:8083/register", member, 
        {headers: {"Authorization" : `${tok}`}}).then((response) => {
            console.log(response)
            window.location.href = '/Users';

        });
        }


    changeUsernameHandler = (event) => {
        this.setState({ username: event.target.value });
    }
    changeEmailHandler = (event) => {
        this.setState({ email: event.target.value });
    }
    changePasswordHandler = (event) => {
        this.setState({ password: event.target.value });
    }
    changeRoleHandler = (event) => {
        this.setState({ role: event.target.value });
    }

    render() {
        return (
            <div>
                <div className="container">
                    <div className="row">
                        <div className="card col-md-6 offset-md-3 offset-md-3">
                            <h3 className="text-center">Create a user</h3>
                            <div className="card-body">
                                <form onSubmit = {this.hndlSubmit} style = {{ marginBottom: '30mm' }}>

                                    <div className="form=group">
                                        <label> Username : </label>
                                        <input placeholder="Username" name="usrname" className="form-control"
                                            value={this.state.username} onChange={this.changeUsernameHandler} />
                                    </div>
                                    <div className="form=group">
                                        <label> Password : </label>
                                        <input placeholder="Password" name="password" className="form-control"
                                            value={this.state.password} onChange={this.changePasswordHandler} />
                                    </div>
                                    <div className="form=group">
                                    <FormGroup>
          <Label for="Gender">Select Role</Label>
          <Input type="select" name="select" id="exampleSelect" 
          value = {this.state.role} onChange={this.changeRoleHandler}>
            <option value="" select disabled>Select role</option>
            <option value="DRIVER">Driver</option>
            <option value="FLEETOWNER">Fleet owner</option>
            <option value="CROSSYNEMPLOYEE">Crossyn Employee</option>

          </Input>
        </FormGroup>
                                    </div>
                                    <br></br>
                                    <button className="btn btn-success" onClick={this.saveMember}>Create user</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}
export default CreateUser