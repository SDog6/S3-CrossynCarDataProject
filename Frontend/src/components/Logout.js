import React, { Component } from 'react'
import axios from 'axios';
  function Logout (){
    localStorage.clear();
    window.location.href = '/Login';
}
  export default Logout