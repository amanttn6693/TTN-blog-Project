package com.blog.demo.core.models.impl;

import com.adobe.granite.security.user.UserProperties;
import com.adobe.granite.security.user.UserPropertiesManager;
import com.adobe.granite.security.user.UserPropertiesService;
import com.blog.demo.core.models.Heading;
import com.day.cq.wcm.api.Page;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import java.text.SimpleDateFormat;
import java.util.Date;

@Model(adaptables = SlingHttpServletRequest.class, adapters = Heading.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class HeadingImpl implements Heading {

    @ScriptVariable
    private Page currentPage;

    @ScriptVariable
    private ResourceResolver resolver;

    @ValueMapValue(name = "jcr:created")
    private Date created;

    @ValueMapValue(name ="jcr:createdBy")
    private String userId;

    @Override
    public String getTitle() {
        return currentPage.getTitle();
    }

    @Override
    public String getAuthorName() {
        String createdBy=getUserName();
        return createdBy != null ? createdBy : "Unknown Author";
    }

    @Override
    public String getCreatedDate() {
        if (created != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("MMMMM dd, yyyy");
            return sdf.format(created.getTime());
        }
        return "No Date Found";
    }

    private String getUserName() {
        try {
                UserPropertiesManager upm = resolver.adaptTo(UserPropertiesManager.class);
                if (upm != null) {
                    UserProperties userProperties = upm.getUserProperties(userId, UserPropertiesService.PROFILE_PATH);;
                    if (userProperties != null) {
                        String fullName = userProperties.getProperty("profile/fullName");
                        return (fullName != null && !fullName.isEmpty()) ? fullName : userId;
                    }
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Unknown User";
    }
}