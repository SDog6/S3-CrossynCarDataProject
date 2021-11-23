import React,{Component} from "react";
import axios from "axios";
import { Card, Button } from "react-bootstrap";
import '../styles/Card.css'

class TripList extends Component{
    
    
    constructor(props) {
        super(props)
        this.state = { 
             trips : []
        }
    }    
    
    
    componentDidMount() { 
        axios.get('http://localhost:8083/Trips')
        .then(response =>{
            this.setState({
                trips: response.data
            })
            console.log(response.data)
        })
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
                    <Button variant="primary">View details</Button>
                  </Card.Body>
                </Card></div>)
              }
          </div>
      )
  }
}



export default TripList



