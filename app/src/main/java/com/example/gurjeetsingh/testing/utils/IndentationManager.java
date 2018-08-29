package com.example.gurjeetsingh.testing.utils;

import android.content.Context;

import com.example.gurjeetsingh.testing.classes.CollapseDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * Created by gurjeetsingh on 2017-09-23.
 * Simply has 2 methods:
 * <p>
 * 1) getIndentedText is used to fill the braceLocation Map and operates on lines(main) as the parameter and returns
 * the indented lines
 * <p>
 * 2) getBraceLocation is used to return the braceLocation Map
 */

public class IndentationManager
{
    public static final String SPACE = "      "; //6 Characters
    Context context;
    List<String> indentedLines;
    ButtonManager buttonManager;

    public IndentationManager(Context context, ButtonManager buttonManager)
    {
        this.context = context;
        this.buttonManager = buttonManager;
    }

    public List<String> getIndentedText(List<String> lines)
    {
        buttonManager.resetMap(); //always fill the nodeList again
        //getting nodeList Reference
        Map<Integer, CollapseDetails> currentMap = buttonManager.getNodeListMap();
        Stack openingBrace = new Stack();
        indentedLines = new ArrayList(lines);
        int indexOpen, indexClose;
        for (int i = 0; i < indentedLines.size(); i++)
        {
            indexOpen = indentedLines.get(i).indexOf("{");
            indexClose = indentedLines.get(i).indexOf("}");
            if (indexOpen != -1 && indexClose != -1)
            {//found both
                openingBrace.push(i);
                if (!openingBrace.empty())
                {
                    int top = (int) openingBrace.pop();
                    for (int k = top + 1; k < i; k++)
                    {
                        indentedLines.set(k, SPACE + indentedLines.get(k));
                    }
                }
            } else if (indexClose != -1)
            { //found closing only
                if (!openingBrace.empty())
                {
                    int top = (int) openingBrace.pop();
                    for (int k = top + 1; k < i; k++)
                    {
                        indentedLines.set(k, SPACE + indentedLines.get(k));
                    }
                    Map<Integer, CollapseDetails> tempMap = buttonManager.getNodeListMap();
                    for (int g = 0; g < openingBrace.size(); g++)
                    {
                        int child = (int) openingBrace.get(g);
                        tempMap = tempMap.get(child).getChildren();
                    }
                    tempMap.get(top).setEndLine(i);
                    currentMap = tempMap;
                }
            } else if (indexOpen != -1)
            { //only opening
                openingBrace.push(i);
                currentMap.put(i, new CollapseDetails());
                currentMap = currentMap.get(i).getChildren();
            }
        }

        return indentedLines;
    }

    public int getCursorLocation(int currentLine, boolean isButtonPressed)
    {
        //Find the cursor position of placing
        int cursorPos = 0;
        if (isButtonPressed)  //getting cursor location to the start of {....} or just {
        {
            for (int i = 0; i <= currentLine; i++)
            {
                cursorPos = cursorPos + (indentedLines.get(i).length() + 1);
            }
            cursorPos = cursorPos - 1 - indentedLines.get(currentLine).trim().length();
            return cursorPos;
        } else  //on a hit of enter
        {
            for (int i = 0; i <= currentLine; i++)
            {
                cursorPos = cursorPos + (indentedLines.get(i).length() + 1);

            }
            cursorPos = (cursorPos + indentedLines.get(currentLine + 1).length()) - indentedLines.get(currentLine + 1).trim().length();
            return cursorPos;
        }
    }

    public List<String> indentateModifiedLines(List<String> lines)
    {
        Stack openingBrace = new Stack();
        indentedLines = new ArrayList(lines);

        List<String> trimmedStrings = new ArrayList<String>();
        for (String s : indentedLines)
        {
            trimmedStrings.add(s.trim());
        }

        indentedLines = trimmedStrings;
        int indexOpen, indexClose;
        for (int i = 0; i < indentedLines.size(); i++)
        {
            indexOpen = indentedLines.get(i).indexOf("{");
            indexClose = indentedLines.get(i).indexOf("}");
            if (indexOpen != -1 && indexClose != -1)
            {//found both
                openingBrace.push(i);
                if (!openingBrace.empty())
                {
                    int top = (int) openingBrace.pop();
                    for (int k = top + 1; k < i; k++)
                    {
                        indentedLines.set(k, SPACE + indentedLines.get(k));
                    }
                }
            } else if (indexClose != -1)
            { //found closing only
                if (!openingBrace.empty())
                {
                    int top = (int) openingBrace.pop();
                    for (int k = top + 1; k < i; k++)
                    {
                        indentedLines.set(k, SPACE + indentedLines.get(k));
                    }
                }
            } else if (indexOpen != -1)
            { //only opening
                openingBrace.push(i);
            }
        }
        return indentedLines;
    }

}
