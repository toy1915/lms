import styled from 'styled-components';
import sub from '../../../public/image/login_sub.jpg';
import { Button, Dialog, TextField } from '@mui/material';
import * as yup from 'yup';
import { useFormik } from 'formik';
import { ErrorMessageConstants } from '../../../constants/ErrorMessage.constants';
import { CustomRegex } from '../../../util/common/Regex.util';
import { CustomDialog } from '../../common/Dialog';
import { useDispatch } from 'react-redux';
import { dialogOpen } from '../../../util/store/Dialog.reducer';
import Signup from './Signup';

const Login = () => {
  const validationSchema = yup.object({
    id: yup
      .string()
      .required(ErrorMessageConstants.ID_REQUIRED)
      .min(8, ErrorMessageConstants.ID_VALID_FAILURE)
      .max(15, ErrorMessageConstants.ID_VALID_FAILURE)
      .test('', ErrorMessageConstants.ID_VALID_FAILURE, (value) => CustomRegex(value, 1)),
    password: yup
      .string()
      .required(ErrorMessageConstants.PASSWORD_REQUIRED)
      .min(12, ErrorMessageConstants.PASSWORD_VALID_FAILURE)
      .max(20, ErrorMessageConstants.PASSWORD_VALID_FAILURE)
      .test('', ErrorMessageConstants.PASSWORD_VALID_FAILURE, (value) => CustomRegex(value, 1)),
  });

  const formik = useFormik({
    initialValues: {
      id: '',
      password: '',
    },
    validationSchema: validationSchema,
    onSubmit: (values) => {
      alert(JSON.stringify(values, null, 2));
    },
  });

  const dispatch = useDispatch();

  const handleClickSignup = () => {
    dispatch(dialogOpen());
  };

  return (
    <>
      <Flew>
        <SubFrame></SubFrame>
        <LoginFrame>
          <div id="title">LOGIN</div>
          <form onSubmit={formik.handleSubmit}>
            <div>
              <TextField
                fullWidth
                id="id"
                name="id"
                label="아이디"
                value={formik.values.id}
                onChange={formik.handleChange}
                error={formik.touched.id && Boolean(formik.errors.id)}
                helperText={formik.touched.id && formik.errors.id}
              />
            </div>
            <div>
              <TextField
                fullWidth
                id="password"
                name="password"
                label="비밀번호"
                type="password"
                value={formik.values.password}
                onChange={formik.handleChange}
                error={formik.touched.password && Boolean(formik.errors.password)}
                helperText={formik.touched.password && formik.errors.password}
                margin="normal"
              />
            </div>
            <ButtonFrame>
              <div className="flex-end mb-1">
                <span onClick={handleClickSignup}>회원가입</span>
              </div>
              <Button id="button" variant="contained" fullWidth type="submit">
                로그인
              </Button>
            </ButtonFrame>
          </form>
        </LoginFrame>
      </Flew>
      <CustomDialog title="회원가입" width="xs">
        <Signup />
      </CustomDialog>
    </>
  );
};

const Flew = styled.div`
  width: 820px;
  height: 600px;
  border-radius: 8px;

  box-shadow: 3px 3px 2px 1px #5f5d5d;
  background-color: #d0d0bd;
  overflow: visible;

  position: relative;
`;

const SubFrame = styled.div`
  width: 270px;
  height: 600px;
  border-radius: 8px 0 0 8px;

  position: absolute;
  top: 0;
  left: 0;

  background-image: url('${sub.src}');
  background-size: 100% 100%;
  background-repeat: no-repeat;
  background-position: center;
`;

const LoginFrame = styled.div`
  padding: 0 49px 0 319px;

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

const ButtonFrame = styled.div`
  margin-top: 90px;

  & #button {
    background-color: #4d4d4d;
  }
`;

export default Login;
