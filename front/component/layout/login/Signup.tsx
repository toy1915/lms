import { useFormik } from 'formik';
import * as yup from 'yup';
import { ErrorMessageConstants } from '../../../constants/ErrorMessage.constants';
import { CustomRegex } from '../../../util/common/Regex.util';
import { useEffect, useState } from 'react';
import { Button, TextField } from '@mui/material';

const Signup = () => {
  const [temp, setTemp] = useState({
    accountId: '',
    password: '',
    isPassword: '',
    roleId: '',
    nameK: '',
    nameE: '',
    sex: '',
    email: '',
    tel_num: '',
    birth: '',
  });

  const validationSchema = yup.object({
    accountId: yup
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
    isPassword: yup
      .string()
      .required(ErrorMessageConstants.PASSWORD_REQUIRED)
      .test('', ErrorMessageConstants.PASSWORD_TO_DIFFERENT, (value) => temp.password == value),
  });

  const formik = useFormik({
    initialValues: temp,
    validationSchema: validationSchema,
    onSubmit: (values) => {
      alert(JSON.stringify(values, null, 2));
    },
  });

  useEffect(() => {}, []);

  useEffect(() => {
    setTemp(formik.values);
  }, [formik.values]);

  return (
    <form onSubmit={formik.handleSubmit}>
      <div>
        <TextField
          fullWidth
          id="accountId"
          name="accountId"
          label="아이디"
          value={formik.values.accountId}
          onChange={formik.handleChange}
          error={formik.touched.accountId && Boolean(formik.errors.accountId)}
          helperText={formik.touched.accountId && formik.errors.accountId}
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
      <div>
        <TextField
          fullWidth
          id="isPassword"
          name="isPassword"
          label="비밀번호 확인"
          type="password"
          value={formik.values.isPassword}
          onChange={formik.handleChange}
          error={formik.touched.isPassword && Boolean(formik.errors.isPassword)}
          helperText={formik.touched.isPassword && formik.errors.isPassword}
          margin="normal"
        />
      </div>
      <Button id="button" variant="contained" fullWidth type="submit">
        회원가입
      </Button>
    </form>
  );
};

export default Signup;
