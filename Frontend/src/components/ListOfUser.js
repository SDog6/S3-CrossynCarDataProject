import React, { Component } from "react";
import axios from "axios";
import {
  Card,
  CardImg,
  CardText,
  CardBody,
  CardTitle,
  CardSubtitle,
  Button,
} from "reactstrap";
import "../styles/userCard.css";

class ListOfUser extends Component {
  constructor(props) {
    super(props);
    this.state = {
      users: [],
    };
  }

  componentDidMount() {
    axios.get(`http://localhost:8083/User`).then((response) => {
      this.setState({
        users: response.data,
      });
      console.log(this.state.users);
      console.log(response);
    });
  }

  render() {
    return (
      <div className="user">
        <div className="vehicleButtons">
          <label htmlFor="user" style={{ fontSize: "40px" }}>
            USERS
          </label>
          <br></br>

          <Button variant="primary" href={"/CreateUser"}>
            Create a user
          </Button>
        </div>
        <div className="grid-container">
          {this.state.users.map((user) => (
            <div>
              <div className="wrapper">
                <Card>
                  <CardBody>
                    <CardTitle>{user.type}</CardTitle>
                    <CardSubtitle>Username :{user.username}</CardSubtitle>
                    <CardSubtitle>Role :{user.role}</CardSubtitle>
                    <CardSubtitle>
                      <b>Connected Vehicle IDs:</b>
                    </CardSubtitle>
                    {user.connectedVehicles.map((product) => (
                      <CardSubtitle> - {product}</CardSubtitle>
                    ))}
                  </CardBody>
                </Card>
                <br></br>
              </div>
            </div>
          ))}
        </div>
      </div>
    );
  }
}
export default ListOfUser;
