import React,{Component, useEffect, useState} from "react";
import axios from "axios";
import { Table } from "reactstrap";

function SingleTrip (props) {
    
    const PropsID = props.match.params.id
    const [Product,setProduct] = useState([])

    useEffect(() => {
        var tok = localStorage.getItem('token');
        axios.get(`http://localhost:8083/Trips/${PropsID}`, 
        {headers: {"Authorization" : `${tok}`}})
        .then(response =>{
            setProduct(response.data)
            console.log(response.data)
        })
    },[])

        return(
            <div>
                <br></br>
                <br></br>
                <Table hover>
                <tbody>
                <tr>
                    <th scope="row">1</th>
                    <td>Vehicle ID:</td>
                    <td>Start Time:</td>
                    <td>End Time:</td>
                    <td>Start Address:</td>
                    <td>End Address:</td>
                    <td>Speed Limit Broken:</td>
                    <td>Average Speed:</td>
                    <td>Average Road:</td>
                </tr>
                <tr>
                    <th scope="row">2</th>
                    <td>{Product.vehicleId}</td>
                    <td>{Product.startTime}</td>
                    <td>{Product.endTime}</td>
                    <td>{Product.startAddress}</td>
                    <td>{Product.endAddress}</td>
                    <td>{Product.speedLimitBreakCounter}</td>
                    <td>{Product.averageSpeed}</td>
                    <td>{Product.averageRoad}</td>
                </tr>
                </tbody>
                </Table>
            </div>
        )
}


export default SingleTrip;



