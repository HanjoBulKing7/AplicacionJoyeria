import {z} from "zod";

export const IdParamDTO = z.object({
    id: z.coerce.number().int().positive()
});

export const CreateInventoryDTO  = z
    .object({
    name: z.string().trim().min(1, "Name is required").max(70),
    description: z.string().trim().max(200).optional(),
    price: z.coerce.number().positive("Price must be > 0"),
    stock: z.coerce.number().int().min(0, "Stock must be >= 0"),
    image_url: z.string().trim().max(255).optional(),
    status: z.enum(["ACTIVE", "INACTIVE"]).optional().default("ACTIVE"),
});

export const UpdateInventoryDTO = z
    .object({
    name: z.string().trim().min(1, "Name is required").max(70).optional(),
    description: z.string().trim().max(200).optional(),
    price: z.coerce.number().positive("Price must be greater than 0").optional(),
    stock: z.coerce.number().positive("Stock must be at least 0").optional(),
    image_url: z.string().trim().max(255).optional(),
    status: z.enum(["ACTIVE", "INACTIVE"]).optional(),

})
    .refine(//Validate if the fields were sent
        (data) =>
            data.name !== undefined ||
            data.description !== undefined || 
            data.price !== undefined ||
            data.stock !== undefined ||
            data.image_url !== undefined ||
            data.status !== undefined, 
            { message: "No fields to update "}
        );


export type CreateInventoryInput = z.infer<typeof CreateInventoryDTO>;
export type UpdateInventoryInput = z.infer<typeof UpdateInventoryDTO>
export type IdParamInput = z.infer<typeof IdParamDTO>;