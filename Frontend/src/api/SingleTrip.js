import React,{Component, useEffect, useState} from "react";
import axios from "axios";

function SingleTrip (props) {
    
    const PropsID = props.match.params.id
    const [Product,setProduct] = useState([])

    useEffect(() => {
        axios.get(`http://localhost:8083/Trips/${PropsID}`)
        .then(response =>{
            setProduct(response.data)
            console.log()
        })
    },[])

        return(
            <div>
                <h1>{Product.vehicleId}</h1>
                <h1>{Product.startTime }</h1>
                <h1>{Product.endTime}</h1>
                <h1>{Product.currentlyOngoing}</h1>

            </div>
        )
    
}


export default SingleTrip;



