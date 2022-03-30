import React, {useState, useEffect} from 'react'
import axios from 'axios';
import {Card} from 'react-native-shadow-cards';
import MapView,{Marker,Polyline} from "react-native-maps";
import { usePosition } from 'use-position';
import * as Location from 'expo-location';

import { View, Text, Button ,Image ,StyleSheet} from 'react-native';
import { useLogin } from '../context/LoginProvider';


const Detail_poi = ({ navigation }) => {
  const [location, setLocation] = useState(null);
const [errorMsg, setErrorMsg] = useState(null);

const [region, setRegion] = useState({
    latitude: 43.668065 ,
    longitude: 7.216075,
    latitudeDelta: 0.001,
    longitudeDelta: 0.001,
  });
  const [coordds, setCoordds] = useState({});
  const {
    latitude,
    longitude,
    speed,
    timestamp,
    accuracy,
    heading,
    error,
  } = usePosition();
const {p,fn} = useLogin();
useEffect(() => {
  (async () => {
    let { status } = await Location.requestForegroundPermissionsAsync();
    if (status !== 'granted') {
      setErrorMsg('Permission to access location was denied');
      return;
    }

    let location = await Location.getCurrentPositionAsync({});
    setLocation(location.coords);

  })();
}, []);
let  myLocation = 'Waiting..';
if (errorMsg) {
   myLocation = errorMsg;
} else if (location) {
   myLocation = JSON.stringify(location);
   const Mylat = location.latitude
    const Mylng = location.longitude
console.log("mylocation",myLocation)
console.log("Mylat",Mylat)
console.log("Mylng",Mylng)


}

const Mylat = myLocation.latitude
const Mylng = myLocation.longitude
const niceRegion = {
  latitude: 43.69460105838741,
  longitude: 7.216075,
  latitudeDelta: 0.3,
  longitudeDelta: 0.3
};

console.log("p",p)
//console.log("fn",fn)

    return (
  
    <View style={styles.logotypeContainer}>
    <Card style={{padding: 10, margin: 10,}}>
                   <Text>Poi : {p.id}</Text>
     <Text>{p.title}</Text>  
     <Image
           style={styles.tinyLogo}
           source={{
             uri: `http://10.192.27.79:8884/stockage/${fn.fileName}`
           }}
         />
    
    
            <Button
             onPress={()=>{       
              //useflullParcour(parcour.id)
            //  navigation.navigate('Map')
              }}
             title="Learn More"
             color="#841584"
             accessibilityLabel="Learn more about this purple button"
           />
         </Card>
         <Card style={{padding: 170, margin: 10,}}>

         <MapView    
    style={styles.map}
    initialRegion={niceRegion}
    onRegionChangeComplete={(region) => setRegion(region)}
    
  >
      


 <Marker coordinate={{latitude: p.lat, longitude: p.lng}}
    pinColor="red"
    resizeMode={'cover'}
    title={p.title} 
    //image={require("../../assets/map_marker_sh.png")} //uses relative file path. 
  />
   <Marker coordinate={{latitude: Mylat, longitude: Mylng}}
    pinColor="green"
    resizeMode={'cover'}
    title='ma postion '  
    //image={require("../../assets/map_marker_sh.png")} //uses relative file path. 
  />
{
     <Polyline
     coordinates={[
			{ latitude: p.lat, longitude: p.lng },
			{ latitude: Mylat, longitude: Mylng }
		]}        strokeColor={"#000"}
        strokeWidth={3}
        lineDashPattern={[1]}
      />}

  </MapView>
  </Card>
  <Text style={styles.text}>Current latitude: {region.latitude}</Text>
      <Text style={styles.text}>Current longitude: {region.longitude}</Text>
  
     </View> 
          );
  
}
export default Detail_poi;


const utils = {
  colors: {primaryColor: '#af0e66'},
  dimensions: {defaultPadding: 12},
  fonts: {largeFontSize: 18, mediumFontSize: 16, smallFontSize: 12},
};


const styles = {
  innerContainer: {
    marginBottom: 32,
  },
  logotypeContainer: {
    alignItems: 'center',
  },
  logotype: {
    maxWidth: 280,
    maxHeight: 100,
    resizeMode: 'contain',
    alignItems: 'center',
  },
  containerStyle: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
    backgroundColor: '#f6f6f6',
  },
  input: {
    height: 50,
    padding: 12,
    backgroundColor: 'white',
    borderRadius: 6,
    shadowColor: '#000',
    shadowOffset: {
      width: 0,
      height: 4,
    },
    shadowOpacity: 0.05,
    shadowRadius: 4,
    marginBottom: utils.dimensions.defaultPadding,
  },
  loginButton: {
    borderColor: utils.colors.primaryColor,
    borderWidth: 2,
    padding: utils.dimensions.defaultPadding,
    alignItems: 'center',
    justifyContent: 'center',
    borderRadius: 6,
  },
  loginButtonText: {
    color: utils.colors.primaryColor,
    fontSize: utils.fonts.mediumFontSize,
    fontWeight: 'bold',
  },
  errorMessageContainerStyle: {
    marginBottom: 8,
    backgroundColor: '#fee8e6',
    padding: 8,
    borderRadius: 4,
  },
  errorMessageTextStyle: {
    color: '#db2828',
    textAlign: 'center',
    fontSize: 12,
  },
  tinyLogo: {
    width: 332,
    height: 200,
    justifyContent: 'center'
  },
  logo: {
    width: 66,
    height: 58,
  },
  container: {
    ...StyleSheet.absoluteFillObject,
    flex: 1, //the container will fill the whole screen.
    justifyContent: "flex-end",
    alignItems: "center",
  },
  map: {
    ...StyleSheet.absoluteFillObject,
  },
 
};
