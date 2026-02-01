import { dbPromiseInit } from '../migrations/CreateInventoryTable'
import { InsertJewel } from '../../domain/DomainSQLite'
import { CategoryKey } from '../../domain/inventory'

type StockFromSQL = { 
    category: CategoryKey;
    stock: number;
};




export const InventoryRepository  = {

    async insertItem(item: InsertJewel): Promise<void>{

        const db = await dbPromiseInit;

        const InsertionQuery = `
            INSERT OR IGNORE INTO jewel ( id, name, description, category, stock, price, status, image_url )
            VALUES  
            ( ? , ? , ? , ? , ? , ? , ? , ? );
        `;

          await db.runAsync(InsertionQuery,[
            item.id,
            item.name,
            item.description,
            item.category,
            item.stock,
            item.price,
            item.status,
            item.image_url
        ]);
    },

    //Here we expect an object thats why create the type of obejct
    //Does not return a number 
    //export type StockResult = <number | null >;

    async getCategoriesStock(): Promise<Partial<Record<CategoryKey, number>>> {
        const db = await dbPromiseInit;
        
        const result  = await db.getAllAsync<StockFromSQL>(`
            SELECT category, COALESCE(SUM(stock), 0) as stock
            FROM jewel 
            GROUP BY category
        `
        );

        return Object.fromEntries(result.map( row => [ row.category, row.stock ]));
    },
}