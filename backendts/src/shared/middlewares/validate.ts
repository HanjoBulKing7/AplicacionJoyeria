import { Request, Response, NextFunction } from "express";
import { ZodTypeAny } from "zod";


export const validateBody = 
    (schema: ZodTypeAny )=> ( req: Request, res: Response, next: NextFunction )=>{
        const result = schema.safeParse(req.body);
        if(!result.success){
            return res.status(400).json({
                message: "Validation error",
                errors: result.error.issues,
            })
        }

    req.body = result.data;
    next();
};

export const validateParams = 
    (schema: ZodTypeAny ) => ( req: Request, res: Response, next: NextFunction ) => {
        const result = schema.safeParse( req.params );
        if(!result.success){
            return res.status(400).json({
                message: "Validation error",
                errrors: result.error.issues,
            })
        }

        req.params = result.data as any;
        next();
};