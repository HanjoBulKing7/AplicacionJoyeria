import { useState, useEffect } from 'react'
import { SearchParams, useNavigate, useLocation, useSearchParams } from 'react-router-dom'


const Filter = () => {

    const [ SearchParams ] = useSearchParams();
    console.log(SearchParams);


}

export default Filter;