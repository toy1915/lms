import { DialogType } from '../../types/Dialog.type';

const DIALOG_OPEN = 'dialog/open' as const;
const DIALOG_CLOSE = 'dialog/close' as const;

export const dialogOpen = () => ({
  type: DIALOG_OPEN,
  payload: { open: true },
});

export const dialogClose = () => ({
  type: DIALOG_CLOSE,
  payload: { open: false },
});

type DialogAction = ReturnType<typeof dialogOpen> | ReturnType<typeof dialogClose>;

const initState: DialogType = {
  isOpen: false,
};

const DialogReducer = (state: DialogType = initState, action: DialogAction) => {
  switch (action.type) {
    case DIALOG_OPEN:
      return { isOpen: true };
    case DIALOG_CLOSE:
      return { isOpen: false };
    default:
      return state;
  }
};

export default DialogReducer;
