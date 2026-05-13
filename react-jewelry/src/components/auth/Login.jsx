import { useForm } from 'react-hook-form'
import React from 'react'
import { useDispatch } from 'react-redux'
import { useNavigate, Link } from 'react-router-dom'
import { ImEnter } from "react-icons/im";
import { useState } from 'react'
import { MutatingDots } from 'react-loader-spinner'
import { FaEye } from "react-icons/fa";
import { FaEyeSlash } from "react-icons/fa6";
import { loginUser } from '../../redux/actions/authActions';


function Login() {

  const { register, handleSubmit, reset, formState: { errors } } = useForm({mode: "onTouched"})
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const [ loading, setLoading ] = useState(false)
  const [ showPwd, setShowPwd ] = useState(false)

  const handleLogin = (data) => {
    dispatch(loginUser(data));
  }

  return (
    <div className=' flex items-center justify-center min-h-[calc(100vh-80px)]'>
      <form action=""
        className='bg-black w-70 md:w-100 rounded-2xl font-montserrat font-light' onSubmit={handleSubmit(handleLogin)}
      >
        <div className='flex flex-col items-center justify-center space-y-4 py-5 text-white'>
          <ImEnter className=" text-5xl"/>
          <h1 className='text-4xl '>Login</h1>

          <label htmlFor="UserName">Username</label>
          <input
            className='border-b-2 w-55 border-amber-50 focus:ring-0 focus:outline-none bg-transparent pr-8'
          type="text" {...register("username",{
            required: "Username is required",
            minLength: { value: 4, message: "Username must be at least 4 characters"},
            })} />
          {errors.username && <span className='text-red-500 text-xs'>{errors.username.message}</span>}


          <label htmlFor="Username">Password</label>
          <div className='relative w-fit'>
            <input
              className='border-b-2 w-55 border-amber-50 focus:ring-0 focus:outline-none bg-transparent pr-8' 
              type={showPwd ? "text" : "password"}
              {...register("password", {
                  required: "Password is required",
                  minLength: {value: 7, message: "Password must be at least 7 characters"},
                  pattern: { value: /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).+$/, message: "Must include uppercase, lowercase, and a number" }
                })}
                />
                <button
                type="button" 
                onClick={() => setShowPwd(!showPwd)}
                className='absolute right-0 top-1/2 -translate-y-1/2 text-xl text-amber-50 cursor-pointer'
                
                >
                  {showPwd ? <FaEyeSlash /> : <FaEye />}
                </button>
              </div>
              {errors.password && <span className='text-red-500 text-xs'>{errors.password.message}</span>}
          <button
            className='bg-button-gradient p-2 rounded-lg'
          >
            { loading ? 
            <><MutatingDots />Logging in</>
            :
            <>Login</> 
            }
          </button>
          <span className='text-white '>Don't have an account? <Link to='/signup' >Sign up</Link></span>
        </div>
      </form>
    </div>
  )
}

export default Login