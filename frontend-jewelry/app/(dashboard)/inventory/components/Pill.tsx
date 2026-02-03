import { View, Pressable, Image, Text, StyleSheet,ImageSourcePropType, Modal } from 'react-native'
import { useState } from 'react'
import { preventAutoHideAsync } from 'expo-router/build/utils/splash';

export type ActivePill = 'sort' | 'filter' | null;

type PillProps =  {
    menu: ActivePill;
};

const  Arrows: Record<string, ImageSourcePropType> = {
    asc: require( '../../../../assets/icons/actions/arrow_upward.png' ),
    desc: require( '../../../../assets/icons/actions/arrow_downward.png' ),
};

export default function OverlayPill ({ menu }: PillProps) {

    //Sort pill
    type SortKey = 'alpha' | 'price' | 'stock';
    type SortDir = 'asc' | 'desc';

    type SortState = { key: SortKey, dir: SortDir };

    const [ currentSort, setCurrentSort ] = useState<SortState>({ key: 'alpha', dir: 'asc' })

    function changeSorting( sorting: SortKey) {

        setCurrentSort(prev => {
            if( !prev || prev.key !== sorting ) return { key: sorting, dir: 'asc'};
        return { key: sorting, dir: prev.dir === 'asc' ? 'desc' : 'asc'};
        }); 
    }

    //Filter pill
    type StockFilter = 'any' | 'in_stock' | 'out_of_stock';
    type PricePreset = 'any' | '1-49' | '50-99' | '100-149' | '150-299';

    type FilterState = {
        stock: StockFilter;
        price: PricePreset;
    };

    const [ filters, setFilters ] = useState<FilterState>({ stock: 'any', price: 'any'});

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
            <View style={ sort.container } > 
                <View>
                    <Pressable onPress={( ) =>{changeSorting('alpha') } } style={sort.elementContainer} > 
                        <Text style={ currentSort?.key === 'alpha' ? sort.activeText : sort.inactiveText  }  > A - Z </Text> 
                        { currentSort.key === 'alpha' ? <Image style ={ sort.arrow } source={ Arrows[currentSort.dir] }/> : null }
                    </Pressable>
                </View>

                <Pressable onPress={( ) =>{changeSorting('price') }} style={sort.elementContainer} > 
                    <Text style={ currentSort?.key === 'price' ? sort.activeText : sort.inactiveText  } > Precio </Text> 
                    { currentSort.key === 'price' ? <Image style ={ sort.arrow } source={ Arrows[currentSort.dir] }/> : null }
                </Pressable>

                <Pressable onPress={( ) =>{changeSorting('stock') }} style={sort.elementContainer} > 
                    <Text style={ currentSort?.key === 'stock' ? sort.activeText : sort.inactiveText  } > Cantidad </Text> 
                    { currentSort.key === 'stock' ? <Image style ={ sort.arrow } source={ Arrows[currentSort.dir] }/> : null }
                </Pressable>
            </View>
        );
    }else if( menu === 'filter' ){{/*Pill for filtering*/}
        const currentMenu =
            openMenu === 'stock' ? STOCK_OPTIONS :
            openMenu === 'price' ? PRICE_OPTIONS :
            [];

        return (
            <View style={filter.container} >
                <Pressable onPress={ () => setOpenMenu('stock') } > 
                    <Text> Filtrar por cantidad:  </Text>
                </Pressable>

                <Pressable onPress={ () => setOpenMenu('price') } > 
                    <Text> Filtrar por precio:  </Text>
                </Pressable>
            </View>
        );
    }else return null;
}

const sort = StyleSheet.create({

    container: {
        backgroundColor: '#8d8989',

        height: 40,
        width: 330,
        flexDirection: 'row',

        marginHorizontal: 25,
        marginVertical: 15,
        justifyContent: 'center',
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

    },
})