import React from 'react';
import { UncontrolledCarousel } from 'reactstrap';
import Crossyn1 from "../images/Crossyn1.jpg"
import Crossyn2 from "../images/Crossyn2.jpg"
import Crossyn3 from "../images/Crossyn3.jpg"
import "../styles/Home.css"

const items = [
  {
    src: Crossyn1,
  },
  {
    src: Crossyn2,
  },
  {
    src: Crossyn3,
  },

];

const Carousel = () => <UncontrolledCarousel items={items} />;

export default Carousel;