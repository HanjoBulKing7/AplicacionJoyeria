import { Response, Request, Router } from "express";
import inventoryController from "./inventory.controller";
const router = Router();
//Inventory API
//GET /inventory
router.get("/", inventoryController.list);

//POST /inventory/
router.post("/", inventoryController.create)

//UPDATE /inventory/:id
router.put("/:id", inventoryController.update)

//DELETE //inventory/:id
router.delete("/:id", inventoryController.delete);

export default router;