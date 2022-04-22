import React, {useState, useEffect,useRef} from 'react'
import {Card} from 'react-native-shadow-cards';
import FormSelectorBtn from './FormSelectorBtn';
import MapView,{Marker} from "react-native-maps";
import * as Location from 'expo-location';
const { width } = Dimensions.get('window');

import { View, StyleSheet, Text, Image,SafeAreaView ,Animated,Dimensions } from 'react-native';
import { useLogin } from '../context/LoginProvider';
import MapViewDirections from 'react-native-maps-directions';

import FormContainer from './FormContainer';
const Parcours = ({ navigation }) => {
const [duration, setDuration] = useState('');
const [distance, setdistance] = useState('');
  const KEY = "your apikey here";

const {fullparcour,p,setLocation} = useLogin();
console.log("je cherche le parcours dans poi",fullparcour)
const animation = useRef(new Animated.Value(0)).current;
const loginColorInterpolate = animation.interpolate({
  inputRange: [0, width],
  outputRange: ['rgba(27,27,51,1)', 'rgba(27,27,51,0.4)'],
});

const strP = fullparcour.backgroundPic;
const strPfileName = strP.substr(31);
//console.log("fileName",fileName)

const [errorMsg, setErrorMsg] = useState(null);
  useEffect(() => {
    (async () => {
      let { status } = await Location.requestForegroundPermissionsAsync();
      if (status !== 'granted') {
        setErrorMsg('Permission to access location was denied');
        return;
      }

      let location = await Location.getCurrentPositionAsync({});
      setLocation(location);
    })();
  }, []);
  
const [region, setRegion] = useState({
  latitude: 43.668065 ,
  longitude: 7.216075,
  latitudeDelta: 0.001,
  longitudeDelta: 0.001,
});
const parcous =  [];
  
  fullparcour.pois.map((element) => { 
    if (element.isGeolocEnabled === false){
      console.log("si valide isGeolocEnabled", element.isGeolocEnabled)
      parcous.push({latitude:element.lat,longitude:element.lng,isGeolocEnabled:element.isGeolocEnabled})
    }
  });
const Coordonee = fullparcour.pois.map((poi)=>{

var x= " ";
 if(poi.isGeolocEnabled){
    x = "red"
 }else x = "green"
 

   return <Marker coordinate={{latitude:poi.lat,longitude:poi.lng}}
   
   pinColor={x}
   resizeMode={'cover'}
   key={poi.id}
   title={poi.title}   
    />
   });

 const niceRegion = {
   latitude: 43.69460105838741,
   longitude: 7.216075,
   latitudeDelta: 0.3,
   longitudeDelta: 0.3
 };

     const dst = parcous.length - 1
     const origin = parcous[0]
     const destination = parcous[dst]
  return (
        
    <FormContainer>
        <SafeAreaView>
          
          <Card style={{padding: 5, margin: 0,borderRadius:0,borderTopLeftRadius: 5,
      borderTopRightRadius: 5,}}>
                   <Text>point d'intert : {fullparcour.title}</Text>
     <Text>{fullparcour.id}</Text>
     <Text>{fullparcour.title}</Text>
   
     <Image
           style={styles.tinyLogo}
           source={{
             uri: `http://10.192.27.79:8884/stockage/${strPfileName}`
           }}
         />
         <Text>date decreation du  {p.title}</Text>
                   <Text> {p.address}</Text>
                   <Text > Lorem Ipsum est simplement un faux texte de l'industrie de
                      l'impression et de la composition. Le Lorem Ipsum es  électronique, restant essentiellement inchangé. Il a été popularisé 
                       Ipsum.</Text>

         </Card>
         <Card style={{padding: 130, margin: 0,padding:130,borderRadius: 0,}}>

         <MapView
    
    style={styles.map}
    initialRegion={niceRegion}
    onRegionChangeComplete={(region) => setRegion(region)}

    
  >
    
      
 {Coordonee}

<MapViewDirections
            origin={origin}
            destination={destination}
            apikey={KEY}
            strokeWidth={3}
            waypoints={parcous}
            mode="WALKING"
            strokeColor="hotpink"
            onStart={(params) => {
              console.log(`Started routing between "${params.origin}" and "${params.destination}"`);
            }}
            onReady={result => {
              setdistance(result.distance)
              setDuration(result.duration)
              console.log(`Distance: ${result.distance} km`)
              console.log(`Duration: ${result.duration} min.`)
           
            }}
            onError={(errorMessage) => {
            }}
          />

  </MapView>

  
</Card>
<View
        style={{
          flexDirection: 'row',
          paddingHorizontal: 0,
          marginBottom: 1,
          margin:1,
        }}
      >
        <FormSelectorBtn
          style={styles.borderLeft}
          backgroundColor={loginColorInterpolate}
          title='Activer un  Parcours'
          onPress={()=>{       
            navigation.navigate('QR_code')   

                      }}
        />
        <FormSelectorBtn
          style={styles.borderRight}
          backgroundColor={loginColorInterpolate}
          title='Commencer le parcours'
          onPress={() => 
            navigation.navigate('Map')  
          }
        />
      </View>
                   </SafeAreaView>
   </FormContainer>
   
             );
     
   }
   export default Parcours;

   
   const utils = {
     colors: {primaryColor: '#af0e66'},
     dimensions: {defaultPadding: 12},
     fonts: {largeFontSize: 18, mediumFontSize: 16, smallFontSize: 12},
   };
   
   const styles = StyleSheet.create({
    container: {
      ...StyleSheet.absoluteFillObject,
      flex: 1, //the container will fill the whole screen.
      justifyContent: "flex-end",
      alignItems: "center",
    },
    map: {
      ...StyleSheet.absoluteFillObject,
      marginBottom: 1,
      padding: 30,
    },
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
       marginBottom: 20,
       justifyContent: 'center'
     },
     logo: {
       width: 66,
       height: 58,
     },
     borderLeft: {
      borderTopLeftRadius: 0,
      borderBottomLeftRadius: 5,
      height: 60,
      width: '50%',
      backgroundColor: '#1b1b33',
      justifyContent: 'center',
      alignItems: 'center',
     
    },
    borderRight: {
      borderTopRightRadius: 0,
      borderBottomRightRadius: 5,
      height: 60,
      width: '50%',
      backgroundColor: '#1b1b33',
      justifyContent: 'center',
      alignItems: 'center',
      marginBottom: -30,
      marginLeft:1
    },
   });
   