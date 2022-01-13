import React from "react";
import { Switch } from "react-router";
import { Button, Form, FormGroup, Label, Input, FormText } from "reactstrap";
import axios from "axios";

export default class AddVehicle extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      id: "",
      brand: "",
      lplate: "",
      color: "",
      errorMessage: "",
    };
    this.changeIdHandler = this.changeIdHandler.bind(this);
    this.changeBrandHandler = this.changeBrandHandler.bind(this);
    this.changeLPlateHandler = this.changeLPlateHandler.bind(this);
    this.changeColorHandler = this.changeColorHandler.bind(this);
    this.saveProd = this.saveProd.bind(this);
  }

  saveProd = (hndl) => {
    hndl.preventDefault();
    if (
      this.state.id === "" ||
      this.state.lplate === "" ||
      this.state.color === "" ||
      this.state.brand === ""
    ) {
      this.setState({ errorMessage: "Fill in all the empty fields" });
    } else {
      const vehicle = {
        id: this.state.id,
        brand: this.state.brand,
        lplate: this.state.lplate,
        color: this.state.color,
      };
      console.log(this.state.color);
      axios.post("http://localhost:8083/Vehicle", vehicle).then(
        (response) => {
          console.log(response);
          this.setState({ errorMessage: "New vehicle is created" });
        },
        (error) => {
          console.log(error);
          this.setState({ errorMessage: "Vehicle already exists" });
        }
      );
    }
  };

  changeIdHandler = (event) => {
    this.setState({ id: event.target.value });
  };
  changeBrandHandler = (event) => {
    this.setState({ brand: event.target.value });
  };
  changeLPlateHandler = (event) => {
    this.setState({ lplate: event.target.value });
  };
  changeColorHandler = (event) => {
    this.setState({ color: event.target.value });
  };

  back() {
    this.props.history.push("/Trips");
  }

  render() {
    return (
      <div>
        <Form>
          <legend className="AddVehicle">New Vehicle</legend>
          <FormGroup>
            <Label for="artNumb">ID:</Label>
            <Input
              type="id"
              name="id"
              id="id"
              placeholder="ID"
              value={this.state.id}
              onChange={this.changeIdHandler}
            />
          </FormGroup>
          <FormGroup>
            <Label for="CatName">Brand:</Label>
            <Input
              type="brand"
              name="brand"
              id="brand"
              placeholder="Brand"
              value={this.state.brand}
              onChange={this.changeBrandHandler}
            />
          </FormGroup>
          <FormGroup>
            <Label for="lplate">License Plate:</Label>
            <Input
              type="lplate"
              name="lplate"
              id="lplate"
              placeholder="License Plate"
              value={this.state.lplate}
              onChange={this.changeLPlateHandler}
            />
          </FormGroup>
          <FormGroup>
            <Label for="quantity">Color:</Label>
            <Input
              type="text"
              name="color"
              id="color"
              placeholder="Color"
              value={this.state.color}
              onChange={this.changeColorHandler}
            />
          </FormGroup>
          <br></br>
          <Button onClick={this.saveProd} style={{ marginLeft: "10px" }}>
            Submit
          </Button>
          <Button onClick={this.back.bind(this)} style={{ marginLeft: "10px" }}>
            Back
          </Button>
        </Form>
        {this.state.errorMessage && (
          <p className="message" style={{ textAlign: "center" }}>
            {" "}
            {this.state.errorMessage}{" "}
          </p>
        )}
      </div>
    );
  }
}
