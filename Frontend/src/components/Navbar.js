import React from 'react';
import '../styles/NavBar.css';
import {  Link } from "react-router-dom";
import {Container ,Navbar, Nav} from 'react-bootstrap'
import logo from "../images/logo.png";
import SecuredRoute from './SecureRoute';



class NavBar extends React.Component  {

  constructor(props) {
    super(props);
    this.state = {
        isAuthenticated: ''
    };
}

componentDidMount() {
  if (localStorage.getItem('token') === "logged in") {
      this.setState({ isAuthenticated: "logged in" });
  }
}

render(){
  
  const isAuthenticated = localStorage.getItem("token")

  return (
    <div className="Navigation">
    <Navbar bg="dark" variant="dark">
    <Container>
    <Navbar.Brand href="/"> <img src={logo} className="img-logo" alt="Logo"/></Navbar.Brand>
     <Nav className="me-auto" >
     {isAuthenticated === "logged in" ? "" : <Nav.Link href="/login">Log in</Nav.Link>}  
      {isAuthenticated === "logged in" ? <Nav.Link href="/Trips">Trips</Nav.Link> : ""} 
      {isAuthenticated === "logged in" ? <Nav.Link href="/Logout">Log out</Nav.Link> : ""} 
    </Nav>
    </Container>
  </Navbar>
</div>
  );
}
}
export default NavBar;