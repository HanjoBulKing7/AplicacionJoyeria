import { useEffect, useState } from 'react'
import { useLocation, Link} from 'react-router-dom' 
import { GiHamburgerMenu } from 'react-icons/gi';
import { IoIosMenu } from 'react-icons/io' 
import { RxCross2 } from 'react-icons/rx'
import { FaShoppingCart } from 'react-icons/fa'
import { Badge } from '@mui/material'
import { useSelector } from 'react-redux'

const navLinks = [
  { name: 'Home', path: '/' },
  { name: 'Catalog', path: '/items' },
  { name: 'Contact', path: '/contact' },
  { name: 'About us', path: '/about' },
];

const Navbar = () => {

    const [ isOpen, setIsOpen ] = useState(false);
    const pathname = useLocation().pathname;
    const cart = useSelector((state) => state.cart.cart);

    return(
        <div className='bg-black'>
            <ul className={`flex flex-col items-center justify-center w-full text-white gap-6 md:flex-row md:gap-20 md:h-20 md:overflow-visible 
                 ${isOpen ? 'h-fit gap-10 pt-2' : 'h-0 overflow-hidden'}`}>
                {navLinks.map((link, i) => (
                    <li key={i}>
                    <Link 
                        to={link.path} 
                        className={`transition-all duration-300 relative group
                        ${pathname === link.path 
                            ? 'text-amber-400 font-medium' // Selected color
                            : 'text-white hover:text-amber-200' // Default color
                        }`}
                    >
                        {link.name}
                        
                        {/* The "Elegant Underline" Logic */console.log("Current path", link.path)}
                        <span className={`absolute -bottom-1 left-0 h-[1px] bg-amber-400 transition-all duration-500
                        ${pathname === link.path ? 'w-full' : 'w-0 group-hover:w-full'}`}>
                        </span>
                    </Link>
                    </li>
                ))}
                <li>
                    <Link to='/cart'>
                        <Badge
                            showZero
                            badgeContent={cart.length}
                            color='primary'
                            overlap='circular'
                            anchorOrigin={{vertical: 'top', horizontal: 'right',}}
                        >
                            <FaShoppingCart size={25} />                            
                        </Badge>
                    </Link>
                </li>
            </ul>

            <button 
                onClick={()=>setIsOpen(!isOpen)}
                className='md:hidden p-3'>
                    {   isOpen ?  <RxCross2 className='text-white text-3xl'/> : <GiHamburgerMenu className='text-white text-3xl'/> }
            </ button>
        </div>
    );

}

export default Navbar;