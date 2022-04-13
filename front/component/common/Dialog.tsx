import * as React from 'react';
import Button from '@mui/material/Button';
import { styled } from '@mui/material/styles';
import Dialog from '@mui/material/Dialog';
import { Fragment } from 'react';
import { DialogActions, DialogContent, DialogTitle, IconButton } from '@mui/material';
import { CustomDialogProps, CustomDialogTitleProps } from '../../types/Dialog.type';
import { useDispatch, useSelector } from 'react-redux';
import { RootState } from '../../util/store';
import CloseIcon from '@mui/icons-material/Close';
import { dialogClose } from '../../util/store/Dialog.reducer';

const CustomDialogTitle = (props: CustomDialogTitleProps) => {
  const { children, onClose, ...other } = props;

  return (
    <DialogTitle sx={{ m: 0, p: 2 }} {...other}>
      {children}
      <IconButton
        aria-label="close"
        onClick={onClose}
        sx={{
          position: 'absolute',
          right: 8,
          top: 8,
          color: (theme) => theme.palette.grey[500],
        }}
      >
        <CloseIcon />
      </IconButton>
    </DialogTitle>
  );
};

export const CustomDialog = (props: CustomDialogProps) => {
  const { title, children, width, ...outer } = props;

  const open = useSelector((state: RootState) => state.DialogReducer.isOpen);
  const dispatch = useDispatch();

  const handleClickClose = () => {
    dispatch(dialogClose());
  };

  return (
    <Fragment>
      <BootstrapDialog open={open} fullWidth={true} maxWidth={width} {...outer}>
        <CustomDialogTitle id="title" onClose={handleClickClose}>
          {title}
        </CustomDialogTitle>
        <DialogContent>{children}</DialogContent>
        <DialogActions>
          <Button autoFocus onClick={handleClickClose}>
            닫기
          </Button>
        </DialogActions>
      </BootstrapDialog>
    </Fragment>
  );
};

const BootstrapDialog = styled(Dialog)(({ theme }) => ({
  '& .MuiDialogContent-root': {
    padding: theme.spacing(2),
  },
  '& .MuiDialogActions-root': {
    padding: theme.spacing(1),
  },
}));
