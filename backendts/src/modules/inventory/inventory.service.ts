import InventoryRepo,{ JewelCreate, JewelUpdate } from "./inventory.repository";
import { AppError } from "../../shared/errors/AppError";
import { InventorySQLepository } from './InventorySQLite.repository';
import { Jewel } from "./inventory.repository";
const inventoryService = {

    listInventory () {
        return InventorySQLepository.list();
    },


    createInventory(item: Jewel){//Using my type of Jewel without Id 

        if( !item.name) throw Error("Name is required");//If name isn´t sent throw an error
        if( item.stock==null || item.stock< 0 ) throw Error ( "Stock must be > 0");//If stock isn´t sent or less than zero throw an error 
        if( item.price==null || item.price<=0 ) throw Error ( "Price must be > 0");//If price isn´t sent or <=0 throw error

        return InventorySQLepository.create(item);
    },

    updateInventory(item: JewelUpdate, id: number){

        //Validate if the object "item" contains any of those properties
        const hasAny =
        item.name !== undefined || 
        item.price !== undefined ||
        item.stock !== undefined;
        
        if(!hasAny)  throw Error("No fields to update");//If it does not contain any throw an error
    
        if(item.name!== undefined && !item.name.trim()) throw new AppError ( "Name is required", 400)//Check if the name exists not undefined and not just blank space

        if(item.stock!== undefined && item.stock < 0 ) throw new AppError ( "Stock must not be >= 0", 400);//Check if the stock is not undefined and not less than zero

        if(item.price !== undefined && item.price <= 0) throw new AppError ("Price must be greater than zero", 400);//Check if the price is not undefined and not <= than zero

        return InventorySQLepository.update(item, id);

    },

    deleteInventory (id: number): boolean{

        return InventorySQLepository.delete(id);
    }
};

export default inventoryService;