package com.talkingandroid.hour14application;

import android.app.Activity;
import android.graphics.Path;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class WriteExternalPublicActivity extends Activity {
    EditText editText;
    TextView textView;
    Button readButton;
    Button writeButton;
    Button deleteButton;
    private static String demoFile = "demo_file.txt";
    File file;
    File path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_write);
        editText = (EditText) findViewById(R.id.editText);
        textView = (TextView) findViewById(R.id.textView);
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {

            path = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOCUMENTS);
            file = new File(path, demoFile);
            path.mkdirs();

            writeButton = (Button) findViewById(R.id.writeButton);
            writeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        FileWriter fileWriter = new FileWriter(file, true);
                        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                        String data = editText.getText().toString();
                        bufferedWriter.write(data);
                        bufferedWriter.close();
                        fileWriter.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            readButton = (Button) findViewById(R.id.readButton);
            readButton.setOnClickListener(new View.OnClickListener() {
                StringBuffer stringBuffer = new StringBuffer();

                @Override
                public void onClick(View v) {
                    try {
                        FileReader fileReader = new FileReader(file);
                        BufferedReader bufferedReader = new BufferedReader(fileReader);
                        int readData;
                        while ((readData = bufferedReader.read()) != -1) {
                            char data = (char) readData;
                            stringBuffer.append(data);
                        }
                        bufferedReader.close();
                        fileReader.close();
                        textView.setText("");
                        textView.setText(stringBuffer.toString());
                    } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            });

            deleteButton = (Button) findViewById(R.id.deleteButton);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean result = file.delete();
                    if (result) {
                        textView.setText("File deleted");
                    } else {
                        textView.setText("File NOT deleted");
                    }
                }
            });
        } else{
            textView.setText("Cannot write to External Storage");

        }
    }
}
