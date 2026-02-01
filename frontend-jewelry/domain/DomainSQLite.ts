
import { CategoryKey, StatusEnum } from './inventory'

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
