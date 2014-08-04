package com.tyut.sssy.utils;

import org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.ArrayList;

/**
 * ClassName:WrapperStrutsFilter
 * Function:WrapperStrutsFilter
 * Author: LiuXiang
 * Date: 2012-5-3
 * Time: 9:43:28
 * Mail:LXiang.tyut@gmail.com
 * Company:山西省太原市和信至诚科技有限公司
 */
public class WrapperStrutsFilter extends StrutsPrepareAndExecuteFilter {

    private ArrayList<String> urlList;
    private ArrayList<String> admitSuffixList;
    private int totalURLS;
    private int totalAdmitSuffix;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String urls = filterConfig.getInitParameter("avoid-urls");
        String admitSuffix = filterConfig.getInitParameter("admit-suffix");
        StringTokenizer token = new StringTokenizer(urls, ",");
        StringTokenizer token2 = new StringTokenizer(admitSuffix, ",");
        urlList = new ArrayList<String>();
        admitSuffixList = new ArrayList<String>();
        while (token.hasMoreTokens()) {
            urlList.add(token.nextToken());
        }
        while (token2.hasMoreTokens()) {
            admitSuffixList.add(token2.nextToken());
        }
        totalURLS = urlList.size();
        totalAdmitSuffix = admitSuffixList.size();
        super.init(filterConfig);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        boolean flag = checkSession(request, response);

        if (request.getSession().getAttribute("validPath") != null) {
            return;
        }
        if (!flag) {
            if (request.getHeader("x-requested-with") != null
                    && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
                response.setHeader("sessionstatus", "timeout");
            } else {
                response.setHeader("sessionstatus", "timeout");
                request.setAttribute("sessionstatus", "timeout");
                response.sendRedirect(request.getContextPath() + "/login.jsp");
            }
        } else {
            super.doFilter(servletRequest, servletResponse, filterChain);
        }
    }

    private boolean checkSession(HttpServletRequest request, HttpServletResponse response) {

        String requestURI = request.getServletPath().substring(1);
        String reqSuffix = requestURI.substring(requestURI.lastIndexOf('.') + 1);

        boolean allowedRequest = false;

        for (int i = 0; i < totalURLS; i++) {
            if (requestURI.equals(urlList.get(i))) {
                allowedRequest = true;
                break;
            }
        }

        if (!allowedRequest) {
            HttpSession session = request.getSession(false);
            if (session == null)
                allowedRequest = false;
            else {
                if (session.getAttribute("loginUser") == null) {
                    allowedRequest = true;
                } else {
                    boolean flag = false;
                    if (!allowedRequest) {
                        if (request.getLocalAddr().equals(request.getRemoteAddr())) {
                            allowedRequest = true;
                        } else {
                            for (int j = 0; j < totalAdmitSuffix; j++) {
                                if (reqSuffix.equals(admitSuffixList.get(j))) {
                                    flag = true;
                                    allowedRequest = true;
                                    break;
                                }
                            }
                            if (!flag) {
                                request.getSession().setAttribute("validPath", "非法的路径名");
                                allowedRequest = true;
                            }
                        }
                    }
                }
            }
        }

        return allowedRequest;

    }
}