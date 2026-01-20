import { Request, Response } from "express"
import inventoryService from "./inventory.service"
import { CreateInventoryDTO, IdParamDTO, UpdateInventoryDTO } from "./inventory.dto";

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
            const createBody = CreateInventoryDTO.parse(req.body);

            const created  = inventoryService.createInventory(createBody);

            return res.status(201).json({message: "Item added succesfully ", data: created })

        }catch(err){
            
            const message = err instanceof Error ? err.message : "Bad request";
            return res.status(400).json({ message });

        }
    },

    update(req: Request, res: Response ){

        try{

            const parseResult = IdParamDTO.safeParse(req.params);

            const updateBody = UpdateInventoryDTO.parse(req.body);

            if (!parseResult.success)   return res.status(400).json({ message: "Invalid id" });

            const { id } = parseResult.data;

            const updated = inventoryService.updateInventory(updateBody, id);

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

            const parseResult = IdParamDTO.safeParse(req.params);

            if (!parseResult.success)   return res.status(400).json({ message: "Invalid id" });

            const { id } = parseResult.data;

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