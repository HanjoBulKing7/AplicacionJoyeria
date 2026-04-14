


const ItemCard = ({item}) => {

    return(
        <div className='lg:w-50 w-45 lg:h-60 h-50 bg-black border border-zinc-400 rounded-lg'>
            <p className='text-white'>Item name</p>
            {item.name}
            <p className='text-white'>Item price</p>
            {item.price}
        </div>
    );


}

export default ItemCard;