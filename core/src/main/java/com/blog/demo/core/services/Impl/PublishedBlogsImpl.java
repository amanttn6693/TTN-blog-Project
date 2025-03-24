package com.blog.demo.core.services.Impl;

import com.blog.demo.core.services.PublishedBlogs;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.Designate;

@Component(service = PublishedBlogs.class)
@Designate(ocd = PublishedBlogsConfiguration.class)
public class PublishedBlogsImpl implements PublishedBlogs {
    int blogs;

    @Activate
    public void activate(PublishedBlogsConfiguration config){
        blogs=config.noOfBlogs();
    }
    @Override
    public int noOfBlogs() {
        return blogs;
    }
}
