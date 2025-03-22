package com.blog.demo.core.services;

import com.day.cq.wcm.api.Page;
import java.util.List;
import java.util.Map;

public interface PublishedBlogsService {
    List<Map<String, String>> getPublishedBlogs(Page currentPage, String monthFilter, int limit);
}
