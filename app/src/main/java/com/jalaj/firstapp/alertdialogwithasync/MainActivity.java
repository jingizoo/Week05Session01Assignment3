package com.jalaj.firstapp.alertdialogwithasync;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    int initialValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        final View aDView =  layoutInflater.inflate(R.layout.layout_dialog,null);


        AlertDialog.Builder aDB = new AlertDialog.Builder(this);
        final AlertDialog aD = aDB.create();
        aD.setTitle("Count Down Button");
        aD.setView(aDView);
        final EditText w5TimerText = (EditText) aDView.findViewById(R.id.w5TimerText);
        Button w5BtnOk = (Button) aDView.findViewById(R.id.w5BtnOkay);
        Button  w5BtnCancel = (Button) aDView.findViewById(R.id.w5BtnCancel);

        w5BtnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initialValue = new Integer(w5TimerText.getText().toString());
                CountDownTimer cdt = new CountDownTimer(MainActivity.this);
                cdt.execute();
            }
        });
        w5BtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               aD.dismiss();
                aD.hide();
            }
        });

aD.show();

    }

    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    class CountDownTimer extends AsyncTask<Integer, Integer, Integer> {
        Context context;
        int timeLimit = initialValue;
       ProgressBar w5ProgressBar;
        AlertDialog.Builder aDB;
        AlertDialog aD;
        CountDownTimer(Context v)
        {
            this.context = v;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            LayoutInflater layoutInflater = LayoutInflater.from(context);
            View view = layoutInflater.inflate(R.layout.layout_progressbar_liner,null);
           w5ProgressBar = (ProgressBar) view.findViewById(R.id.w5ProgressBar);
             aD  = new AlertDialog.Builder(context).create();
            aD.setView(view);
            aD.setCancelable(false);
            aD.show();

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            w5ProgressBar.setProgress((values[0]/initialValue)*100);
        }

        @Override
        protected Integer doInBackground(Integer... params) {


            while (timeLimit >0) {

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress(timeLimit--);
            }


            return new Integer(0);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            aD.dismiss();

            AlertDialog.Builder aDB = new AlertDialog.Builder(context);
            aDB.setMessage("Countdown Timer Message");
            aDB.setTitle("Complete");
            aDB.show();

        }
    }


}
