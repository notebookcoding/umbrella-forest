package com.simulation.notebook.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * @author Administrator
 */
public class FeignBasicRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            Enumeration<String> headerNames = request.getHeaderNames();
            if (headerNames != null) {
                while (headerNames.hasMoreElements()) {
                    String name = headerNames.nextElement();
                    String values = request.getHeader(name);
                    requestTemplate.header(name, values);
                }
            }

//            Enumeration<String> bodyNames = request.getParameterNames();
//            StringBuffer body = new StringBuffer();
//            if (bodyNames != null) {
//                while (bodyNames.hasMoreElements()) {
//                    String name = bodyNames.nextElement();
//                    String values = request.getParameter(name);
//                    body.append(name).append("=").append(values).append("&");
//                }
//            }
//            if (body.length() != 0) {
//                body.deleteCharAt(body.length() - 1);
//                requestTemplate.body(body.toString());
//            }
        }
    }
}
