import React, { createContext, useContext, useState } from 'react';

const LoginContext = createContext();

const LoginProvider = ({ children }) => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [profile, setProfile] = useState({});
  const [role, setRole] = useState({});
  const [fullparcour, setfullParcour] = useState( {} );
  const [p, setP] = useState({});
  const [fn, setFn] = useState({});
  const [p_id, seP_id] = useState({});
  const [parcours, setParcours] = useState( [] );
  const [location, setLocation] = useState( {  coords:{latitude: 43.668065 ,
    longitude: 7.216075,
    latitudeDelta: 0.001,
    longitudeDelta: 0.001,}} );


  return (
    <LoginContext.Provider
      value={{ isLoggedIn, setIsLoggedIn, profile, setProfile , role, setRole,fullparcour, setfullParcour, p, setP, fn,setFn,p_id, seP_id, 
        parcours, setParcours,location, setLocation
      }}
    >
      {children}
    </LoginContext.Provider>
  );
};

export const useLogin = () => useContext(LoginContext);
export {LoginContext}
export default LoginProvider;
