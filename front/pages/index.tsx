import type { NextPage } from 'next';
import styled from 'styled-components';
import Head from 'next/head';

const Home: NextPage = () => {
  return (
    <Container>
      <Head>
        <title>lms</title>
      </Head>
    </Container>
  );
};

export default Home;

const Container = styled.div``;
