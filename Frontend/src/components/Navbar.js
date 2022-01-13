import React, { Component } from "react";
import {
  Collapse,
  Navbar,
  NavbarToggler,
  NavbarBrand,
  Nav,
  NavItem,
  NavLink,
} from "reactstrap";
import "../styles/NavBar.css";
import logo from "../images/logo.png";
import jwtDecode from "jwt-decode";

class NavigationBar extends Component {
  constructor(props) {
    super(props);
    this.toggle = this.toggle.bind(this);
    this.state = {
      isOpen: false,
      username: "",
      isAuthenticated: "",
    };
  }
  toggle() {
    this.setState({
      isOpen: !this.state.isOpen,
    });
  }

  componentDidMount() {
    var tok = localStorage.getItem("token");
    if (tok == null) {
    } else {
      var decoded = jwtDecode(tok);
      this.setState({ isAuthenticated: decoded.role });
      console.log(decoded);
    }
  }

  render() {
    var username = this.state.username;
    var big = username.toUpperCase();
    return (
      <div>
        <Navbar color="dark" light expand="md">
          <NavbarBrand href="/">
            <img src={logo} className="img-logo" alt="Logo" />
          </NavbarBrand>
          <NavbarToggler onClick={this.toggle} />
          <Collapse isOpen={this.state.isOpen} navbar>
            <Nav className="ml-auto" navbar>
              {this.state.isAuthenticated != "" ? (
                <NavItem>
                  <NavLink href="/Vehicles">VEHICLES</NavLink>
                </NavItem>
              ) : (
                ""
              )}

              {this.state.isAuthenticated === "CROSSYNEMPLOYEE" ? (
                <NavItem>
                  <NavLink href="/users">LIST OF USER</NavLink>
                </NavItem>
              ) : (
                ""
              )}
              {this.state.isAuthenticated === "" ? (
                ""
                ) : (

                <NavItem>
                  <NavLink href="/Trips">TRIPS</NavLink>
                </NavItem>
              )}

              {this.state.isAuthenticated === "" ? (
                <NavItem>
                  <NavLink href="/login">LOGIN</NavLink>
                </NavItem>
              ) : (
             ""
              )}

              {this.state.isAuthenticated === "" ? (
                "") : (
                <NavItem>
                  <NavLink href="/Logout">LOGOUT</NavLink>
                </NavItem>
              )}
            </Nav>
          </Collapse>
        </Navbar>
      </div>
    );
  }
}
export default NavigationBar;
