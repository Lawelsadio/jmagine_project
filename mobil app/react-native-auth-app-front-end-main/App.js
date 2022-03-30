import React from 'react';
import { NavigationContainer } from '@react-navigation/native';

import MainNavigator from './app/MainNavigator';
import LoginProvider from './app/context/LoginProvider';
import Map from './app/components/Map';
import QR_code from './app/components/QR_code'
import Nfc_Reader from './app/components/Nfc_Reader';
import Poi from './app/components/Poi';
import Test from './app/ParcourNavigateur';

export default function App() {
  return (
    <LoginProvider>
      <NavigationContainer>
       {/* <Map/> */}
       {/* <MainNavigator/> */}
        {/* <QR_code/> */}
        {/* <Nfc_Reader/> */}
        {/* <Poi/> */}

       <MainNavigator/>
      </NavigationContainer>
    </LoginProvider>
  );
}
