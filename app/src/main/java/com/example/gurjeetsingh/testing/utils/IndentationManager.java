package com.example.gurjeetsingh.testing.utils;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 * Created by gurjeetsingh on 2017-09-23.
 * Simply has 2 methods:
 *
 * 1) getIndentedText is used to fill the braceLocation Map and operates on lines(main) as the parameter and returns
 *   the indented lines
 *
 * 2) getBraceLocation is used to return the braceLocation Map
 */

public class IndentationManager {
    public static final String SPACE = "      "; //6 Characters
    Context context;
    HashMap<Integer,Integer> braceLocation;

    public IndentationManager(Context context) {
        this.context = context;
    }

    int closingFunction = 0;

    public String getIndentedText(List<String> lines) {
        braceLocation = new HashMap<Integer, Integer>();
        Stack openingBrace;
        closingFunction = 0;
        List<String> indentedLines = new ArrayList(lines);

        openingBrace = new Stack();
        for (int i = 0; i < indentedLines.size(); i++) {
            int indexOpen = 0;
            int indexClose = 0;
            while (true) {
                indexOpen = indentedLines.get(i).toString().indexOf("{", indexOpen);
                indexClose = indentedLines.get(i).toString().indexOf("}", indexClose);

                if (indexOpen == -1 && indexClose == -1) { //none
                    break;
                }
                if (indexOpen == -1 && indexClose != -1) //found closing only
                {
                    if (!openingBrace.empty()) {
                        int top = (int) openingBrace.pop();
                        for (int k = top + 1; k < i; k++) {
                            indentedLines.set(k, SPACE + indentedLines.get(k));
                        }
                        braceLocation.put(top ,i);
                        indexClose++;
                        closingFunction++;
                    }
                }
                if (indexOpen != -1 && indexClose == -1) //only opening
                {
                    openingBrace.push(i);
                    indexOpen++;
                }

                if (indexOpen != -1 && indexClose != -1) //found both
                {
                    // mainEditText.append("hello");
                    openingBrace.push(i);
                    indexOpen++;

                    if (!openingBrace.empty()) {
                        int top = (int) openingBrace.pop();
                        for (int k = top + 1; k < i; k++) {
                            indentedLines.set(k, SPACE + indentedLines.get(k));
                        }
                        braceLocation.put(top ,i);
                        indexClose++;
                        closingFunction++;
                    }
                }
                break;
            }
        }
        return TextUtils.join("\n", indentedLines);
    }

    public HashMap<Integer, Integer> getBraceLocation() {
        return braceLocation;
    }
}
