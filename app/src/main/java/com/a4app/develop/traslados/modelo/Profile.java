package com.a4app.develop.traslados.modelo;

public class Profile {
    private String firstName;
    private String lastName;

    public Profile()
    {

    }

    public Profile(String firstName, String lastName)
    {
        this.firstName = firstName;
        this.lastName = lastName;
    }
    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }
    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }
    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}