export interface Jewel{
    //Properties for Jewel so far
    id: number;
    name: string;
    description: string;
    price: number;
    stock: number;
    image_url: string;
    status: "ACTIVE" | "INACTIVE";

}

export type JewelCreate = Omit<Jewel, "id">;//New type to omit the Id, then won't be received because it is auto assigned on the backend
export type JewelUpdate = Partial<Omit<Jewel, "id">>;//New type to make partial other fields not Id so we can update at least 1 propertie


export interface InventoryRepository{
    //Signatre of each function
    list(): Jewel[];
    create( item: JewelCreate): Jewel;
    update( item: JewelUpdate, id: number): Jewel  | undefined;
    delete( id: number): boolean;
}

const data: Jewel[] = [//Hardcoded array
    {
        id: 1, 
        name: "Gold ring", 
        description: "cute testing ring",
        stock: 10, 
        price: 180,
        image_url: "holatilin.jpg",
        status: "ACTIVE"
    },
];

let autoIncrementId = Math.max(0, ...data.map(j => j.id)) + 1;//Auto increment to assign Id backend handled

const inventoryRepository: InventoryRepository = {

    list () {
        return [...data];
    },
    
    create (item: JewelCreate): Jewel  {
        
        const newItem = { ...item, id: autoIncrementId++};
        data.push(newItem);
        console.log("Saved new item: ", newItem?.name);
        return newItem;
    },

    update(item: JewelUpdate, id: number): Jewel |  undefined { 

        const idx = data.findIndex(j=> j.id === id);//Find the index of the needed object
        if( idx === -1) return undefined;//If does not  exists (-1) return undefined

        data[idx] = { ...data[idx], ...item, id };//Merge changes on the array
        return data[idx];//Return the updated object
    },

    delete( id: number): boolean {
        const i = data.findIndex(j=> j.id===id );//Find the index of the needed object
        if ( i === -1 ) return false;//If does not  exists (-1) return undefined

        data.splice(i, 1); //Delete just one object from the obtainted position
        return true;//Return true because it could be deleted
    }

};

export default inventoryRepository;