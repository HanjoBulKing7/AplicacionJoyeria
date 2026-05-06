import { useState, useEffect } from 'react'
import {  useNavigate, useLocation, useSearchParams } from 'react-router-dom'
import { FiSearch, FiRefreshCcw, FiArrowDown, FiArrowUp } from 'react-icons/fi'
import { IoIosPricetag } from "react-icons/io";
import { MdOutlineInventory2 } from "react-icons/md";
import { IoReorderThree, IoTextOutline } from "react-icons/io5";
import { FormControl, InputLabel, MenuItem, Select, Tooltip, Button, useSelectFocusSource, Menu, useEventCallback } from '@mui/material'
import { useDispatch, useSelector } from 'react-redux'
import { fetchCategories, fetchItems } from '../../redux/actions/itemActions'
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

    useEffect(()=>{
        if( keyword.trim() === "")
            dispatch(fetchCategories());

        const keywordDebounce = setTimeout(() => {
            dispatch(fetchItems({keyword}));
        }, 500);

        return ()=> clearTimeout(keywordDebounce);
    },[keyword])

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
        updateFilters({name: "categoryId", value: selectedCategory })
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
        setCategory("All");
        setOrder("asc");
        setSortBy("Defualt");
        setKeyword("");
        navigate(pathname);
    };

    
return (
    <div className='bg-gray-950 w-full flex flex-col md:flex-row items-center justify-center border-b border-amber-200 p-4 gap-4'>
        
        {/*SEARCH*/}
        <div className='flex items-center bg-amber-50 rounded-2xl px-4 w-full md:w-auto md:min-w-[300px]'>
            <input
                id='keyword-input'
                className='bg-transparent w-full text-gray-950 text-md focus:outline-none h-10'
                placeholder='Gold ring...'
                value={keyword}
                onChange={handleKeywordChange}
                onKeyDown={(event) => { if (event.key === 'Enter') handleSearchByKeyword(); }}
            />
            <FiSearch className='text-gray-950 text-2xl cursor-pointer' onClick={handleSearchByKeyword} />
        </div>

        {/* 2. SELECTORS CONTAINER*/}
        <div className='flex flex-wrap items-center justify-center gap-2 w-full md:w-auto'>
            
            {/* CATEGORY */}
            <FormControl size="small" sx={{ minWidth: 120 }}>
                <InputLabel sx={{ color: 'white', '&.Mui-focused': { color: '#f59e0b' } }}>Category</InputLabel>
                <Select
                    value={category}
                    label="Category"
                    onChange={handleCategory}
                    sx={{
                        color: 'white',
                        height: '40px',
                        '.MuiOutlinedInput-notchedOutline': { borderColor: 'rgba(255, 255, 255, 0.5)' },
                        '&:hover .MuiOutlinedInput-notchedOutline': { borderColor: 'white' },
                        '&.Mui-focused .MuiOutlinedInput-notchedOutline': { borderColor: '#f59e0b' },
                    }}
                >
                    <MenuItem value="All">All</MenuItem>
                    {categories?.map((cat) => (
                        <MenuItem key={cat.id} value={cat.id}>{cat.name}</MenuItem>
                    ))}
                </Select>
            </FormControl>

            {/* SORT BY */}
            <FormControl size="small" sx={{ minWidth: 120 }}>
                <InputLabel sx={{ color: 'white', '&.Mui-focused': { color: '#f59e0b' } }}>Sort By</InputLabel>
                <Select
                    value={sortBy}
                    label="Sort By"
                    onChange={handleField}
                    sx={{
                        color: 'white',
                        height: '40px',
                        '.MuiSelect-select': { display: 'flex', alignItems: 'center', gap: '8px' },
                        '.MuiOutlinedInput-notchedOutline': { borderColor: 'rgba(255, 255, 255, 0.5)' },
                        '&:hover .MuiOutlinedInput-notchedOutline': { borderColor: 'white' },
                    }}
                >
                    {fields.map((field, id) => {
                        const Icon = field.icon;
                        return (
                            <MenuItem key={id} value={field.label}>
                                <Icon size={18} /> <span className="ml-2">{field.label}</span>
                            </MenuItem>
                        );
                    })}
                </Select>
            </FormControl>

            {/* ACTION BUTTONS */}
            <div className="flex gap-2">
                <Tooltip title="Toggle Order">
                    <Button 
                        variant="outlined" 
                        onClick={toggleOrder}
                        startIcon={order === 'asc' ? <FiArrowUp /> : <FiArrowDown />}
                        sx={{ color: 'white', borderColor: 'white', height: '40px', textTransform: 'none' }}
                    >
                        Sort
                    </Button>
                </Tooltip>

                <Tooltip title="Reset">
                    <Button 
                        variant="outlined" 
                        onClick={resetFilters}
                        sx={{ color: '#f59e0b', borderColor: '#f59e0b', height: '40px', textTransform: 'none' }}
                    >
                        <FiRefreshCcw />
                    </Button>
                </Tooltip>
            </div>
        </div>
    </div>
);
}

export default Filter;