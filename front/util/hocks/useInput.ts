import { useCallback, useState } from 'react';

const useInput = (initalValue: data) => {
  const [data, setData] = useState(initalValue);

  const onHandler = useCallback(
    (e) => {
      const { value, name } = e.target;
      setData({
        ...data,
        [name]: value,
      });
    },
    [data],
  );
  return [data, onHandler];
};

type data = {
  [key: string]: string;
};
