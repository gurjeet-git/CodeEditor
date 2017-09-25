package com.example.gurjeetsingh.testing.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.gurjeetsingh.testing.R;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by gurjeetsingh on 2017-09-23.
 */

public class ButtonManager {
    Context context;
    Map<Integer, ImageButton> buttons;
    Map<Integer, List<String>> collapsedStrings;

    public ButtonManager(Context context) {
        this.context = context;
        this.buttons = new HashMap<Integer, ImageButton>();
        this.collapsedStrings = new HashMap<Integer, List<String>>();
    }

    public void createButtons(List<String> lines, View.OnClickListener onClickListener) {
        RelativeLayout relativeLayout = (RelativeLayout) ((Activity)context).findViewById(R.id.buttonlayout);
        relativeLayout.removeAllViews();
        //buttons.clear();

        for (int i = 0; i<lines.size();i++) {
            //identify functions and set the button visibility
            if (lines.get(i).contains("{") && !buttons.containsKey(i)) {
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

    public void moveButtonsDown(int index) {
        Set<Integer> keySet = new HashSet(buttons.keySet());

        for(int key : keySet) {
            if(key >= index){
                ImageButton button = buttons.remove(key);
                button.setId(key+1);
                button.setY((key+1) * 58);
                buttons.put(key+1, button);
            }
        }
    }

    public void moveButtonsUp(int index) {
        Set<Integer> keySet = new HashSet(buttons.keySet());

        for(int key : keySet) {
            if(key >= index){
                ImageButton button = buttons.remove(key);
                button.setId(key-1);
                button.setY((key-1) * 58);
                buttons.put(key-1, button);
            }
        }
    }

    public Map<Integer, List<String>> getCollapsedStrings() {
        return collapsedStrings;
    }
}
