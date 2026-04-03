import React, { useState } from 'react'
import { assets } from '../../assets/assets'
import { Link, useNavigate } from 'react-router-dom'
import { useContext } from 'react'
import { StoreContext } from '../../context/StoreContext'
import './Menubar.css'

function Menubar () {
  const navigate = useNavigate()
  const [active, setActive] = useState('home')
  const { quantities, token, setToken, setQuantities }=useContext(StoreContext)
  const uniqueItemInCart = Object.values(quantities || {}).filter(
    qty => qty > 0
  ).length

  const logout = () => {
    localStorage.removeItem('token')
    setToken('')
	  setQuantities({})
    navigate('/')
  }
  return (
    <nav className='navbar navbar-expand-lg sticky-top custom-navbar my-3 mx-auto glass-panel'>
      <div className='container'>
        <Link to='/' className="navbar-brand">
          <img
            src={assets.logo}
            alt='Logo'
            className='logo-img hover-scale'
            height={48}
            width={48}
          />
          <span className="brand-text text-gradient ms-2 fs-3 fw-bold">Foodies</span>
        </Link>
        <button
          className='navbar-toggler'
          type='button'
          data-bs-toggle='collapse'
          data-bs-target='#navbarSupportedContent'
          aria-controls='navbarSupportedContent'
          aria-expanded='false'
          aria-label='Toggle navigation'
        >
          <span className='navbar-toggler-icon'></span>
        </button>
        <div className='collapse navbar-collapse' id='navbarSupportedContent'>
          <ul className='navbar-nav mx-auto mb-2 mb-lg-0'>
            <li className='nav-item'>
              <Link
                className={
                  active === 'home' ? 'nav-link fw-bold active' : 'nav-link'
                }
                to='/'
                onClick={() => setActive('home')}
              >
                Home
              </Link>
            </li>
            <li className='nav-item'>
              <Link
                className={
                  active === 'explore' ? 'nav-link fw-bold active' : 'nav-link'
                }
                to='/explore'
                onClick={() => setActive('explore')}
              >
                Explore
              </Link>
            </li>
            <li className='nav-item'>
              <Link
                className={
                  active === 'contact-us'
                    ? 'nav-link fw-bold active'
                    : 'nav-link'
                }
                to='/contact'
                onClick={() => setActive('contact-us')}
              >
                Contact us
              </Link>
            </li>
          </ul>
          <div className='d-flex align-items-center gap-4'>
            <Link to={`/cart`} className="cart-icon hover-scale">
              <div className='position-relative'>
                <img
                  src={assets.cart}
                  alt='cart'
                  height={28}
                  width={28}
                  style={{ filter: 'brightness(0) invert(1)' }}
                />
                {uniqueItemInCart > 0 && (
                  <span className='position-absolute top-0 start-100 translate-middle badge rounded-pill bg-primary shadow'>
                    {uniqueItemInCart}
                  </span>
                )}
              </div>
            </Link>
            {!token ? (
              <div className="d-flex gap-2">
                <button
                  className='btn btn-glass'
                  onClick={() => navigate('/login')}
                >
                  Login
                </button>
                <button
                  className='btn btn-gradient'
                  onClick={() => navigate('/register')}
                >
                  Register
                </button>
              </div>
            ) : (
              <div className='dropdown text-end'>
                <a
                  href='#'
                  className='d-block link-body-emphasis text-decoration-none dropdown-toggle hover-scale'
                  data-bs-toggle='dropdown'
                  aria-expanded='false'
                >
                  <img
                    src={assets.profile}
                    alt='profile'
                    width={40}
                    height={40}
                    className='rounded-circle border border-2 border-primary object-fit-cover'
                  />
                </a>

                <ul className='dropdown-menu dropdown-menu-end dropdown-menu-dark shadow-lg glass-panel border-0 text-small'>
                  <li
                    className='dropdown-item py-2'
                    onClick={() => navigate('/myorder')}
                  >
                    <i className="bi bi-box-seam me-2 text-primary"></i> Orders
                  </li>
                  <li><hr class="dropdown-divider border-secondary" /></li>
                  <li className='dropdown-item py-2 text-danger' onClick={logout}>
                    <i className="bi bi-box-arrow-right me-2"></i> Logout
                  </li>
                </ul>
              </div>
            )}
          </div>
        </div>
      </div>
    </nav>
  )
}

export default Menubar
