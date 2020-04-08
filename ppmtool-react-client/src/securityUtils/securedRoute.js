import React from "react";
import { Route, Redirect } from "react-router-dom";
import { connect } from "react-redux";
import Proptypes from "prop-types";

const SecuredRoute = ({ component: Component, security, ...otherProps }) => (
  <Route
    {...otherProps}
    render={(props) =>
      security.validToken === true ? (
        <Component {...props} />
      ) : (
        <Redirect to="/login" />
      )
    }
  />
);
SecuredRoute.propTypes = {
  security: Proptypes.object.isRequired,
};

const mapStateToProps = (state) => ({
  security: state.security,
});
export default connect(mapStateToProps)(SecuredRoute);
