import styled from 'styled-components';
import sub from '../../public/image/login_sub.jpg';
import { TextField } from '@mui/material';
import { useInput } from '../../util/hocks/useInput';

const Login = () => {
  const [text, setText] = useInput({
    id: '',
    password: '',
  });

  console.log(text);

  return (
    <Flew>
      <Sub></Sub>
      <LoginForm>
        <div id="title">LOGIN</div>
        <div>
          <TextField variant="outlined" id="id" label="아이디" onChange={setText} required />
          <TextField variant="outlined" id="password" label="비밀번호" onChange={setText} required />
        </div>
      </LoginForm>
    </Flew>
  );
};

const Flew = styled.div`
  display: flex;

  width: 820px;
  height: 600px;
`;

const Sub = styled.div`
  width: 240px;
  height: inherit;
  overflow: visible;
  background-image: url('${sub.src}');
  background-size: 100% 100%;
  background-repeat: no-repeat;
  background-position: center;
  border-radius: 8px;
`;

const LoginForm = styled.div`
  width: 550px;
  height: inherit;
  background-color: #d0d0bd;
  overflow: visible;
  aspect-ratio: 0.9166666666666666 / 1;
  border-radius: 8px;
  box-shadow: 3px 3px 2px 1px #5f5d5d;
  padding: 0 49px 0 49px;

  & #title {
    display: flex;
    height: 216px;
    justify-content: center;
    align-items: center;

    font-weight: 700;
    font-size: 40px;
    line-height: 1.2;
  }
`;

export default Login;
