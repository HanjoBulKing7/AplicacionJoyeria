import { Text, View, StyleSheet } from "react-native";

export default function Index() {
  return (
    <View style={styles.mainContainer}>
      <Text style={styles.welcomeTitle}>{"     "}Hola{"\n"}Bienvenida!</Text>
    </View>
  );
}
const styles = StyleSheet.create({
    mainContainer: {
      backgroundColor: "#790604",
      flex: 1,
      justifyContent: "flex-start",
      alignItems: "center",
    },
    welcomeTitle:{
      fontFamily: 'DancingScript_400Regular',
      color: "#ACB124",
      fontSize: 70,
      paddingTop: 90,
      fontWeight: 100
    }
  });
