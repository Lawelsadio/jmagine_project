import React, { useState, useEffect } from 'react';
import { Text, View, StyleSheet, Button ,Image, ScrollView,SafeAreaView,} from 'react-native';
import { useLogin } from '../context/LoginProvider';

import { BarCodeScanner } from 'expo-barcode-scanner';
import {Card} from 'react-native-shadow-cards';

import axios from 'axios';


const QR_code = ({ navigation }) => {
  const [hasPermission, setHasPermission] = useState(null);
  const [scanned, setScanned] = useState(false);
  const { setIsLoggedIn, setProfile, role, setRole,p,setP,fn,setFn } = useLogin();



  useEffect(() => {
    (async () => {
      const { status } = await BarCodeScanner.requestPermissionsAsync();
      setHasPermission(status === 'granted');
    })();
  }, []);

  const UsePoi = (data) => {
      let dt = data
    axios.get(`http://10.192.27.79:8886${dt}`).then((res) => {
        let str = res.data.backgroundPic;
        let fileName = str.substr(31);
        console.log("filname",fileName)
        setFn({fileName:fileName})
        setP(res.data)
        
       
        });
        
        console.log("setFn",fn)
        navigation.navigate('QR_code_detail')
       

};


  const handleBarCodeScanned = ({ type, data }) => {
    setScanned(true);
        UsePoi(data)
    

  };

  if (hasPermission === null) {
    return <Text>Requesting for camera permission</Text>;
  }
  if (hasPermission === false) {
    return <Text>No access to camera</Text>;
  }

  return (
    <View style={styles.container}>
      <BarCodeScanner
        onBarCodeScanned={scanned ? undefined : handleBarCodeScanned}
        style={StyleSheet.absoluteFillObject}
      />
      {scanned && <Button title={'Tap to Scan Again'} onPress={() => setScanned(false)} />}
   
      
    </View>
  );
};
export default QR_code;

const styles = StyleSheet.create({
  container: {
    flex: 1,
    flexDirection: 'column',
    justifyContent: 'center',
  },
});
