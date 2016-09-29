package app.ifthen.com.if_then;


import com.ifthen.backend.ifBeanApi.model.IfBean;
import com.ifthen.backend.thenBeanApi.model.ThenBean;

import lombok.Getter;

/**
 * Created by bjeyara on 9/22/16.
 */
//@Getter
public class IfThen {
    private final IfBean ifBean;
    private final ThenBean thenBean;

    public IfThen(IfBean ifBean, ThenBean thenBean) {
        this.ifBean=ifBean;
        this.thenBean=thenBean;
    }

    public IfBean getIfBean() {
        return ifBean;
    }

    public ThenBean getThenBean() {
        return thenBean;
    }
}
