package org.example.mindmap.controller;

import org.example.mindmap.service.nodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class mindmapController {

    private final nodeService service;

    @Autowired
    public mindmapController(nodeService service) {
        this.service = service;
    }

    @PostMapping("/nodes")
    public String getNodes() {
        return service.processNode("node");
    }
}
