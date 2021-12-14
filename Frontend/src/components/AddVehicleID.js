import axios from 'axios'
import React, { Component } from 'react'
import "../styles/form.css"


class AddVehicleID extends Component {
    constructor(props) {
        super(props)
        this.state = {         
            username: '',
            vehcileID: '',
        }
        this.changeUsernameHandler = this.changeUsernameHandler.bind(this);
        this.changeVehicleHandler = this.changeVehicleHandler.bind(this);
        this.saveMember = this.saveMember.bind(this);
    }


    saveMember = (hndl) => {
        hndl.preventDefault();
        let member = { username: this.state.username, password: this.state.password , role : "DRIVER"};
        console.log(this.state.color);
        axios.put(`http://localhost:8083/${this.state.username}/${this.state.vehcileID}`, member).then((response) => {
            console.log(response)
        });
        }


    changeUsernameHandler = (event) => {
        this.setState({ username: event.target.value });
    }
    changeVehicleHandler = (event) => {
        this.setState({ vehcileID: event.target.value });
    }


    render() {
        return (
            <div>
                <div className="container">
                    <div className="row">
                        <div className="card col-md-6 offset-md-3 offset-md-3">
                            <h3 className="text-center">REGISTER</h3>
                            <div className="card-body">
                                <form onSubmit = {this.hndlSubmit} style = {{ marginBottom: '30mm' }}>

                                    <div className="form=group">
                                        <label> Username : </label>
                                        <input placeholder="Username" name="usrname" className="form-control"
                                            value={this.state.username} onChange={this.changeUsernameHandler} />
                                    </div>
                                    <div className="form=group">
                                        <label> Vehicle ID : </label>
                                        <input placeholder="VeheicleID" name="vehicleID" className="form-control"
                                            value={this.state.vehcileID} onChange={this.changeVehicleHandler} />
                                    </div>
                                    <br></br>
                                    <button className="btn btn-success" onClick={this.saveMember}>Add vehicle to user</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}
export default AddVehicleID