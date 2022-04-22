import React, { useState } from 'react';
import { StyleSheet, Text } from 'react-native';
import APIKit from '../shared/APIKit';
import { useLogin } from '../context/LoginProvider';
import { isValidObjField, updateError } from '../utils/methods';
import FormContainer from './FormContainer';
import FormInput from './FormInput';
import {setClientToken} from '../shared/APIKit';
import FormSubmitButton from './FormSubmitButton';

const LoginForm = () => {
  const { setIsLoggedIn, setProfile, setRole } = useLogin();
  const [userInfo, setUserInfo] = useState({
    username: '',
    password: '',
  });

  const [error, setError] = useState('');
  const { username, password } = userInfo;
  const handleOnChangeText = (value, fieldName) => {
    setUserInfo({ ...userInfo, [fieldName]: value });
  };

  const isValidForm = () => {
    if (!isValidObjField(userInfo))
      return updateError('Required all fields!', setError);

      if (!username.trim() || username.length < 2) return updateError('Invalid username!', setError);

    if (!password.trim() || password.length < 3)
      return updateError('Password is too short!', setError);

    return true;
  };

  const submitForm = async () => {
    if (isValidForm()) {
      try {
        const res = await APIKit.post('/api/login', { ...userInfo });

        if (res.data) {
          setClientToken(res.data.access_token);
          //console.log("res.data.token",res.data.access_token);

         // setUserInfo({ username: '', password: '' });
          setProfile(userInfo);
          setIsLoggedIn(true);
          setRole(res.data.roles[0])
        //  console.log("role egale res.data.roles",res.data.roles[0])
        }
      } catch (error) {
        console.log(error);
      }
    }
  };

  return (
    <FormContainer>
      {error ? (
        <Text style={{ color: 'red', fontSize: 18, textAlign: 'center' }}>
          {error}
        </Text>
      ) : null}
      <FormInput
        value={username}
        onChangeText={value => handleOnChangeText(value, 'username')}
        label='username'
        placeholder='username'
        autoCapitalize='none'
      />
      <FormInput
        value={password}
        onChangeText={value => handleOnChangeText(value, 'password')}
        label='Password'
        placeholder='********'
        autoCapitalize='none'
        secureTextEntry
      />
      <FormSubmitButton onPress={submitForm} title='Login' />
    </FormContainer>
  );
};

const styles = StyleSheet.create({});

export default LoginForm;