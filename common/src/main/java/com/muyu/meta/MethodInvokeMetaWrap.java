package com.muyu.meta;

import java.io.Serializable;
import java.util.Map;

/**
 * @author 七小鱼
 */
public class MethodInvokeMetaWrap implements Serializable {
    private MethodInvokeMeta invokeMeta;
    private Map<Object, Object> attchments;

    private ResultWrap resultWrap;


    public ResultWrap getResultWrap() {
        return resultWrap;
    }

    public void setResultWrap(ResultWrap resultWrap) {
        this.resultWrap = resultWrap;
    }

    public MethodInvokeMetaWrap(MethodInvokeMeta invokeMeta) {
        super();
        this.invokeMeta = invokeMeta;
    }

    public MethodInvokeMeta getInvokeMeta() {
        return invokeMeta;
    }

    public void setInvokeMeta(MethodInvokeMeta invokeMeta) {
        this.invokeMeta = invokeMeta;
    }

    public Map<Object, Object> getAttchments() {
        return attchments;
    }

    public void setAttchments(Map<Object, Object> attchments) {
        this.attchments = attchments;
    }

}
