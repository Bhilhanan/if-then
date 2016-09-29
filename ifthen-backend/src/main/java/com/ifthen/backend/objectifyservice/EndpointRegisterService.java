package com.ifthen.backend.objectifyservice;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.ifthen.backend.IfBean;
import com.ifthen.backend.ThenBean;

/**
 * Created by bjeyara on 9/22/16.
 */

public class EndpointRegisterService {
    static{
        ObjectifyService.register(IfBean.class);
        ObjectifyService.register(ThenBean.class);
    }

    public static Objectify ofy() {
        return ObjectifyService.ofy();
    }

    public static ObjectifyFactory factory() {
        return ObjectifyService.factory();
    }
}
