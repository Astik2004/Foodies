import React from 'react'
import { Link } from 'react-router-dom';
import './Header.css';

function Header() {
  return (
    <div className="header-container mb-5 mt-4 position-relative overflow-hidden rounded-5 shadow-lg">
        {/* Background Decorative Elements */}
        <div className="hero-glow hero-glow-1"></div>
        <div className="hero-glow hero-glow-2"></div>
        
        <div className="container-fluid py-5 position-relative z-1">
            <div className="row align-items-center min-vh-50 py-md-5">
                <div className="col-lg-7 col-md-10 glass-panel p-5 m-4 header-content hover-lift">
                    <h1 className="display-3 fw-bold mb-4">
                        Discover & Savor <br/>
                        <span className="text-gradient">Delicious Cuisine</span>
                    </h1>
                    <p className="fs-4 text-muted mb-5 col-xl-9">
                        Experience the finest recipes and drinks in Delhi. 
                        We deliver happiness right to your doorstep.
                    </p>
                    <div className="d-flex gap-3">
                        <Link className="btn btn-gradient btn-lg px-5 hover-scale" to="/explore">
                            Explore Menu <i className="bi bi-arrow-right ms-2"></i>
                        </Link>
                    </div>
                </div>
            </div>
        </div>
    </div>
  )
}

export default Header
