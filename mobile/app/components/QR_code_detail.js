import React, {useState, useEffect} from 'react'
import {Card} from 'react-native-shadow-cards';

import { View, Text, Button ,Image } from 'react-native';
import { useLogin } from '../context/LoginProvider';


const QR_code_detail = ({ navigation }) => {
const {p,fn} = useLogin();

    return (
  
    <View style={styles.logotypeContainer}>
    <Card style={{padding: 10, margin: 10,}}>
                   <Text>Poi : {p.id}</Text>
     <Text>{p.title}</Text>  
     <Image
           style={styles.tinyLogo}
           source={{
             uri: `http://172.20.10.5:8884/stockage/${fn.fileName}`
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
  
     </View> 
          );
  
}
export default QR_code_detail;


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
