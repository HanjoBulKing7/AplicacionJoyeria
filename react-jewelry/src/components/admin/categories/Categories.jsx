import { DataGrid } from '@mui/x-data-grid';
import { useDispatch, useSelector } from 'react-redux';
import { CategoriesTableColumns } from '../../helper/TableColumns';
import { useEffect, useState } from 'react';
import FormModal from '../../shared/FormModal';
import { fetchCategories, updateCategory } from '../../../redux/actions/itemActions';
import Form from '../../shared/Form';

const Categories = () => {

  const dispatch = useDispatch();
  const [ currentCategory, setCurrentCategory ] = useState(null);
  const  { categories, pagination } = useSelector((state) => state.categories );
  const [ currentPage, setCurrentPage ] = useState(1);
  const [ openModal , setOpenModal ] = useState(null);
  const [ open , setOpen ] = useState(false);

  const categoryFields = [
    { id: "name", label: "Category Name", type: "text", required: true, min: 3, placeholder: "New category name", pattern: "name"},
  ];

  useEffect(()=>{
    dispatch(fetchCategories());
  },[])

  const handleCreate = (category) => {
    setOpenModal('save')
  };

  const handleEdit = (category) => {
    setCurrentCategory(category);
    setOpenModal('save');
    setOpen(true)
    console.log('Edit finally triggered')
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
      {
        open && openModal === 'save' 
          ?
          (
          <FormModal open={open} setOpen={setOpen} 
            title={currentCategory !== null ? `Edit "${currentCategory?.name}"` : 'Create a Category'}
            >
            <Form fields={categoryFields} selected={currentCategory} customHandler={ currentCategory !== null ? handleEdit : handleCreate} />
          </FormModal>
          )
          :
          <h1>Delete</h1>

      }
    </div>
  )
}

export default Categories