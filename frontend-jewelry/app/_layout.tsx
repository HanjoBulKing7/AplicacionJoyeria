import { HeaderShownContext } from "@react-navigation/elements";
import { Stack } from "expo-router";
import { useFonts, DancingScript_400Regular, DancingScript_700Bold } from '@expo-google-fonts/dancing-script';

export default function RootLayout() {
    const [fontsLoaded] = useFonts({
    DancingScript_400Regular,
    DancingScript_700Bold,
    "PlusJakartaSans-Light": require("../assets/fonts/PlusJakartaSans-Light.ttf"),
    "PlusJakartaSans-Regular": require("../assets/fonts/PlusJakartaSans-Regular.ttf"),
  });
    if (!fontsLoaded) {
    return null;   
  }
  return( 
    <Stack
    screenOptions={{
          headerShown: false,
    }}
    />
  );
}
