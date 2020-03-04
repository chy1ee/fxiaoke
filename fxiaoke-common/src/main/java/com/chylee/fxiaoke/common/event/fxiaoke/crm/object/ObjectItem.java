package com.chylee.fxiaoke.common.event.fxiaoke.crm.object;

public class ObjectItem {
    private String describeApiName;
    private String describeDisplayName;
    private String defineType;
    private boolean isActive;

    public String getDescribeApiName() {
        return describeApiName;
    }

    public void setDescribeApiName(String describeApiName) {
        this.describeApiName = describeApiName;
    }

    public String getDescribeDisplayName() {
        return describeDisplayName;
    }

    public void setDescribeDisplayName(String describeDisplayName) {
        this.describeDisplayName = describeDisplayName;
    }

    public String getDefineType() {
        return defineType;
    }

    public void setDefineType(String defineType) {
        this.defineType = defineType;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
