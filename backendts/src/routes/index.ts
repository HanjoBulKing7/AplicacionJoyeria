import {Router} from "express";
import InventoryRouter from "../modules/inventory/inventory.routes"

const router = Router();


router.use("/inventory", InventoryRouter);

export default router;