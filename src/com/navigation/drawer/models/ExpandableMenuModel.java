package com.navigation.drawer.models;

public class ExpandableMenuModel {

    public String menuName, url;
    public boolean hasChildren, isGroup;

    public ExpandableMenuModel(String menuName, boolean isGroup, boolean hasChildren, String url) {

        this.menuName = menuName;
        this.url = url;
        this.isGroup = isGroup;
        this.hasChildren = hasChildren;
    }
}