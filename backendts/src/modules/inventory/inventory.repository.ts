export interface Jewel{
    //Properties for Jewel so far
    id: string;
    name: string;
    description: string | null;
    category:  "RING" | "CHAIN" | "BRACELET" | "EARRINGS" | "ROSARY";
    price: number;
    stock: number;
    image_url: string | null;
    status: "ACTIVE" | "INACTIVE";

}

export type JewelCreate = Omit<Jewel, "id">;//Omit the id to generate it here 
export type JewelUpdate = Partial<JewelCreate>;//New type to make partial all fields


export interface InventoryRepository{
    //Signatre of each function
    list(): Jewel[];
    create (item: JewelCreate): Jewel;
    update( item: JewelUpdate, id: string): Jewel  | undefined;
    delete( id: string): boolean;
}

const data: Jewel[] = [//Hardcoded array
    {
        id: crypto.randomUUID(), 
        name: "Gold ring", 
        description: "cute testing ring",
        category: "RING",
        stock: 10, 
        price: 180,
        image_url: "holatilin.jpg",
        status: "ACTIVE"
    },
];

// Not longer used||||||For self created ID let autoIncrementId = Math.max(0, ...data.map(j => j.id)) + 1;//Auto increment to assign Id backend handled

const inventoryRepository: InventoryRepository = {

    list () {

        return [...data];
    },
    
    create (item: JewelCreate): Jewel  {

        const generatedId = crypto.randomUUID();//Generate an UUID for the new item
        const createdJewel = {...item, id: generatedId};
        data.push(createdJewel);//Save the new object on the array using the spread operator

        return createdJewel;//Return the saved object using the spread operator

    },

    update(item: JewelUpdate, id: string): Jewel |  undefined { 

        const idx = data.findIndex(j=> j.id === id);//Find the index of the needed object
        if( idx === -1) return undefined;//If does not  exists (-1) return undefined

        data[idx] = { ...data[idx], ...item, id };//Merge changes on the array
        return data[idx];//Return the updated object
    },

    delete( id: string): boolean {

        const i = data.findIndex(j=> j.id===id );//Find the index of the needed object
        if ( i === -1 ) return false;//If does not  exists (-1) return undefined

        data.splice(i, 1); //Delete just one object from the obtainted position
        return true;//Return true because it could be deleted
    }

};

export default inventoryRepository;