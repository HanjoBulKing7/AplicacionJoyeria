import { View, Text, Image, TextInput, StyleSheet, Pressable } from "react-native";
import { router, useLocalSearchParams } from 'expo-router'
import { useState, useEffect } from 'react'
import { SafeAreaView } from "react-native-safe-area-context";
import  JewelCard from './components/JewelCard'
import OverlayPill from './components/Pill'
import { StockFilter, PricePreset , SortKey , SortDir} from '../../../domain/inventory'
import { InventoryRepository, ActiveOrder , ActiveFilter  } from "@/data/repositories/InventoryRepository";
import { JewelRow } from "@/domain/DomainSQLite";


export type ActivePill = "sort" | "filter" | null;
export type SortState = { key: SortKey; dir: SortDir };
export type FilterState = {
  stock: StockFilter;
  price: PricePreset;
};
//Props fot the pill defined in the parent component 
export type PillProps = {
  menu: ActivePill;

  sort: SortState;
  onSortChange: (next: SortState) => void;

  filters: FilterState;
  onFiltersChange: (patch: Partial<FilterState>) => void;
};


export default function CategoryScreen() {

  const { category } = useLocalSearchParams <{ category: string }>();//Use the params sent by URL

  const [ inputText, setInputText ] = useState('')

  const [ activePill, setActivePill ] = useState<ActivePill>(null);
  
  const [ currentSort, setCurrentSort ] = useState<SortState>({ key: 'alpha', dir: 'asc' })

  const [ filters, setFilters ] = useState<FilterState>({ stock: 'any', price: 'any'});

  const [ jewelList, setJewelList ] = useState<JewelRow[]>( [] )

  //Si el request puede completar después del unmount, uso un flag o aborto para evitar 
  // setState en unmounted.”
  useEffect( () => {

    let cancelled = false;//If the user laves the screen cancel setState( jewelList)
    (
      async ( ) => {
        //Mapping the states from the parent component for the repository contract 
        const mapSort: ActiveOrder = {
          orderType: currentSort.key,
          orderDir: currentSort.dir,
        };
        const mapFilters: ActiveFilter = {
          activeStock: filters.stock,
          activePrice: filters.price,
        };

        const jewelList = await InventoryRepository.listItems( mapSort , mapFilters );
        if( cancelled ) return;

        setJewelList( jewelList );

      }) ();
    
      return () => { cancelled = true };
      //Include true values that affect the query to avoid unnecesary changes
  }, [ currentSort.key, currentSort.dir, filters.stock, filters.price ])


  return (
    <SafeAreaView style={categoryScreen.screen}>
      {/*Header container*/}
      <View  style={ categoryScreen.headerContainer }>

        <View style= { categoryScreen.leftCatContainer }>
          <Pressable
            onPress={ ( ) => router.replace("/(dashboard)/inventory")}
          >
            <Image source={require('../../../assets/icons/actions/back.png')}  style= { categoryScreen.icon} />
          </Pressable>
          <Text style={ categoryScreen.categoryTitle }>{category}</Text>
        </View>

        <View style={ categoryScreen.middleContainer } > 
          <Image source={ require('../../../assets/icons/actions/search.png')} style= { categoryScreen.searchIcon } />
          <TextInput
              style={ categoryScreen.inputText }
              placeholder={`${category} de oro`}
              placeholderTextColor="#8d8d8d"
              onChangeText={setInputText}
              value={ inputText }
            />
            <Pressable
              onPress={ ()=> setInputText('')}
            >
              <Image 
                source={ require('../../../assets/icons/actions/close.png') } 
                style={ categoryScreen.closeIcon }
              />
            </Pressable>
        </View>

        <Pressable style= { categoryScreen.rightContainer}>
          <Image source={ require('../../../assets/icons/actions/add.png')} style= { categoryScreen.addIcon} />
        </Pressable>
      </View>

      {/*More actions container*/}
      <View style={ categoryScreen.actions } >
        <Pressable 
          onPress={ ( ) =>( setActivePill( current => (current ==='sort' ? null : 'sort'))  ) } > 
          <Image source={ require('../../../assets/icons/actions/sort.png') } style={ categoryScreen.sort } /> 
        </Pressable>

        <Pressable 
          onPress={ ( ) =>(  setActivePill( current =>( current === 'filter' ? null : 'filter')))} > 
          <Image source={ require('../../../assets/icons/actions/filter.png')} style={ categoryScreen.filter } /> 
        </Pressable>
      </View>
      
      {/*Render the overlay corresponding pill*/}
      <OverlayPill
        menu={activePill}
        sort={currentSort}
        onSortChange={ setCurrentSort }
        filters={filters}
        onFiltersChange={ 
          (patch) => setFilters(prev => ({ ...prev, ...patch }))

         }
      />

      {/*List of cards */}
      <View style= { categoryScreen.jewelsContainer}>
        {jewelList.map( singleJewel =>
          <JewelCard 
            key={ singleJewel.id }
            name={ singleJewel.name }
            price={ singleJewel.price }
            stock={ singleJewel.stock }
          />
        )
        }
      </View>
    </SafeAreaView>
  );
}

const categoryScreen = StyleSheet.create({
  screen:{
    backgroundColor: '#191717',
    flex: 1,
  },
  headerContainer: {
    flexDirection: 'row',
    alignItems: 'center',
    paddingTop: 10,
    paddingHorizontal: 12, 
    gap: 12,
    paddingBottom: 40,
  },
  leftCatContainer: {
    flexDirection: 'row',
    alignItems: 'center',
  },
  categoryTitle: {
    color: '#FFF',
    fontSize: 22,
  },
  middleContainer: {
    height: 48,
    alignItems: 'center',
    paddingHorizontal: 12,
    flexDirection: 'row',
    backgroundColor: '#5a5a5a',
    borderRadius: 999,
    width: 220,
    maxWidth: 250,
    overflow: 'hidden',
  },
  searchIcon: {
    width: 34,
    height: 34,
    resizeMode: 'contain',
  },
  inputText: {
    flex: 1,
    color: '#eaeaea',
    fontSize: 18,
    overflow: 'hidden',
  },
  closeIcon: {
    width: 22,
    height: 22,

  },
  rightContainer: {
    width: 48,
    height: 48,
    borderRadius: 50,
    borderWidth: 0.5,
    borderColor: '#ddd9d9',
    alignItems: 'center',
    justifyContent: 'center', 
  },
  addIcon: {
    height: 30,
    width: 30,
    resizeMode: 'contain',
  },
  icon: {
    height: 50,
    width: 30,
  },actions:{
    flexDirection: 'row',
    justifyContent: 'space-between',
    paddingBottom: 20,
    paddingHorizontal: 15,
  },
  sort: {
    height: 40,
    width: 40,
  },
  filter: {
    height: 40,
    width: 40,
  },
  jewelsContainer:{

    alignItems: 'center',

  }
})