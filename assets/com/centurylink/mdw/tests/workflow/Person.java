package com.centurylink.mdw.tests.workflow;

import io.swagger.annotations.ApiModelProperty;
import org.json.JSONException;
import org.json.JSONObject;

import com.centurylink.mdw.model.Jsonable;

/**
 * JavaBean for testing Jsonable and Yaml variables.
 */
public class Person implements Jsonable {

    private String firstName;
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String first) {
        this.firstName = first;
    }

    private String lastName;
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String last) {
        this.lastName = last;
    }

    @ApiModelProperty(allowableValues = "Developer,Tester")
    private String occupation;
    public String getOccupation() {
        return occupation;
    }
    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }


    /**
     * no-arg constructor required for yaml
     */
    public Person() {
    }

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Jsonable requires this constructor
     */
    public Person(JSONObject json) throws JSONException {
        firstName = json.getString("firstName");
        lastName = json.getString("lastName");
        if (json.has("occupation"))
            occupation = json.getString("occupation");
    }

    @Override
    public JSONObject getJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("firstName", firstName);
        json.put("lastName", lastName);
        if (occupation != null)
            json.put("occupation", occupation);
        return json;
    }

    @Override
    public String getJsonName() {
        return "person";
    }
}
