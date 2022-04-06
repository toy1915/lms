import { createGlobalStyle } from 'styled-components';

const GlobalStyle = createGlobalStyle`
  body{
    margin: 0;
    padding: 0;
    
    width: 100%;
    height: 100vh;

    font-family: 'Inter', sans-serif;
    font-weight: 300;
    white-space: pre;
  }
`;

export default GlobalStyle;
