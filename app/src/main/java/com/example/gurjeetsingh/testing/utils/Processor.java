package com.example.gurjeetsingh.testing.utils;

import android.content.Context;
import android.text.Editable;
import android.text.Layout;
import android.text.Selection;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.gurjeetsingh.testing.R;
import com.example.gurjeetsingh.testing.activities.SignInActivity;
import com.example.gurjeetsingh.testing.classes.CollapseDetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by gurjeetsingh on 2017-08-09.
 */

public class Processor
{
    Context context;
    EditText mainEditText;
    EditText lineBox; //for Line numbers
    List<String> lines;
    List<String> indentedLines;
    List<String> collapsedLines;
    TextWatcher textWatcher;
    boolean isPositiveDelta, isNegativeDelta;
    String positiveDelta, negativeDelta;
    int cursorPosition;
    View.OnClickListener onClickListener;
    IndentationManager indentationManager;
    ButtonManager buttonManager;
    LineManager lineManager;
    SignInActivity signInActivity;
    TestManager testManager;
    ColorManager colorManager;

    public Processor(Context context, EditText editText, EditText lineBox)
    {
        this.context = context;
        mainEditText = editText; //get the reference to the main CodeBox;
        this.lineBox = lineBox; //get the ref to lineNumber edittext.

        lineManager = new LineManager();

        // Initialize the lines variable(main) and also the collapsedLines list
        initializeCodeBuffer(mainEditText.getText().toString());
        setTextWatcher();

        onClickListener = new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                handleOnClick(view);
            }
        };
        buttonManager = new ButtonManager(context, onClickListener, lineManager);
        buttonManager.createButtons(lines);
        indentationManager = new IndentationManager(context, buttonManager);
        colorManager = new ColorManager(context);
    }

    //Getting the lines, trimming it and getting the copy stored back in the CollapsedLines variable
    public void initializeCodeBuffer(String codeContent)
    {    //Also fills the lineNumbers of LineManager class
        lines = new ArrayList(Arrays.asList(codeContent.split("\n")));
        //trim spaces
        for (int i = 0; i < lines.size(); i++)
        {
            lines.set(i, lines.get(i).trim());
        }
        collapsedLines = new ArrayList(lines);
    }

    public void process()
    {
        indentedLines = indentationManager.getIndentedText(lines);
        String collapsedString = buttonManager.getCollapsedString(indentedLines);
        SpannableString coloredIntendedLines = colorManager.getColoredString(collapsedString);
        updateEditText(coloredIntendedLines);
        updateLineNumberText(lineManager.getLineNumbers());
    }

    public void updateEditText(SpannableString coloredIntendedLines)
    {
        mainEditText.removeTextChangedListener(textWatcher);
        mainEditText.setText(coloredIntendedLines);
        try
        {
            mainEditText.setSelection(cursorPosition);
        } catch (Exception e)
        {
            mainEditText.setSelection(0);
        }
        mainEditText.addTextChangedListener(textWatcher);
    }

    public void updateLineNumberText(String lineNumbers)
    {
        lineBox.setText(lineNumbers);
    }

    public void removeTextWatcher(EditText mainEditText)
    {
        mainEditText.removeTextChangedListener(textWatcher);
    }

    public void setTextWatcher()
    {
        textWatcher = new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
                if (after == 0)
                {
                    cursorPosition = start;
                    negativeDelta = s.subSequence(start, start + count).toString();
                    isNegativeDelta = true;
                } else if (count != 0)
                {
                    isNegativeDelta = true;
                    isPositiveDelta = true;
                    cursorPosition = start;
                    negativeDelta = s.subSequence(start, start + count).toString();
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if (isNegativeDelta && isPositiveDelta)
                {
                    positiveDelta = s.subSequence(start, start + count).toString(); //cursor pos already set
                } else if (before == 0)
                {
                    cursorPosition = start;
                    positiveDelta = s.subSequence(start, start + count).toString();
                    isPositiveDelta = true;
                }
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                handleDelta();
            }
        };
        mainEditText.addTextChangedListener(textWatcher);
    }

    public int getCurrentCursorLine(EditText editText)
    {
        int selectionStart = Selection.getSelectionStart(editText.getText());
        Layout layout = editText.getLayout();

        if (!(selectionStart == -1))
        {
            return layout.getLineForOffset(selectionStart);
        }

        return -1;
    }

    private void handleDelta()
    {
        List<String> modifiedLines = Arrays.asList(mainEditText.getText().toString().split("\n", -1));
        for (int i = 0; i < modifiedLines.size(); i++)
        {
            modifiedLines.set(i, modifiedLines.get(i).trim());
        }


        int lineNumber = mainEditText.getLayout().getLineForOffset(cursorPosition);
//        int lineNumber = getCurrentCursorLine(mainEditText);
        int lineNumberTwo = mainEditText.getLayout().getLineForOffset(mainEditText.getSelectionEnd());


        if (isPositiveDelta && isNegativeDelta)
        {
            // Handle replacing logic
            int updatedLineCountP = positiveDelta.split("\n", -1).length;
            int updatedLineCountN = negativeDelta.split("\n", -1).length;
            if (updatedLineCountN <= updatedLineCountP) //move down
            {
                for (int i = 0; i < updatedLineCountP; i++)
                {
                    if (i < updatedLineCountN)
                    {
                        lines.set(lineNumber + i, modifiedLines.get(lineNumber + i));
                    } else
                    {
                        lines.add(lineNumber + i, modifiedLines.get(lineNumber + i));
                        buttonManager.moveButtonsDown(lineNumber + i, 1);
                    }
                }
            } else //move up
            {
                for (int i = 0; i < updatedLineCountN; i++)
                {
                    if (i < updatedLineCountP)
                    {
                        lines.set(lineNumber + i, modifiedLines.get(lineNumber + i));
                    } else
                    {
                        lines.remove(lineNumber + i);
                        buttonManager.moveButtonsUp(lineNumber + i, 1);
                    }
                }
            }

        } else if (isPositiveDelta)
        {
            // Handle Addition logic
            int updatedLineCountP = positiveDelta.split("\n", -1).length;
            int actualLineNumber;
            int nextLine;
            boolean isContinue = false;

            try
            {                       //To be removed Later
                actualLineNumber = Integer.parseInt(lineManager.lineNumbers.get(lineNumber + 1));
                nextLine = actualLineNumber;
            } catch (Exception e)
            {
                actualLineNumber = lines.size();
                nextLine = actualLineNumber;
            }


            if (positiveDelta.equals("{"))
            {  //Just add a new button
                buttonManager.addButton(lineNumber, actualLineNumber - 1);
//                mainEditText.getText().insert(mainEditText.getSelectionStart(), "\n");
            }

            if (updatedLineCountP == 1)
            {   //only 1 character is added. No newLine
                if (modifiedLines.get(lineNumber).contains(".."))
                {
                    buttonManager.setTag(Integer.parseInt(lineManager.lineNumbers.get(lineNumber)));
                    process();
                } else
                {
                    lines.set(actualLineNumber - 1, modifiedLines.get(lineNumber));
                }
            } else
            {  // more than 2 characters separated by a Line ("/n")
                if (modifiedLines.get(lineNumber).equals(""))
                {                                                          //Case 3: Start of the line
                    actualLineNumber = Integer.parseInt(lineManager.lineNumbers.get(lineNumber));
                    lines.add(actualLineNumber, modifiedLines.get(lineNumber));
                    buttonManager.moveButtonsDown(actualLineNumber, 1);
                } else if (modifiedLines.get(lineNumber + 1).equals("") && !modifiedLines.get(lineNumber).equals(""))
                {  //Case 1: If enter has been hit after the end of the line
                    lines.add(actualLineNumber, modifiedLines.get(lineNumber + 1));
                    buttonManager.moveButtonsDown(actualLineNumber, 1);
                } else
                {                      //Case 2: middle of the line
                    actualLineNumber = Integer.parseInt(lineManager.lineNumbers.get(lineNumber));
                    if (actualLineNumber + 1 == nextLine) isContinue = true;
                    if (isContinue)
                    {
                        if (modifiedLines.get(lineNumber + 1).contains("{"))  //In the middle of the function declaration
                        {
                            lines.set(actualLineNumber, modifiedLines.get(lineNumber));
                            lines.add(actualLineNumber + 1, modifiedLines.get(lineNumber + 1));
                            buttonManager.moveButtonsDown(actualLineNumber, 1);
                        } else                                                //Normal word or syntax
                        {
                            lines.set(actualLineNumber, modifiedLines.get(lineNumber));
                            lines.add(actualLineNumber + 1, modifiedLines.get(lineNumber + 1));
                            buttonManager.moveButtonsDown(actualLineNumber + 1, 1);
                        }
                    } else
                    {
                        buttonManager.setTag(actualLineNumber);
                        process();
                    }
                }
            }

        } else if (isNegativeDelta)
        {
            int updatedLineCountN = negativeDelta.split("\n", -1).length;
            int actualLineNumber = Integer.parseInt(lineManager.lineNumbers.get(lineNumber));

            if (updatedLineCountN == 1)
            {
                if (modifiedLines.get(lineNumber).contains(".."))
                {
                    buttonManager.setTag(actualLineNumber);
                    process();
                } else
                {
                    lines.set(actualLineNumber, modifiedLines.get(lineNumber));
                    if (negativeDelta.equals("{"))
                    {  //Just remove the button  //Also delete the button itself.
                        buttonManager.removeButton(actualLineNumber);
                    }
                }
            } else
            {
                actualLineNumber = Integer.parseInt(lineManager.lineNumbers.get(lineNumber + 1));
                if (modifiedLines.get(lineNumber).contains("{....}"))
                {  //if above line has a closed function
                    if (lines.get(actualLineNumber).equals(""))
                    {
                        lines.remove(actualLineNumber);
                        buttonManager.moveButtonsUp(actualLineNumber, 1);
                    }
                    process();
                } else
                {
                    lines.set(actualLineNumber - 1, modifiedLines.get(lineNumber));
                    lines.remove(actualLineNumber);
                    buttonManager.moveButtonsUp(actualLineNumber, 1);
                    process();
                }
            }
        }

        handleCharacters(lineNumber);
        //reset flags
        positiveDelta = null;
        negativeDelta = null;
        isNegativeDelta = false;
        isPositiveDelta = false;
    }


    private void handleCharacters(int lineNumber)
    {
        if (positiveDelta != null)
        {
            switch (positiveDelta)
            {
                case "\n":
                    handleEnter(lineNumber);
                    break;
                case "{":
                    mainEditText.getText().insert(mainEditText.getSelectionStart(), "}");
                    mainEditText.setSelection(cursorPosition);
                    break;
                case "[":
                    mainEditText.getText().insert(mainEditText.getSelectionStart(), " ]\n");
                    mainEditText.setSelection(cursorPosition);
                    break;
                case "'":
                    mainEditText.getText().insert(mainEditText.getSelectionStart(), " '");
                    mainEditText.setSelection(cursorPosition);
                    break;
                case "\"":
                    mainEditText.getText().insert(mainEditText.getSelectionStart(), " \"");
                    mainEditText.setSelection(cursorPosition);
                    break;
                case "(":
                    mainEditText.getText().insert(mainEditText.getSelectionStart(), " )");
                    mainEditText.setSelection(cursorPosition);
                    break;
                default:
            }
        }

        if (negativeDelta != null)
        {
            if (negativeDelta.equals("\n"))
            {
                //  process();
            }
        }
    }

    public void handleEnter(int lineNumber)
    {
        indentedLines = indentationManager.getIndentedText(lines);
        String collapsedString = buttonManager.getCollapsedString(indentedLines);
        SpannableString coloredIntendedLines = colorManager.getColoredString(collapsedString);
        cursorPosition = indentationManager.getCursorLocation(lineNumber, false); //since button is not clicked
        updateEditText(coloredIntendedLines);
        updateLineNumberText(lineManager.getLineNumbers());
    }

    private void handleOnClick(View view)
    {
        ImageButton button = (ImageButton) view;
        int buttonNumber = 0; //or LineNumber where button is there
        if ((boolean) button.getTag())
        {
            button.setImageResource(R.drawable.ic_remove_circle_outline_black_18dp);
            button.setTag(false);
            buttonNumber = (int) button.getY() / 58;
        } else
        {
            button.setImageResource(R.drawable.ic_add_circle_outline_black_18dp);
            button.setTag(true);
            buttonNumber = (int) button.getY() / 58;
        }
        handleButtonClick(buttonNumber);
    }

    public String getLinesForEmail()
    {
        List<String> indentedLines = indentationManager.getIndentedText(lines);
        return TextUtils.join("\n", indentedLines);
    }

    public void handleButtonClick(int lineNumber)
    {
        indentedLines = indentationManager.getIndentedText(lines);
        String collapsedString = buttonManager.getCollapsedString(indentedLines);
        SpannableString coloredIntendedLines = colorManager.getColoredString(collapsedString);
        cursorPosition = indentationManager.getCursorLocation(lineNumber, true);
        updateEditText(coloredIntendedLines);
        updateLineNumberText(lineManager.getLineNumbers());
    }

}

