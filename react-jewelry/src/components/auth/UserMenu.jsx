import React from 'react'
import { FaUser } from 'react-icons/fa'
import { GiConsoleController } from 'react-icons/gi';
import { IoLogOutSharp } from "react-icons/io5";
import { IoSettingsSharp } from "react-icons/io5";
import { Link } from 'react-router-dom'

const menuElements = [
    { icon: FaUser, label: "Profile", destination: '/profile' },
    { icon:  IoSettingsSharp, label: "Settings", destination: '/settings' },
    { icon: IoLogOutSharp , label: "Logout", destination: '/logout' }
]

function UserMenu({isVisible}) {


  return (
    <div
     className='absolute flex flex-col items-start space-y-5 py-2 z-20 right-0 bg-gray-900 rounded-2xl'>
        {
            menuElements.map((e, i)=>{
                let CurrentIcon = e.icon;
                return (
                    <div key={i} className=' px-4 py-3 cursor-pointer transition-all duration-300
                        hover:bg-[radial-gradient(circle,rgba(255,255,255,0.2)_0%,rgba(251,191,36,0.1)_40%,transparent_70%)]'>
                            <Link className='flex flex-row gap-2' to={e.destination}>
                                <CurrentIcon className='text-white text-xl' />
                                <span>{e.label}</span>
                            </Link>
                    </div>
                );
            })
        }
    </div>
  )
}

export default UserMenu