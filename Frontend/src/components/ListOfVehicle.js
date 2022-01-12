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

  changeVehicleStatus(id){
    axios.put(
      `http://localhost:8083/Vehicle/DisableVehicle/${id}`
    )
    .then((response) => {
      this.setState({
        vehicles: response.data,
      });
      console.log(this.state.vehicles);
    });
  }

  checkVehicleStatus(status){
    if(String(status) === "false"){
      return "Disabled"
    }
    else if(String(status) === "true"){
      return "Enabled"
    }
  }

  render() {
    return (
      <div className="vehicle">
        <div className="vehicleButtons">
        <label htmlFor="vehicle" style={{fontSize:"40px"}}>VEHICLE</label>
        <Button variant="primary" className="vButtons" href={"/VehicleCreation"}>Add vehcile</Button>
        <br></br>
        <Button variant="primary"className="vButtons" href={"/ConnectVehicle"}>Connect user to vehicle</Button>
        </div>


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
              <CardSubtitle>Status :{this.checkVehicleStatus(vehicle.active)}</CardSubtitle>

              <Button variant="primary" href={"/" + "Vehicle" + "/" + vehicle.id}>Change Details</Button>
              <Button variant="primary" onClick={() => this.changeVehicleStatus(vehicle.id)}> Change vehicle status </Button>

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
