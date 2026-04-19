import { useState } from 'react'
import ItemModal from './ItemModal'

const ItemCard = ({ item }) => {

    const [ isVisible, setIsVisible ] = useState(false);

    const toggleModalView = () => {
        setIsVisible(true);
    };
    
    return (
        <div 
        onClick={toggleModalView}
        className='flex flex-col bg-black border border-amber-200/50 shadow-md shadow-yellow-900/20 rounded-lg overflow-hidden hover:scale-105 transition-transform'>
            {/* Contenedor de imagen con aspecto cuadrado */}
            <div className="aspect-square w-full bg-neutral-900">
                <img 
                    src={`${import.meta.env.VITE_BACKEND_URL}/public/images/${item.image}`} 
                    alt={item.name}
                    className="w-full h-full object-cover" 
                />
            </div>
            
            {/* Contenedor de datos con padding ajustado */}
            <div className="p-3 flex flex-col gap-1">
                <p className='text-xs text-gray-500 uppercase font-bold'>Name</p>
                <h3 className='text-white text-sm truncate'>{item.name}</h3>
                
                <p className='text-xs text-gray-500 uppercase font-bold mt-2'>Price</p>
                <span className='text-amber-200 font-semibold'>${item.price}</span>

                { item.stock > 0 ? 
                (
                <>
                    <p className='text-xs text-gray-500 uppercase font-bold mt-2'>Stock</p>
                    <span className='text-amber-200 font-semibold'>{item.stock}</span>
                </>
                )   :   (
                    <span className='text-red-600 font-bold'>Out of stock </span>
                )}
            </div>
            {
                isVisible && (
                    <ItemModal item={item} open={isVisible} setOpen={setIsVisible} />
                )
            }
        </div>
    );
}

export default ItemCard;