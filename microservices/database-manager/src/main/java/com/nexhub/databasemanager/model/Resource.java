package com.nexhub.databasemanager.model;

import org.springframework.data.neo4j.core.schema.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.data.neo4j.core.schema.Relationship.Direction.OUTGOING;

@Node
public class Resource {
    @Id
    @GeneratedValue
    private final String resourceID;
    @Property("title")
    private String resourceTitle;
    @Property("uri")
    private String resourceUri;
    @Relationship(type = "OF_CATEGORY", direction = OUTGOING)
    private Set<Category> resourceCategory = new HashSet<>();


    public Resource(String resourceID, String resourceTitle, String resourceUri) {
        this.resourceID = resourceID;
        this.resourceTitle = resourceTitle;
        this.resourceUri = resourceUri;
    }

    public String getResourceID() {
        return resourceID;
    }


    public String getResourceTitle() {
        return resourceTitle;
    }

    public void setResourceTitle(String resourceTitle) {
        this.resourceTitle = resourceTitle;
    }

    public String getResourceUri() {
        return resourceUri;
    }

    public void setResourceUri(String resourceUri) {
        this.resourceUri = resourceUri;
    }
}
