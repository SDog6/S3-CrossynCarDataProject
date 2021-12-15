import React,{Component} from "react";
import axios from "axios";
import { Card, Button } from "react-bootstrap";
import '../styles/Card.css'
import jwtDecode from "jwt-decode";

class TripList extends Component{
    
    
    constructor(props) {
        super(props)
        this.state = { 
             trips : []
        }
    }    
    
    
    componentDidMount() { 
        var tok = localStorage.getItem('token');
        if(tok == null){
        }
        else {
          var decoded = jwtDecode(tok);
          if(decoded.role === "CROSSYNEMPLOYEE"){
            axios.get(`http://localhost:8083/Trips`)
            .then(response =>{
                this.setState({
                    trips: response.data
                })
                console.log(response.data)
            })
          }
          else {
            axios.get(`http://localhost:8083/Trips/connected/${decoded.sub}`)
            .then(response =>{
                this.setState({
                    trips: response.data
                })
                console.log(response.data)
            })
          }
        }
    }
    
    
    render() { 
      const {trips} = this.state
      return(
          <div>
              {
                  trips.map(trip => <div className = 'wrapper'><Card className="card">
                  <Card.Body>
                    <Card.Title>Trip with vehicle {trip.vehicleId}</Card.Title>
                    <Card.Text>
                    Started on : {trip.startTime}
                    <br/>
                    Ended on : {trip.endTime}
                    </Card.Text>
                    <Button variant="primary" href={"/Trips/" + trip.id}>View details</Button>
                  </Card.Body>
                </Card></div>)
              }
          </div>
      )
  }
}



export default TripList



