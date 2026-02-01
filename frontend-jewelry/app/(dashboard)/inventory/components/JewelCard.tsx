import { View, Pressable, Text, StyleSheet, Image } from 'react-native'

export default function JewelCard ()  {

    return(
        <Pressable style={ s.card }>
        
            <Image 
                style={ s.img }
                source={ require('../../../../assets/images/anillotest.jpg') }
            />
            
            <View style={ s.right }> 
                <Text style={ s.name } >Anillo de plata gótico</Text>

                <View style={ s.row  } >
                    <Text style={ s.price } >$1,200</Text>

                    <Text style={ s.stock } >Cantidad: 30</Text>
                </View>
            </View>
        </Pressable>
    );
}

const s = StyleSheet.create( {

    card: {
        height: 100,
        width: 370,
        backgroundColor: "#2a2828",

        borderRadius: 30,
        borderWidth: 0.3,
        borderColor: '#FFF',

        flexDirection: 'row',

    },
    img: {

        height: '100%',
        width: 80,


        borderTopLeftRadius: 30,
        borderBottomLeftRadius: 30,

        resizeMode: 'cover',
    },
    right: {
        flex: 1,
        justifyContent: 'space-between',
        paddingHorizontal: 20,

    },
    name: {
        color: '#FFF',
        fontSize: 25,
        fontWeight: 600,
    
    },
    row:{
        alignItems: 'center',
        justifyContent: 'space-between',
        flexDirection: 'row',
    },
    price: {
        color: '#FFF',
        fontSize: 25,
    },
    stock: {
        color: '#FFF',
        fontSize: 20,
    }
});