import { CreateInventoryItem, UpdateInventoryItem } from './inventory.dto'
import { InventorySQLepository } from './InventorySQLite.repository';

const inventoryService = {

    listInventory () {
        return InventorySQLepository.list();
    },


    createInventory(item: CreateInventoryItem){//Using my type of Jewel without Id 

        return InventorySQLepository.create(item);

    },

    updateInventory(item: UpdateInventoryItem, id: number){

        return InventorySQLepository.update(item, id);

    },

    deleteInventory (id: number): boolean{

        return InventorySQLepository.delete(id);
    }
};

export default inventoryService;