export class AppError extends Error{

    public statusCode: number = 0;

    constructor( message: string, statusCode = 400){
        super(message);
        this.statusCode = statusCode;
    }

}