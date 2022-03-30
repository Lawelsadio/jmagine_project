import React, { useContext } from 'react';
import { createStackNavigator } from '@react-navigation/stack';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';

import AppForm from './components/AppForm';
import ImageUpload from './components/ImageUpload';
import UserProfile from './components/UserProfile';
import { useLogin } from './context/LoginProvider';
import DrawerNavigator from './DrawerNaviagtor';
import DrawerOpNavigator from './DrawerOpNavigator';
import Map from './components/Map';


const Stack = createBottomTabNavigator();

const StackNavigator = () => {
  return (

    <Stack.Navigator screenOptions={{ headerShown: false }}>
      <Stack.Screen component={AppForm} name='AppForm' />
      <Stack.Screen component={ImageUpload} name='ImageUpload' />
      <Stack.Screen component={UserProfile} name='UserProfile' />
    </Stack.Navigator>
  );
};

const MainNavigator = () => {
  const { isLoggedIn, role } = useLogin();
  if(isLoggedIn && role==="ROLE_ADMIN"){
    return <DrawerNavigator />
  }else if(isLoggedIn && role!=="ROLE_ADMIN"){
    return <DrawerOpNavigator />
  }else return <StackNavigator/>
};
export default MainNavigator;
