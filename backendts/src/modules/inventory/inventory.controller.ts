import { Request, Response } from "express"
import inventoryService from "./inventory.service"


const inventoryController = {

    list(req: Request, res: Response){
        try{
            const items = inventoryService.listInventory();
            return res.json(items);

        }catch(err){
            console.log(err);
            return res.status(500).json({message: "Internal server error"});
        }
    },

    create( req: Request, res: Response ){
        try{
            const created  = inventoryService.createInventory(req.body);
            return res.status(201).json({message: "Item added succesfully: ", data: created })

        }catch(err){
            
            const message = err instanceof Error ? err.message : "Bad request";
            return res.status(400).json({ message });

        }
    },

    update(req: Request, res: Response ){
        try{
            const id = Number(req.params.id);

            if(Number.isNaN(id) ||  id<=0)
                return res.status(400).json({ message: "Invalid id "});

            const updated = inventoryService.updateInventory(req.body, id);

            if(!updated)
                return res.status(404).json({ message: "Item not found"})

            return res.status(200).json({ message: "Item updated succesfully", updated})
            
        }catch(err){
            
            const message = err instanceof Error ? err.message : "Bad request";
            return res.status(400).json({ message });

        }
    },

    delete( req: Request, res: Response ){
        try{
            const id = Number(req.params.id);
            if(Number.isNaN(id) || id<=0)
                return res.status(400).json({ message: "Invalid id" });

            const deleted = inventoryService.deleteInventory(id);

            if(!deleted)
                return res.status(404).json({ message: "Item not found"});

            return res.status(204).send();
            
        }catch(err){

            const message = err instanceof Error ? err.message : "Bad request";
            return res.status(400).json({ message });

        }
    }

};

export default inventoryController;