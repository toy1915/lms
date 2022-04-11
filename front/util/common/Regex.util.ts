import { RegexConstants } from '../../constants/Regex.constants';

export const CustomRegex = (value: string = '', level: number) => {
  return RegexConstants[level].test(value);
};
