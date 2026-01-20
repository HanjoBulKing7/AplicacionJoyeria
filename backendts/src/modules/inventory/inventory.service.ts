import { CreateInventoryItem, UpdateInventoryItem } from './inventory.dto'
import { InventorySQLepository } from './InventorySQLite.repository';
import  { Jewel } from  './inventory.repository'

const inventoryService = {

    listInventory (): Jewel[] {

        return InventorySQLepository.list();

    },

    createInventory(item: CreateInventoryItem): Jewel {//Using my type of Jewel without Id 

        const toCreateItem = {
            ...item,
            description: item.description ?? null,
            image_url: item.image_url ?? null,
            status: item.status ??  "ACTIVE" 
        }

        return InventorySQLepository.create(toCreateItem);

    },

    updateInventory(item: UpdateInventoryItem, id: string): Jewel | undefined {

        return InventorySQLepository.update(item, id);

    },

    deleteInventory (id: string): boolean {

        return InventorySQLepository.delete(id);

    }
};

export default inventoryService;