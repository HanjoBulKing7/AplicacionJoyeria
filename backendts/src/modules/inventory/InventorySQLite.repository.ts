import { Jewel,JewelCreate, JewelUpdate } from "./inventory.repository";
import db from "../../shared/db/sqlite";
import { InventoryRowDTO }from './inventory.dto'
import { AppError } from "../../shared/errors/AppError";


export const InventorySQLepository = {

    list(){
        const listItemsStatment  = db.prepare(` SELECT * FROM inventory; `);
        const listedItems = listItemsStatment.all();
        return listedItems;
    },

    create(entryJewel: JewelCreate) {
        const createJewelStatement = db.prepare(` INSERT INTO inventory ( name, description, stock, price, status, image_url ) VALUES( ?, ?, ?, ?, ?, ? ); `)
        const createdJewel = createJewelStatement.run(
            entryJewel.name,
            entryJewel.description,
            entryJewel.stock,
            entryJewel.price,
            entryJewel.status,
            entryJewel.image_url
        );
    },

    update(entryJewel: JewelUpdate, id: number): Jewel | undefined  {
        //Whitelist to check which fields should be accepted becuase we do not know exactly which ones were sent
        const whiteList = ["name","description","stock","price","status","image_url"];

        const entries = Object.entries(entryJewel);//Store the values on another diffrent object 

        const filtered = entries.filter( ( [ k, v ])=> whiteList.includes(k)&& v !== undefined );//Check which fields were sent to modify

        if(filtered.length === 0) return undefined;//If filtered is empty then any valid field were sent  and return undefined
        else{

            const columnsArray = filtered.map( ([ k ]) => k+' = ? ');//Store the columns of the table to reference and concat = ? to prepare the SQL statement 
            //Using placeholders '?' common backend practice so we can send the values when the run utility is used 
            const valuesArray = filtered.map( ([ ,sentValue ]) => sentValue);//Store the values of each column 
            
            const statementComplement = columnsArray.join(", ");//Separar cada key = ? con una ' , '

            const updateStatement = db.prepare(' UPDATE inventory SET '+statementComplement+" WHERE id = ? ");//Prepare the SQL statement and concat the complement with placeholders 
            
            const statementResult = updateStatement.run( ...valuesArray, id);//Send the object with the values of each key to assign to the placeholders using a spread operator

            if( statementResult.changes === 0){
                return undefined;//If the statment result has no changes ( .changes === 0) it means any rows was affected then return undefined
            }else{
                //Prepare the statemenmt which the fields available on the interface to retrieve the updated row
                const getUpdatedRowStament = db.prepare(' SELECT id, name, description, price, stock, image_url, status FROM inventory WHERE id = ?');
                //Compare the retrieved row with the InventoryRowDTO and checkl if they are the same 
                const updatedRowResult = InventoryRowDTO.safeParse(getUpdatedRowStament.get( id ));//Use get utility to run retrieve one row from DB 

                if (!updatedRowResult.success) {//If was not succesffully performed then throw an error and stop the execution
                throw new AppError("Inventory row shape mismatch", 500);
                }

                return updatedRowResult.data;

            }
        }

    },

    delete( id: number ): boolean  {

            const deleteStatement = db.prepare(' DELETE FROM inventory WHERE id =  ? ');
            
            return deleteStatement.run( id ).changes !== 0;
    }
} 