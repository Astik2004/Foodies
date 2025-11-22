import React from 'react'
import { Link } from 'react-router-dom';

function Header() {
  return (
    <div className="p-5 mb-4 bf-light rounded-3 mt-1">
        <div className="container-fluid py-5">
            <h1 className="display-5 fw-bold">Welcome to Foodies</h1>
            <p className="col-md-8 fs-4">Discover delicious recipes and drinks in Delhi</p>
            <Link className="btn btn-primary" to="/explore">Explore Now</Link>
        </div>
    </div>

  )
}

export default Header
