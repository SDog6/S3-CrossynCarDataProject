import React, { Component } from "react";
import axios from "axios";
import { Card, CardImg, CardText, CardBody,
  CardTitle, CardSubtitle, Button } from 'reactstrap';
import "../styles/vehicleCard.css"

class ListOfVehicle extends Component {
  constructor(props) {
    super(props);
    this.state = {
      vehicles: [],
    };
  }

  componentDidMount() {
    axios
      .get(
        `http://localhost:8083/Vehicle`
      )
      .then((response) => {
        this.setState({
          vehicles: response.data,
        });
        console.log(this.state.vehicles);
      });
  }

  render() {
    return (
      <div className="vehicle">
        <label htmlFor="vehicle" style={{fontSize:"40px"}}>VEHICLE</label>
      <div className="grid-container">
        {this.state.vehicles.map(vehicle => (   
        <div>
          <div className="wrapper">
            <Card>
            <CardBody>
              <CardTitle className="lpate" style={{fontWeight:"bolder"}}>{vehicle.lplate}</CardTitle>
              <CardSubtitle>ID :{vehicle.id}</CardSubtitle>
              <CardSubtitle>Brand :{vehicle.brand}</CardSubtitle>
              <CardSubtitle>Color :{vehicle.color}</CardSubtitle>
              <Button variant="primary" href={"/" + "Vehicle" + "/" + vehicle.id}>Change Details</Button>

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
export default ListOfVehicle;
