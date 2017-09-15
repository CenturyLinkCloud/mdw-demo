package com.centurylink.mdw.demo.bugs;
    
import javax.validation.constraints.Size;

import org.json.JSONObject;

import com.centurylink.mdw.model.Jsonable;

import io.swagger.annotations.ApiModelProperty;

public class Bug implements Jsonable {
        
    public Bug(JSONObject json) {
        bind(json);
    }

    public Bug(Long id, JSONObject json) {
    	this(json);
    	this.id = id;
    }
    
    @ApiModelProperty(readOnly=true)
    private Long id;
    public Long getId() { return id; }
    
    @ApiModelProperty(value="Headline bug information", required=true)
    @Size(max=512)    
    private String title;
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    private String description;
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
        
    public String toString() {
        return getJson().toString(2);
    }
}