import React from 'react';
import axios from 'axios';
import { View, StyleSheet, Text, TextInput } from 'react-native';

import FormContainer from './FormContainer';


 class Home extends React.Component {
  state = {
    parcours: [],
  }

  componentDidMount() {
    axios.get(`http://10.192.27.79:8886/api/parcours`)
      .then(res => {
        const parcours = res.data;
        console.log("parcours",parcours)
        this.setState({ parcours });
      })
  }

  

  render() {
    return (
        
        <FormContainer>
          <View style={styles.logotypeContainer}>
          {
          this.state.parcours.map(parcour =>
              <Text key={parcour.id}>
                {parcour.id},
                {parcour.title},
                {parcour.description},
                {parcour.backgroundPic}


              </Text>
            )
        }

          </View>
          </FormContainer>

          );
  }
}

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
};

export default Home;
