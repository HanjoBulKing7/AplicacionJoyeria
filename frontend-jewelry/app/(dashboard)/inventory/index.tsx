import {  StyleSheet, Text, View} from "react-native";
import { useEffect,useState } from "react";
import CategoryCard from '@/app/(dashboard)/inventory/components/CategoryCard'
import { Categories as cat } from "../../../domain/inventory"
import { InventoryRepository } from '../../../data/repositories/InventoryRepository'

export default function InventoryScreen(){

    const [ catStock, setCatStock ] = useState<Record<string, number>>({})


    useEffect ( ( ) => {
        ( async ( ) => {
            const obj = await InventoryRepository.getCategoriesStock();
            setCatStock( obj );
        }) ()
    }, [] );

    console.log("InventoryScreen render", Date.now());

    return(
        <View style={styles.screen}>
            <Text style={ styles.mainTitle }> Inventario </Text>

            <Text style={ styles.subtitle} >Seleciona una categoría</Text>

            <View style={ styles.categoriesContainer }>
                {
                cat.map(c => {
                    return (
                        <CategoryCard
                            key={c.key}  //Indicate the key for the child on each map() iteration, avoid warning 
                            categoryKey={c.key}
                            label={c.label}
                            stock={catStock[c.key] ?? 0}
                        />
                        );
                    })
                }
            </View>
        </View>
    );
}   

const styles = StyleSheet.create({

    screen: {
        backgroundColor: "#1A1818",
        flex: 1,
        alignItems: "center",

        
    },
    mainTitle: {
        fontFamily: "PlusJakartaSans-Light",
        color: '#ffffff',
        fontSize: 55,
        marginTop: 30
        
    },
    subtitle: {
        fontFamily: "PlusJakartaSans-Light",
        color: '#8d8686',
        fontSize: 20,
        marginTop: 100,
        marginBottom: 30,

    },
    categoriesContainer: {
        backgroundColor: "rgba(255,255,255,0.06)",
        width: "85%",
        maxWidth: 320,
        height: "62%",

        borderRadius: 22,
        borderWidth: 1,
        borderColor: "rgba(255,255,255,0.10)",

        paddingLeft: 20,
        justifyContent: "center",
        gap: 30,
    },
    categoryCard: {

    },
    categoryName: {
        color: '#FFFFFF',
        backgroundColor: '#807b7b',
        borderWidth: 1,
        borderRadius: 10,

    }
});