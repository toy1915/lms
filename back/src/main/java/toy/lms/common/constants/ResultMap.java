package toy.lms.common.constants;

import toy.lms.common.utils.StringUtils;

import java.util.HashMap;
import java.util.List;

public class ResultMap extends HashMap {
    public void setSuccess() {
        this.put(Constant.KEY_RESULT, Constant.RESULT_SUCCESS);
    }

    public void setFailure() {
        this.put(Constant.KEY_RESULT, Constant.RESULT_FAILURE);
        if(!this.containsKey(Constant.KEY_MESSAGE) || StringUtils.isBlank(this.get(Constant.KEY_MESSAGE).toString()))
            this.setMessage(Constant.DEFAULT_MESSAGE);//default 메세지 설정..
    }

    public void setData(Object data) {
        this.put(Constant.KEY_DATA, data);
    }

    public void setList(List<?> list) {
        this.put(Constant.KEY_LIST, list);
    }

    public void setMessage(String message) {
        this.put(Constant.KEY_MESSAGE, message);
    }
}
