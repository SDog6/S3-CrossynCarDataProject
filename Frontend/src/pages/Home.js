import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import HomeImg from "../images/homeimg.jpg";

import { BrowserRouter as Router, Switch, Route } from "react-router-dom";

function Home() {
  return (
    <div>
<div className="HomePage">
<h1>Welcome</h1>
<br></br>
<img src={HomeImg} className="home-img" alt="homeimg"/>
</div>
    </div>
   
  );
}

export default Home;
