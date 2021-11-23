import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import TripList from '../api/TripList';

import { BrowserRouter as Router, Switch, Route } from "react-router-dom";

function Home() {
  return (
    <div>
<div className="HomePage">
    <br></br>
<h1>Trip History</h1>

</div>
<TripList/>
    </div>
   
  );
}

export default Home;
