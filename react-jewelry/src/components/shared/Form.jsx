import React, { useEffect } from 'react'
import { useForm } from 'react-hook-form';
import CustomInput from './CustomInput';
import { getFilledInputUtilityClass } from '@mui/material/FilledInput';

const Form = ({fields = null , selected = null , customHandler }) => {

    const { register, handleSubmit, reset, setValue, formState: { errors } } = useForm({ mode: "onTouched" });

    useEffect(() => {
        reset(selected ?? {});
    }, [selected, reset]);

    const onClickSave = (data) => {
        customHandler(data);
    }

  return (
    <div className='flex flex-col gap-2 h-full'>
        <form noValidate action="" className='mt-5 flex flex-col items-center justify-between flex-1'onSubmit={handleSubmit(onClickSave)} >
            <div className='mt-5 mb-5 flex flex-col justify-center w-full'>
                {
                    fields.map((field)=>{
                        return( 
                            <CustomInput 
                                key={field.id}
                                id={field.id}
                                type={field.type}
                                label={field.label}
                                register={register}
                                required={field.required}
                                min={field.min}
                                placeholder={field.placeholder}
                                placeholderStyles={null}
                                errors={errors}
                                pattern={field.pattern}
                            />
                        )
                    })
                } 
            </div>
            <button type='submit' className='hover:cursor-pointer bg-blue-600 rounded-md text-white text-xl max-w-fit p-2 mb-5'>Save</button>
        </form>
    </div>
  )
}

export default Form