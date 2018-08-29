package com.example.gurjeetsingh.testing.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.ImageButton;

import com.example.gurjeetsingh.testing.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by gurjeetsingh on 2017-11-21.
 */


public class TestManager
{
    Context context;
    static Map<String, Integer> temp;

    public TestManager(Context context)
    {
        this.context = context;
    }

    String keyWordsBlue[] = {
            "abstract", "continue", "for", "new", "switch"
            , "assert", "default", "goto", "package", "synchronized",
            "boolean", "do", "if", "private", "this", "break", "double", "implements", "protected", "throw",
            "byte", "else", "import", "public", "throws", "case", "enum", "instanceof", "return", "transient",
            "catch", "extends", "int", "short", "try", "char", "final", "interface", "static", "void",
            "class", "finally", "long", "strictfp", "volatile", "const", "float", "native", "super", "while", "true", "false"
    };

    public void printString()
    {

    }

    /*
    {
        //Code for Blue key words
        int startIndex = 0;
        int lastIndex = 0;
        int wordLength = 0;
        for (int i = 0; i < keyWordsBlue.length; i++)
        {
            lastIndex = 0;
            wordLength = keyWordsBlue[i].length();
            while (lastIndex != -1)
            {
                lastIndex = sp.toString().indexOf(keyWordsBlue[i], lastIndex);
                if (lastIndex != -1)
                {
                    char before;
                    char after;


                    char a = sp.toString().charAt(lastIndex + wordLength);
                    if (((int) a >= 32 & (int) a <= 47) | ((int) a >= 58 & (int) a <= 64) | ((int) a >= 91 & (int) a <= 96) | ((int) a >= 123 & (int) a <= 126))
                    {
                        sp.setSpan(new ForegroundColorSpan(Color.BLUE), lastIndex, lastIndex + wordLength, 0);
                        lastIndex = lastIndex + keyWordsBlue[i].length();
                    } else
                    {
                        break;
                    }
                }
            }
        }
    } */


}
///*
//    public static void setMaroonForClasses(SpannableString sp)
//    {
//
//        String s = sp.toString();
//        String[] classNames = s.split("\\W");
//
//        int lastIndex = 0;
//        for (int i = 0; i < classNames.length; i++)
//        {
//            if (!classNames[i].equals("") && (int) classNames[i].charAt(0) >= 65 && (int) classNames[i].charAt(0) <= 90 && mapOfClasses.containsKey(classNames[i]))
//            {
//                lastIndex = sp.toString().indexOf(classNames[i], lastIndex);
//                if (lastIndex != -1)
//                {
//                    sp.setSpan(new ForegroundColorSpan(Color.rgb(139, 0, 139)), lastIndex, lastIndex + classNames[i].length(), 0);
//                    lastIndex = lastIndex + classNames[i].length();
//                }
//            }
//        }
//
//
//        for (int i = 0; i < classNames.length; i++)
//        {
//            if (!classNames[i].equals("") && (int) classNames[i].charAt(0) >= 65 && (int) classNames[i].charAt(0) <= 90)
//            {
//                int lastIndex = 0;
//                while (lastIndex != -1)
//                {
//                    lastIndex = sp.toString().indexOf(classNames[i], lastIndex);
//                    if (lastIndex != -1)
//                    {
//                        sp.setSpan(new ForegroundColorSpan(Color.rgb(139, 0, 139)), lastIndex, lastIndex + classNames[i].length(), 0);
//                        lastIndex = lastIndex + classNames[i].length();
//                    }
//                }
//            }
//        }
//
//
//    */



/*
    public static void setColor(EditText mainEditText) {
        //Start of Code for coloring keywords, comments etc
        // int cPos = mainEditText.getSelectionEnd();
        //   mainEditText.setText(mainEditText.getText().toString()); //to do the whole text black and set it to new colors
        SpannableString sp = new SpannableString(mainEditText.getText());
        int lastIndex = 0;
        int wordLength = 0;

        //Code for Blue key words
        String keyWordsBlue[] = {"public", "import", "static", "class", "if", "else", "for", "while"};
        for (int i = 0; i < keyWordsBlue.length; i++) {
            lastIndex = 0;
            wordLength = keyWordsBlue[i].length();
            while (lastIndex != -1) {
                lastIndex = sp.toString().indexOf(keyWordsBlue[i], lastIndex);
                if (lastIndex != -1) {
                    char a = sp.toString().charAt(lastIndex + wordLength);
                    if (((int) a >= 32 & (int) a <= 47) | ((int) a >= 58 & (int) a <= 64) | ((int) a >= 91 & (int) a <= 96) | ((int) a >= 123 & (int) a <= 126)) {
                        sp.setSpan(new ForegroundColorSpan(Color.BLUE), lastIndex, lastIndex + wordLength, 0);
                        mainEditText.setText(sp);
                        lastIndex = lastIndex + keyWordsBlue[i].length();
                    } else {
                        break;
                    }
                }
            }
        }
        //End of this functionality

        //Code for maroon key words
        String keyWordsMaroon[] = {"String", "System"};
        for (int i = 0; i < keyWordsMaroon.length; i++) {
            lastIndex = 0;
            wordLength = keyWordsMaroon[i].length();
            while (lastIndex != -1) {
                lastIndex = sp.toString().indexOf(keyWordsMaroon[i], lastIndex);
                if (lastIndex != -1) {
                    char a = sp.toString().charAt(lastIndex + wordLength);
                    if (((int) a >= 32 & (int) a <= 47) | ((int) a >= 58 & (int) a <= 64) | ((int) a >= 91 & (int) a <= 96) | ((int) a >= 123 & (int) a <= 126)) {
                        sp.setSpan(new ForegroundColorSpan(Color.rgb(165, 42, 42)), lastIndex, lastIndex + wordLength, 0);
                        mainEditText.setText(sp);
                        lastIndex = lastIndex + keyWordsMaroon[i].length();
                    } else {
                        break;
                    }
                }
            }
        }

        //code for " " (double quotes)
        int startIndex = 0;
        lastIndex = -1;
        while (true) {
            startIndex = sp.toString().indexOf(quote, lastIndex + 1);
            lastIndex = sp.toString().indexOf(quote, startIndex + 1);
            int endOfLine = sp.toString().indexOf("\n", startIndex);
            if (startIndex == -1) {
                break;
            }
            if (lastIndex != -1 && lastIndex < endOfLine) {
                sp.setSpan(new ForegroundColorSpan(Color.rgb(139, 0, 139)), startIndex, lastIndex + 1, 0);
                mainEditText.setText(sp);

            } else {

                lastIndex = endOfLine;
                sp.setSpan(new ForegroundColorSpan(Color.rgb(139, 0, 139)), startIndex, endOfLine, 0);
                mainEditText.setText(sp);
            }
        }


        //code for '' (single quotes)
        quote = "'";
        lastIndex = -1;

        while (true) {
            startIndex = sp.toString().indexOf(quote, lastIndex + 1);
            lastIndex = sp.toString().indexOf(quote, startIndex + 1);
            int endOfLine = sp.toString().indexOf("\n", startIndex);
            if (startIndex == -1) {
                break;
            }
            if (lastIndex != -1 && lastIndex < endOfLine) {
                sp.setSpan(new ForegroundColorSpan(Color.rgb(139, 0, 139)), startIndex, lastIndex + 1, 0);
                mainEditText.setText(sp);
                //startIndex++;
            } else {

                lastIndex = endOfLine;
                sp.setSpan(new ForegroundColorSpan(Color.rgb(139, 0, 139)), startIndex, endOfLine, 0);
                mainEditText.setText(sp);
                // startIndex++;
                //break;
            }
        }

        //code for // (double quotes)
        String doubleSlash = "//";
        lastIndex = -1;
        while (true) {

            startIndex = sp.toString().indexOf(doubleSlash, lastIndex + 1);
            if (startIndex == -1) {
                break;
            }
            lastIndex = sp.toString().indexOf("\n", startIndex + 1);

            sp.setSpan(new ForegroundColorSpan(Color.rgb(0, 100, 0)), startIndex, lastIndex, 0);
            mainEditText.setText(sp);
            //break;
        }
        //End of Code for coloring keywords, comments etc

        //code for escape sequences
        String escapeSequence[] = {"\\t", "\\b", "\\n", "\\r", "\\f", "\\'", "\\\"", "\\\\"};
        startIndex = 0;

        sp = new SpannableString(mainEditText.getText());
        for (int i = 0; i < escapeSequence.length; i++) {
            while (true) {
                int a = sp.toString().indexOf(escapeSequence[i], startIndex);
                if (a != -1) {
                    sp.setSpan(new ForegroundColorSpan(Color.BLUE), a, a + 2, 0);
                    mainEditText.setText(sp);
                    startIndex = startIndex + 2;

                    if (escapeSequence[i] == "\\\"") {
                        int temp = sp.toString().indexOf(escapeSequence[i], a + 2);
                        if (temp != -1) {
                            sp.setSpan(new ForegroundColorSpan(Color.YELLOW), a + 2, temp, 0);
                            mainEditText.setText(sp);
                        }
                    }
                } else {
                    break;
                }
            }
        }

        //set the position of the cursor back to original
        //  mainEditText.setSelection(cPos);
    }
*/