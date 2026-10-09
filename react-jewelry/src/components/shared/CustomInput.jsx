import React from 'react'
import { patterns } from '../../utils/regexPatterns'

const CustomInput = ({ id, type, label, register, required, min, placeholder, placeholderStyles, errors, pattern }) => {
  
  const activePattern = patterns[pattern] || null;

  return (
        <div className='flex flex-col gap-4 w-full text-center justify-center'>
            <label htmlFor={id} className='text-black text-xl'>
                {label}
            </label>
            <input 
                type={type}
                id={id}
                placeholder={placeholder}
                className={placeholderStyles ? placeholderStyles : 'placeholder:text-gray-500/70 placeholder:italic placeholder:text-lg border border-black ring-1 text-xl rounded-lg'}
                {
                    ...register(id, {
                        required: { value: required, message: `${label} is required` },
                        minLength: min ? { value: min, message: `Minimum of ${min} characters is required` } : null,
                        pattern: activePattern ? { value: activePattern, message: `Please enter a valid ${label.toLowerCase()}` } : null,
                    })
                }
            />
            {
                errors[id]?.message && (
                    <p className='text-md font-semibold text-red-600 mt-1'>{errors[id]?.message}</p>
                )
            }
        </div>
  )
}


export default CustomInput;