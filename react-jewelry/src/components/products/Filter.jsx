import { useState, useEffect } from 'react'
import {  useNavigate, useLocation, useSearchParams } from 'react-router-dom'
import { FiSearch, FiRefreshCcw, FiArrowDown, FiArrowUp } from 'react-icons/fi'
import { IoIosPricetag } from "react-icons/io";
import { MdOutlineInventory2 } from "react-icons/md";
import { IoReorderThree, IoTextOutline } from "react-icons/io5";
import { FormControl, InputLabel, MenuItem, Select, Tooltip, Button, useSelectFocusSource, Menu, useEventCallback } from '@mui/material'
import { useDispatch, useSelector } from 'react-redux'
import { fetchCategories } from '../../redux/actions/itemActions'
import { clearCategories } from '../../redux/slices/categorySlice'

// Items fields used to display ordered by 
const fields = [ 
     {label: "Default", icon: IoReorderThree }, 
     {label: "Name", icon: IoTextOutline}, 
     {label: "Price", icon: IoIosPricetag}, 
     {label: "Stock", icon: MdOutlineInventory2}
    ];

const Filter = () => {
    // For URL handling
    const [searchParams] = useSearchParams();
    const pathname = useLocation().pathname;
    const params = new URLSearchParams(searchParams);
    const navigate = useNavigate();
    
    // Redux
    const dispatch = useDispatch();
    const categories = useSelector((state)=> state.categories.categories);

    //Needed hooks
    const [ category, setCategory] = useState("All");
    const [ order, setOrder ] = useState("asc");
    const [sortBy, setSortBy ] = useState("Default");
    const [ keyword, setKeyword] = useState("");


    // Fetch categories with cleanup
    useEffect(()=>{
        dispatch(fetchCategories());
        return ()=>dispatch(clearCategories());
    },[]);

    const handleKeywordChange = (e) => {
        const newText = e.target.value;
        setKeyword(newText);
    };

    const handleSearchByKeyword = () => {
        const debounceForKeyword = setTimeout(() => {
            updateFilters({ name: "keyword", value: keyword})
        }, 500);
        return clearTimeout(debounceForKeyword);
    }

    const handleCategory = (event) => {
        const selectedCategory = event.target.value;
        setCategory(selectedCategory);
        updateFilters({name: "category", value: selectedCategory })
    };

    const handleField = (event) => {
        const selectedField = event.target.value;
        setSortBy(selectedField);
        updateFilters({name: "sortBy", value: selectedField})
    };

    const toggleOrder = () => {
        setOrder(prev => {
            const nextOrder = prev === 'asc' ? 'desc' : 'asc';
            updateFilters({name: "sortOrder", value: nextOrder}); 
            return nextOrder;
        });
    };


    const updateFilters = ({ name, value }) => {
        if (value && value !== 'All' && value !== 'Default') {
            params.set(name, value);
        } else {
            params.delete(name);
        }
        navigate(`${pathname}?${params.toString()}`);
    };

    const resetFilters = () => {
        setCategory("all");
        setOrder("asc");
        setSortBy("Defualt");
        setKeyword("");
        navigate(pathname);
    };

    
    return(
        <div className='bg-gray-950 w-full min-h-20 flex flex-row items-center justify-center border-b border-amber-200 gap-4 px-4 '>
            {/*SEARCH BAR*/}
            <div className='flex flex-row sm:w-80 w-100 lg:50 px-3 gap-1'>
                <input
                    id='keyword-input'
                    className='bg-amber-50 text-gray-950 text-md focus:ring-fuchsia-700 h-9 px-1 border-0.5 rounded-2xl'
                    placeholder='Gold ring...'
                    value={keyword}
                    onChange={handleKeywordChange}
                    onKeyDown={(event)=>{if(event.key === 'Enter')handleSearchByKeyword();} }
                />
                <FiSearch className=' text-white text-3xl ' onClick={handleSearchByKeyword}/>
            </div>
            {/*CATEGORY SELECTOR*/}
            <div>
                <FormControl size="small" sx={{ m: 1, minWidth: 80, height: 40  }}>
                <InputLabel 
                    id="category-label" 
                    sx={{ color: 'white', '&.Mui-focused': { color: '#f59e0b' } }} // Blanco por defecto, Ámbar al enfocar
                >
                    Category
                </InputLabel>
                <Select
                    labelId="category-label"
                    value={category}
                    label="Category"
                    onChange={handleCategory}
                    sx={{
                    color: 'white', // Texto seleccionado en blanco
                    height: '40px',
                    '.MuiOutlinedInput-notchedOutline': { borderColor: 'rgba(255, 255, 255, 0.5)' }, // Borde gris claro
                    '&:hover .MuiOutlinedInput-notchedOutline': { borderColor: 'white' }, // Blanco al pasar el mouse
                    '&.Mui-focused .MuiOutlinedInput-notchedOutline': { borderColor: '#f59e0b' }, // Ámbar al hacer clic
                    }}
                >
                    <MenuItem value="All">All</MenuItem>
                    {categories.map((category) => (
                    <MenuItem key={category.id} value={category.id}>
                        {category.name}
                    </MenuItem>
                    ))}
                </Select>
                </FormControl>
            </div>
            {/*FIELD(SORT BY) SELECTOR*/}
            <div>
                <FormControl size="small" sx={{ m: 1, minWidth: 80, height: 40  }}>
                <InputLabel 
                    id="category-label" 
                    sx={{ color: 'white', '&.Mui-focused': { color: '#f59e0b' } }} >
                    Sort By
                </InputLabel>
                        <Select
                            labelId="category-label"
                            value={sortBy}
                            onChange={handleField}
                            label="Sort By" // Cambié esto a "Sort By" para que coincida con tu imagen
                            sx={{
                                color: 'white',
                                // 1. Esto alinea el icono y el texto en fila dentro del Select
                                '.MuiSelect-select': {
                                display: 'flex',
                                alignItems: 'center',
                                gap: '13px', // Espacio entre el icono y el texto
                                },
                                '.MuiOutlinedInput-notchedOutline': { borderColor: 'rgba(255, 255, 255, 0.5)' },
                                '&:hover .MuiOutlinedInput-notchedOutline': { borderColor: 'white' },
                                '&.Mui-focused .MuiOutlinedInput-notchedOutline': { borderColor: '#f59e0b' },
                            }} >
                            {fields.map((field, id) => {
                                const IconComponent = field.icon;
                                return (
                                <MenuItem key={id} value={field.label} >
                                    <IconComponent size={20} className='pr-2' /> {field.label}
                                </MenuItem>
                                );
                    })}
                </Select>
                </FormControl>
            </div>
                {/* BOTÓN PARA ORDEN */}
                <Tooltip title={`Order: ${order === 'asc' ? 'Ascending' : 'Descending'}`}>
                    <Button 
                        sx={{
                            color: 'white',
                            borderColor: 'white',
                            textTransform: 'none', // Para que no salga todo en mayúsculas
                            height: '40px', // Misma altura que tus Selects
                            '&:hover': { borderColor: 'white', backgroundColor: 'rgba(255,255,255,0.1)' }
                        }}
                        variant="outlined"
                        startIcon={order === 'asc' ? <FiArrowUp /> : <FiArrowDown />}
                        onClick={toggleOrder}
                    >
                        Sort
                    </Button>
                </Tooltip>

                {/* BOTÓN RESET */}
                <Tooltip title="Reset all filters">
                    <Button 
                    variant='outlined'
                        sx={{
                            color: '#f59e0b',
                            textTransform: 'none',
                            height: '40px',
                            gap: 1,
                            borderColor: 'white',
                        }}
                        onClick={resetFilters}
                    >
                        Reset Filters
                        <FiRefreshCcw />
                    </Button>
                </Tooltip>
        </div>
    );
}

export default Filter;