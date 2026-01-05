import {  Router } from "express";
import inventoryController from "./inventory.controller";
import { validateBody, validateParams  } from "../../shared/middlewares/validate";
import { IdParamDTO, CreateInventoryDTO, UpdateInventoryDTO } from "./inventory.dto";

const router = Router();
//Inventory API
//GET /inventory
router.get("/", inventoryController.list);

//POST /inventory/
router.post("/",  validateBody(CreateInventoryDTO), inventoryController.create)

//UPDATE /inventory/:id
router.put(
    "/:id",
    validateParams(IdParamDTO),
    validateBody(UpdateInventoryDTO),
    inventoryController.update
);

//DELETE //inventory/:id
router.delete("/:id", validateParams(IdParamDTO), inventoryController.delete);

export default router;