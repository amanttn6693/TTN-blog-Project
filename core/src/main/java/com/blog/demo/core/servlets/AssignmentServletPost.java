package com.blog.demo.core.servlets;


import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

@Component(service = Servlet.class, name = "Demo Servlet POST", property = {
        org.osgi.framework.Constants.SERVICE_DESCRIPTION + "=Demo Servlet POST for Bootcamp",
        "sling.servlet.resourceTypes=" + "dummy1/dummy2",
        "sling.servlet.methods=" + HttpConstants.METHOD_POST,
        "sling.servlet.selectors=" + "testPage",
        "sling.servlet.extensions=" + "text"

})
public class AssignmentServletPost extends SlingAllMethodsServlet {
    @Override
    protected void doPost(SlingHttpServletRequest request,SlingHttpServletResponse response) throws ServletException, IOException {
        String path=request.getParameter("path")+"/jcr:content";
        String title=request.getParameter("title");

        ResourceResolver resolver = request.getResourceResolver();

        Resource resource=resolver.getResource(path);

        ModifiableValueMap mvp = resource.adaptTo(ModifiableValueMap.class);

        mvp.put("jcr:title", title);

        resolver.commit();
        response.setStatus(SlingHttpServletResponse.SC_OK);
        response.getWriter().write("Property updated successfully.");
        resolver.close();



    }
}
