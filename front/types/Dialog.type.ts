import * as React from 'react';

export type DialogType = {
  isOpen: boolean;
};

export interface CustomDialogTitleProps {
  id: string;
  children?: React.ReactNode;
  onClose: () => void;
}

export interface CustomDialogProps {
  title: String;
  children: React.ReactNode;
  width: 'xs' | 'sm' | 'md' | 'lg' | 'xl';
}
