/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates

 */
package qmsjee.filter;

import java.io.IOException;
import java.util.Date;
import javax.inject.Inject;
import javax.servlet.Filter;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import qmsjee.view.controlers.UserMBean;

@WebFilter(urlPatterns = "/faces/app/*")
public class AuthorizationFilter implements Filter {

    @Inject
    UserMBean userMBean;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;

        if (userMBean != null && userMBean.isLoggedIn()) {
            // User is logged in, so just continue request.
            chain.doFilter(request, response);
        } else {
            // User is not logged in, so redirect to index.
            HttpServletResponse res = (HttpServletResponse) response;
            HttpSession session = req.getSession();
            Date date = new Date();
            if ((date.getTime() - session.getCreationTime()) > (1000 * 60 * 15)) {
                session.setAttribute("message", "Session time expire");
            }
            res.sendRedirect(req.getContextPath() + "/faces/Login.xhtml");
        }
    }

    // You need to override init() and destroy() as well, but they can be kept empty.
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }

}
