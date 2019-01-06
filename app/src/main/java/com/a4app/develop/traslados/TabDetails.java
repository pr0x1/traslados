package com.a4app.develop.traslados;

public class TabDetails {
    private String tabName;
    private LecturaActivity.PlaceholderFragment fragment;

    public TabDetails(String tabName, LecturaActivity.PlaceholderFragment fragment) {
        this.tabName = tabName;
        this.fragment = fragment;
    }

    public String getTabName() {
        return tabName;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }

    public LecturaActivity.PlaceholderFragment getFragment() {
        return fragment;
    }

    public void setFragment(LecturaActivity.PlaceholderFragment fragment) {
        this.fragment = fragment;
    }

}