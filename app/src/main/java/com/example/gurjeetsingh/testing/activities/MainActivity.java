package com.example.gurjeetsingh.testing.activities;

import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Color;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.gurjeetsingh.testing.R;
import com.example.gurjeetsingh.testing.utils.Processor;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.OpenFileActivityBuilder;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Stack;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private static final int REQUEST_CODE_SELECT = 101;
    private static final int REQUEST_CODE_RESOLUTION = 102;
    private static final int REQUEST_CODE_SAVE = 103;
    private static final String TAG = "MainActivity";

    HashMap<String, String> Locations = new HashMap<String, String>();
    Processor processor;
    EditText mainEditText;
    private GoogleApiClient googleApiClient;
    String functionLocation[][];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.final_layout);
        buildGoogleApiClient();
        ImageButton colorButton = (ImageButton) findViewById(R.id.buttonF);
        colorButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                testlayout_setColor();
            }
        });

        //Open Button
        ImageButton openButton = (ImageButton) findViewById(R.id.openFromDrive);
        openButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentSender intentSender = Drive.DriveApi.newOpenFileActivityBuilder().setMimeType(new String[]{DriveFolder.MIME_TYPE, "text/plain", "image/png"}).build(googleApiClient);
                try {
                    startIntentSenderForResult(intentSender, REQUEST_CODE_SELECT, null, 0, 0, 0);
                } catch (IntentSender.SendIntentException e) {
                }

            }
        });

        //Save Button
        ImageButton saveButton = (ImageButton) findViewById(R.id.savetodrive);
        saveButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentSender intentSender = Drive.DriveApi.newOpenFileActivityBuilder().setMimeType(new String[]{DriveFolder.MIME_TYPE, "text/plain", "image/png"}).build(googleApiClient);
                try {
                    startIntentSenderForResult(intentSender, REQUEST_CODE_SAVE, null, 0, 0, 0);
                } catch (IntentSender.SendIntentException e) {
                }

            }
        });

        mainEditText = (EditText) findViewById(R.id.editText2);
        processor = new Processor(this, mainEditText);
        processor.process();
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {

            case (KeyEvent.KEYCODE_ENTER):
                //  processor.handleEnter();
                break;
        }


        return super.onKeyUp(keyCode, event);
    }

    public void updateLocally(int diff, int ref) {
        for (int i = 0; i < functionLocation.length; i++) {
            for (int j = 0; j < 2; j++) {

                if (Integer.parseInt(functionLocation[i][j]) > ref) {
                    functionLocation[i][j] = String.valueOf(Integer.parseInt(functionLocation[i][j]) - diff);
                }
            }
        }

        mainEditText = (EditText) findViewById(R.id.editText2);
        String lines[] = mainEditText.getText().toString().split("\n");

        for (int i = 0; i < functionLocation.length; i++) {

            if (!functionLocation[i][0].equals(functionLocation[i][1])) {
                functionLocation[i][2] = ""; //set it "" and then fill it again
                // functionLocation[i][3] = "diff";
                for (int k = Integer.parseInt(functionLocation[i][0]) + 1; k < Integer.parseInt(functionLocation[i][1]); k++) {

                    if (k == Integer.parseInt(functionLocation[i][1]) - 1) {
                        functionLocation[i][2] = functionLocation[i][2] + lines[k];
                    } else {
                        functionLocation[i][2] = functionLocation[i][2] + lines[k] + "\n";
                    }

                }
            }
        }

    }

    //fill the location of the braces and the corresponding data
    public void fillLocationArray() {
        String lines[] = mainEditText.getText().toString().split("\n");
        Stack openingBrace = new Stack();
        int j = 0;

        for (int i = 0; i < lines.length; i++) {
            int indexOpen = 0;
            int indexClose = 0;
            while (true) {
                indexOpen = lines[i].toString().indexOf("{", indexOpen);
                indexClose = lines[i].toString().indexOf("}", indexClose);

                if (indexOpen == -1 && indexClose == -1) { //none
                    break;
                }
                if (indexOpen == -1 && indexClose != -1) //found closing only
                {
                    if (!openingBrace.empty()) {
                        int top = (int) openingBrace.pop();
                        functionLocation[j][0] = Integer.toString(top);
                        functionLocation[j][1] = Integer.toString(i);
                        j++;
                        indexClose++;
                    }
                }
                if (indexOpen != -1 && indexClose == -1) //only opening
                {
                    openingBrace.push(i);
                    indexOpen++;
                }

                if (indexOpen != -1 && indexClose != -1) //found both
                {
                    openingBrace.push(i);
                    indexOpen++;

                    if (!openingBrace.empty()) {
                        int top = (int) openingBrace.pop();
                        functionLocation[j][0] = Integer.toString(top);
                        functionLocation[j][1] = Integer.toString(i);
                        j++;
                        indexClose++;
                    }
                }
                break;
            }
        }

        // adding the data in the third column
        for (int i = 0; i < functionLocation.length; i++) {

            if (!functionLocation[i][0].equals(functionLocation[i][1])) {
                functionLocation[i][2] = ""; //set it "" and then fill it again
                // functionLocation[i][3] = "diff";
                for (int k = Integer.parseInt(functionLocation[i][0]) + 1; k < Integer.parseInt(functionLocation[i][1]); k++) {

                    if (k == Integer.parseInt(functionLocation[i][1]) - 1) {
                        functionLocation[i][2] = functionLocation[i][2] + lines[k];
                    } else {
                        functionLocation[i][2] = functionLocation[i][2] + lines[k] + "\n";
                    }

                }
            } else {
                functionLocation[i][2] = "hello"; //set it "" and then fill it again
            }
        }

    }

    public void mail_mailButton(View v) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_SUBJECT, "emailing code");
        intent.putExtra(Intent.EXTRA_TEXT, mainEditText.getText().toString());
        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "Select App"));
    }

    public void testlayout_setColor() {
        //Start of Code for coloring keywords, comments etc
        int cPos = mainEditText.getSelectionEnd();
        mainEditText.setText(mainEditText.getText().toString()); //to do the whole text black and set it to new colors
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
        String quote = "\"";
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
        mainEditText.setSelection(cPos);
    }

    /*build the google api client*/
    private void buildGoogleApiClient() {
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(Drive.API)
                    .addScope(Drive.SCOPE_FILE)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

        switch (i) {
            case 1:
                Log.i(TAG, "Connection suspended - Cause: " + "Service disconnected");
                break;
            case 2:
                Log.i(TAG, "Connection suspended - Cause: " + "Connection lost");
                break;
            default:
                Log.i(TAG, "Connection suspended - Cause: " + "Unknown");
                break;
        }

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (!connectionResult.hasResolution()) {
            GooglePlayServicesUtil.getErrorDialog(connectionResult.getErrorCode(), this, 0).show();
            return;
        }
        try {
            connectionResult.startResolutionForResult(this, REQUEST_CODE_RESOLUTION);
        } catch (IntentSender.SendIntentException e) {
            Log.d(TAG, e.getMessage());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        googleApiClient.disconnect();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_CODE_SELECT:
                if (resultCode == RESULT_OK) {
                    DriveId driveId = (DriveId) data.getParcelableExtra(OpenFileActivityBuilder.EXTRA_RESPONSE_DRIVE_ID);//this extra contains the drive id of the selected file
                    DriveFile selectedFile = Drive.DriveApi.getFile(googleApiClient, driveId);

                    ResultCallback<DriveApi.DriveContentsResult> contentsOpenedCallback = new ResultCallback<DriveApi.DriveContentsResult>() {
                        @Override
                        public void onResult(@NonNull DriveApi.DriveContentsResult result) {
                            DriveContents contents = result.getDriveContents();
                            BufferedReader reader = new BufferedReader(new InputStreamReader(contents.getInputStream()));
                            StringBuilder builder = new StringBuilder();
                            String line;
                            mainEditText.setText("");
                            try {
                                while ((line = reader.readLine()) != null) {
                                    builder.append(line);
                                    mainEditText.append(line + "\n");
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            String contentsAsString = builder.toString();
                            Log.i(TAG, contentsAsString);
                            //  mainEditText.setText(contentsAsString);
                        }
                    };

                    selectedFile.open(googleApiClient, DriveFile.MODE_READ_ONLY, null).setResultCallback(contentsOpenedCallback);

                }
                break;

            case REQUEST_CODE_SAVE:
                if (resultCode == RESULT_OK) {
                    DriveId driveId = (DriveId) data.getParcelableExtra(OpenFileActivityBuilder.EXTRA_RESPONSE_DRIVE_ID);//this extra contains the drive id of the selected file
                    DriveFile selectedFile = Drive.DriveApi.getFile(googleApiClient, driveId);

                    ResultCallback<DriveApi.DriveContentsResult> contentsOpenedCallback = new ResultCallback<DriveApi.DriveContentsResult>() {
                        @Override
                        public void onResult(@NonNull DriveApi.DriveContentsResult result) {
                            DriveContents contents = result.getDriveContents();
                            try {
                                ParcelFileDescriptor parcelFileDescriptor = contents.getParcelFileDescriptor();

                                FileInputStream fileInputStream = new FileInputStream(parcelFileDescriptor.getFileDescriptor());
                                // Read to the end of the file.
                                fileInputStream.read(new byte[fileInputStream.available()]);

                                // Append to the file.
                                FileOutputStream fileOutputStream = new FileOutputStream(parcelFileDescriptor
                                        .getFileDescriptor());
                                Writer writer = new OutputStreamWriter(fileOutputStream);
                                writer.write(mainEditText.getText().toString());
                                writer.close();
                                contents.commit(googleApiClient, null).setResultCallback(new ResultCallback<Status>() {
                                    @Override
                                    public void onResult(Status result) {
                                        if (!result.getStatus().isSuccess()) {
                                            Log.d(TAG, result.toString());
                                        }
                                    }
                                });
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    selectedFile.open(googleApiClient, DriveFile.MODE_WRITE_ONLY, null).setResultCallback(contentsOpenedCallback);
                }
                break;

            case REQUEST_CODE_RESOLUTION:
                if (resultCode == RESULT_OK) {
                    Log.i(TAG, "in onActivityResult() - resolving connection, connecting...");
                    googleApiClient.connect();
                }
                break;

            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

}