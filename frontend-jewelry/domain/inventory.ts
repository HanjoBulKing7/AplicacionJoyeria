import { ImageSourcePropType } from 'react-native'
export type CategoryKey = "ring" | "earrings" | "bracelet" | "chain" | "rosary" ;
export type StatusEnum = "active" | "inactive" ;

//Props for the category card
type CategoryConfig = {
  
  key: CategoryKey;//Key when implemented ona child
  label: string;
};


export const Categories: CategoryConfig[] = [
  { key: "ring", label: "Anillos" },
  { key: "earrings", label: "Aretes" } , 
  { key: "bracelet", label: "Pulseras"  }, 
  { key: "chain", label: "Cadenas" } , 
  { key: "rosary", label: "Rosarios" } 
];

export type CardProps = { 
  stock: number; 
  categoryKey: CategoryKey;
  label: string;
};

export const Icons: Record<CategoryKey, ImageSourcePropType> = {
  ring:   require('../assets/icons/categories/ring.png'),
  earrings: require('../assets/icons/categories/earrings.png'),
  bracelet: require('../assets/icons/categories/bracelet.png'), 
  chain: require('../assets/icons/categories/chain.png'),
  rosary: require('../assets/icons/categories/rosary.png'),
};