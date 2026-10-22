import React, { useEffect, useState } from 'react'
import { fetchItems } from '../../../redux/actions/itemActions'
import { useDispatch, useSelector } from 'react-redux';
import { DataGrid } from '@mui/x-data-grid';
import { InventoryTableColumns } from '../../helper/TableColumns';

const Inventory = () => {

  const dispatch = useDispatch();

  const { items , pagination } = useSelector((state) => state.inventory )
  const [ currentPage, setCurrentPage ] = useState(1);

  useEffect(()=>{
    dispatch(fetchItems())

  },[dispatch])

  const handleEdit = () => {

  } 

  const handleDelete = () => {

  } 

    const formattedInventory = items.map((i)=>{
      return  {
        id: i.productId,  ...i, 
      }
    })
  
  
  return (
    <div className='flex flex-col justify-center items-center'>

      <h1 className='text-4xl text-blue-600 font-bold'>Inventory</h1>

      <div>
        <DataGrid 
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
    </div>
  )
}

export default Inventory