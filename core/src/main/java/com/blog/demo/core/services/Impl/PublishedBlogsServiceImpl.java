package com.blog.demo.core.services.Impl;

import com.blog.demo.core.services.PublishedBlogsService;
import com.day.cq.wcm.api.Page;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.osgi.service.component.annotations.Component;
import java.text.SimpleDateFormat;
import java.util.*;

@Component(service = PublishedBlogsService.class)
public class PublishedBlogsServiceImpl implements PublishedBlogsService {

    @Override
    public List<Map<String, String>> getPublishedBlogs(Page currentPage, String monthFilter, int limit) {
        List<Map<String, String>> blogs = new ArrayList<>();
        Iterator<Page> childPages = currentPage.listChildren();
        SimpleDateFormat sdf = new SimpleDateFormat("MMMMM yyyy");
        SimpleDateFormat sdf2 = new SimpleDateFormat("MMM yyyy");

        while (limit > 0 && childPages.hasNext()) {
            Page childPage = childPages.next();
            Date createdDate = childPage.getProperties().get("jcr:created", Date.class);
            String formattedDate = sdf.format(createdDate);

            if (monthFilter != null) {
                String[] splitParam = monthFilter.split(" ");
                if (splitParam.length == 2) {
                    String month = splitParam[0];
                    String year = splitParam[1];
                    if (!formattedDate.contains(month) || !formattedDate.contains(year)) {
                        continue;
                    }
                }
            }

            Map<String, String> blog = new HashMap<>();
            blog.put("Title", childPage.getTitle());
            blog.put("Date", sdf2.format(createdDate));
            blog.put("Img", getImagePath(childPage));
            blog.put("Link", childPage.getPath() + ".html");
            blog.put("SubHeading", childPage.getDescription() != null ? childPage.getDescription() : "No Description");

            blogs.add(blog);
            limit--;
        }
        return blogs;
    }

    private String getImagePath(Page childPage) {
        Resource imageResource = childPage.getContentResource("cq:featuredimage");

        if (imageResource != null) {
            Resource fileResource = imageResource.getChild("file");
            if (fileResource != null) {
                return fileResource.getPath();
            }

            ValueMap properties = imageResource.getValueMap();
            String fileReference = properties.get("fileReference", String.class);
            if (fileReference != null) {
                return fileReference;
            }
        }

        return null;
    }
}
