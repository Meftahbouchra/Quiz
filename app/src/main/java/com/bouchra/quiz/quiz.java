package com.bouchra.quiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class quiz extends AppCompatActivity {

    private TextView quest;
    private ImageButton oui;
    private ImageButton non;
    private ProgressBar progressBar;
    private LinearLayout laypout_vrai, laypout_faux;
    private TextView Score;
    private Dialog myDialog;
    private MyCountDownTimer myCountDownTimer;
    private TrueFalse[] mQuestion;
    private int score = 0;
    private int indice = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        quest = findViewById(R.id.quest);
        oui = findViewById(R.id.oui);
        non = findViewById(R.id.non);
        Score = findViewById(R.id.Score);
        progressBar = findViewById(R.id.progressBar);
        laypout_vrai = findViewById(R.id.laypout_vrai);
        laypout_faux = findViewById(R.id.laypout_faux);
        myDialog = new Dialog(this);
        String[] question = getResources().getStringArray(R.array.quiz_Question);
        String[] information = getResources().getStringArray(R.array.quiz_Information);
        mQuestion = new TrueFalse[]{
                new TrueFalse(question[0], true, information[0], R.drawable.cartedelalgerie),
                new TrueFalse(question[1], false, information[1], R.drawable.mecanisme_marees),
                new TrueFalse(question[2], true, information[2], R.drawable.moustique_sang),
                new TrueFalse(question[3], false, information[3], R.drawable.poil),
                new TrueFalse(question[4], true, information[4], R.drawable.canauxwifi),
                new TrueFalse(question[5], false, information[5], R.drawable.lunule),
                new TrueFalse(question[6], false, information[6], R.drawable.appareildigestif),
                new TrueFalse(question[7], false, information[7], R.drawable.claviertel),
                new TrueFalse(question[8], true, information[8], R.drawable.pierreponce),
                new TrueFalse(question[9], false, information[9], R.drawable.ossatureelephant),
                new TrueFalse(question[10], false, information[10], R.drawable.solarsystem),

        };

        final String Q = mQuestion[0].getmQuestion();
        Score.setText(String.valueOf(score));
        quest.setText(Q);
        startCountTimer();

        oui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswerTrue(true);

            }
        });
        non.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswerFalse(false);
            }
        });


    }

    private void checkAnswerFalse(boolean UserAnswer) {
        boolean answer = mQuestion[indice].ismTrueQuestio();
        if (UserAnswer == answer) {
            laypout_faux.setBackgroundResource(R.drawable.reponse_true);
            score = score + 10;
            Score.setText(String.valueOf(score));

        } else {
            laypout_faux.setBackgroundResource(R.drawable.reponce_false);
        }
        myCountDownTimer.cancel();
        int image = mQuestion[indice].img;
        String description = mQuestion[indice].descrip;
        show(image, description);

    }

    private void checkAnswerTrue(boolean UserAnswer) {
        boolean answer = mQuestion[indice].ismTrueQuestio();
        if (UserAnswer == answer) {
            laypout_vrai.setBackgroundResource(R.drawable.reponse_true);
            score = score + 5;
            Score.setText(String.valueOf(score));
        } else {
            laypout_vrai.setBackgroundResource(R.drawable.reponce_false);
        }
        myCountDownTimer.cancel();
        int image = mQuestion[indice].img;
        String description = mQuestion[indice].descrip;
        show(image, description);
    }

    private void startCountTimer() {
        myCountDownTimer = new MyCountDownTimer(10000, 2000);
        myCountDownTimer.start();
    }


    private class TrueFalse {
        private String mQuestion;
        private boolean mTrueQuestio;
        private String descrip;
        int img;

        public TrueFalse(String mQuestion, boolean mTrueQuestio, String descrip, int img) {
            this.mQuestion = mQuestion;
            this.mTrueQuestio = mTrueQuestio;
            this.descrip = descrip;
            this.img = img;
        }

        public String getmQuestion() {
            return mQuestion;
        }

        public void setmQuestion(String mQuestion) {
            this.mQuestion = mQuestion;
        }

        public boolean ismTrueQuestio() {
            return mTrueQuestio;
        }

        public void setmTrueQuestio(boolean mTrueQuestio) {
            this.mTrueQuestio = mTrueQuestio;
        }
    }

    public class MyCountDownTimer extends CountDownTimer {
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

            int progress = (int) (millisUntilFinished / 1000);

            progressBar.setProgress(progressBar.getMax() - progress);
        }

        @Override
        public void onFinish() {
            if (indice == mQuestion.length - 1) {
                myCountDownTimer.cancel();
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction t = manager.beginTransaction();
                final gameOver m4 = new gameOver();
                Bundle b2 = new Bundle();
                b2.putInt("score", score);
                m4.setArguments(b2);
                t.add(R.id.fragment, m4);
                t.commit();

            } else {
                indice = (indice + 1);
                String q = mQuestion[indice].getmQuestion();
                quest.setText(q);
                //restart
                myCountDownTimer.cancel();
                myCountDownTimer.start();
            }
        }


    }




    private void show(int image, String escription) {
        TextView txtclose, info;
        ImageView imagee;
        myDialog.setContentView(R.layout.show_desc);
        txtclose = (TextView) myDialog.findViewById(R.id.retour);
        info = myDialog.findViewById(R.id.information);
        imagee = myDialog.findViewById(R.id.image);
        info.setText(escription);
        // implementation 'com.github.bumptech.glide:glide:4.11.0'
        Glide.with(this)
                .asBitmap()
                .load(image)
                .into(imagee);
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                laypout_faux.setBackgroundResource(R.drawable.textview_border);
                laypout_vrai.setBackgroundResource(R.drawable.textview_border);
                myCountDownTimer.onFinish();
                myDialog.dismiss();
            }
        });
        myDialog.show();
    }

}

