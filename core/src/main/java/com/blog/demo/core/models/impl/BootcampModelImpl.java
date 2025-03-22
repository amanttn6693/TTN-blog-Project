package com.blog.demo.core.models.impl;

import com.blog.demo.core.models.BootcampModel;
import com.blog.demo.core.services.BootcampService;
import com.day.cq.wcm.api.Page;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

@Model(adaptables = SlingHttpServletRequest.class, adapters = BootcampModel.class ,resourceType = "/apps/blogproject/components/bootcamp")
public class BootcampModelImpl implements BootcampModel {

    @Via("resource")
    @Inject
    private String authorName;

    @OSGiService
    private BootcampService bootcampService;

    private String serviceMessage;

    @PostConstruct
    protected void init(){
        if (bootcampService != null) {
            serviceMessage = bootcampService.getService();
        } else {
            serviceMessage = "Service unavailable";
        }
    }

    public String getServiceMessage() {
        return serviceMessage;
    }


    @ScriptVariable
    private Page currentPage;

    @Override
    public String getAuthorName() {
        return authorName;
    }

    @Override
    public String getPageTitle() {
        return currentPage.getTitle();
    }


}
