package com.duo.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TreeBean {

    private String id;
    private String pId;
    private String name;
    private boolean parent;
    private Object obj;


    public boolean getIsParent() {
        return parent;
    }

}
