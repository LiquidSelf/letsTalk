package main.webapp.java;

import configuration.WebConfig;
import security.WebSecurityConfig;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.lang.Nullable;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AnnotationsBasedApplicationInitializer
        extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Nullable
    @Override
    protected ApplicationContextInitializer<?>[] getRootApplicationContextInitializers() {
        return super.getRootApplicationContextInitializers();
    }

    @Nullable
    @Override
    protected Class<?>[] getRootConfigClasses() {
        System.err.println("Using Root-Application-Config class");
        return new Class[]{WebConfig.class, WebSecurityConfig.class};
    }

    @Nullable
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[0];
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    protected WebApplicationContext createRootApplicationContext() {
        return super.createRootApplicationContext();
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

        super.onStartup(servletContext);

//        final CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter(){
//            @Override
//            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//                response.setHeader("privet", "ANDREEEY BLINAAA!! PRIVET:)");
//                super.doFilterInternal(request, response, filterChain);
//            }
//        };
//
//        characterEncodingFilter.setEncoding("UTF-8");
//        characterEncodingFilter.setForceEncoding(true);
//
//        FilterRegistration.Dynamic registration = servletContext.addFilter(CharacterEncodingFilter.class.getUsername(), characterEncodingFilter);
//
//        registration.addMappingForUrlPatterns(null, true, "/*");
    }

    @Override
    protected DispatcherServlet createDispatcherServlet(WebApplicationContext servletAppContext) {
        System.out.println("createDispatcherServlet");
        final DispatcherServlet dispatcherServlet = (DispatcherServlet) super.createDispatcherServlet(servletAppContext);
        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
        return dispatcherServlet;
    }
}