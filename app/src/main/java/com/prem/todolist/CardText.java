package com.prem.todolist;

import static android.Manifest.permission.RECORD_AUDIO;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;



import com.prem.todolist.DB.NoteRepo;
import com.prem.todolist.databinding.ActivityCardTextBinding;

import java.util.ArrayList;

public class CardText extends AppCompatActivity {

    private SpeechRecognizer speechRecognizer;
    private Intent intentRecognizer;
    TextView text;
    int id;
    ActivityCardTextBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCardTextBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());

        binding.materialToolbar2.setNavigationOnClickListener(view -> onBackPressed());
        TextView titleTv = binding.title;

        Intent intent = getIntent();
        id = intent.getIntExtra("cardId",0);
        String title = intent.getStringExtra("cardTitle");
        titleTv.setText(title);

        text = binding.textEdtv;
        text.setText(NoteRepo.getInstance(getApplicationContext()).getNoteText(id));
        text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
               NoteRepo.getInstance(getApplicationContext()).update(id,s.toString());
            }
        });



        ActivityCompat.requestPermissions(this, new String[]{RECORD_AUDIO}, PackageManager.PERMISSION_GRANTED);

        intentRecognizer = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intentRecognizer.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float v) {

            }

            @Override
            public void onBufferReceived(byte[] bytes) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int i) {

            }

            @Override
            public void onResults(Bundle bundle) {
                ArrayList<String> matches = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                StringBuilder str = new StringBuilder(text.getText()+" ");
                if (matches != null) {
                    str.append(matches.get(0));
                    text.setText(str);
                }
            }

            @Override
            public void onPartialResults(Bundle bundle) {
//                ArrayList<String> matches = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
//                if (matches != null) {
//                    StringBuilder str = new StringBuilder();
//                    for (String partialResult : matches) {
//                        str.append(partialResult).append(" ");
//                    }
//                    text.setText(str.toString());
//                }
            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        });
    }

    public void startButton(View view) {
        speechRecognizer.startListening(intentRecognizer);
    }

    public void deleteNote(View view) {
        NoteRepo.getInstance(this).delete(id);
        CardAdapter.getCardAdapter().updateCardList(id);
        onBackPressed();
    }
}