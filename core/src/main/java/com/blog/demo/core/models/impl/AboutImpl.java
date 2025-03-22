package com.blog.demo.core.models.impl;

import com.blog.demo.core.models.About;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(
        adaptables = Resource.class,
        adapters = About.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class AboutImpl implements About {

    @ValueMapValue
    private String about;

    @Override
    public String getAbout() {
        return about;
    }
}
