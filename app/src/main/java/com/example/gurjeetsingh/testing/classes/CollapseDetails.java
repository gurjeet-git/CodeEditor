package com.example.gurjeetsingh.testing.classes;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gurjeetsingh on 2017-09-23.
 */

public class CollapseDetails {

    Map<Integer, CollapseDetails> children;
    int endLine;

    public CollapseDetails(){
        children = new HashMap<Integer, CollapseDetails>();
        this.endLine = -1;
    }

    public Map<Integer, CollapseDetails> getChildren() {
        return children;
    }

    public void setEndLine(int endLine) {
        this.endLine = endLine;
    }

    public int getEndLine() {
        return endLine;
    }

}
