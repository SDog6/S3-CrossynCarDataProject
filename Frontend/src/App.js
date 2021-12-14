import SecuredRoute from './components/SecureRoute';
import './App.css';
import NavBar from './components/Navbar';
import Login from './pages/Login';
import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import Home from './pages/Home';
import Trips from './pages/Trips';
import { Redirect } from 'react-router';
import { useState } from 'react';
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";
import PrivateRoute from './components/PrivateRoute';
import Logout from './components/Logout';
import AddVehicle from './components/AddVehicle';
import Register from './components/Register';
import ListOfVehicle from './components/ListOfVehicle';
import ListOfUser from './components/ListOfUser';
import SingleTrip from './api/SingleTrip';
import CreateUser from './components/CreateUser';
import UpdateVehicle from './components/UpdateVehicle';
import AddVehicleID from './components/AddVehicleID';

function App() {

  return (
    <div>
    <Router>
      <NavBar/>
      <Switch>
      <Route path='/' exact component={Home} />
      <Route path='/Login' exact component={Login} />
      <Route path='/Trips' exact component={Trips}/>
      <Route path='/Logout' exact component={Logout}/>
      <Route path="/VehicleCreation" component={AddVehicle}></Route>
      <Route path="/Register" component={Register}></Route>
      <Route path="/Vehicles" component={ListOfVehicle}></Route>
      <Route path="/Users" component={ListOfUser}></Route>
      <Route path="/CreateUser" component={CreateUser}></Route>
      <Route path='/Trips/:id' exact component={SingleTrip}/>
      <Route path='/Vehicle/:id' exact component={UpdateVehicle}/>
      <Route path='/ConnectVehicle' exact component={AddVehicleID}/>


      </Switch>

    </Router>

   

    </div>
    
   
  );
}

export default App;
