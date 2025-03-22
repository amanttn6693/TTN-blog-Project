package com.blog.demo.core.services.Impl;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "Published Blogs")
public @interface PublishedBlogsConfiguration {

    @AttributeDefinition(name ="Enter the number of Blogs")
    int noOfBlogs() default 3;
}
