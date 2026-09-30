import { DataGrid } from '@mui/x-data-grid';
import { useSelector } from 'react-redux';
import { CategoriesTableColumns } from '../../helper/TableColumns';
import { useState } from 'react';

const Categories = () => {

  const [ currentCategory, setCurrentCategory ] = useState(null);

  const  { categories, pagination } = useSelector((state) => state.categories );
  const [ currentPage, setCurrentPage ] = useState(1);

  
  const handleEdit = (category) => {
    setCurrentCategory(category);
  };

  const handleDelete = (category) => {
    setCurrentCategory(category);
  };

  return (
    <div className='bg-gray-900 flex flex-col items-center justify-center'>
      <h1 className='text-blue-950 text-4xl'>Categories</h1>

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
    </div>
  )
}

export default Categories