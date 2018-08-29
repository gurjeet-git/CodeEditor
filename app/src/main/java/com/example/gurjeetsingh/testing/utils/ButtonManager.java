package com.example.gurjeetsingh.testing.utils;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.gurjeetsingh.testing.R;
import com.example.gurjeetsingh  .testing.classes.CollapseDetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gurjeetsingh on 2017-09-23.
 */

public class ButtonManager
{

    Context context;
    Map<Integer, ImageButton> buttons;
    Map<Integer, CollapseDetails> nodeList;
    View.OnClickListener onClickListener;
    LineManager lineManager;

    public ButtonManager(Context context, View.OnClickListener onClickListener, LineManager lineManager)
    {
        this.context = context;
        this.onClickListener = onClickListener;
        this.buttons = new HashMap<Integer, ImageButton>();
        this.nodeList = new HashMap<Integer, CollapseDetails>();
        this.lineManager = lineManager;
    }

    public void createButtons(List<String> lines)
    {
        RelativeLayout relativeLayout = (RelativeLayout) ((Activity) context).findViewById(R.id.buttonlayout);
        relativeLayout.removeAllViews();
        //buttons.clear();
        for (int i = 0; i < lines.size(); i++)
        {
            //identify functions and set the button visibility
            if (lines.get(i).contains("{") && !lines.get(i).contains("\""))
            {
                ImageButton button = new ImageButton(context);
                button.setImageResource(R.drawable.ic_remove_circle_outline_black_18dp);
                LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(58, 58);
                p.setMargins(0, 10, 0, 0);
                button.setLayoutParams(p);
                button.setId(i);
                button.setTag(false);
                button.setY(i * 58);
                button.setOnClickListener(onClickListener);
                relativeLayout.addView(button);
                buttons.put(i, button);
            }
        }
    }

    public void addButton(int lineNumber, int actualLineNumber)
    {
        RelativeLayout relativeLayout = (RelativeLayout) ((Activity) context).findViewById(R.id.buttonlayout);
        ImageButton button = new ImageButton(context);
        button.setImageResource(R.drawable.ic_remove_circle_outline_black_18dp);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(58, 58);
        button.setLayoutParams(p);
        button.setId(actualLineNumber);
        button.setTag(false);
        button.setY(lineNumber * 58);
        button.setOnClickListener(onClickListener);
        relativeLayout.addView(button);
        buttons.put(actualLineNumber, button);
    }

    public void setTag(int lineNumber)
    {
        RelativeLayout relativeLayout = (RelativeLayout) ((Activity) context).findViewById(R.id.buttonlayout);
        ImageButton button = buttons.get(lineNumber);
        button.setImageResource(R.drawable.ic_remove_circle_outline_black_18dp);
        button.setTag(false);
        buttons.put(lineNumber, button);
    }

    public void removeButton(int lineNumber)
    {
        buttons.get(lineNumber).setVisibility(View.GONE);
        buttons.remove(lineNumber);
    }

    public void moveButtonsDown(int index, int length)
    {
        List<Integer> keySet = new ArrayList<>(buttons.keySet());
        Collections.sort(keySet);
        Collections.reverse(keySet); //move the lowest button down first
        for (int key : keySet)
        {
            if (key >= index)
            {
                ImageButton button = buttons.remove(key);
                button.setId(key + length);
                button.setY((key + length) * 58);
                buttons.put(key + length, button);
            }
        }
    } //just called from the handleDelta Function

    public void moveButtonsUp(int index, int length)
    {
        List<Integer> keySet = new ArrayList<>(buttons.keySet());
        Collections.sort(keySet); //move the top button up first
        for (int key : keySet)
        {
            if (key >= index)
            {
                ImageButton button = buttons.remove(key);
                button.setId(key - length);
                button.setY((button.getY()) - length * 58);
                buttons.put(key - length, button);
            }
        }
    } //Just called from the handleDelta Function

    public void visuallyMoveButtonsUp(int index, int length)
    {
        List<Integer> keySet = new ArrayList<>(buttons.keySet());
        Collections.sort(keySet);
        for (int key : keySet)
        {
            if (key >= index)
            {
                ImageButton button = buttons.get(key);
                button.setY((button.getY() - length * 58));
            }
        }
    }

    public String getCollapsedString(List<String> indentedLines)
    {
        List<String> collapsedLines = indentedLines;
        refreshButtons();
        lineManager.clearLineNumbers();
        lineManager.setLineNumbers(collapsedLines.size());
        replaceCollapsedLines(nodeList, collapsedLines);
        return TextUtils.join("\n", collapsedLines);
    }

    private void refreshButtons()
    {  //it just adjusts and make sure the buttons are set to new locations.
        for (int key : buttons.keySet())
        {
            ImageButton button = buttons.get(key);
            button.setY(button.getId() * 58);
            button.setVisibility(View.VISIBLE);
        }
    }

    private void replaceCollapsedLines(Map<Integer, CollapseDetails> nodeList, List<String> collapsedLines)
    {
        Object[] keys = nodeList.keySet().toArray();
        Arrays.sort(keys, Collections.reverseOrder());

        for (Object temp : keys)
        {
            int key = (int) temp;
            if ((boolean) buttons.get(key).getTag())
            {   //means the tag has changed from - to +, also the image sign.
                //now just collapse it
                if (nodeList.get(key).getEndLine() != -1) //check if it actually has closing brace
                {
                    //Find space or word in the beginning of {
                    String space = collapsedLines.get(key).substring(0, collapsedLines.get(key).indexOf("{"));
                    collapsedLines.set(key, space + "{....}");
                    for (int delKey = nodeList.get(key).getEndLine(); delKey >= key + 1; delKey--)
                    {
                        collapsedLines.remove(delKey);
                        lineManager.remove(delKey);
                    }
                    visuallyMoveButtonsUp(key + 1, nodeList.get(key).getEndLine() - key); //move All the buttons up starting at index key+1 which will just be the next line after {...}
                    hideButtons(nodeList.get(key).getChildren());  //hides the nested buttons of the block collapsed
                }
            } else
            {
                replaceCollapsedLines(nodeList.get(key).getChildren(), collapsedLines);
            }


        }

        int x = 10; //debugging purpose
    }

    private void hideButtons(Map<Integer, CollapseDetails> children)
    { //recursively sets the visibility of the children to GONE
        for (int key : children.keySet())
        {
            buttons.get(key).setVisibility(View.GONE);
            hideButtons(children.get(key).getChildren());
        }
    }

    public void resetMap()
    {
        this.nodeList = new HashMap<Integer, CollapseDetails>();
    }

    public Map<Integer, CollapseDetails> getNodeListMap()
    {
        return nodeList;
    }
}
