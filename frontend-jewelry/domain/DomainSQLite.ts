import { CategoryKey, StatusEnum } from './inventory'

//Template type to insert a new record 
export type InsertJewel = {

  id: string;
  name: string;
  description: string | null; 
  category: CategoryKey;
  stock: number;
  price: number;
  status: StatusEnum;
  image_url: string | null;
}

//Template type to cast the rows
export type JewelRow = {
  id: string;
  name: string;
  description: string | null; 
  category: CategoryKey;
  stock: number;
  price: number;
  status: StatusEnum;
  image_url: string | null;   
};