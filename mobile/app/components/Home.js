import React, {useEffect} from 'react'
import APIKit from '../shared/APIKit';
import {Card} from 'react-native-shadow-cards';
import { View, Text, Image, ScrollView,SafeAreaView, Button  } from 'react-native';
import { useLogin } from '../context/LoginProvider';
import FormContainer from './FormContainer';


const Home = ({ navigation }) => {

  const { seP_id , setfullParcour, parcours, setParcours} = useLogin();

  useEffect(() => {
    APIKit.get('/api/parcours').then((response) => {
      //console.log("response.data", response.data)
      setParcours(response.data);
    });
  }, []);

 
   const useflullParcour = (parcourId) => {
    APIKit.get(`api/parcours/${parcourId}/full`).then((res) => {
                //console.log("response.data", response.data)
                setfullParcour(res.data);
                seP_id(parcourId)
                navigation.navigate('Parcours')
                //console.log("parcourId", parcourId)
               // console.log("fullparcour", fullparcour)

        });
  };

  


  const UseParcours = parcours.map((parcour)=>{
    let str = parcour.backgroundPic;
  let fileName = str.substr(31);
    return  <View key={parcour.id} style={styles.logotypeContainer}>
    <Card style={{padding: 10, margin: 10,}}>
                   <Text>Parcour : {parcour.id}</Text>
     <Text>{parcour.title}</Text>
     <Text>{parcour.description}</Text>
   
     <Image
           style={styles.tinyLogo}
           source={{
             uri: `http://10.192.27.79:8884/stockage/${fileName}`
           }}
           onPress={()=>{       
            useflullParcour(parcour.id)
            }}
         />
            <Button
             onPress={()=>{       
              useflullParcour(parcour.id)
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
       <ScrollView Style={{ flexGrow: 1, height: '80%',}}>
  
                {UseParcours}
                </ScrollView>
                </SafeAreaView>
</FormContainer>

          );
  
}
export default Home;


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
