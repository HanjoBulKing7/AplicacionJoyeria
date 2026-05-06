
// While using localstorage
export const handleCartAction = (cart, item, qtyAction) => {

    let found = false;

    const updatedCart = cart.map(i => {
        if (i.id === item.id) {
            found = true;
            return { ...i, quantity: i.quantity + qtyAction };
        }
        return i;
    }).filter(i => i.quantity > 0);

    return found ? updatedCart : [...cart, {...item, quantity: 1}]
}

/*
export const removeFromCart = (cart, id) => {
    const updatedCart = cart.filter(i => i.id !== id);
    return updatedCart;
}*/