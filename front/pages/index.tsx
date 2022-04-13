import type { NextPage } from 'next';
import Head from 'next/head';
import Login from '../component/layout/login/Login';
import styled from 'styled-components';

const Home: NextPage = () => {
  return (
    <>
      <div>
        <Head>
          <title>lms</title>
        </Head>
        <Container>
          <Login />
        </Container>
      </div>
    </>
  );
};

export default Home;

const Container = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;

  width: 100%;
  height: 100vh;
`;
