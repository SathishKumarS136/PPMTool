import React, { Component } from "react";
import { Link } from "react-router-dom";
import { connect } from "react-redux";
import { logoutUser } from "../../actions/securityActions";
import PropTypes from "prop-types";

class Header extends Component {
  logout() {
    this.props.logoutUser();
  }
  render() {
    const { validToken, user } = this.props.security;
    const authenticatedUser = (
      <div className="collapse navbar-collapse" id="mobile-nav">
        <ul className="navbar-nav mr-auto">
          <li className="nav-item">
            <Link className="nav-link" to={"/dashboard"}>
              Dashboard
            </Link>
          </li>
        </ul>

        <ul className="navbar-nav ml-auto">
          <li className="nav-item">
            <Link className="nav-link " to={"/dashboard"}>
              <i className="fas fa-user-circle mr-1" />
              {user.username}
            </Link>
          </li>
          <li className="nav-item" onClick={this.logout.bind(this)}>
            <Link className="nav-link" to={"/"}>
              Logout
            </Link>
          </li>
        </ul>
      </div>
    );
    const publicUser = (
      <div className="collapse navbar-collapse" id="mobile-nav">
        <ul className="navbar-nav ml-auto">
          <li className="nav-item">
            <Link className="nav-link " to={"/register"}>
              Sign Up
            </Link>
          </li>
          <li className="nav-item">
            <Link className="nav-link" to={"/login"}>
              Login
            </Link>
          </li>
        </ul>
      </div>
    );

    let headerLinks;
    if (validToken && user) headerLinks = authenticatedUser;
    else headerLinks = publicUser;
    return (
      <nav className="navbar navbar-expand-sm navbar-dark bg-primary mb-4">
        <div className="container">
          <Link className="navbar-brand" to={"/"}>
            Personal Project Management Tool
          </Link>
          <button
            className="navbar-toggler"
            type="button"
            data-toggle="collapse"
            data-target="#mobile-nav"
          >
            <span className="navbar-toggler-icon" />
          </button>
          {headerLinks}
        </div>
      </nav>
    );
  }
}
Header.propTypes = {
  logoutUser: PropTypes.func.isRequired,
  security: PropTypes.object.isRequired,
};
const mapStateToProps = (state) => ({
  security: state.security,
});
export default connect(mapStateToProps, { logoutUser })(Header);
