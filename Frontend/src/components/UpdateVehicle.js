import axios from "axios";
import React, { Component, useEffect, useState } from "react";

// import postFormData from '../api/post'
// import "../css/form.css"

function UpdateVehicle(props) {
  const PropsID = props.match.params.id;
  const [Vehicle, setVehicle] = useState([]);
  const [LPlate, setLPlate] = useState([]);
  const [Color, setColor] = useState([]);
  const [message, setmessage] = useState("");

  useEffect(() => {
    var tok = localStorage.getItem('token');
    axios.get(`http://localhost:8083/Vehicle/${PropsID}`, 
    {headers: {"Authorization" : `${tok}`}}).then((response) => {
      setVehicle(response.data);
      console.log(response.data);
      console.log(LPlate);
    });
  }, []);

  function saveMember(event) {
    var tok = localStorage.getItem('token');
    event.preventDefault();
    if (LPlate === [] || Color === []) {
      setmessage("Fill in the empty fields");
    } else {
      let vehicle = {
        id: Vehicle.id,
        brand: Vehicle.brand,
        lplate: LPlate,
        color: Color,
      };
      axios
        .put(`http://localhost:8083/Vehicle/${Vehicle.id}`, vehicle, 
        {headers: {"Authorization" : `${tok}`}})
        .then((response) => {
          console.log(response.data);
          setmessage("Updated");
        });
    }
  }

  return (
    <div>
      <div className="container">
        <div className="row">
          <div className="card col-md-6 offset-md-3 offset-md-3">
            <h3 className="text-center">Update Vehicle information</h3>
            <div className="card-body">
              <form style={{ marginBottom: "10mm" }}>
                <div className="form=group">
                  <label> Vehicle ID : </label>
                  <input
                    placeholder={Vehicle.id}
                    name="fname"
                    className="form-control"
                    value=""
                    disabled
                  />
                </div>
                <div className="form=group">
                  <label> Brand : </label>
                  <input
                    placeholder={Vehicle.brand}
                    name="lname"
                    className="form-control"
                    value=""
                    disabled
                  />
                </div>
                <div className="form=group">
                  <label> License Plate : </label>
                  <input
                    placeholder={Vehicle.lplate}
                    name="lPlate"
                    className="form-control"
                    value={LPlate}
                    onChange={(e) => setLPlate(e.target.value)}
                  />
                </div>
                <div className="form=group">
                  <label> Color : </label>
                  <input
                    placeholder={Vehicle.color}
                    name="color"
                    className="form-control"
                    value={Color}
                    onChange={(e) => setColor(e.target.value)}
                  />
                </div>
                <br></br>

                <button className="btn btn-success" onClick={saveMember}>
                  Save
                </button>

                {
                <p className="message" style={{ textAlign: "center" }}>
                  {" "}
                  {message}{" "}
                </p>
              }
              </form>

           
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default UpdateVehicle;
