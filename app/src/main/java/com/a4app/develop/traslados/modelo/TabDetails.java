package com.a4app.develop.traslados.modelo;

/**
        * Clase que hace referencia a la información de los tabs
        * The type Centros activity.
        * @author Yamit Huertas
        * @version 1.0
        *
        *
        */
public class TabDetails {
    /**
     * Nombre del tab
     */
    private String tabName;
    /**
     * Fragmento del tab
     */
    private android.support.v4.app.Fragment fragment;

    public TabDetails(String tabName, android.support.v4.app.Fragment fragment) {
        this.tabName = tabName;
        this.fragment = fragment;

    }

    /**
     * retorna el nombre del tab
     * @return
     */
    public String getTabName() {
        return tabName;
    }

    /**
     * Cambia el nombre del tab
     * @param tabName
     */
    public void setTabName(String tabName) {
        this.tabName = tabName;
    }

    /**
     * Retorna el fragment del tab
     * @return
     */
    public android.support.v4.app.Fragment getFragment() {
        return fragment;
    }

    /**
     * Cambia el fragment del tab
     * @param fragment
     */
    public void setFragment(android.support.v4.app.Fragment fragment) {
        this.fragment = fragment;
    }

}