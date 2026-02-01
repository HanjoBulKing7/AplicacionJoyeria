import { Image, Text, Pressable, StyleSheet, View } from 'react-native'
import { router  } from 'expo-router';
import { CardProps, Icons } from '@/domain/inventory';

export default function CategoryCard( props: CardProps ) {

  return(

      <Pressable style={ CatCardSty.cardContainer }
        onPress= {()=> 
          router.push({
          pathname: "/(dashboard)/inventory/[category]",
          params: { category : props.label}
          })
        }
      >
        <View  style={CatCardSty.leftContainer}>
          <Image source={Icons[props.categoryKey]}
            style= { CatCardSty.categoryIcon }
          />
            <Text style = { CatCardSty.cardName } >{props.label}</Text>
        </View>

        <View  style={CatCardSty.rightContainer}>
          <Text style = { CatCardSty.cardStock } >{ props.stock}</Text>
          <Image source={require('../../../../assets/icons/chevron.png')}
            style= { CatCardSty.chevronIcon }/>
        </View>

      </Pressable>
  );
}


const CatCardSty = StyleSheet.create( {

  cardContainer: {  
    backgroundColor: '#ffffff3d',
    flexDirection: 'row',

    height: 70,
    width: 280,
    borderRadius: 14,
    
    paddingHorizontal: 16,
  },
    leftContainer: {
    flex: 1,
    flexDirection: 'row',
    alignItems: "center",
    gap: 12,
  },
  categoryIcon: {
    height: 30,
    width: 30,
    resizeMode: 'contain',
  },
  cardName: {
    fontFamily: "PlusJakartaSans-Regular",
    color: '#ffffff',
    fontSize: 20,
  }, 
  rightContainer: {
    flexDirection: 'row',
    alignItems: "center",
    gap: 10,
    minWidth: 60,
  },
  cardStock: {
    fontFamily: "PlusJakartaSans-Light",
    fontSize: 25,
    color: '#ffffff',
  },
  chevronIcon: {
    height: 30,
    width: 30,
    paddingTop: 13,
    resizeMode: 'contain',
  },

});