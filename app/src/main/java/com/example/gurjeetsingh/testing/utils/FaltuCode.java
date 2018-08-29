package com.example.gurjeetsingh.testing.utils;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

import java.util.Arrays;

/**
 * Created by gurjeetsingh on 2017-11-05.
 */

/*
public void handleButtonClick(int lineNumber)
        {
        List<String> indentedLines = indentationManager.getIndentedText(lines);
        String collapsedString = buttonManager.getCollapsedString(indentedLines);
        SpannableString coloredIntendedLines = colorManager.getColoredString(collapsedString);

        // indentationManager.indentateModifiedLines()
//        IndentationManager tempOb = new IndentationManager();
//        tempOb.indentateModifiedLines(Arrays.asList(collapsedString.split("\n", -1)));

        cursorPosition = indentationManager.getCursorLocation(lineNumber, true);
        updateEditText(coloredIntendedLines);
        updateLineNumberText(lineManager.getLineNumbers());
        }


      public void handleEnter(int lineNumber)
    {
        IndentationManager tempOb = new IndentationManager();
        tempOb.indentateModifiedLines(Arrays.asList(mainEditText.getText().toString().split("\n", -1)));
        int a = tempOb.getCursorLocation(lineNumber, false);
        cursorPosition = a;
        process();
    }


    public static void setMaroonColorSingleQuote(String singleQuote[], SpannableString sp)
    {

        int startIndex = 0;
        int lastIndex = -1;
        while (true)
        {
            startIndex = sp.toString().indexOf(singleQuote[0], lastIndex + 1);
            lastIndex = sp.toString().indexOf(singleQuote[0], startIndex + 1);
            int endOfLine = sp.toString().indexOf("\n", startIndex + 1);
            if (startIndex == -1)
            {
                break;
            }
            if (lastIndex != -1 && lastIndex < endOfLine) // if closing double is on one line only
            {
                int extraQuote = sp.toString().indexOf(singleQuote[0], lastIndex + 1);
                if (extraQuote != -1 && extraQuote < endOfLine)
                {
                    sp.setSpan(new ForegroundColorSpan(Color.rgb(139, 0, 139)), startIndex, extraQuote + 1, 0);
                    lastIndex = extraQuote;
                } else
                {
                    sp.setSpan(new ForegroundColorSpan(Color.rgb(139, 0, 139)), startIndex, lastIndex + 1, 0);
                }
            } else
            {
                lastIndex = endOfLine;
                sp.setSpan(new ForegroundColorSpan(Color.rgb(139, 0, 139)), startIndex, endOfLine, 0);
            }
        }
    }


    //new tried code but not working
    //Maroon for character Literals
    public static void setMaroonColorSingleQuote(String singleQuote[], SpannableString sp)
    {
        int startIndex = 0;
        int lastIndex = -1;
        while (true)
        {
            startIndex = sp.toString().indexOf(singleQuote[0], lastIndex + 1);
            lastIndex = sp.toString().indexOf(singleQuote[0], startIndex + 1);
            int endOfLine = sp.toString().indexOf("\n", startIndex + 1);
            if (startIndex == -1)
            {
                break;
            }
            if (endOfLine == -1)
            {
                sp.setSpan(new ForegroundColorSpan(Color.rgb(139, 0, 139)), startIndex, sp.length(), 0);
                break;
            }
            if (lastIndex == -1)
            {
                sp.setSpan(new ForegroundColorSpan(Color.rgb(139, 0, 139)), startIndex, endOfLine, 0);
                break;
            } else
            {
                sp.setSpan(new ForegroundColorSpan(Color.rgb(139, 0, 139)), startIndex, lastIndex, 0);
            }
        }
    }
     */
public class FaltuCode {


//if(modifiedLines.get(lineNumber).equals("")){lines.set(lineNumber,modifiedLines.get(lineNumber)); }
//  if(modifiedLines.get(lineNumber).equals("")){lines.add(lineNumber,modifiedLines.get(lineNumber)); buttonManager.moveButtonsDown(lineNumber + i, 1);}
//
//
//    // Handle Addition logic
//    int updatedLineCountP = positiveDelta.split("\n", -1).length;
////            lines.set(lineNumber, modifiedLines.get(lineNumber));    //only 1 character is added. No newLine
//
//            if (positiveDelta.equals("{")) {
//                    buttonManager.addButton(lineNumber);
//                    }//Just add a button
//
////            if (updatedLineCountP >= 2) {  // more than 2 characters separated by a Line ("/n")
////                for (int i = 1; i < updatedLineCountP; i++) {
////                    lines.add(lineNumber + i, modifiedLines.get(lineNumber + i));
////                    buttonManager.moveButtonsDown(lineNumber + i, 1);
////                }
////            }
}



//    public void handleEnter(int lineNumber) {
////        indentationManager.getIndentedText(lines);
////        cursorPosition = indentationManager.getCursorLocation(lineNumber);
////        process();
////
////        IndentationManager tempOb = new IndentationManager();
////        tempOb.indentateModifiedLines(Arrays.asList(mainEditText.getText().toString().split("\n", -1)));
////        cursorPosition = tempOb.getCursorLocation(lineNumber);
////        process();
//    }


//{
//        // Handle Addition logic
//        int updatedLineCountP = positiveDelta.split("\n", -1).length;
//        int actualLineNumber;
//
//        try {                       //To be removed Later
//        actualLineNumber = Integer.parseInt(lineManager.lineNumbers.get(lineNumber + 1));
//        } catch (Exception e) {
//        actualLineNumber = lines.size();
//        }
//
//        if (positiveDelta.equals("{")) {  //Just add a new button
//        buttonManager.addButton(lineNumber, actualLineNumber - 1);
//        lineManager.add(actualLineNumber);
//        }
//
//        if (updatedLineCountP == 1) {   //only 1 character is added. No newLine
//        lines.set(actualLineNumber - 1, modifiedLines.get(lineNumber));
//        } else {  // more than 2 characters separated by a Line ("/n")
//
//
//        //Case 2: middle of the line
//
//        if (modifiedLines.get(lineNumber).equals("")) {  //Case 3: Start of the line
//        actualLineNumber = Integer.parseInt(lineManager.lineNumbers.get(lineNumber));
//        lines.add(actualLineNumber, modifiedLines.get(lineNumber));
//        buttonManager.moveButtonsDown(actualLineNumber, 1);
//        }
//
//        // if (modifiedLines.get(lineNumber + 1).equals(""))
//        else{  //Case 1: If enter has been hit after the end of the line
//        lines.add(actualLineNumber, modifiedLines.get(lineNumber+1));
//        buttonManager.moveButtonsDown(actualLineNumber, 1);
//        }
//
//
////                lines.set(actualLineNumber - 1, modifiedLines.get(lineNumber));
////                for (int i = 1; i < updatedLineCountP; i++) {
////                    if (lineNumber + 1 == actualLineNumber) {
////                        lines.add(lineNumber + i, modifiedLines.get(lineNumber + i));
////                        buttonManager.moveButtonsDown(lineNumber + i, 1);
////                    } else {
////                        lines.add(actualLineNumber, modifiedLines.get(lineNumber + i));
////                        buttonManager.moveButtonsDown(actualLineNumber, 1);
////                    }
////                }
//        }
//
//        }


//else if (isNegativeDelta) {
//        int updatedLineCountN = negativeDelta.split("\n", -1).length;
//
//        int actualLineNumber = Integer.parseInt(lineManager.lineNumbers.get(lineNumber));
//
//        if (updatedLineCountN == 1) {
//        if (modifiedLines.get(lineNumber).contains("..")) {
//        buttonManager.setTag(actualLineNumber);
//        process();
//        } else {
//        lines.set(actualLineNumber, modifiedLines.get(lineNumber));
//        if (negativeDelta.equals("{")) {  //Just remove the button  //Also delete the button itself.
//        buttonManager.removeButton(actualLineNumber);
//        }
//        }
//        } else {
//        actualLineNumber = Integer.parseInt(lineManager.lineNumbers.get(lineNumber + 1));
//        for (int i = updatedLineCountN; i > 1; i--) {
//        lines.set(actualLineNumber - 1, modifiedLines.get(lineNumber));
//        lines.remove(actualLineNumber);
//        buttonManager.moveButtonsUp(actualLineNumber, 1);
//        process();
//        }
//        }
//        }

/* color <code></code>

 //Code for Blue key words
        for (int i = 0; i < keyWordsBlue.length; i++) {
            lastIndex = 0;
            wordLength = keyWordsBlue[i].length();
            while (lastIndex != -1) {
                lastIndex = sp.toString().indexOf(keyWordsBlue[i], lastIndex);
                if (lastIndex != -1) {
                    char a = sp.toString().charAt(lastIndex + wordLength);
                    if (((int) a >= 32 & (int) a <= 47) | ((int) a >= 58 & (int) a <= 64) | ((int) a >= 91 & (int) a <= 96) | ((int) a >= 123 & (int) a <= 126)) {
                        sp.setSpan(new ForegroundColorSpan(Color.BLUE), lastIndex, lastIndex + wordLength, 0);
                        lastIndex = lastIndex + keyWordsBlue[i].length();
                    } else {
                        break;
                    }
                }
            }
        }
        //End of this functionality

        //Code for Dark Orange
        String closingBrace[] = {"{....}", "^"};
        for (int i = 0; i < closingBrace.length; i++) {
            int startIndex = 0;
            while (true) {
                int a = sp.toString().indexOf(closingBrace[i], startIndex);
                if (a != -1) {
                    sp.setSpan(new ForegroundColorSpan(Color.rgb(238, 118, 0)), a, a + 6, 0);
                    startIndex = a + 4;
                } else {
                    break;
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
                        sp.setSpan(new ForegroundColorSpan(Color.rgb(139, 0, 139)), lastIndex, lastIndex + wordLength, 0);
                        lastIndex = lastIndex + keyWordsMaroon[i].length();
                    } else {
                        break;
                    }
                }
            }
        }

        //code for " " (double quotes)
        String quote = "\"";
        int startIndex = 0;
        lastIndex = -1;
        while (true) {
            startIndex = sp.toString().indexOf(quote, lastIndex + 1);
            lastIndex = sp.toString().indexOf(quote, startIndex + 1);
            int endOfLine = sp.toString().indexOf("\n", startIndex + 1);
            if (startIndex == -1) {
                break;
            }
            if (lastIndex != -1 && lastIndex < endOfLine) {
                int extraQuote = sp.toString().indexOf(quote, lastIndex + 1);
                if (extraQuote != -1 && extraQuote < endOfLine) {
                    sp.setSpan(new ForegroundColorSpan(Color.rgb(139, 0, 139)), startIndex, extraQuote + 1, 0);
                    lastIndex = extraQuote;
                } else {
                    sp.setSpan(new ForegroundColorSpan(Color.rgb(139, 0, 139)), startIndex, lastIndex + 1, 0);
                }
            } else {
                lastIndex = endOfLine;
                sp.setSpan(new ForegroundColorSpan(Color.rgb(139, 0, 139)), startIndex, endOfLine, 0);
            }
        }

//        code for '' (single quotes)
//        quote = "'";
//        lastIndex = -1;
//        while (true) {
//            startIndex = sp.toString().indexOf(quote, lastIndex + 1);
//            lastIndex = sp.toString().indexOf(quote, startIndex + 1);
//            int endOfLine = sp.toString().indexOf("\n", startIndex);
//            if (startIndex == -1) {
//                break;
//            }
//            if (lastIndex != -1 && lastIndex < endOfLine) {
//                sp.setSpan(new ForegroundColorSpan(Color.rgb(139, 0, 139)), startIndex, lastIndex + 1, 0);
//            } else {
//                lastIndex = endOfLine;
//                sp.setSpan(new ForegroundColorSpan(Color.rgb(139, 0, 139)), startIndex, endOfLine, 0);
//            }
//        }


        //End of Code for coloring keywords, comments etc

        //code for escape sequences
        String escapeSequence[] = {"\\\\", "\\t", "\\b", "\\n", "\\r", "\\f", "\\'", "\\\""};
        for (int i = 0; i < escapeSequence.length; i++) {
            startIndex = 0;
            while (true) {
                int a = sp.toString().indexOf(escapeSequence[i], startIndex);
                if (a != -1) {
                    sp.setSpan(new ForegroundColorSpan(Color.BLUE), a, a + 2, 0);
                    startIndex = a + 2;
                } else {
                    break;
                }
            }
        }


        //code for // (double quotes)

        lastIndex = -1;
        while (true) {
            startIndex = sp.toString().indexOf(doubleSlash, lastIndex + 1);
            if (startIndex == -1) {
                break;
            }
            lastIndex = sp.toString().indexOf("\n", startIndex + 1);
            if(lastIndex==-1)
                break;
            sp.setSpan(new ForegroundColorSpan(Color.rgb(0, 100, 0)), startIndex, lastIndex, 0);
        }

        return sp;
    }
 */

//for (int i = 0; i < lines.size(); i++)
//        {
//        if (lines.get(i).indexOf(doubleSlash[0]) == 0)
//        {
//        currentPointer = setGreen(sp, 0);
//        continue;
//        }
//        else if (lines.get(i).indexOf(closeBrace[0]) == 0){
//        currentPointer = setOrange(sp, 0);
//        continue;
//        }
//        else
//        {
//
//        }
//
//        }

//    public static int setGreen(SpannableString sp, int start)
//    {
//        int lastIndex = sp.toString().indexOf("\n", start);
//        sp.setSpan(new ForegroundColorSpan(Color.rgb(0, 100, 0)), start, lastIndex + 1, 0);
//        return lastIndex + 1;
//    }
//
//    public static int setOrange(SpannableString sp, int start)
//    {
//        int lastIndex = sp.toString().indexOf("\n", start);
//        sp.setSpan(new ForegroundColorSpan(Color.rgb(238, 118, 0)), start, start + 6, 0);
//        return lastIndex + 1;
//    }
//
//    public static int findLocation(String[] words, String line, int location)
//    {
//        for (int i = 0; i < 5; i++)
//        {
//
//        }
//        return location;
//    }


//Code for maroon key words
//        String keyWordsMaroon[] = {"String", "System"};
//        for (int i = 0; i < keyWordsMaroon.length; i++) {
//            lastIndex = 0;
//            wordLength = keyWordsMaroon[i].length();
//            while (lastIndex != -1) {
//                lastIndex = sp.toString().indexOf(keyWordsMaroon[i], lastIndex);
//                if (lastIndex != -1) {
//                    char a = sp.toString().charAt(lastIndex + wordLength);
//                    if (((int) a >= 32 & (int) a <= 47) | ((int) a >= 58 & (int) a <= 64) | ((int) a >= 91 & (int) a <= 96) | ((int) a >= 123 & (int) a <= 126)) {
//                        sp.setSpan(new ForegroundColorSpan(Color.rgb(139, 0, 139)), lastIndex, lastIndex + wordLength, 0);
//                        lastIndex = lastIndex + keyWordsMaroon[i].length();
//                    } else {
//                        break;
//                    }
//                }
//            }
//        }


//        code for '' (single quotes)
//        quote = "'";
//        lastIndex = -1;
//        while (true) {
//            startIndex = sp.toString().indexOf(quote, lastIndex + 1);
//            lastIndex = sp.toString().indexOf(quote, startIndex + 1);
//            int endOfLine = sp.toString().indexOf("\n", startIndex);
//            if (startIndex == -1) {
//                break;
//            }
//            if (lastIndex != -1 && lastIndex < endOfLine) {
//                sp.setSpan(new ForegroundColorSpan(Color.rgb(139, 0, 139)), startIndex, lastIndex + 1, 0);
//            } else {
//                lastIndex = endOfLine;
//                sp.setSpan(new ForegroundColorSpan(Color.rgb(139, 0, 139)), startIndex, endOfLine, 0);
//            }
//        }


//End of Code for coloring keywords, comments etc