import { DataGrid } from '@mui/x-data-grid';
import { useDispatch, useSelector } from 'react-redux';
import { CategoriesTableColumns } from '../../helper/TableColumns';
import { useEffect, useState } from 'react';
import FormModal from '../../shared/FormModal';
import { createCategory, deleteCategory, fetchCategories, updateCategory } from '../../../redux/actions/itemActions';
import Form from '../../shared/Form';
import { IoIosAddCircle } from "react-icons/io";
import DeletionModal from '../../shared/DeletionModal';
import CustomSpinner from '../../shared/CustomSpinner';


const Categories = () => {

  const dispatch = useDispatch();
  const [ currentCategory, setCurrentCategory ] = useState(null);
  const  { categories, pagination , isLoading } = useSelector((state) => state.categories );

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
    if (openModal === 'save') {
      if (currentCategory) {
        await dispatch(updateCategory(formData )).unwrap();
      } else {
        await dispatch(createCategory(formData)).unwrap();
      }
    } else if (openModal === 'delete') {
      await dispatch(deleteCategory(currentCategory)).unwrap();
    }

    setOpen(false);
    setOpenModal(null);
    setCurrentCategory(null);
    dispatch(fetchCategories());
    toast.success('Operation completed');
  } catch (error) {
    toast.error(typeof error === 'string' ? error : 'Error performing the operation');
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
      {
        isLoading === false ?
        (
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
        )
        :
        (
          <CustomSpinner text='Loading categories' />
        )
      }
      </div>
      {
        open && openModal === 'save' && ( 
          <FormModal open={open} setOpen={setOpen} 
            title={currentCategory !== null ? `Edit "${currentCategory?.name}"` : 'Add a Category'}
            >
            <Form fields={categoryFields} selected={currentCategory} customHandler={handleFormSubmit} />
          </FormModal>
        )
      }
      {
        openModal && openModal === 'delete' && (
          <DeletionModal open={open} setOpen={setOpen} itemName={currentCategory?.name} customDelete={handleFormSubmit} />
        )
      }
    </div>
  )
}

export default Categories