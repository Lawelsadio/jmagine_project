import React, {useState, useEffect} from 'react'
import {Card} from 'react-native-shadow-cards';
import { View, Text, Button ,Image ,StyleSheet} from 'react-native';
import { useLogin } from '../context/LoginProvider';


const Detail_poi = ({ navigation }) => {

const {p,fn} = useLogin();
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

<Text>date decreation du  {p.title}</Text>
                   <Text> {p.address}</Text>
                   <Text > Lorem Ipsum est simplement un faux texte de l'industrie de
                      l'impression et de la composition. Le Lorem Ipsum est le texte 
                      factice standard de l'industrie depuis les années 1500, lorsqu'un
                       imprimeur inconnu a pris une galère de caractères et l'a brouillé
                        pour en faire un livre de spécimens de caractères. Il a survécu 
                        non seulement à cinq siècles, mais aussi au saut dans la composition
                         électronique, restant essentiellement inchangé. Il a été popularisé 
                         dans les années 1960 avec la sortie de feuilles Letraset contenant des 
                         passages de Lorem Ipsum,
                      et plus récemment avec des logiciels de publication assistée par ordinateur
                       comme Aldus PageMaker comprenant des versions de Lorem Ipsum
                       électronique, restant essentiellement inchangé. Il a été popularisé 
                         dans les années 1960 avec la sortie de feuilles Letraset contenant des 
                         passages de Lorem Ipsum,
                      
                       comme Aldus PageMaker comprenant des versions de Lorem Ipsum.</Text>
            <Button
             onPress={()=>{       
               navigation.navigate('Map')
              }}
             title="Continuez votre le parcours"
             color="#841584"
             accessibilityLabel="Learn more about this purple button"
           />
         </Card>
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
    justifyContent: 'center',
    marginBottom: 12
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
