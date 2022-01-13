import React, { Component } from "react";
import axios from "axios";
import { Card, CardImg, CardText, CardBody,
  CardTitle, CardSubtitle, Button } from 'reactstrap';
import "../styles/vehicleCard.css"
import jwtDecode from "jwt-decode";

class ListOfVehicle extends Component {
  constructor(props) {
    super(props);
    this.state = {
      vehicles: [],
      role : ""
    };
  }

  componentDidMount() {
      var tok = localStorage.getItem('token');
      if(tok == null){
      }
      else {
        var decoded = jwtDecode(tok);
        this.setState({role : decoded.role})
        if(decoded.role === "CROSSYNEMPLOYEE"){
          axios
          .get(
            `http://localhost:8083/Vehicle`,
            {headers: {"Authorization" : `${tok}`}}
          )
          .then((response) => {
            this.setState({
              vehicles: response.data,
            });
            console.log(this.state.vehicles);
          });
        }
        else {
          axios
          .get(
            `http://localhost:8083/Vehicle/UserVehicles/${decoded.sub}`,
            {headers: {"Authorization" : `${tok}`}}
          )
          .then((response) => {
            this.setState({
              vehicles: response.data,
            });
            console.log(response.data);
          });
        }
      }
  }

  changeVehicleStatus(id){
    var tok = localStorage.getItem('token');
    var decoded = jwtDecode(tok);
    if(decoded.role === "CROSSYNEMPLOYEE"){
      axios.put(
        `http://localhost:8083/Vehicle/DisableVehicle/${id}`,
        {headers: {"Authorization" : `${tok}`}}
      )
      .then((response) => {
        this.setState({ 
          vehicles: response.data,
        });
        console.log(this.state.vehicles);
      });
    }
    else if(decoded.role === "FLEETOWNER"){
      axios.put(
        `http://localhost:8083/Vehicle/DisableVehicle/${id}/${decoded.sub}`,
        {headers: {"Authorization" : `${tok}`}}
      )
      .then((response) => {
        this.setState({ 
          vehicles: response.data,
        });
        console.log(this.state.vehicles);
      });
    }
    
   
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
        <label htmlFor="vehicle" style={{fontSize:"40px"}}>VEHICLES</label>
        {this.state.role === "CROSSYNEMPLOYEE" ? (
          <div  className="vehicleButtons"> 
                 <Button variant="primary" className="vButtons" href={"/VehicleCreation"}>Add vehcile</Button>
                 <br></br>
                 <Button variant="primary"className="vButtons" href={"/ConnectVehicle"}>Connect user to vehicle</Button>
                 </div>
              ) : (
                ""
              )}
{this.state.role === "FLEETOWNER" ? (
          <div  className="vehicleButtons"> 
                 <Button variant="primary" className="vButtons" href={"/VehicleCreation"}>Add vehcile</Button>
                 <br></br>
                 </div>
              ) : (
                ""
              )}
      
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

              {this.state.role === "CROSSYNEMPLOYEE" || this.state.role === "FLEETOWNER"? (
          <div>
 <Button variant="primary" href={"/" + "Vehicle" + "/" + vehicle.id}>Change Details</Button>
              <Button variant="primary" onClick={() => this.changeVehicleStatus(vehicle.id)}> Change vehicle status </Button>
                 </div>
              ) : (
                ""
              )}
             
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
