import { FaEdit, FaEye, FaFileImage, FaImage, FaTrash } from "react-icons/fa";


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
                    ><FaEdit/>Edit
                    </button>
                    <button 
                    className=" bg-red-600 text-white h-fit flex flex-row items-center justify-center cursor-pointer rounded-xl
                    hover:bg-red-600/60"
                        onClick={()=>handleDelete(params.row)}
                    ><FaTrash />Delete
                    </button>
                </div>
            )
        }
    }
];

export const InventoryTableColumns =  ( handleEdit , handleDelete ) => [
    {
        sortable: false,
        disableColumnMenu: true,
        field: "id",
        headerName: "id",
        width: 50,
        headerAlign: "center",
        editable: false,
        headerClassName: "text-slate-700 font-semibold border-4",
        cellClassName: "text-slate-700 font-normal border ",
        renderHeader: () => <h1 className="text-center font-bold ">Id</h1>,
    },
    {
        sortable: false,
        disableColumnMenu: true,
        field: "name",
        headerName: "productName",
        width: 100,
        headerAlign: "center",
        editable: false,
        headerClassName: "text-slate-700 font-semibold border-4",
        cellClassName: "text-slate-700 font-normal border",
        renderHeader: () => <h1 className="text-center font-bold">Name</h1>
    },
    {
        sortable: false,
        disableColumnMenu: true,
        field: "description",
        headerName: "productDescription",
        width: 100,
        headerAlign: "center",
        editable: false,
        headerClassName: "text-slate-700 font-semibold border-4",
        cellClassName: "text-slate-700 font-normal border",
        renderHeader: () => <h1 className="text-center text-sm font-bold">Description</h1>
    },
        {
        sortable: false,
        disableColumnMenu: true,
        field: "price",
        headerName: "productPrice",
        width: 100,
        headerAlign: "center",
        editable: false,
        headerClassName: "text-slate-700 font-semibold border-4",
        cellClassName: "text-slate-700 font-normal border",
        renderHeader: () => <h1 className="text-center font-bold">Price</h1>
    },
    {
        sortable: false,
        disableColumnMenu: true,
        field: "stock",
        headerName: "productStock",
        width: 70,
        headerAlign: "center",
        editable: false,
        headerClassName: "text-slate-700 font-semibold border-4",
        cellClassName: "text-slate-700 font-normal border",
        renderHeader: () => <h1 className="text-center font-bold">Stock</h1>
    },   
    {
        sortable: false,
        disableColumnMenu: true,
        field: "status",
        headerName: "productStatus",
        width: 110,
        headerAlign: "center",
        editable: false,
        headerClassName: "text-slate-700 font-semibold border-4",
        cellClassName: "text-slate-700 font-normal border",
        renderHeader: () => <h1 className="text-center font-bold">Status</h1>
    },
    {
        sortable: false,
        disableColumnMenu: true,
        field: "categoryId",
        headerName: "productCategory",
        width: 100,
        headerAlign: "center",
        editable: false,
        headerClassName: "text-slate-700 font-semibold border-4",
        cellClassName: "text-slate-700 font-normal border",
        renderHeader: () => <h1 className="text-center font-bold">Category</h1>
    },
    {
        sortable: false,
        disableColumnMenu: true,
        field: "actions",
        headerName: "actions",
        width: 200,
        headerAlign: "center",
        editable: false,
        headerClassName: "text-slate-700 font-semibold border-4",
        cellClassName: "text-slate-700 font-normal border",
        renderHeader: () => <h1 className="text-center font-bold">Actions</h1>,
        renderCell: (params) => {
            return(
                <div className="flex flex-row justify-between h-fit text-md">
                    <button 
                    className=" bg-green-600 text-white h-fit flex flex-row items-center justify-center cursor-pointer rounded-xl
                    hover:bg-green-600/60"
                        onClick={()=>handleEdit(params.row)}
                    ><FaEdit/>Edit
                    </button>
                    <button className="">
                        <FaFileImage  />Preview image
                    </button>
                    <button 
                    className=" bg-red-600 text-white h-fit flex flex-row items-center justify-center cursor-pointer rounded-xl
                    hover:bg-red-600/60"
                        onClick={()=>handleDelete(params.row)}
                        ><FaTrash />Delete
                    </button>
                </div>
            )
        }
    }
]