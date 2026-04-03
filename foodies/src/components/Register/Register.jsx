import React, { useState } from 'react'
import './Register.css'
import { Link, useNavigate } from 'react-router-dom'
import { toast } from "react-toastify";
import { registerUser } from '../../service/authService';

const Register = () => {
  const navigate=useNavigate()
  const [data, setData] = useState({
    name: '',
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
      const response=await registerUser(data)
      if (response.status === 201 || response.status === 200) {
        toast.success("Registration completed. Please login");
        navigate('/login')
      } else {
        toast.error("Unable to register. Please try again");
      }
    } catch (error) {
      toast.error("Unable to register. Please try again");
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className='register-container min-vh-100 d-flex align-items-center justify-content-center py-5'>
      <div className='container'>
        <div className='row justify-content-center'>
          <div className='col-12 col-md-8 col-lg-5'>
            <div className='glass-panel border-0 shadow-lg p-4 p-sm-5 hover-lift'>
              <div className='text-center mb-5'>
                <h2 className='text-gradient fw-bold mb-2'>Create Account</h2>
                <p className='text-muted'>Join Foodies to order your favorites</p>
              </div>
              
              <form onSubmit={onSubmitHandler}>
                <div className='form-floating mb-4'>
                  <input
                    type='text'
                    className='form-control bg-glass text-main border-secondary border-opacity-25'
                    id='floatingName'
                    placeholder='John Doe'
                    name='name'
                    onChange={onChangehandler}
                    value={data.name}
                    required
                  />
                  <label htmlFor='floatingName' className='text-muted'>Full Name</label>
                </div>
                
                <div className='form-floating mb-4'>
                  <input
                    type='email'
                    className='form-control bg-glass text-main border-secondary border-opacity-25'
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
                    className='form-control bg-glass text-main border-secondary border-opacity-25'
                    id='floatingPassword'
                    placeholder='Password'
                    name='password'
                    onChange={onChangehandler}
                    value={data.password}
                    required
                  />
                  <label htmlFor='floatingPassword' className='text-muted'>Password</label>
                </div>

                <div className='d-grid gap-3 mt-4'>
                  <button className='btn btn-gradient btn-lg text-uppercase fw-semibold shadow' type='submit' disabled={isLoading}>
                    {isLoading ? <span className="spinner-border spinner-border-sm me-2" role="status" aria-hidden="true"></span> : null}
                    Sign Up
                  </button>
                  <button
                    className='btn btn-glass btn-lg text-uppercase text-danger fw-semibold hover-scale'
                    type='button'
                    onClick={() => setData({ name: "", email: "", password: "" })}
                  >
                    Reset
                  </button>
                </div>
                
                <div className='mt-5 text-center text-muted'>
                  Already have an account? <Link to="/login" className='text-primary fw-bold text-decoration-none ms-1'>Sign in</Link>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  )
}

export default Register;
