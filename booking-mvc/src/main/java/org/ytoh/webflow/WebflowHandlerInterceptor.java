package org.ytoh.webflow;

import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.webflow.context.ExternalContext;
import org.springframework.webflow.context.ExternalContextHolder;
import org.springframework.webflow.context.servlet.DefaultFlowUrlHandler;
import org.springframework.webflow.context.servlet.FlowUrlHandler;
import org.springframework.webflow.mvc.servlet.MvcExternalContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * A {@link org.springframework.web.servlet.HandlerInterceptor} implementation initializing {@link ExternalContextHolder} for Spring MVC web requests.
 * This is needed to access variables in scopes managed by webflow.
 *
 * @author: Martin [ytoh] Hvizdos (martin <dot> hvizdos <at> testile <dot> org)
 * @see WebflowArgumentResolver
 */
public class WebflowHandlerInterceptor extends HandlerInterceptorAdapter implements ServletContextAware {

//--------------------------------------------------------------------------------------------------------------------
// instance fields
//--------------------------------------------------------------------------------------------------------------------

    private ServletContext servletContext;
    private FlowUrlHandler flowUrlHandler = new DefaultFlowUrlHandler();

//--------------------------------------------------------------------------------------------------------------------
// public methods
//--------------------------------------------------------------------------------------------------------------------

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        ExternalContextHolder.setExternalContext(createServletExternalContext(request, response));
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ExternalContextHolder.setExternalContext(null);
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

//--------------------------------------------------------------------------------------------------------------------
// private methods
//--------------------------------------------------------------------------------------------------------------------

    private ExternalContext createServletExternalContext(HttpServletRequest request, HttpServletResponse response) {
        return new MvcExternalContext(servletContext, request, response, flowUrlHandler);
    }

//--------------------------------------------------------------------------------------------------------------------
// setters
//--------------------------------------------------------------------------------------------------------------------

    /**
     * @param flowUrlHandler - an optional {@link FlowUrlHandler} implementation.
     * @see DefaultFlowUrlHandler - default implementation
     */
    public void setFlowUrlHandler(FlowUrlHandler flowUrlHandler) {
        this.flowUrlHandler = flowUrlHandler;
    }
}