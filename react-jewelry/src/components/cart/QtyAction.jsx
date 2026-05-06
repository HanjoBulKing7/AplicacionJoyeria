import { useDispatch } from "react-redux";  // ← falta esto
import { FaPlus, FaMinus } from "react-icons/fa6";
import { cartAction } from "../../redux/slices/cartSlice";

const Action = ({ item }) => {
    const dispatch = useDispatch();

    return (
        <div className="flex flex-row justify-center gap-5">
            <button onClick={() => dispatch(cartAction({ item, qty: -1 }))}>
                <FaMinus className="cursor-pointer" />
            </button>
            <span className="text-2xl font-montserrat">{item.quantity}</span>
            <button onClick={() => dispatch(cartAction({ item, qty: +1 }))}>
                <FaPlus className="cursor-pointer" />
            </button>
        </div>
    );
}

export default Action;