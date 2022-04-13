import styled from 'styled-components';

const Custom404 = () => {
  return (
    <Container>
      <div className="title">404 Error</div>
    </Container>
  );
};

const Container = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;

  width: 100%;
  height: 100vh;

  & .title {
    font-weight: 700;
    font-size: 200px;

    color: #fff;
  }
`;

export default Custom404;
