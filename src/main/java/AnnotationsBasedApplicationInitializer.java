package main.webapp.java;

import configuration.WebConfig;
import configuration.WebSecurityConfig;
import filters.CharacterSetFilter;
import org.springframework.lang.Nullable;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

public class AnnotationsBasedApplicationInitializer
        extends AbstractAnnotationConfigDispatcherServletInitializer {

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
        FilterRegistration.Dynamic charFilter = servletContext.addFilter(CharacterSetFilter.class.getName(), new CharacterSetFilter());
//        charFilter.setInitParameter("encoding", "UTF-8");
//        charFilter.setInitParameter("forceEncoding", "true");
        charFilter.addMappingForUrlPatterns(null, true, "/*");

    }

}