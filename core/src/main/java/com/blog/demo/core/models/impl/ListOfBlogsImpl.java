package com.blog.demo.core.models.impl;

import com.blog.demo.core.models.ListOfBlogs;
import com.blog.demo.core.services.PublishedBlogsService;
import com.day.cq.wcm.api.Page;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;


import java.util.*;

@Model(
        adaptables = {Resource.class, SlingHttpServletRequest.class},
        adapters = ListOfBlogs.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class ListOfBlogsImpl implements ListOfBlogs {

    @ValueMapValue
    private String blogpath;

    @ScriptVariable
    private ResourceResolver resolver;

    @OSGiService
    PublishedBlogsService publishedBlogsService;


    @Override
    public List<Map<String, String>> getBlogs() {
        Resource r=resolver.resolve(blogpath);
        Page currPage=r.adaptTo(Page.class);

        return publishedBlogsService.getPublishedBlogs(currPage,null,3);

    }
}
