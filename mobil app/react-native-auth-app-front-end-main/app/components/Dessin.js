import React, { useContext } from 'react';
import { createStackNavigator } from '@react-navigation/stack';
import { Text, StatusBar, Button, StyleSheet } from 'react-native';

import Screen1 from './Ecran'
import Screen2 from './Ecran'


const Stack = createStackNavigator();

const Dessin = () => {
  return (
    <Stack.Navigator screenOptions={{ headerShown: false }}>
      <Stack.Screen component={Screen1} name='Screen1' />
      <Stack.Screen component={Screen2} name='Screen2' />
    </Stack.Navigator>
  );
};
export default Dessin;
    const styles = StyleSheet.create({
      container: { flex: 1, justifyContent: 'center', alignItems: 'center' },
    });