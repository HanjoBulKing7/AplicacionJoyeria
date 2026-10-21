import { DataGrid } from '@mui/x-data-grid';
import { useDispatch, useSelector } from 'react-redux';
import { CategoriesTableColumns } from '../../helper/TableColumns';
import { useEffect, useState } from 'react';
import FormModal from '../../shared/FormModal';
import { createCategory, fetchCategories, updateCategory } from '../../../redux/actions/itemActions';
import Form from '../../shared/Form';
import { IoIosAddCircle } from "react-icons/io";
import DeletionModal from '../../shared/DeletionModal';


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

  const handleCreate = () => {
    setOpenModal('save');
    setCurrentCategory(null);
    setOpen(true);
  };

  const handleEdit = (category) => {
      setCurrentCategory(category);
      setOpenModal('save');
      setOpen(true);
    };

  const handleDelete = (category) => {
    setCurrentCategory(category);
    setOpenModal('delete')
    setOpen(true)
  };

  const handleFormSubmit = async (formData) => {
    try {

      openModal === 'save' ? (
        currentCategory !== null ?  
          await dispatch(updateCategory(formData))
        :
          await dispatch(createCategory(formData))
        )
        :
        (
          await dispatch(deleteCategory(id))
        )
      setOpen(false);
      setOpenModal(null);
      dispatch(fetchCategories());
    } catch (error) {
      console.error("Error al actualizar:", error);
    }
  };

  return (
    <div className=' flex flex-col items-center justify-center'>
      <h1 className='text-blue-950 text-4xl mb-10'>Categories</h1>

      <div className='flex flex-col'>

      <div className='flex flex-row justify-end my-10 '>
        <button className='text-xl bg-blue-600 text-white w-fit p-2 rounded-xl flex flex-row items-center gap-2'
          onClick={()=>handleCreate()}
        >
          <IoIosAddCircle size={20}/>Add Category
        </button>
      </div>

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
        open && openModal === 'save' && ( 
          <FormModal open={open} setOpen={setOpen} 
            title={currentCategory !== null ? `Edit "${currentCategory?.name}"` : 'Create a Category'}
            >
            <Form fields={categoryFields} selected={currentCategory} customHandler={handleFormSubmit} />
          </FormModal>
        )
      }
      {
        openModal && openModal === 'delete' && (
          <DeletionModal open={open} setOpen={setOpen} itemName={currentCategory?.name} customDelete={handleDelete} />
        )
      }
    </div>
  )
}

export default Categories