package com.blog.demo.core.models.impl;


import com.blog.demo.core.models.ImportantLinks;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceUtil;
import org.apache.sling.models.annotations.*;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Model(
        adaptables = {SlingHttpServletRequest.class},
        adapters = ImportantLinks.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class ImportantLinksImpl implements ImportantLinks {

    @Inject
    private ResourceResolver resourceResolver;

    @SlingObject
    Resource componentResource;

    @ValueMapValue
    private String maintitle;

    @Override
    public String getMainTitle() {
        return maintitle;
    }

    @Override
    public List<Map<String, String>> getLinksWithTitle() {
        List<Map<String, String>> linksWithTitleMap=new ArrayList<>();

            Resource links = componentResource.getChild("linkswithtitle");
            if (links != null) {
                for (Resource r : links.getChildren()) {
                    Map<String, String> linkMap = new HashMap<>();
                    linkMap.put("Title", r.getValueMap().get("title", String.class));
                    linkMap.put("Link", formatLink(r.getValueMap().get("link", String.class)));
                    linkMap.put("LinkTarget", r.getValueMap().get("linktarget", String.class));
                    linksWithTitleMap.add(linkMap);
                }
            }
        return linksWithTitleMap;
    }

    public String formatLink(String url) {
        Resource resource = resourceResolver.resolve(url);
        if (resource != null && !ResourceUtil.isNonExistingResource(resource)) {
            return url.endsWith(".html") ? url : url + ".html";
        }
        return url;
    }
}

