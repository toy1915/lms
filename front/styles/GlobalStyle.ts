import { createGlobalStyle } from 'styled-components';
import bg from '../public/image/background.jpg';

const GlobalStyle = createGlobalStyle`
  body{
    margin: 0;
    padding: 0;
    
    width: 100%;
    height: 100vh;

    font-family: 'Inter', sans-serif;
    font-weight: 300;
    white-space: pre;

    background-size: 100% 100%;
    background-repeat: no-repeat;
    background-position: center;
    background-image: url('${bg.src}');
  }
  
  & .flex-end{
    display: flex;
    justify-content: end;
  }
  
  & .mb-1{
    margin-bottom: 0.5rem;
  }

  & .mb-2{
    margin-bottom: 1rem;
  }
`;

export default GlobalStyle;
