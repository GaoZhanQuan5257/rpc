package com.muyu.meta;

import java.io.Serializable;
import java.util.Map;

/**
 * @author 七小鱼
 */
public class ResultWrap implements Serializable {
    private Result result;
    private Map<Object, Object> attchments;

    public ResultWrap(Result result) {
        super();
        this.result = result;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Map<Object, Object> getAttchments() {
        return attchments;
    }

    public void setAttchments(Map<Object, Object> attchments) {
        this.attchments = attchments;
    }

}
