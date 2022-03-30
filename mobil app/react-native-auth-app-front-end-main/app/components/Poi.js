import React, {useState, useEffect} from 'react'
import axios from 'axios';
import {Card} from 'react-native-shadow-cards';

import { View, StyleSheet, Text, TextInput, Image, ScrollView,SafeAreaView, Button  } from 'react-native';
import { useLogin } from '../context/LoginProvider';

import FormContainer from './FormContainer';

const Poi = ({ navigation }) => {
const {fullparcour,setP,p,fn,p_id,setFn} = useLogin();

const Coordonee = fullparcour.map((poi)=>{

  let str = poi.backgroundPic;
  let fileName = str.substr(31);


  const UsePoi = (data) => {
    let dt = data
  axios.get(`http://10.192.27.79:8886/parcours/${p_id}/pois/${dt}`).then((res) => {
      let str = res.data.backgroundPic;
      let fileName = str.substr(31);
      console.log("filname",fileName)
      setFn({fileName:fileName})
      setP(res.data)
     
      console.log("res.data",res.data)
     
      console.log("fileName",fileName)

     
      });
      console.log("res.p",p)
      console.log("fn",fn)

      navigation.navigate('Detail_poi')   


};

  return  <View key={poi.id} style={styles.logotypeContainer}>
    <Card style={{padding: 10, margin: 10,}}>
                   <Text>point d'intert : {poi.title}</Text>
     <Text>{poi.title}</Text>
     <Text>{poi.description}</Text>
   
     <Image
           style={styles.tinyLogo}
           source={{
             uri: `http://10.192.27.79:8884/stockage/${fileName}`
           }}
         />
            <Button
             onPress={()=>{       
              UsePoi(poi.id)
                       }}
             title="Learn More"
             color="#841584"
             accessibilityLabel="Learn more about this purple button"
           />
         </Card>
  
     </View>
    });
  return (
        
    <FormContainer>
        <SafeAreaView>
          <ScrollView>
     
                   {Coordonee}
                   </ScrollView>
                   <Button
             onPress={()=>{       
             // UsePoi(poi.id)
                       }}
             title="Learn More"
             color="#841584"
             accessibilityLabel="Learn more about this purple button"
           />
                   </SafeAreaView>
   </FormContainer>
   
             );
     
   }
   export default Poi;
   
   
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
   };
   