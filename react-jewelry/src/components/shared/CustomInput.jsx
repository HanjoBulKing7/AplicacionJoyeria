import React from 'react'
import { patterns } from '../../utils/regexPatterns'

const CustomInput = ({ id, type, label, register, required, minLength, maxLength,
  minValue, step, options = [], placeholder = '', placeholderStyles, errors, pattern }) => {

  const activePattern = patterns[pattern];
  const rules = {
    required: { value: !!required, message: `${label} is required` },
    ...(minLength && { minLength: { value: minLength, message: `Minimum of ${minLength} characters` } }),
    ...(maxLength && { maxLength: { value: maxLength, message: `Maximum of ${maxLength} characters` } }),
    ...(type === 'number' && { valueAsNumber: true }),
    ...(minValue !== undefined && { min: { value: minValue, message: `${label} must be at least ${minValue}` } }),
    ...(activePattern && { pattern: { value: activePattern, message: `Please enter a valid ${label.toLowerCase()}` } }),
  };

  const styles = placeholderStyles ||
  'border border-gray-400 rounded-lg text-xl px-3 py-2 w-full max-w-sm mx-auto ' +
  'placeholder:text-gray-500/70 placeholder:italic placeholder:text-lg ' +
  'outline-none transition focus:border-blue-500 focus:ring-2 focus:ring-blue-500/50';

  return (
    <div className='flex flex-col gap-4 w-full text-center justify-center'>
      <label htmlFor={id} className='text-black text-xl'>{label}</label>

      {type === 'select' ? (
        <select id={id} className={styles} {...register(id, rules)}>
          <option value="">Select...</option>
          {options.map((o) => <option key={o.value} value={o.value}>{o.label}</option>)}
        </select>
            
      ) : (
        <input type={type} id={id} step={step} placeholder={placeholder}
          className={styles} {...register(id, rules)} />
      )}

      {errors[id]?.message && <p className='text-md font-semibold text-red-600 mt-1'>{errors[id].message}</p>}
    </div>
  );
};


export default CustomInput;