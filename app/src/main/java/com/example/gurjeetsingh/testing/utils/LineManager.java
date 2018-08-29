package com.example.gurjeetsingh.testing.utils;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gurjeetsingh on 2017-10-02.
 */

public class LineManager
{

    List<String> lineNumbers;


    public LineManager()
    {
        lineNumbers = new ArrayList<String>();
    }


    public String getLineNumbers()
    {

        return TextUtils.join("\n", lineNumbers);
    }

    public void clearLineNumbers()
    {

        lineNumbers.clear();
    }


    public void setLineNumbers(int length)
    {
        for (int i = 0; i < length; i++)
        {
            lineNumbers.add(i, Integer.toString(i));
        }
    }


    public void add(int key)
    {
        lineNumbers.add(Integer.toString(key));
    }

    public void remove(int delKey)
    {
        lineNumbers.remove(delKey);
    }

}
