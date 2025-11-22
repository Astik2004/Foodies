import React, { useContext, useState } from 'react'
import './Login.css'
import { Link, Navigate, useNavigate } from 'react-router-dom'
import { toast } from 'react-toastify'
import { login } from '../../service/authService';
import { StoreContext } from '../../context/StoreContext';

function Login() {
  const {setToken,loadCart}=useContext(StoreContext)
  const navigate=useNavigate()
  const [data, setData] = useState({
    email: '',
    password: ''
  });

  const onChangehandler = (event) => {
    const { name, value } = event.target;
    setData((data) => ({ ...data, [name]: value }));
  };

  const onSubmitHandler = async (event) => {
    event.preventDefault();
    try {
      const response = await login(data)
      
      if (response.status === 200) {
        setToken(response.data.token)
        localStorage.setItem('token',response.data.token)
        await loadCart(response.data.token)
        navigate('/')
      } else {
        toast.error("Invalid credentials");
      }
    } catch (error) {
      console.log(error)
      toast.error("Unable to login. Try again.");
    }
  };

  return (
    <div className='login-container'>
      <div className='row'>
        <div className='col-sm-9 col-md-7 col-lg-5 mx-auto'>
          <div className='card border-0 shadow rounded-3 my-5'>
            <div className='card-body p-4 p-sm-5'>
              <h5 className='card-title text-center mb-5 fw-light fs-5'>Sign In</h5>
              <form onSubmit={onSubmitHandler}>
                <div className='form-floating mb-3'>
                  <input
                    type='email'
                    className='form-control'
                    id='floatingInput'
                    placeholder='name@example.com'
                    name='email'
                    onChange={onChangehandler}
                    value={data.email}
                  />
                  <label htmlFor='floatingInput'>Email address</label>
                </div>
                <div className='form-floating mb-3'>
                  <input
                    type='password'
                    className='form-control'
                    id='floatingPassword'
                    placeholder='Password'
                    name='password'
                    onChange={onChangehandler}
                    value={data.password}
                  />
                  <label htmlFor='floatingPassword'>Password</label>
                </div>

                <div className='form-check mb-3'>
                  <input
                    className='form-check-input'
                    type='checkbox'
                    id='rememberPasswordCheck'
                  />
                  <label className='form-check-label' htmlFor='rememberPasswordCheck'>
                    Remember password
                  </label>
                </div>

                <div className='d-grid'>
                  <button className='btn btn-outline-primary btn-login text-uppercase' type='submit'>
                    Sign in
                  </button>
                  <button
                    className='btn btn-outline-danger btn-login text-uppercase mt-2'
                    type='button'
                    onClick={() => setData({ email: "", password: "" })}
                  >
                    Reset
                  </button>
                </div>

                <div className='mt-4'>
                  Don't have an account? <Link to="/register">Sign up</Link>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  )
}

export default Login;
