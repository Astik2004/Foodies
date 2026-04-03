import React, { useContext, useState } from 'react'
import './Login.css'
import { Link, useNavigate } from 'react-router-dom'
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
  const [isLoading, setIsLoading] = useState(false);

  const onChangehandler = (event) => {
    const { name, value } = event.target;
    setData((data) => ({ ...data, [name]: value }));
  };

  const onSubmitHandler = async (event) => {
    event.preventDefault();
    setIsLoading(true);
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
      console.error(error)
      toast.error("Unable to login. Try again.");
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className='login-container min-vh-100 d-flex align-items-center justify-content-center py-5'>
      <div className='container'>
        <div className='row justify-content-center'>
          <div className='col-12 col-md-8 col-lg-5'>
            <div className='glass-panel border-0 shadow-lg p-4 p-sm-5 hover-lift'>
              <div className='text-center mb-5'>
                <h2 className='text-gradient fw-bold mb-2'>Welcome Back</h2>
                <p className='text-muted'>Sign in to your Foodies account</p>
              </div>
              
              <form onSubmit={onSubmitHandler}>
                <div className='form-floating mb-4'>
                  <input
                    type='email'
                    className='form-control bg-glass text-light border-secondary border-opacity-50'
                    id='floatingInput'
                    placeholder='name@example.com'
                    name='email'
                    onChange={onChangehandler}
                    value={data.email}
                    required
                  />
                  <label htmlFor='floatingInput' className='text-muted'>Email address</label>
                </div>
                
                <div className='form-floating mb-4'>
                  <input
                    type='password'
                    className='form-control bg-glass text-light border-secondary border-opacity-50'
                    id='floatingPassword'
                    placeholder='Password'
                    name='password'
                    onChange={onChangehandler}
                    value={data.password}
                    required
                  />
                  <label htmlFor='floatingPassword' className='text-muted'>Password</label>
                </div>

                <div className='d-flex justify-content-between align-items-center mb-4'>
                  <div className='form-check'>
                    <input
                      className='form-check-input bg-glass border-secondary'
                      type='checkbox'
                      id='rememberPasswordCheck'
                    />
                    <label className='form-check-label text-muted user-select-none' htmlFor='rememberPasswordCheck'>
                      Remember me
                    </label>
                  </div>
                  <a href='#' className='text-primary text-decoration-none small'>Forgot password?</a>
                </div>

                <div className='d-grid gap-3'>
                  <button className='btn btn-gradient btn-lg text-uppercase fw-semibold shadow' type='submit' disabled={isLoading}>
                    {isLoading ? <span className="spinner-border spinner-border-sm me-2" role="status" aria-hidden="true"></span> : null}
                    Sign in
                  </button>
                  <button
                    className='btn btn-glass btn-lg text-uppercase text-danger fw-semibold hover-scale'
                    type='button'
                    onClick={() => setData({ email: "", password: "" })}
                  >
                    Reset
                  </button>
                </div>

                <div className='mt-5 text-center text-muted'>
                  Don't have an account? <Link to="/register" className='text-primary fw-bold text-decoration-none ms-1'>Sign up</Link>
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
