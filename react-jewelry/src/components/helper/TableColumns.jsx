import { FaEdit, FaEye, FaImage, FaTrash } from "react-icons/fa";


export const CategoriesTableColumns = (handleEdit, handleDelete) => [
    {
        sortable: false,
        disableColumnMenu: true,
        field: "id",
        headerName: "categoryId",
        width: 200,
        headerAlign: "center",
        editable: false,
        headerClassName: "text-slate-700 font-semibold border-4",
        cellClassName: "text-slate-700 font-normal border",
        renderHeader: () => <h1 className="text-center font-bold">Category Id</h1>
    },
    {
        sortable: false,
        disableColumnMenu: true,
        field: "name",
        headerName: "categoryName",
        width: 200,
        headerAlign: "center",
        editable: false,
        headerClassName: "text-slate-700 font-semibold border-4",
        cellClassName: "text-slate-700 font-normal border",
        renderHeader: () => <h1 className="text-center font-bold">Category Name</h1> 
    },
    {
        sortable: false,
        disableColumnMenu: true,
        field: "actions",
        headerName: "actions",
        width: 250,
        headerAlign: "center",
        editable: false,
        headerClassName: "text-slate-700 font-semibold border-4",
        cellClassName: "text-slate-700 font-normal border",
        renderHeader: () => <h1 className="text-center font-bold">Actions</h1>,
        renderCell: (params) => {
            return(
                <div className="flex flex-row justify-between h-fit">
                    <button 
                    className=" bg-green-600 text-white h-fit flex flex-row items-center justify-center cursor-pointer rounded-xl
                    hover:bg-green-600/60"
                        onClick={()=>handleEdit(params.row)}
                    ><FaEdit/>Edit</button>
                    <button 
                    className=" bg-red-600 text-white h-fit flex flex-row items-center justify-center cursor-pointer rounded-xl
                    hover:bg-red-600/60"
                        onClick={()=>handleDelete(params.row)}
                    ><FaTrash />Delete</button>
                </div>
            )
        }
    }
];