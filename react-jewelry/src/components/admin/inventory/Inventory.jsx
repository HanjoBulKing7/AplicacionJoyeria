import React, { useEffect, useState } from 'react'
import { fetchCategories, fetchItems } from '../../../redux/actions/itemActions'
import { useDispatch, useSelector } from 'react-redux';
import { DataGrid } from '@mui/x-data-grid';
import { InventoryTableColumns } from '../../helper/TableColumns';
import FormModal from '../../shared/FormModal';
import DeletionModal from '../../shared/DeletionModal';
import Form from '../../shared/Form';

const Inventory = () => {

  const dispatch = useDispatch();

  const { items , pagination } = useSelector((state) => state.inventory )
  const  { categories } = useSelector((state) => state.categories );


  const [ currentPage, setCurrentPage ] = useState(1);
  const [ openModal , setOpenModal ] = useState(null);
  const [ open , setOpen ] = useState(false);
  const [ currentItem, setCurrentItem ] = useState(null);

  const categoryOptions = categories.map(
    (c) => {
      return{
      value: c.id,     
      label: c.name, 
      }
    }
  );

  console.log(categoryOptions)
  
  const statusOptions = [
    { value: "ACTIVE", label: "Active" },
    { value: "INACTIVE", label: "Inactive" },
  ];
  
  const itemFields = [
    { id: "name", label: "Item Name", type: "text", required: true, minLength: 1, maxLength: 15, placeholder: "Gold chain" },
    { id: "description", label: "Description", type: "text", maxLength: 100, placeholder: "Short description" },
    { id: "price", label: "Price", type: "number", required: true, minValue: 0.01, step: "0.01", placeholder: "0.00" },
    { id: "stock", label: "Stock", type: "number", required: true, minValue: 0, step: "1", placeholder: "0" },
    { id: "status", label: "Status", type: "select", required: true, options: statusOptions },
    { id: "categoryId", label: "Category", type: "select", required: true, options: categoryOptions },
  ];
  

  useEffect(()=>{
    dispatch(fetchItems());
    dispatch(fetchCategories());
  },[dispatch])

  const handleCreate = () => {
    setOpenModal('save');
    setOpen(true);
    setCurrentItem(null);
  };

  const handleEdit = (item) => {
    setCurrentItem(item);
    setOpenModal('save');
    setOpen(true);
  } 

  const handleDelete = (item) => {
    setCurrentItem(item);
    setOpenModal('delete');
    setOpen(true);
  } 

  const formattedInventory = items.map((i)=>{
    return  {
      id: i.productId,  ...i, 
    }
  })

  const handleFormSubmit = () => {
    console.log('Form triggered');
  };
  
  
  return (
    <div className='flex flex-col justify-center items-center w-fit'>


      <h1 className='text-4xl text-blue-600 font-bold mb-10'>Inventory</h1>

      <div className=' flex flex-col items-center'>
        <DataGrid 
          className='w-fit'
          paginationMode='server'
          columns={InventoryTableColumns(handleEdit, handleDelete)}
          rows={formattedInventory}
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
            title={currentItem?.name !== null ? `Edit "${currentItem?.name}"` : 'Add an Item'}
            >
            <Form fields={itemFields} selected={currentItem} customHandler={handleFormSubmit} />
          </FormModal>
        )
      }
      {
        openModal && openModal === 'delete' && (
          <DeletionModal open={open} setOpen={setOpen} itemName={currentItem?.name} customDelete={handleFormSubmit} />
        )
      }
    </div>
  )
}

export default Inventory