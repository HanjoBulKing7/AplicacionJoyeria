import { DataGrid } from '@mui/x-data-grid';
import { useDispatch, useSelector } from 'react-redux';
import { CategoriesTableColumns } from '../../helper/TableColumns';
import { useEffect, useState } from 'react';
import FormModal from '../../shared/FormModal';
import { fetchCategories } from '../../../redux/actions/itemActions';
import Form from '../../shared/Form';

const Categories = () => {

  const dispatch = useDispatch();
  const [ currentCategory, setCurrentCategory ] = useState(null);
  const  { categories, pagination } = useSelector((state) => state.categories );
  const [ currentPage, setCurrentPage ] = useState(1);
  const [ openModal , setOpenModal ] = useState(null);
  const [ open , setOpen ] = useState(false);

  useEffect(()=>{
    dispatch(fetchCategories());
  },[])

  const handleEdit = (category) => {
    setCurrentCategory(category);
    setOpenModal('edit');
    setOpen(true)

  };

  const handleDelete = (category) => {
    setCurrentCategory(category);
    setOpenModal('delete')
    setOpen(true)
  };

  return (
    <div className=' flex flex-col items-center justify-center'>
      <h1 className='text-blue-950 text-4xl mb-20'>Categories</h1>

      <div className='flex flex-col'>
        <DataGrid 
          paginationMode='server'
          columns={CategoriesTableColumns(handleEdit, handleDelete)}
          rows={categories}
          initialState={{
            pagination: {
              paginationModel: {
                pageSize: pagination?.pageSize || 2,
                page: currentPage - 1,
              },
            },
          }}
          pagination
          paginationOptions={{
            showFirstButton: true,
            showLastButton: true,
            hideNextButton: currentPage === pagination?.totalPages
          }}
        />
      </div>

      <FormModal open={open} setOpen={setOpen} 
        title={openModal === 'edit' ? `Edit` : 'Create a category'}
        selected={currentCategory}
         >
        <Form />
      </FormModal>
    </div>
  )
}

export default Categories