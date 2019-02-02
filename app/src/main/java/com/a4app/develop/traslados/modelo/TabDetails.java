package com.a4app.develop.traslados.modelo;

<<<<<<< HEAD
public class TabDetails {
    private String tabName;
=======
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
>>>>>>> 274fc7e6b7dded5d44c1a9d13d6fa316ecaa3f3b
    private android.support.v4.app.Fragment fragment;

    public TabDetails(String tabName, android.support.v4.app.Fragment fragment) {
        this.tabName = tabName;
        this.fragment = fragment;

    }

<<<<<<< HEAD
=======
    /**
     * retorna el nombre del tab
     * @return
     */
>>>>>>> 274fc7e6b7dded5d44c1a9d13d6fa316ecaa3f3b
    public String getTabName() {
        return tabName;
    }

<<<<<<< HEAD
=======
    /**
     * Cambia el nombre del tab
     * @param tabName
     */
>>>>>>> 274fc7e6b7dded5d44c1a9d13d6fa316ecaa3f3b
    public void setTabName(String tabName) {
        this.tabName = tabName;
    }

<<<<<<< HEAD
=======
    /**
     * Retorna el fragment del tab
     * @return
     */
>>>>>>> 274fc7e6b7dded5d44c1a9d13d6fa316ecaa3f3b
    public android.support.v4.app.Fragment getFragment() {
        return fragment;
    }

<<<<<<< HEAD
=======
    /**
     * Cambia el fragment del tab
     * @param fragment
     */
>>>>>>> 274fc7e6b7dded5d44c1a9d13d6fa316ecaa3f3b
    public void setFragment(android.support.v4.app.Fragment fragment) {
        this.fragment = fragment;
    }

}