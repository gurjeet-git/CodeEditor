package com.example.gurjeetsingh.testing.activities;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener
{
    private static final int REQUEST_CODE_SELECT = 101;
    private static final int REQUEST_CODE_RESOLUTION = 102;
    private static final int REQUEST_CODE_SAVE = 103;
    private static final String TAG = "MainActivity";

    HashMap<String, String> Locations = new HashMap<String, String>();
    Processor processor;
    EditText mainEditText;
    EditText lineNumber;
    private GoogleApiClient googleApiClient;
    SignInActivity signInActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.final_layout);
        buildGoogleApiClient();

        //Open Button
        ImageButton openButton = (ImageButton) findViewById(R.id.openFromDrive);
        openButton.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                IntentSender intentSender = Drive.DriveApi.newOpenFileActivityBuilder().setMimeType(new String[]{DriveFolder.MIME_TYPE, "text/x-java-source", "text/plain"}).build(googleApiClient);
                try
                {
                    startIntentSenderForResult(intentSender, REQUEST_CODE_SELECT, null, 0, 0, 0);
                } catch (IntentSender.SendIntentException e)
                {
                }
            }
        });

        //Save Button
        ImageButton saveButton = (ImageButton) findViewById(R.id.savetodrive);
        saveButton.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                IntentSender intentSender = Drive.DriveApi.newOpenFileActivityBuilder().setMimeType(new String[]{DriveFolder.MIME_TYPE, "text/plain", "image/png", "text/txt"}).build(googleApiClient);
                try
                {
                    startIntentSenderForResult(intentSender, REQUEST_CODE_SAVE, null, 0, 0, 0);
                } catch (IntentSender.SendIntentException e)
                {
                }
            }
        });

        mainEditText = (EditText) findViewById(R.id.editText2);
        lineNumber = (EditText) findViewById(R.id.LineNumber);
        processor = new Processor(this, mainEditText, lineNumber);
        processor.process();
    }

    public void exitApp(View v)
    {
        //signInActivity.signOut();
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event)
    {
        switch (keyCode)
        {
            case (KeyEvent.KEYCODE_ENTER):
                break;
        }
        return super.onKeyUp(keyCode, event);
    }

    public void shareCode(View v)
    {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_SUBJECT, "emailing code");
        intent.putExtra(Intent.EXTRA_TEXT, processor.getLinesForEmail());
        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "Select App"));
    }

    /*build the google api client*/
    private void buildGoogleApiClient()
    {
        if (googleApiClient == null)
        {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(Drive.API)
                    .addScope(Drive.SCOPE_FILE)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle)
    {
    }

    @Override
    public void onConnectionSuspended(int i)
    {
        switch (i)
        {
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
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult)
    {
        if (!connectionResult.hasResolution())
        {
            GooglePlayServicesUtil.getErrorDialog(connectionResult.getErrorCode(), this, 0).show();
            return;
        }
        try
        {
            connectionResult.startResolutionForResult(this, REQUEST_CODE_RESOLUTION);
        } catch (IntentSender.SendIntentException e)
        {
            Log.d(TAG, e.getMessage());
        }
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        googleApiClient.disconnect();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case REQUEST_CODE_SELECT:
                if (resultCode == RESULT_OK)
                {
                    DriveId driveId = (DriveId) data.getParcelableExtra(OpenFileActivityBuilder.EXTRA_RESPONSE_DRIVE_ID);//this extra contains the drive id of the selected file
                    DriveFile selectedFile = Drive.DriveApi.getFile(googleApiClient, driveId);
                    ResultCallback<DriveApi.DriveContentsResult> contentsOpenedCallback = new ResultCallback<DriveApi.DriveContentsResult>()
                    {
                        @Override
                        public void onResult(@NonNull DriveApi.DriveContentsResult result)
                        {
                            DriveContents contents = result.getDriveContents();
                            BufferedReader reader = new BufferedReader(new InputStreamReader(contents.getInputStream()));
                            StringBuilder builder = new StringBuilder();
                            String line;
                            try
                            {
                                while ((line = reader.readLine()) != null)
                                {
                                    builder.append(line + "\n");
                                }
                            } catch (IOException e)
                            {
                                e.printStackTrace();
                            }
                            String contentsAsString = builder.toString();
                            processor.removeTextWatcher(mainEditText);
                            mainEditText.setText(contentsAsString);
                            processor = new Processor(MainActivity.this, mainEditText, lineNumber);
                            processor.process();
                            Log.i(TAG, contentsAsString);

                        }
                    };
                    selectedFile.open(googleApiClient, DriveFile.MODE_READ_ONLY, null).setResultCallback(contentsOpenedCallback);
                }
                break;

            case REQUEST_CODE_SAVE:
                if (resultCode == RESULT_OK)
                {
                    DriveId driveId = (DriveId) data.getParcelableExtra(OpenFileActivityBuilder.EXTRA_RESPONSE_DRIVE_ID);//this extra contains the drive id of the selected file
                    DriveFile selectedFile = Drive.DriveApi.getFile(googleApiClient, driveId);

                    ResultCallback<DriveApi.DriveContentsResult> contentsOpenedCallback = new ResultCallback<DriveApi.DriveContentsResult>()
                    {
                        @Override
                        public void onResult(@NonNull DriveApi.DriveContentsResult result)
                        {
                            DriveContents contents = result.getDriveContents();
                            try
                            {
                                ParcelFileDescriptor parcelFileDescriptor = contents.getParcelFileDescriptor();

                                FileInputStream fileInputStream = new FileInputStream(parcelFileDescriptor.getFileDescriptor());
                                // Read to the end of the file.
                                fileInputStream.read(new byte[fileInputStream.available()]);

                                // Append to the file.
                                FileOutputStream fileOutputStream = new FileOutputStream(parcelFileDescriptor
                                        .getFileDescriptor());
                                Writer writer = new OutputStreamWriter(fileOutputStream);
                                writer.write(processor.getLinesForEmail());
                                writer.close();
                                contents.commit(googleApiClient, null).setResultCallback(new ResultCallback<Status>()
                                {
                                    @Override
                                    public void onResult(Status result)
                                    {
                                        if (!result.getStatus().isSuccess())
                                        {
                                            Log.d(TAG, result.toString());
                                        }
                                    }
                                });
                            } catch (IOException e)
                            {
                                e.printStackTrace();
                            }
                        }
                    };
                    selectedFile.open(googleApiClient, DriveFile.MODE_WRITE_ONLY, null).setResultCallback(contentsOpenedCallback);
                }
                break;

            case REQUEST_CODE_RESOLUTION:
                if (resultCode == RESULT_OK)
                {
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