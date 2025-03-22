package com.blog.demo.core.models.impl;

import com.adobe.granite.security.user.UserProperties;
import com.adobe.granite.security.user.UserPropertiesManager;
import com.adobe.granite.security.user.UserPropertiesService;
import com.blog.demo.core.models.Heading;
import com.day.cq.wcm.api.Page;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.jcr.Session;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Model(adaptables = SlingHttpServletRequest.class, adapters = Heading.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class HeadingImpl implements Heading {

    @ScriptVariable
    private Page currentPage; // Fetches Page Properties

    @SlingObject
    private ResourceResolver resourceResolver;

    @ValueMapValue(name = "jcr:created")
    private Calendar created; // Fetches the Creation Date

    @ValueMapValue(name ="jcr:createdBy")
    private String userId;

    // Getter for Page Title
    @Override
    public String getTitle() {
        return currentPage.getTitle();
    }

    // Getter for Created By
    @Override
    public String getAuthorName() {
        String createdBy=getLoggedInUserName();
        return createdBy != null ? createdBy : "Unknown Author";
    }

    // Getter for Created Date (Formatted)
    @Override
    public String getCreatedDate() {
        if (created != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("MMMMM dd, yyyy");
            return sdf.format(created.getTime());
        }
        return "No Date Found";
    }

    private String getLoggedInUserName() {
        try {
                UserPropertiesManager upm = resourceResolver.adaptTo(UserPropertiesManager.class);
                if (upm != null) {
                    UserProperties userProperties = upm.getUserProperties(userId, UserPropertiesService.PROFILE_PATH);
                    if (userProperties != null) {
                        // Fetch full name, if available; otherwise, fallback to userId
                        String fullName = (String) userProperties.getProperty("profile/fullName");
                        return (fullName != null && !fullName.isEmpty()) ? fullName : userId;
                    }
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Unknown User";
    }
}