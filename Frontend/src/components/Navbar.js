import React from 'react';
import '../styles/NavBar.css';
import {  Link } from "react-router-dom";
import {Container ,Navbar, Nav} from 'react-bootstrap'
import logo from "../images/logo.png";
import SecuredRoute from './SecureRoute';
import jwtDecode from 'jwt-decode';


class NavBar extends React.Component  {

  constructor(props) {
    super(props);
    this.state = {
        isAuthenticated: ''
    };
}

componentDidMount() {
  var tok = localStorage.getItem('token');
  if(tok == null){
  }
  else {
    var decoded = jwtDecode(tok);
      this.setState({ isAuthenticated: decoded.role});
  }
}

render(){
  
  return (
    <div className="Navigation">
    <Navbar bg="dark" variant="dark">
    <Container>
    <Navbar.Brand href="/"> <img src={logo} className="img-logo" alt="Logo"/></Navbar.Brand>
     <Nav className="me-auto" >
     {this.state.isAuthenticated === '' ?  <Nav.Link href="/login">Log in</Nav.Link> : ""}  
      {this.state.isAuthenticated != '' ? <Nav.Link href="/Trips">Trips</Nav.Link> : ""} 
      {this.state.isAuthenticated === "CROSSYNEMPLOYEE" ? <Nav.Link href="/Vehicles">Vehicles</Nav.Link> : ""} 
      {this.state.isAuthenticated === "CROSSYNEMPLOYEE" ? <Nav.Link href="/VehicleCreation">Add vehicle</Nav.Link> : ""} 
      {this.state.isAuthenticated === "CROSSYNEMPLOYEE" ? <Nav.Link href="/Users">Users</Nav.Link> : ""} 
      {this.state.isAuthenticated === "CROSSYNEMPLOYEE" ? <Nav.Link href="/CreateUser">Create user</Nav.Link> : ""} 
      {this.state.isAuthenticated === '' ? "" : <Nav.Link href="/Logout">Log out</Nav.Link>} 


    </Nav>
    </Container>
  </Navbar>
</div>
  );
}
}
export default NavBar;