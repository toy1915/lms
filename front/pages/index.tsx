import type { NextPage } from 'next';
import styled from 'styled-components';
import Head from 'next/head';
import Login from './login/Login';

const Home: NextPage = () => {
  return (
    <Container>
      <Head>
        <title>lms</title>
      </Head>
      <body>
        <Login />
      </body>
    </Container>
  );
};

export default Home;

const Container = styled.div``;
