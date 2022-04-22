import React, { useState, useEffect } from 'react';
import { Text, View, StyleSheet, Button } from 'react-native';
import { useLogin } from '../context/LoginProvider';
import { BarCodeScanner } from 'expo-barcode-scanner';
import APIKit from '../shared/APIKit';

const QR_code = ({ navigation }) => {
  const [hasPermission, setHasPermission] = useState(null);
  const [scanned, setScanned] = useState(false);
  const { setP,setFn,fullparcour } = useLogin();

  useEffect(() => {
    (async () => {
      const { status } = await BarCodeScanner.requestPermissionsAsync();
      setHasPermission(status === 'granted');
    })();
  }, []);

  const UsePoi = (data) => {
      let dt = data
      const point = data.slice(-2);
      console.log("data slice",point)
      APIKit.get(`${dt}`).then((res) => {
        let str = res.data.backgroundPic;
        let fileName = str.substr(31);

    for(let poi in fullparcour.pois){
      if(fullparcour.pois[poi].id == point){
        fullparcour.pois[poi].isGeolocEnabled =true
        break;

      }
    }

        setFn({fileName:fileName})

        setP(res.data)
        
       
        });

        navigation.navigate('Detail_poi')
};


  const handleBarCodeScanned = ({ type, data }) => {
    setScanned(true);
        UsePoi(data)
        console.log("la data sur android",data)
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
