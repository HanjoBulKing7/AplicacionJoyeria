import { DataGrid } from '@mui/x-data-grid';
import { useSelector } from 'react-redux';

const Categories = () => {

  const categories = useSelector((state) => state.categories.categories );
  console.log(categories);

  return (
    <div className='bg-gray-900 flex flex-col items-center justify-center'>
      <h1 className='text-blue-950 text-4xl'>Categories</h1>

      <div className='flex flex-col'>

      </div>
    </div>
  )
}

export default Categories