import { Stack } from "expo-router";

export default function DashboardLayout() {
  return (
    <Stack
      screenOptions={{
        headerShown: false,
      }}
    >
      <Stack.Screen name="sales" />
      <Stack.Screen name="customers" />
      <Stack.Screen name="installments" />
    </Stack>
  );
}
