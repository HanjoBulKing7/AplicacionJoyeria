import { View, Pressable, Image, Text, StyleSheet,ImageSourcePropType, Modal } from 'react-native'
import { useState } from 'react'
import { Picker } from '@react-native-picker/picker'
import { StockFilter, PricePreset, SortKey, SortDir } from '../../../../domain/inventory'
import { SortState , FilterState , PillProps } from '../[category]'


const  Arrows: Record<string, ImageSourcePropType> = {
    asc: require( '../../../../assets/icons/actions/arrow_upward.png' ),
    desc: require( '../../../../assets/icons/actions/arrow_downward.png' ),
};

export default function OverlayPill ({ menu, sort, onSortChange, filters, onFiltersChange  }: PillProps) {

    function changeSorting(sorting: SortKey) {
        const isNewKey = sort.key !== sorting;

        const next: SortState = isNewKey
            ? { key: sorting, dir: 'asc' }
            : { key: sorting, dir: sort.dir === 'asc' ? 'desc' : 'asc' };

        onSortChange(next);
    }

        function changeFilters(patch: Partial<FilterState>) {
            // opcional: evitar updates si no cambió nada
            const changed =
                (patch.stock !== undefined && patch.stock !== filters.stock) ||
                (patch.price !== undefined && patch.price !== filters.price);

            if (!changed) return;//Sino cambió nada terminar el onchange

            onFiltersChange(patch);//Actualizar con el patch ya que contiene uno de los 2 así lo define la firma desde el padre 
        }


    type OpenMenu = 'stock' | 'price' | null;
    const [ openMenu, setOpenMenu ] = useState<OpenMenu>( null );
    // Options for filters
    const STOCK_OPTIONS: { key: StockFilter; label: string }[] = [
        { key: 'any', label: 'Cualquier cantidad' },
        { key: 'in_stock', label: 'Con stock' },
        { key: 'out_of_stock', label: 'Sin stock' },
    ];
    //| Options for price presets
    const PRICE_OPTIONS: { key: PricePreset; label: string }[] = [
        { key: 'any', label: 'Cualquier precio' },
        { key: '1-49', label: '$1 - $49' },
        { key: '50-99', label: '$50 - $99' },
        { key: '100-149', label: '$100 - $149' },
        { key: '150-299', label: '$150 - $299' },
    ];
    
    if( menu === 'sort' ){{/*Pill for sorting*/}
        return(
            <View style={ sortStyle.container } > 
                <View>
                    <Pressable onPress={( ) =>{changeSorting('alpha') } } style={sortStyle.elementContainer} > 
                        <Text style={ sort?.key === 'alpha' ? sortStyle.activeText : sortStyle.inactiveText  }  > A - Z </Text> 
                        { sort.key === 'alpha' ? <Image style ={ sortStyle.arrow } source={ Arrows[sort.dir] }/> : null }
                    </Pressable>
                </View>

                <Pressable onPress={( ) =>{changeSorting('price') }} style={sortStyle.elementContainer} > 
                    <Text style={ sort?.key === 'price' ? sortStyle.activeText : sortStyle.inactiveText  } > Precio </Text> 
                    { sort.key === 'price' ? <Image style ={ sortStyle.arrow } source={ Arrows[sort.dir] }/> : null }
                </Pressable>

                <Pressable onPress={( ) =>{changeSorting('stock') }} style={sortStyle.elementContainer} > 
                    <Text style={ sort?.key === 'stock' ? sortStyle.activeText : sortStyle.inactiveText  } > Cantidad </Text> 
                    { sort.key === 'stock' ? <Image style ={ sortStyle.arrow } source={ Arrows[ sort.dir] }/> : null }
                </Pressable>
            </View>
        );
    }else if( menu === 'filter' ){{/*Pill for filtering*/}

        const stockLabel = STOCK_OPTIONS.find( o => o.key === filters.stock )?.label ?? 'Cualquier cantidad';
        const priceLabel = PRICE_OPTIONS.find( o => o.key === filters.price )?.label ?? 'Cualquier cantidad';

        return (
        <View style={filter.container}>
            <Pressable onPress={() => setOpenMenu("stock")}>
            <Text style={filter.title}>Filtrar por cantidad:</Text>
            <Text style={filter.text}>{stockLabel}</Text>
            </Pressable>

            <Pressable onPress={() => setOpenMenu("price")}>
            <Text style={filter.title}>Filtrar por precio:</Text>
                <Text style={filter.text}>{priceLabel}</Text>
            </Pressable>

            {/* Dropdown modal */}
            <Modal visible={openMenu !== null} transparent animationType="fade" onRequestClose={() => setOpenMenu(null)}>
            {/* Overlay */}
            <Pressable style={{ flex: 1, backgroundColor: "rgba(0,0,0,0.5)" }} onPress={() => setOpenMenu(null)}>
                {/* Card */}
                <Pressable
                onPress={() => {}}
                style={ filter.optionsContainer }
                >
                {(openMenu === "stock" ? STOCK_OPTIONS : PRICE_OPTIONS).map((option) => (
                    <Pressable
                    key={option.key}
                    onPress={() => {
                        if (openMenu === "stock") {
                        onFiltersChange({ stock: option.key as StockFilter });
                        } else {
                        onFiltersChange({ price: option.key as PricePreset });
                        }
                        setOpenMenu(null);
                    }}
                    style={filter.option}
                    >
                    <Text style={{ color: "white" }}>{option.label}</Text>
                    </Pressable>

                ))}
                </Pressable>
            </Pressable>
            </Modal>
        </View>
        );
    }else return null;
}

const sortStyle = StyleSheet.create({

    container: {
        backgroundColor: '#8d8989',

        height: 60,
        width: 330,
        flexDirection: 'row',

        marginHorizontal: 25,
        marginVertical: 15,
        justifyContent: 'center',
        paddingVertical: 10,
        gap: 10,

        borderBottomLeftRadius: 30,
        borderBottomRightRadius: 30,
        borderTopRightRadius:30,

        animationDuration: '1',

    },
    elementContainer: {
        flexDirection: 'row'
    },
    filter: {
        backgroundColor: '#8d8989',
    },
    activeText: {
        color: '#ffffff',
        
        fontFamily: "PlusJakartaSans-Light",
        fontSize: 25,
    },
    inactiveText: {
        color: '#fff5f5b0',

        fontFamily: "PlusJakartaSans-Light",
        fontSize: 23,
    },
    arrow:{
        width: 30,
        height: 33,

    },
})

const filter = StyleSheet.create({

    container: {
        backgroundColor: '#8d8989',
        height: 60,
        width: 350,
        flexDirection: 'row',

        marginHorizontal: 25,
        marginVertical: 10,
        paddingVertical: 10,
        justifyContent: 'center',
        gap: 35,

        borderBottomLeftRadius: 30,
        borderBottomRightRadius: 30,
        borderTopLeftRadius: 30,

    },
    optionsContainer: {

        marginTop: 140, 
        marginHorizontal: 16, 
        borderRadius: 20, 
        borderWidth: 0.2,
        borderColor: '#FFFFFF',
        padding: 12, 
        backgroundColor: "#2A2A2A",
        alignItems: 'center',
    },
    option: {
        paddingVertical: 12, 
        paddingHorizontal: 12, 
        borderRadius: 12,

    },
    title: {
        fontFamily: "PlusJakartaSans-Light",
        fontSize: 18,
        color: '#FFFFFF'
    },
    text: {
        fontSize: 15,
        fontFamily: "PlusJakartaSans-Light",

        color: '#d9ff00'
    },

})