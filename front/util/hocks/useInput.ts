import { ChangeEvent, useCallback, useState } from 'react';

// TODO any 말고 들어갈 타입이 도대체 뭘까
export const useInput = (initalValue: any) => {
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
