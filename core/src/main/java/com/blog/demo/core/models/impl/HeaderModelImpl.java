package com.blog.demo.core.models.impl;

import com.blog.demo.core.models.HeaderModel;
import com.day.cq.wcm.api.Page;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Model(
        adaptables = {SlingHttpServletRequest.class},
        adapters = HeaderModel.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class HeaderModelImpl implements HeaderModel {
    @ValueMapValue
    private String logoPath;

    @ValueMapValue
    private String websiteName;

    @SlingObject
    Resource resource;

    @ScriptVariable
    private ResourceResolver resolver;

    @Override
    public String getLogoPath() {
        return logoPath;
    }

    @Override
    public String getWebsiteName() {
        return websiteName;
    }


    @Override
    public List<Map<String, String>> getNavigation() {
        List<Map<String, String>> navigationMap = new ArrayList<>();
        Resource links = resource.getChild("actions");
        if (links != null) {
            for (Resource r : links.getChildren()) {
                String link=r.getValueMap().get("link", String.class);
                Resource linkresource=resolver.resolve(link);
                if((linkresource.adaptTo(Page.class).getProperties().get("hideInNav",String.class))==null) {
                    Map<String, String> linkMap = new HashMap<>();
                    linkMap.put("Title", r.getValueMap().get("text", String.class));
                    linkMap.put("Link", link);
                    navigationMap.add(linkMap);
                }
            }
        }
        return navigationMap;
    }
}
