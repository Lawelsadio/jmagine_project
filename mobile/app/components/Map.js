import React, {useState,useRef} from 'react'
import FormSelectorBtn from './FormSelectorBtn';
import { StyleSheet, Text, View,Animated,Dimensions } from "react-native";
import { useLogin } from '../context/LoginProvider';
import MapViewDirections from 'react-native-maps-directions';

const { width } = Dimensions.get('window');
import MapView,{Marker} from "react-native-maps";

const Map = ({ navigation }) => {
  const KEY = "your apikey here";

  const animation = useRef(new Animated.Value(0)).current;
const signupColorInterpolate = animation.interpolate({
  inputRange: [0, width],
  outputRange: ['rgba(27,27,51,0.4)', 'rgba(27,27,51,1)'],
});
const {fullparcour,location} = useLogin();
const [duration, setDuration] = useState('');
const [distance, setdistance] = useState('');



const [region, setRegion] = useState({
    latitude: 43.668065 ,
    longitude: 7.216075,
    latitudeDelta: 0.001,
    longitudeDelta: 0.001,
  });
  const parcous =  [];
  const myla = location.coords.latitude
  const mylong =location.coords.longitude
 //const myLong = JSON.stringify(location.coords.longitude);
 console.log("mylat", myla)
 console.log("myLolon", mylong)
 
 

 //console.log("myLocation.coords", myLo)

 fullparcour.pois.map((element) => { 
  //console.log("element.isGeolocEnabled", element.isGeolocEnabled)
  if (element.isGeolocEnabled === false){
    console.log("si valide isGeolocEnabled", element.isGeolocEnabled)
    parcous.push({latitude:element.lat,longitude:element.lng,isGeolocEnabled:element.isGeolocEnabled})
  }
});


const Coordonee = fullparcour.pois.map((poi)=>{
  //console.log("si valide isQREnabled", poi.isQREnabled)
 // console.log("si valide isGeolocEnabled", poi.isGeolocEnabled)
var x= " ";

   // let str = poi.backgroundPic;
  //let fileName = str.substr(31);
  console.log("isGeolocEnableddd",poi.isGeolocEnabled)
  if(poi.isGeolocEnabled){
    console.log(" dans isgeoloca true",x)

     x = "red"
  }else x = "green"

  // si je decide de de ne plus afficher le parcours activé, je met juste le return 
  // dans dans la contion if du dessu.

    return <Marker coordinate={{latitude:poi.lat,longitude:poi.lng}}
    
    pinColor={x}
    resizeMode={'cover'}
    key={poi.id}
    title={poi.title}   
    //image={require("../../assets/map_marker_sh.png")} //uses relative file path. 
     />
    });
const malocation ={
    latitude: myla,
    longitude: mylong,
    latitudeDelta: 0.3,
    longitudeDelta: 0.3
}


  const dst = parcous.length - 1
const destination = parcous[dst]
  return (
    <View style={styles.container}>
        <MapView
    
    style={styles.map}
    initialRegion={region}
 
  >
      
 {Coordonee}

  <MapViewDirections
            origin={{latitude:myla, longitude: mylong}}
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

 <Marker coordinate={{latitude: myla, longitude: mylong}}
    pinColor="red"
    resizeMode={'cover'}
    title='ma postion '  
  />
 
  </MapView>
    
      <Text style={styles.text}>Current latitude: {region.latitude}</Text>
      <Text style={styles.text}>Current longitude: {region.longitude}</Text>
      <Text style={styles.text}>la distance max: {distance} km</Text>
      <Text style={styles.text}>la durée max: {duration} min</Text>
      
      <FormSelectorBtn
          style={styles.borderRight}
          backgroundColor={signupColorInterpolate}
          title='Activez un point'
    
           onPress={()=>{       
               navigation.navigate('QR_code')
              }}
        />

    </View>
  );

}
export default  Map;
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
  borderLeft: {
    borderTopLeftRadius: 0,
    borderBottomLeftRadius: 0,
    height: 60,
    width: '50%',
    backgroundColor: '#1b1b33',
    justifyContent: 'center',
    alignItems: 'center',
  },
  borderRight: {
    borderTopRightRadius: 20,
    borderBottomRightRadius: 20,
    borderTopLeftRadius: 20,
    borderBottomLeftRadius: 20,

    height: 60,
    width: '95%',
    marginBottom: 60,
    backgroundColor: '#1b1b33',
    justifyContent: 'center',
    alignItems: 'center',
  },
});
