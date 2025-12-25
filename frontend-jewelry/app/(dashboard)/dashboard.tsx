import { View, Text, Pressable, StyleSheet, Image } from "react-native";
import { router } from "expo-router";

export default function Dashboard() {
  return (
    <View style={styles.dashboardContainer}>

      <Text style={styles.dashboardTitle} >Joyería de Karol </Text>
        <View style={styles.grid}>
          {/* Nueva venta */}
          <Pressable onPress={() => router.push("/(dashboard)/sales")} style={styles.cardContainer}>
            <Text style={styles.cardText} >Nueva{"\n"} venta</Text>
            <Image 
              style={styles.cardIcon}
              source={require('../../assets/icons/cart.png')} 
              />
          </Pressable>

          {/* Clientes */}
          <Pressable onPress={() => router.push("/(dashboard)/customers")} style={styles.cardContainer}>
            <Text style={styles.cardText} > Clientes{"\n"}</Text>
                        <Image 
              style={styles.cardIcon}
              source={require('../../assets/icons/customers.png')} 
              />
          </Pressable>

          {/* Abonos */}
          <Pressable onPress={() => router.push("/(dashboard)/installments")} style={styles.cardContainer}>
            <Text style={styles.cardText} >Abonos{"\n"}</Text>
              <Image 
              style={styles.cardIcon}
              source={require('../../assets/icons/installments.png')} 
              />
          </Pressable>

          {/* Inventario */}
          <Pressable onPress={() => router.push("/(dashboard)/inventory")} style={styles.cardContainer}>
            <Text style={styles.cardText} >Inventario{"\n"}</Text>
              <Image 
              style={styles.cardIcon}
              source={require('../../assets/icons/inventory.png')} 
              />
          </Pressable>
        </View>
    </View>
  );
}

const styles = StyleSheet.create({
  dashboardContainer:{
    backgroundColor: "#000000",
    height: "100%",
  },
  dashboardTitle:{
    color: "#f6ff00ff",  
    fontSize: 45,
    fontFamily: "DancingScript_700Bold",
    marginTop: 50,
    textAlign: "center"
  },
  grid:{
    marginTop: 40,
    marginLeft: 25,
    width: "90%",
    height: "70%",
    flexWrap: "wrap",
    flexDirection: "row",
    justifyContent: "space-between"
  },
  cardContainer: {
    borderWidth: 1.7,
    marginVertical: 6,
    borderColor: "#ACB124",
    borderRadius: 25,
    backgroundColor: "#fff6f615",
    width: "49%",
    height: 210,
  },
  cardText: {
    marginBottom: 20,
    color: "#FFE6E6",
    fontSize: 30,
    fontFamily: "DancingScript_700Bold",
    textAlign: "center"
  },
  cardIcon:{
    height: 80,
    width: 90,
    marginLeft: 37
  }
});