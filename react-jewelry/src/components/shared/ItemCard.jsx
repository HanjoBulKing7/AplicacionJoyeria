

const ItemCard = ({item}) => {

    return(
        <div className='lg:w-50 w-45 lg:h-60 h-50 bg-black border border-amber-200 shadow-lg shadow-yellow-400 rounded-lg'>
            <div>
                <img src={`${import.meta.env.VITE_BACKEND_URL}/public/images/${item.image}`} alt={item.name} />
                {console.log(`URL DE FOTOS ${import.meta.env.VITE_BACKEND_URL}/pu
                    blic/images/${item.image}`)}
            </div>
            <p className='text-white'>Item name</p>
            {item.name}
            <p className='text-white'>Item price</p>
            {item.price}
        </div>
    );


}

export default ItemCard;