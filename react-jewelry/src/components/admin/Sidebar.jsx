import React from 'react'
import { FaTachometerAlt } from "react-icons/fa";
import { sidebarContent } from '../../utils/SidebarContent'
import { ImTerminal } from 'react-icons/im';
import { Link, useLocation } from 'react-router-dom';

const Sidebar = () => {

    const pathName = useLocation().pathname;

    const toMap = sidebarContent;

  return (
    <div className='flex grow flex-col gap-y-7 overflow-y-auto bg-gray-900'>
        <div className='flex h-20 shrink-0 gap-x-3 pt-2'>
            <FaTachometerAlt className='h-8 w-8 text-red-700' />
            <h1 className='text-white text-xl font-bold'>Administration Panel</h1>
        </div>

        <nav className='flex h-16 gap-x-3 pt-2 pl-3'>
            <ul role="list">
                {
                    toMap.map((item)=>
                    (
                    <li key={item.name}>
                        <Link to={item.href}
                            className={`group flex gap-x-5 rounded-lg p-2 text-lg font-light 
                                ${ pathName === item.href ?
                                    "bg-gray-900 text-white hover:bg-blue-500"
                                    : "text-yellow-300 hover:bg-gray-500 hover:text-white"
                                }
                                `}
                        >
                            <item.icon className='text-2xl'/>
                            <span className='text-white text-2xl'>{item.name}</span>
                        </Link>
                    </li>
                    )
             )
                }
            </ul>
        </nav>
    </div>
  )
}

export default Sidebar