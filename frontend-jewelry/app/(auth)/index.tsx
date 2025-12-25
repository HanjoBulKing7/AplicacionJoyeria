import { Text, View, StyleSheet, TextInput,Alert, Pressable, Image, Vibration } from "react-native";
import { useFonts, DancingScript_400Regular, DancingScript_700Bold } from '@expo-google-fonts/dancing-script';
import { LinearGradient } from 'expo-linear-gradient'
import { router } from "expo-router"
import { useState } from "react"
import React from "react";

const CORRECT_PIN = "1234";

export default function Index() {

  const [pin, setPin ] = useState("");//Hook for tthe PIN handling

   const handleSubmit = () => {

      if(pin != CORRECT_PIN ){
        Vibration.vibrate(2000);
        Alert.alert("PIN incorrecto", "Verifica el PIN para poder acceder a la aplicación")
        return;
      }
      router.replace("/dashboard")
   }


  return (
    <LinearGradient
      colors={['#000000', '#1f1d1dff']}
      start={{ x: 0, y: 0 }}
      end={{ x: 1, y: 1 }}
      style={{flex: 1}}
    
    >
      <View style={styles.mainContainer}    >
        {/*Título grande de bienvenida*/}
        <Text style={styles.welcomeTitle}>{"     "}Hola,{"\n"}Bienvenida</Text>

        <Image
          style={styles.mainIcon}
          source={require('../../assets/icons/joyeria.png')}
          />
        {/*Envoltorio de los componentes*/}
        <View   
          style={styles.formContainer}
        >
          <Text style={styles.formTitle}>Ingresa tu PIN</Text>
          <TextInput
            style={styles.input}
            value={pin}
            onChangeText={setPin}
            keyboardType="numeric"
            maxLength={4}
            placeholder="****"
            placeholderTextColor="#F7D9C4"
          />

          <Pressable style={styles.button} onPress={handleSubmit}>
            <Text style={styles.buttonText}>{"Acceder"}</Text>
          </Pressable>
        </View>
      </View>
    </LinearGradient>
  );
}

//Styles 
const styles = StyleSheet.create({
    mainContainer: {
      flex: 1,
      justifyContent: "flex-start",
      alignItems: "center",
    },
    welcomeTitle:{
      fontFamily: 'DancingScript_400Regular',
      color: "#dde255ff",
      fontSize: 55,
      marginTop: 65,
      fontWeight: 100
    },
    mainIcon:{
      marginTop: 40,
      marginBottom: 30,
      height: 80,
      width: 75
    },
    //Form container
    formContainer:{
      backgroundColor: "#fff6f615",
      opacity: 20,
      borderRadius: 10,
      borderWidth: 0.3,
      borderColor: "#ACB124",
      alignItems: "center",
      width: "65%",
      height: "40%"
    },
    formTitle:{
      marginTop: 10,
      marginBottom: 150,
      fontFamily: 'Sans Serif',
      color: "#f6ff00ff",
      fontSize: 27,
      textAlign: "center"
    },
    input:{
      borderWidth: 1.4,
      borderColor: "#ACB124",
      borderRadius: 15,
      textAlign: "center",
      color: "white",
      width: "75%"
    },
    button: {
      marginTop: 30,
      paddingVertical: 4,
      backgroundColor: "#030303f2",
      borderWidth: 1,
      borderColor: "#ACB124",
      borderRadius: 8,
      width: "55%",
      height: "11%"
    },
    buttonText:{
      fontFamily: 'Sans Serif',
      fontSize: 20,
      color: "#f6ff00ff",
      textAlign: "center",
      fontWeight: "bold"
    }
  });
