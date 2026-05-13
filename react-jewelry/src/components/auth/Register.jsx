import { useState } from 'react'
import { useForm } from 'react-hook-form'
import { HiUserAdd } from "react-icons/hi";
import { MutatingDots } from 'react-loader-spinner'
import { FaEye } from "react-icons/fa";
import { FaEyeSlash } from "react-icons/fa6";
import { useDispatch } from 'react-redux';
import { PasswordMinLen, UsernameMinLen } from '../../utils/constants';

function Register() {

  const dispatch = useDispatch();
  const { register, handleSubmit, reset, formState: {errors} } = useForm({mode: "onTouched"});
  const [ loading, setLoading ] = useState(false)
  const [ showPwd, setShowPwd ] = useState(false)

  const handleSignup  = () => {
    dispatch()
  };

  return (
    <div className='flex items-center justify-center min-h-[calc(100vh-80px)] bg-gray-600'>
      <form action="" className='font-montserrat font-light text-white' onSubmit={handleSubmit(handleSignup)}>
        <div className='flex flex-col gap-5 p-5 items-center justify-center bg-black w-100 rounded-xl'>

          <HiUserAdd className='text-5xl text-white' />
          <h1 className='text-white text-4xl'>Sign up</h1>

          <label htmlFor="UserName">Username</label>
          <input
            className='border-b-2 w-55 border-amber-50 focus:ring-0 focus:outline-none bg-transparent pr-8'
          type="text" {...register("username",{
            required: "Username is required",
            minLength: { value: UsernameMinLen, message: `User name must be at least ${UsernameMinLen} characters`},
            })} />
          {errors.username && <span className='text-red-500 text-xs'>{errors.username.message}</span>}

          <label htmlFor="Email">Email</label>
          <input
            type="email"
            className='border-b-2 w-55 border-amber-50 focus:ring-0 focus:outline-none bg-transparent pr-8'
            {...register("email", {
              required: "Email is required",
              pattern: {  value: /^[^\s@]+@[^\s@]+\.[^\s@]+$/,  message: "Please enter a valid email address (must include @ and a domain)" }
            })} />
          {errors.email && (  <span className='text-red-500 text-xs'> {errors.email.message} </span> )}


          <label htmlFor="Password">Password</label>
          <div className='relative w-fit'>
            <input
              className='border-b-2 w-55 border-amber-50 focus:ring-0 focus:outline-none bg-transparent pr-8' 
              type={showPwd ? "text" : "password"}
              {...register("password", { 
              required: "Password is required", 
              minLength: {  value: PasswordMinLen,  message: `Password must be at least ${PasswordMinLen} characters`  },
              pattern: {  value: /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).+$/,  message: "Must include uppercase, lowercase, and a number" }
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
              { errors.password && <span className='text-red-500 text-xs' >{errors.password.message}</span>}
            <button
              className='bg-button-gradient p-2 rounded-lg'
            >
              { loading ? 
              <><MutatingDots />Signin up</>
              :
              <>Sign up</> 
              }
            </button>
            <span className='text-xs text-white text-center'>By signing up, you agree to our <strong>Terms of Service and Privacy Policy</strong></span>
        </div>
      </form>
    </div>
  )
}

export default Register