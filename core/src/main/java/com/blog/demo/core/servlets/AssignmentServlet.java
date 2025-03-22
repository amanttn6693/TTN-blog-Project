package com.blog.demo.core.servlets;


import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component(service = Servlet.class, name = "Demo Servlet", property = {
        org.osgi.framework.Constants.SERVICE_DESCRIPTION + "=Demo Servlet for Bootcamp",
        "sling.servlet.paths=/api/users",
        "sling.servlet.resourceTypes=" + "dummy1/dummy2",
        "sling.servlet.methods=" + HttpConstants.METHOD_GET,
        "sling.servlet.selectors=" + "asc",
        "sling.servlet.selectors=" + "desc",
        "sling.servlet.extensions=" + "text"

})
public class AssignmentServlet extends SlingAllMethodsServlet {
    @Override
    protected void doGet( SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        String path=request.getParameter("path");
        Resource resource=request.getResourceResolver().getResource(path);

        if (resource == null) {
            response.getWriter().println("Resource not found");
            return;
        }



        Map<String, String> pages = new HashMap<>();
        for(Resource child: resource.getChildren()){
            if(child.getValueMap().get("jcr:primaryType",String.class).equals("cq:Page")){
                pages.put(child.getValueMap().get("jcr:created", String.class), child.getName());
            }
        }

        String sortMethod = request.getRequestPathInfo().getSelectorString();

        List<Map.Entry<String, String>> entryList = new ArrayList<>(pages.entrySet());

        if ("asc".equalsIgnoreCase(sortMethod)) {
            entryList.sort(Map.Entry.comparingByKey());
        } else if ("desc".equalsIgnoreCase(sortMethod)) {
            entryList.sort(Map.Entry.<String, String>comparingByKey().reversed());
        }

        for (Map.Entry<String, String> entry : entryList) {
            response.getWriter().println(entry.getKey() + " : " + entry.getValue());
        }

    }
}
