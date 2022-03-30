import React, {useState, useEffect} from 'react'
import {Platform, StyleSheet, Text, View } from "react-native";
import { useLogin } from '../context/LoginProvider';
import MapView,{Marker,Polyline} from "react-native-maps";
import { usePosition } from 'use-position';
import * as Location from 'expo-location';


import {decode} from "@mapbox/polyline";

export default function Map(navigation) {
const { setIsLoggedIn, profile,role, setRole ,fullparcour, setfullParcour} = useLogin();
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
  const parcous =  [];
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


  }


fullparcour.forEach((element) => {    
   parcous.push({latitude:element.lat,longitude:element.lng})
 });


const Coordonee = fullparcour.map((poi)=>{

   // let str = poi.backgroundPic;
  //let fileName = str.substr(31);
  
    return <Marker coordinate={{latitude:poi.lat,longitude:poi.lng}}
    pinColor="green"
    resizeMode={'cover'}
    key={poi.id}
    title={poi.title}   
    //image={require("../../assets/map_marker_sh.png")} //uses relative file path. 
     />
    });


  const niceRegion = {
    latitude: 43.69460105838741,
    longitude: 7.216075,
    latitudeDelta: 0.3,
    longitudeDelta: 0.3
  };

  return (
    <View style={styles.container}>
        <MapView
    
    style={styles.map}
    initialRegion={niceRegion}
    onRegionChangeComplete={(region) => setRegion(region)}
    
  >
      
 {Coordonee}

 {/*<Marker coordinate={{latitude: location.latitude, longitude: location.longitude}}
    pinColor="green"
    resizeMode={'cover'}
    key={poi.id}
    title='ma postion '  
    //image={require("../../assets/map_marker_sh.png")} //uses relative file path. 
  />*/}

     <Polyline
        coordinates={parcous} //specify our coordinates
        strokeColor={"#000"}
        strokeWidth={3}
        lineDashPattern={[1]}
      />

  </MapView>
    
      {/*Display user's current region:*/}
      <Text style={styles.text}>Current latitude: {region.latitude}</Text>
      <Text style={styles.text}>Current longitude: {region.longitude}</Text>
    </View>
  );

}
//create our styling code:
const styles = StyleSheet.create({
  container: {
    ...StyleSheet.absoluteFillObject,
    flex: 1, //the container will fill the whole screen.
    justifyContent: "flex-end",
    alignItems: "center",
  },
  map: {
    ...StyleSheet.absoluteFillObject,
  },
});
