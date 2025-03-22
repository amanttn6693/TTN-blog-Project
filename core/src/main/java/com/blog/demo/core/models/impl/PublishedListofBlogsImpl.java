package com.blog.demo.core.models.impl;

import com.blog.demo.core.models.PublishedListofBlogs;
import com.blog.demo.core.services.PublishedBlogsService;
import com.blog.demo.core.services.PublishedBlogs;
import com.day.cq.wcm.api.Page;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import java.util.*;

@Model(
        adaptables = {SlingHttpServletRequest.class},
        adapters = PublishedListofBlogs.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class PublishedListofBlogsImpl implements PublishedListofBlogs {

    @ScriptVariable
    private Page currentPage;

    @ScriptVariable
    private SlingHttpServletRequest request;

    @OSGiService
    private PublishedBlogsService publishedBlogsService;

    @OSGiService
    private PublishedBlogs publishedBlogs;

    @Override
    public List<Map<String, String>> getBlogs() {
        String monthParam = request.getParameter("month");
        int limit = publishedBlogs.noOfBlogs();
        return publishedBlogsService.getPublishedBlogs(currentPage, monthParam, limit);
    }
}
