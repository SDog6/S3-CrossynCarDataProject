import React from "react";
import { Route } from "react-router"
import { Redirect } from "react-router";


  const SecuredRoute = ({ ...props }) => (
    console.log(props.path),
    <Route path={props.path} render={(data) => (
      console.log(data),
      localStorage.getItem('token')
        ? <props.render {...data} />
        : <Redirect to='/login' />
    )} />
  )

  export default SecuredRoute