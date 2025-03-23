package com.blog.demo.core.models.impl;


import com.blog.demo.core.models.FooterModel;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import java.text.SimpleDateFormat;
import java.util.*;

@Model(adaptables = Resource.class , adapters = FooterModel.class)
public class FooterModelImpl implements FooterModel {

    @SlingObject
    Resource componentResource;

    @SlingObject
    private ResourceResolver resolver;

    @Override
    public List<Map<String, String>> getNavItems() {
        List<Map<String, String>> navItems = new ArrayList<>();
        Resource items = componentResource.getChild("actions");
        if (items != null) {
            for (Resource r : items.getChildren()) {
                    Map<String, String> navItem = new HashMap<>();
                    navItem.put("Title", r.getValueMap().get("navTitle", String.class));
                    navItem.put("Link", r.getValueMap().get("navLink", String.class));
                    navItems.add(navItem);
            }
        }
        return navItems;
    }

    @Override
    public List<Integer> getYear(){
        Date d1=new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        int y =Integer.parseInt(sdf.format(d1));
        return Arrays.asList(y-1, y);
    }
}