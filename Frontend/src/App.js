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

function App() {

  return (
    <div>
    <Router>
      <NavBar/>
      <Switch>
      <Route path='/' exact component={Home} />
      {localStorage.getItem("token") === "logged in" ? <Route path='/Login' exact component={Home} /> : <Route path='/Login' exact component={Login} />}
      {localStorage.getItem("token") === "logged in" ? <Route path='/Trips' exact component={Trips}/> : <Route path='/Trips' exact component={Home}/>}
      </Switch>

    </Router>

   

    </div>
    
   
  );
}

export default App;
