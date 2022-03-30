import React, { createContext, useContext, useState } from 'react';

const LoginContext = createContext();

const LoginProvider = ({ children }) => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [profile, setProfile] = useState({});
  const [role, setRole] = useState({});
  const [fullparcour, setfullParcour] = useState( [] );
  const [p, setP] = useState({});
  const [fn, setFn] = useState({});
  const [p_id, seP_id] = useState({});


  return (
    <LoginContext.Provider
      value={{ isLoggedIn, setIsLoggedIn, profile, setProfile , role, setRole,fullparcour, setfullParcour, p, setP, fn,setFn,p_id, seP_id }}
    >
      {children}
    </LoginContext.Provider>
  );
};

export const useLogin = () => useContext(LoginContext);
export {LoginContext}
export default LoginProvider;
