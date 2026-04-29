import { Pagination } from '@mui/material'
import { useNavigate, useSearchParams, useLocation } from 'react-router-dom' 


const PaginationComp = ({ numberOfPages, totalElements}) => {

    const [ searchParams ] = useSearchParams();
    const pathname = useLocation().pathname;
    const params = new URLSearchParams(searchParams);
    const navigate = useNavigate();
    const pageParam = searchParams.get("pageNumber") 
                    ? parseInt(searchParams.get("page"))
                    : 1;

    const onChangeHandler = (event, value) => {
        params.set("pageNumber", value.toString());
        navigate(`${pathname}?${params}`)
    }

    return(
        <div className='flex items-center justify-center'>
            <Pagination 
                count={numberOfPages} page={pageParam}
                defaultPage={1} siblingCount={1}
                variant="outlined" color="secondary"
                onChange={onChangeHandler}
                hidePrevButton hideNextButton
                sx={{
                    
                    '& .MuiPaginationItem-root': {
                    color: '#ffffff', 
                    backgroundColor: '#a2c3d0',
                    border: '2.5px solid #E3FF00',
                    borderRadius: '50%',
                    },
                }}
            />
        </div>
    )

}

export default PaginationComp;