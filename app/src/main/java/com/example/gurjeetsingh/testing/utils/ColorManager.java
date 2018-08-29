package com.example.gurjeetsingh.testing.utils;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

import com.example.gurjeetsingh.testing.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gurjeetsingh on 2017-09-23.
 */
public class ColorManager
{
    Context context;
    Map<String, Integer> mapOfClasses;
    String closedBraces[] = {"{....}"};
    String doubleQuote[] = {"\""};
    String escapeSequence[] = {"\\\\", "\\t", "\\b", "\\n", "\\r", "\\f", "\\\'", "\\\""};
    String doubleSlash[] = {"//"};
    String singleQuote[] = {"\'"};
    String[] classNames;
    SpannableString sp;
    String[] keyWordsBlue;

    public ColorManager(Context context)
    {
        this.context = context;
        classNames = context.getResources().getStringArray(R.array.ListOfClasses);
        keyWordsBlue = context.getResources().getStringArray(R.array.ListOfKeyWords);
        mapOfClasses = new HashMap<String, Integer>();
        for (int i = 0; i < classNames.length; i++)
        {
            mapOfClasses.put(classNames[i], i);
        }
    }

    public SpannableString getColoredString(String mainEditText)
    {
        sp = new SpannableString(mainEditText);

        setBlue(keyWordsBlue, sp);
        setOrange(closedBraces, sp);
        setMaroonColorDoubleQuote(doubleQuote, sp);
        setMaroonColorSingleQuote(singleQuote, sp);
        setMaroonClass(classNames, sp);
        setEscapeSequenceColor(escapeSequence, sp);
        setGreenDoubleSlash(doubleSlash, sp);
        setGreenMultiLineComment(sp);

        return sp;
    }

    //Blue for 53 reserved java keywords
    public static void setBlue(String keyWordsBlue[], SpannableString sp)
    {
        int startIndex;  //where the search is started
        int lastIndex;  //where the element is found
        for (int i = 0; i < keyWordsBlue.length; i++)
        {
            lastIndex = 0;
            startIndex = 0;
            int wordLength = keyWordsBlue[i].length();  //to be used later
            while (startIndex != -1)
            {
                startIndex = sp.toString().indexOf(keyWordsBlue[i], lastIndex);
                lastIndex = startIndex + wordLength;
                if (startIndex != -1)
                {
                    char before;
                    char after;
                    if (startIndex == 0)  //word is found in the beginning of the program
                    {
                        if (lastIndex == sp.toString().length()) //nothing is there after this word
                        {
                            sp.setSpan(new ForegroundColorSpan(Color.BLUE), startIndex, lastIndex, 0);
                            break;
                        } else //word starts at index 0 but some charcters are there after it
                        {
                            after = sp.toString().charAt(lastIndex);
                            if (!(((int) after >= 48 && (int) after <= 57) || ((int) after >= 65 && (int) after <= 90) || ((int) after >= 97 && (int) after <= 122)))
                            {
                                sp.setSpan(new ForegroundColorSpan(Color.BLUE), startIndex, lastIndex, 0);
                            }
                        }
                    } else
                    {
                        if (lastIndex == sp.toString().length()) //nothing is there after this word
                        {
                            before = sp.toString().charAt(startIndex - 1);
                            if (!(((int) before >= 48 && (int) before <= 57) || ((int) before >= 65 && (int) before <= 90) || ((int) before >= 97 && (int) before <= 122)))
                            {
                                sp.setSpan(new ForegroundColorSpan(Color.BLUE), startIndex, lastIndex, 0);
                                break;
                            }
                        } else  //word is there something in the middle
                        {
                            before = sp.toString().charAt(startIndex - 1);
                            after = sp.toString().charAt(lastIndex);
                            boolean flag1 = (!(((int) after >= 48 && (int) after <= 57) || ((int) after >= 65 && (int) after <= 90) || ((int) after >= 97 && (int) after <= 122)));
                            boolean flag2 = (!(((int) before >= 48 && (int) before <= 57) || ((int) before >= 65 && (int) before <= 90) || ((int) before >= 97 && (int) before <= 122)));
                            if (flag1 && flag2)
                            {
                                sp.setSpan(new ForegroundColorSpan(Color.BLUE), startIndex, lastIndex, 0);
                            }
                        }
                    }
                }
            }
        }
    }

    //Maroon for ~4000 JDK 7.0 version classes
    public static void setMaroonClass(String classNames[], SpannableString sp)
    {
        int startIndex;  //where the search is started
        int lastIndex;  //where the element is found
        for (int i = 0; i < classNames.length; i++)
        {
            lastIndex = 0;
            startIndex = 0;
            int wordLength = classNames[i].length();  //to be used later
            while (startIndex != -1)
            {
                startIndex = sp.toString().indexOf(classNames[i], lastIndex);
                lastIndex = startIndex + wordLength;
                if (startIndex != -1)
                {
                    char before;
                    char after;
                    if (startIndex == 0)
                    {
                        if (lastIndex == sp.toString().length())
                        {
                            sp.setSpan(new ForegroundColorSpan(Color.rgb(139, 0, 139)), startIndex, lastIndex, 0);
                            break;
                        } else
                        {
                            after = sp.toString().charAt(lastIndex);
                            if (!(((int) after >= 48 && (int) after <= 57) || ((int) after >= 65 && (int) after <= 90) || ((int) after >= 97 && (int) after <= 122)))
                            {
                                sp.setSpan(new ForegroundColorSpan(Color.rgb(139, 0, 139)), startIndex, lastIndex, 0);
                            }
                        }
                    } else
                    {
                        if (lastIndex == sp.toString().length())
                        {
                            before = sp.toString().charAt(startIndex - 1);
                            if (!(((int) before >= 48 && (int) before <= 57) || ((int) before >= 65 && (int) before <= 90) || ((int) before >= 97 && (int) before <= 122)))
                            {
                                sp.setSpan(new ForegroundColorSpan(Color.rgb(139, 0, 139)), startIndex, lastIndex, 0);
                                break;
                            }
                        } else
                        {
                            before = sp.toString().charAt(startIndex - 1);
                            after = sp.toString().charAt(lastIndex);
                            boolean flag1 = (!(((int) after >= 48 && (int) after <= 57) || ((int) after >= 65 && (int) after <= 90) || ((int) after >= 97 && (int) after <= 122)));
                            boolean flag2 = (!(((int) before >= 48 && (int) before <= 57) || ((int) before >= 65 && (int) before <= 90) || ((int) before >= 97 && (int) before <= 122)));
                            if (flag1 && flag2)
                            {
                                sp.setSpan(new ForegroundColorSpan(Color.rgb(139, 0, 139)), startIndex, lastIndex, 0);
                            }
                        }
                    }
                }
            }
        }
    }

    //Orange for collapsed block of codes
    public static void setOrange(String closedBraces[], SpannableString sp)
    {
        //Code for Dark Orange
        int startIndex = 0;
        while (true)
        {
            int a = sp.toString().indexOf(closedBraces[0], startIndex);
            if (a != -1)
            {
                sp.setSpan(new ForegroundColorSpan(Color.rgb(238, 118, 0)), a, a + 6, 0);
                startIndex = a + 6;
            } else
            {
                break;
            }
        }
        //End of this functionality
    }

    //Blue for escape sequences
    public static void setEscapeSequenceColor(String escapeSequence[], SpannableString sp)
    {
        //code for escape sequences
        int startIndex = 0;
        for (int i = 0; i < escapeSequence.length; i++)
        {
            startIndex = 0;
            while (true)
            {
                int a = sp.toString().indexOf(escapeSequence[i], startIndex);
                if (a != -1)
                {
                    sp.setSpan(new ForegroundColorSpan(Color.BLUE), a, a + 2, 0);
                    startIndex = a + 2;
                } else
                {
                    break;
                }
            }
        }
    }

    //Comments
    public static void setGreenDoubleSlash(String doubleSlash[], SpannableString sp)
    {
        int lastIndex = -1;
        int startIndex;
        while (true)
        {
            startIndex = sp.toString().indexOf(doubleSlash[0], lastIndex + 1);
            if (startIndex == -1)
            {
                break;
            }
            lastIndex = sp.toString().indexOf("\n", startIndex + 1);
            if (lastIndex == -1)
            {
                sp.setSpan(new ForegroundColorSpan(Color.rgb(0, 100, 0)), startIndex, sp.length(), 0);
                break;
            } else
            {
                sp.setSpan(new ForegroundColorSpan(Color.rgb(0, 100, 0)), startIndex, lastIndex, 0);
            }
        }
    }

    //multiLine Comments
    public static void setGreenMultiLineComment(SpannableString sp)
    {
        int lastIndex = -1;
        int startIndex;
        while (true)
        {
            startIndex = sp.toString().indexOf("/*", lastIndex + 1);
            if (startIndex == -1)
            {
                break;
            }
            lastIndex = sp.toString().indexOf("*/", startIndex + 1);
            if (lastIndex == -1)
            {
                sp.setSpan(new ForegroundColorSpan(Color.rgb(0, 100, 0)), startIndex, sp.length(), 0);
                break;
            } else
            {
                sp.setSpan(new ForegroundColorSpan(Color.rgb(0, 100, 0)), startIndex, lastIndex + 2, 0);
            }
        }
    }

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
            if (lastIndex != -1)
            {
                if (endOfLine == -1)  // closing ' found on last line
                {
                    sp.setSpan(new ForegroundColorSpan(Color.rgb(139, 0, 139)), startIndex, lastIndex + 1, 0);
                    break;
                }
                if (lastIndex < endOfLine)  // Same line before a new line
                {
                    sp.setSpan(new ForegroundColorSpan(Color.rgb(139, 0, 139)), startIndex, lastIndex + 1, 0);
                } else  // closing ' is found on next line
                {
                    sp.setSpan(new ForegroundColorSpan(Color.rgb(139, 0, 139)), startIndex, endOfLine, 0);
                    lastIndex = endOfLine;
                }
            } else
            {
                if (endOfLine == -1)
                {
                    sp.setSpan(new ForegroundColorSpan(Color.rgb(139, 0, 139)), startIndex, sp.length(), 0);
                    break;
                } else
                {
                    sp.setSpan(new ForegroundColorSpan(Color.rgb(139, 0, 139)), startIndex, endOfLine, 0);
                    break;
                }
            }
        }
    }

    //Maroon for String literals
    public static void setMaroonColorDoubleQuote(String doubleQuote[], SpannableString sp)
    {
        //code for " " (double quotes)
        int startIndex = 0;
        int lastIndex = -1;
        while (true)
        {
            startIndex = sp.toString().indexOf(doubleQuote[0], lastIndex + 1);
            lastIndex = sp.toString().indexOf(doubleQuote[0], startIndex + 1);
            int endOfLine = sp.toString().indexOf("\n", startIndex + 1);
            if (startIndex == -1)
            {
                break;
            }
            if (lastIndex != -1 && lastIndex < endOfLine)
            {
                int extraQuote = sp.toString().indexOf(doubleQuote[0], lastIndex + 1);
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

    public static void setMaroonForClasses(Map<String, Integer> mapOfClasses, SpannableString sp)
    {
        String s = sp.toString();
        String[] classNames = s.split("\\W");
        for (int i = 0; i < classNames.length; i++)
        {
            if (mapOfClasses.containsKey(classNames[i]))
            {
                int lastIndex = 0;
                int startIndex = 0;
                int wordLength = classNames[i].length();  //to be used later
                while (startIndex != -1)
                {
                    startIndex = sp.toString().indexOf(classNames[i], lastIndex);
                    lastIndex = startIndex + wordLength;
                    if (startIndex != -1)
                    {
                        char before;
                        char after;
                        if (startIndex == 0)
                        {
                            if (lastIndex == sp.toString().length())
                            {
                                sp.setSpan(new ForegroundColorSpan(Color.rgb(139, 0, 139)), startIndex, lastIndex, 0);
                                //break;
                            } else
                            {
                                after = sp.toString().charAt(lastIndex);
                                if (!(((int) after >= 48 && (int) after <= 57) || ((int) after >= 65 && (int) after <= 90) || ((int) after >= 97 && (int) after <= 122)))
                                {
                                    sp.setSpan(new ForegroundColorSpan(Color.rgb(139, 0, 139)), startIndex, lastIndex, 0);
                                }
                            }
                        } else
                        {
                            if (lastIndex == sp.toString().length())
                            {
                                before = sp.toString().charAt(startIndex - 1);
                                if (!(((int) before >= 48 && (int) before <= 57) || ((int) before >= 65 && (int) before <= 90) || ((int) before >= 97 && (int) before <= 122)))
                                {
                                    sp.setSpan(new ForegroundColorSpan(Color.rgb(139, 0, 139)), startIndex, lastIndex, 0);
                                    //  break;
                                }
                            } else
                            {
                                before = sp.toString().charAt(startIndex - 1);
                                after = sp.toString().charAt(lastIndex);
                                boolean flag1 = (!(((int) after >= 48 && (int) after <= 57) || ((int) after >= 65 && (int) after <= 90) || ((int) after >= 97 && (int) after <= 122)));
                                boolean flag2 = (!(((int) before >= 48 && (int) before <= 57) || ((int) before >= 65 && (int) before <= 90) || ((int) before >= 97 && (int) before <= 122)));

                                if (flag1 && flag2)
                                {
                                    sp.setSpan(new ForegroundColorSpan(Color.rgb(139, 0, 139)), startIndex, lastIndex, 0);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}