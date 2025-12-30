import { Response, Request, Router } from "express";

const router = Router();

//GET /inventory
router.get("/", (req: Request, res: Response)=>{
    res.json([{ id: 1, name: "Anillo oro", stock:10}]);
});

//Inventory API
//POST /inventory/
router.post("/", (req: Request, res: Response)=>{
    const jewel = req.body;
    console.log("New jewel added: ", jewel?.name);
    res.status(201).json({message: "Succesfully added", item: jewel})
})

//UPDATE /inventory/:id
router.put("/:id", (req: Request, res: Response)=>{

    const {id} = req.params;
    const updated = req.body;

    console.log("Jewel updated id=",id," ",updated?.name);
    res.json({message: "Succesfully updated ", id, item: updated});
})

//DELETE //inventory/:id
router.delete("/:id", (req: Request, res: Response)=>{
    const { id } = req.params;

    console.log("Jewel deleted id=", id)
    res.json(["Deleted succesfully", id]);
});

export default router;