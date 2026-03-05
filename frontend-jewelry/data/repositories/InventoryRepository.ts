import { InsertJewel, JewelRow } from '../../domain/DomainSQLite';
import { CategoryKey, PricePreset, SortDir, SortKey, StockFilter } from '../../domain/inventory';
import { dbPromiseInit } from '../migrations/CreateInventoryTable';

//Type for casting the query to get Categories & Stock
type StockFromSQL = { 
    category: CategoryKey;
    stock: number;
};

//Create a type to run the WHERE clause properly and save time 
type WhereRule = { clause: string; params: (string | number)[] };

const PRICE_RULE: Record<PricePreset, WhereRule> = {
  any:      { clause: "", params: [] },
  //         Range of prices    and pararms to assign em later 
  "1-49":   { clause: "price BETWEEN ? AND ?", params: [1, 49] },
  "50-99":  { clause: "price BETWEEN ? AND ?", params: [50, 99] },
  "100-149":{ clause: "price BETWEEN ? AND ?", params: [100, 149] },
  "150-299":{ clause: "price BETWEEN ? AND ?", params: [150, 299] },
};

const STOCK_RULE: Record<StockFilter, WhereRule> = {
  any:         { clause: "", params: [] },
  in_stock:    { clause: "stock > ?", params: [0] },
  out_of_stock:{ clause: "stock = ?", params: [0] },
};

const ORDER_RULE: Record<SortKey, string> = {
  alpha: 'name',
  price: 'price',
  stock: 'stock',
};

//Set the type of order and asc || desc
export type ActiveOrder = {
    orderType: SortKey;
    orderDir: SortDir;
};
//Set the filters just one of each type 
export type ActiveFilter = {
    activeStock: StockFilter;
    activePrice: PricePreset;
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
        //Cast the returned row to get Category & Stock
        const result  = await db.getAllAsync<StockFromSQL>(`
            SELECT category, COALESCE(SUM(stock), 0) as stock
            FROM jewel 
            GROUP BY category
        `
        );

        return Object.fromEntries(result.map( row => [ row.category, row.stock ]));
    },

    async listItems(
        orderState: ActiveOrder,
        filterState: ActiveFilter
        ): Promise<JewelRow[]> {

        const db = await dbPromiseInit;

        let sql = `
            SELECT id, name, description, category, stock, price, status, image_url
            FROM jewel
        `;

        const where: string[] = [];
        const params: (string | number)[] = [];

        // STOCK FILTER
        if (filterState.activeStock === "in_stock") {
            where.push("stock > 0");
        }

        if (filterState.activeStock === "out_of_stock") {
            where.push("stock = 0");
        }

        // PRICE FILTER
        switch (filterState.activePrice) {
            case "1-49":
            where.push("price BETWEEN ? AND ?");
            params.push(1, 49);
            break;

            case "50-99":
            where.push("price BETWEEN ? AND ?");
            params.push(50, 99);
            break;

            case "100-149":
            where.push("price BETWEEN ? AND ?");
            params.push(100, 149);
            break;

            case "150-299":
            where.push("price BETWEEN ? AND ?");
            params.push(150, 299);
            break;
        }

        if (where.length > 0) {
            sql += ` WHERE ${where.join(" AND ")}`;
        }

        // ORDER
        let orderColumn = "name";

        if (orderState.orderType === "price") {
            orderColumn = "price";
        }

        if (orderState.orderType === "stock") {
            orderColumn = "stock";
        }

        const dir = orderState.orderDir === "asc" ? "ASC" : "DESC";

        sql += ` ORDER BY ${orderColumn} ${dir}`;

        return db.getAllAsync<JewelRow>(sql, params);
    }
}