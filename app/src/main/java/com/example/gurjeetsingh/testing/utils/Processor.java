package com.example.gurjeetsingh.testing.utils;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.gurjeetsingh.testing.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by gurjeetsingh on 2017-08-09.
 */

public class Processor {
    Context context;
    EditText mainEditText;
    List<String> lines;
    List<String> collapsedLines;
    TextWatcher textWatcher;
    boolean isPositiveDelta, isNegativeDelta;
    String positiveDelta, negativeDelta;
    int cursorPosition;
    View.OnClickListener onClickListener;

    IndentationManager indentationManager;
    ButtonManager buttonManager;

    public Processor(Context context, EditText editText) {
        this.context = context;
        mainEditText = editText; //get the reference to the main CodeBox;
        initializeCodeBuffer(mainEditText.getText().toString()); // Initialize the lines variable(main) and also the collapsed variable
        setTextWatcher();
        indentationManager = new IndentationManager(context);

        onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleOnClick(view);
            }
        };

        buttonManager = new ButtonManager(context);
        buttonManager.createButtons(lines, onClickListener);
    }

    public void initializeCodeBuffer(String codeContent) {
        lines = new ArrayList(Arrays.asList(codeContent.split("\n")));
        //trim spaces
        for (int i = 0; i < lines.size(); i++) {
            lines.set(i, lines.get(i).trim());
        }
        collapsedLines = new ArrayList(lines);
    }

    public void process() {
        String indentedLines = indentationManager.getIndentedText(lines);
        updateEditText(indentedLines);
    }

    private void updateEditText(String indentedLines) {
        mainEditText.removeTextChangedListener(textWatcher);
        mainEditText.setText(indentedLines);
        mainEditText.addTextChangedListener(textWatcher);
    }

    private void setTextWatcher() {
        textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (after == 0) {
                    cursorPosition = start;
                    negativeDelta = s.subSequence(start, start + count).toString();
                    isNegativeDelta = true;
                } else if(count !=0) {
                    isNegativeDelta = true;
                    isPositiveDelta = true;
                    cursorPosition = start;
                    negativeDelta = s.subSequence(start, start + count).toString();
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(isNegativeDelta && isPositiveDelta) {
                    positiveDelta = s.subSequence(start, start + count).toString(); //cursor pos already set
                } else if (before == 0) {
                    cursorPosition = start;
                    positiveDelta = s.subSequence(start, start + count).toString();
                    isPositiveDelta = true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                handleDelta();
            }
        };

        mainEditText.addTextChangedListener(textWatcher);
    }

    private void handleDelta() {
        List<String> modifiedLines = Arrays.asList(mainEditText.getText().toString().split("\n"));
        for (int i = 0; i < modifiedLines.size(); i++) {
            modifiedLines.set(i, modifiedLines.get(i).trim());
        }

        int lineNumber = mainEditText.getLayout().getLineForOffset(cursorPosition);
        int updatedLineCount =  positiveDelta.split("\n",-1).length;

        if(isPositiveDelta && isNegativeDelta) {
            // Handle replacing logic
        } else if(isPositiveDelta){
            // Handle Addition logic
            lines.set(lineNumber, modifiedLines.get(lineNumber));
            if(updatedLineCount >= 2) {
                for (int i = 1; i < updatedLineCount; i++){
                    lines.add(lineNumber + i, modifiedLines.get(lineNumber + i));
                    buttonManager.moveButtonsDown(lineNumber + i);
                }
            }

        } if(isNegativeDelta) {
            // Handle deletion logic
        }

//        reset flags
        positiveDelta = null;
        negativeDelta = null;
        isNegativeDelta = false;
        isPositiveDelta = false;
    }

    private void handleCharacters() {
        switch (positiveDelta) {
            case "\n":
                handleEnter();
                break;
            default:
                mainEditText.setSelection(cursorPosition);
        }
    }

    public void handleEnter() {

        process();

        int lineNumber = mainEditText.getLayout().getLineForOffset(cursorPosition);
        if (cursorPosition <= mainEditText.getText().length())
            mainEditText.setSelection(cursorPosition + lines.get(lineNumber).length() - lines.get(lineNumber).trim().length());
        else
            mainEditText.setSelection(cursorPosition - 1);
    }

    private void handleOnClick(View view) {
        ImageButton button = (ImageButton)view;
        int startIndex = button.getId();
        boolean isCollapsed = (boolean)button.getTag();

        if(isCollapsed) {  //Code for expanding
            button.setImageResource(R.drawable.ic_remove_circle_outline_black_18dp);
            List<String> stringList = buttonManager.getCollapsedStrings().get(startIndex);
            int endIndex = startIndex + stringList.size();
            collapsedLines.set(startIndex,"{");
            for(int i = startIndex+1; i <= endIndex; i++){
                collapsedLines.add(i,stringList.get(i - startIndex - 1));
                buttonManager.moveButtonsDown(i);
            }
            collapsedLines.add(endIndex+1, "}");
            buttonManager.moveButtonsDown(endIndex+1);
            button.setTag(false);

        } else {  //Code for Collapsing the code
            int endIndex = indentationManager.getBraceLocation().get(startIndex);
            button.setImageResource(R.drawable.ic_add_circle_outline_black_18dp);
            collapsedLines.set(startIndex,"{ ... }");
            for(int i = endIndex; i > startIndex; i--) {
                collapsedLines.remove(i);
                buttonManager.moveButtonsUp(i);
            }
            buttonManager.getCollapsedStrings().put(startIndex,lines.subList(startIndex + 1, endIndex));
            button.setTag(true);
        }

        updateEditText(indentationManager.getIndentedText(collapsedLines));
    }

}
