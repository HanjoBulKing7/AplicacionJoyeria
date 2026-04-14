import { useState, useEffect } from 'react'
import {  useNavigate, useLocation, useSearchParams } from 'react-router-dom'
import { FiSearch } from 'react-icons/fi'

const Filter = () => {

    const [ SearchParams ] = useSearchParams();
    console.log(SearchParams);


    return(
        <div className='bg-gray-800 w-screen h-15 lg:h-18 flex flex-row mb-1 lg:m-0'>
            <div>
                <input

                    placeholder='Gold ring...'
                />
                <FiSearch className='absolute left-3 text-white size={20}' />
            </div>

        </div>
    );
}

export default Filter;