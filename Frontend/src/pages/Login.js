import React, { Component } from 'react'
import axios from 'axios';
import { Redirect } from 'react-router';
import '../styles/Login.css';
import NavBar from '../components/Navbar';


class Login extends Component{
    constructor(props) {
        super(props)
        this.state = {
            email: '',
            password: '',
            errorMessage:''
        }
        this.changeEmailHandler = this.changeEmailHandler.bind(this);
        this.changePasswordHandler = this.changePasswordHandler.bind(this);
        this.checkUser = this.checkUser.bind(this);
    }



    checkUser = (hndl) => {
        hndl.preventDefault();
        // const emails = this.state.email;
        // const passwordd = this.state.password
        // const user = { emails,passwordd };
        // vars = JSON.stringify(user);
        axios.post("http://localhost:8083/login", {
            username:this.state.email,
            password:this.state.password
        })
            .then(response => { 
                console.log(response);
                const token = "logged in";
                localStorage.setItem("token", token);
                this.props.history.push("/");

            
            },
            (error) => {
                console.log(error);
                this.setState({errorMessage: error.message});
              });
    }
    

    changeEmailHandler = (event) => {
        this.setState({ email: event.target.value });
    }
    changePasswordHandler = (event) => {
        this.setState({ password: event.target.value });
    }

    render() {
        return (
            <div>
                <div className="container">
                    <div className="row">
                        <div className="card col-md-6 offset-md-3 offset-md-3">
                            <h3 className="text-center">Sign In</h3>
                            <div className="card-body">
                                <form>
                                    <div className="form=group">
                                        <label> Email : </label>
                                        <input placeholder="Email" name="email" className="form-control"
                                            value={this.state.email} onChange={this.changeEmailHandler} />
                                    </div>
                                    <div className="form=group">
                                        <label> Password : </label>
                                        <input placeholder="Password" name="password" className="form-control"
                                            value={this.state.password} onChange={this.changePasswordHandler} />
                                    </div>
                                  
                                    <br></br><button className="btn btn-success" onClick={this.checkUser}>Sign In</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
                { this.state.errorMessage &&
                   <p className="error"> { "Username or password incorrect" } </p> }
            </div>
        )
    }
}
export default Login