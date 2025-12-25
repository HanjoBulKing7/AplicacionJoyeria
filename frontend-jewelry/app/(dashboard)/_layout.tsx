import { Stack } from "expo-router";

export default function DashboardLayout() {
  return (
    <Stack
      screenOptions={{
        headerShown: false,
      }}
    >
      <Stack.Screen name="index" />
      <Stack.Screen name="ventas" />
      <Stack.Screen name="clientes" />
      <Stack.Screen name="abonos" />
      <Stack.Screen name="inventario" />
    </Stack>
  );
}
