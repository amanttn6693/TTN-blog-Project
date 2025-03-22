package com.blog.demo.core.services.Impl;

import com.blog.demo.core.services.BootcampService;
import org.osgi.service.component.annotations.Component;

@Component(service = BootcampService.class)
public class BootcampServiceImpl implements BootcampService {

    @Override
    public String getService() {
        return "This is from Osgi Service";
    }
}
