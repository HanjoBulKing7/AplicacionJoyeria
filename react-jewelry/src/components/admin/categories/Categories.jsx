import { DataGrid } from '@mui/x-data-grid';
import { useSelector } from 'react-redux';
import { CategoriesTableColumns } from '../../helper/TableColumns';
import { useState } from 'react';
import CategoryForm from './CategoryForm';
import FormModal from '../../shared/FormModal';

const Categories = () => {

  const [ currentCategory, setCurrentCategory ] = useState(null);

  const  { categories, pagination } = useSelector((state) => state.categories );
  const [ currentPage, setCurrentPage ] = useState(1);
  const [ openModal , setOpenModal ] = useState(null);

  
  const handleEdit = (category) => {
    setCurrentCategory(category);
    setOpenModal('edit');

  };

  const handleDelete = (category) => {
    setCurrentCategory(category);
    setOpenModal('delete')
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

          <FormModal open={!!openModal} onClose={setOpenModal} >
        <CategoryForm />
      </FormModal>
    </div>
  )
}

export default Categories