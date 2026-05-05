
// While using localstorage
const handleCartAction = (cart, item, qtyAction) => {


    let found = false;

    const updatedCart = cart.map(i => {
        if (i.cartItemId === item.cartItemId) {
            found = true;
            return { ...i, quantity: i.quantity + qtyAction };
        }
        return i;
    }).filter(i => i.quantity > 0);

    return found ? updatedCart : [...cart, {...item, quantity: 1}]

}