import React from 'react'


const patterns = {
  email: /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/,
  password: /^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*])(.{8,})$/,
  username: /^[a-zA-Z0-9_]{3,20}$/,
  name: /^[a-zA-ZáéíóúÁÉÍÓÚñÑ\s]{2,50}$/,
  url: /^(https?:\/\/)?([\w\-]+\.)+[\w\-]+(\/[\w\-._~:/?#[\]@!$&'()*+,;=]*)?$/
}


const CustomInput = ({ id, type, label, register, required, min , placeholder, placeholderStyles , errors}) => {

  return (
        <div className='flex flex-col gap-4 w-full text-center justify-center'>
            <label htmlFor="id" className='text-black text-xl'>
                {label}
            </label>
            <input 
                type={type}
                pattern={type}
                id={id}
                placeholder={placeholder}
                className={placeholderStyles ? placeholderStyles :'placeholder:text-gray-500/70 placeholder:italic placeholder:text-lg'
                    +' border border-black ring-1 text-xl rounded-lg'
                }
                {
                    ...register( id,{
                        required: { value: required, message: `${type} is required` },
                        minLenght: min ? 
                        { value: min, message: `Minimum of ${min} characters is required`} :null,
                        pattern: patterns[type], mesage: `Please enter a valid ${type}`
                    }
                    )
                }
            />
            {
                errors[id]?.message &&
                (
                    <p className='text-md font-semuibold text-red-600 mt-1'>{errors[id]?.message}</p>
                )
            }
        </div>
  )
}

export default CustomInput