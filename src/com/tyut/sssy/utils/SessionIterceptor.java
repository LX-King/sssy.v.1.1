package com.tyut.sssy.utils;

import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.Action;
import com.tyut.sssy.sysmgr.actions.UserAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

/**
 * ClassName:SessionIterceptor
 * Function:${FUNCTION}
 * Author: LiuXiang
 * Date: 2012-5-2
 * Time: 22:35:30
 * Mail:LXiang.tyut@gmail.com
 * Company:山西省太原市和信至诚科技有限公司
 */
public class SessionIterceptor implements Interceptor {

    public void init() {
    }

    public void destroy() {
    }

    public String intercept(ActionInvocation actionInvocation) throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        
        Action action = (Action) actionInvocation.getAction();
        if (action instanceof UserAction) {
            return actionInvocation.invoke();
        }

        if (request.getSession(false) == null) {
            if (request.getHeader("x-requested-with") != null
                    && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
                response.setHeader("sessionstatus","timeout");
                return null;
            }else{
                return Action.LOGIN;
            }
        } else {
            return actionInvocation.invoke();
        }
    }

}
