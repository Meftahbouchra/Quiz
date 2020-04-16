package com.bouchra.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

public class gameOver extends Fragment {
    private TextView score;
    private TextView resulat;
    private ImageView resulata_image;
    private Button quitter;
    private Button jouer;
    private static final int scroeMoyenn = 45;
    private int image;

    public gameOver() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_gameover, container, false);
        int Score = getArguments().getInt("score");
        score = view.findViewById(R.id.score);
        quitter = view.findViewById(R.id.quitter);
        jouer = view.findViewById(R.id.jouer);
        resulata_image = view.findViewById(R.id.resulata_image);
        resulat = view.findViewById(R.id.resulata);
        if (Score >= scroeMoyenn && Score <= 90) {
            //gagne
            image = R.drawable.gagne;
            resulat.setText("BRAVO \n Vous avez gagné");
            resulat.setTextColor(getResources().getColor(R.color.forest_green));
            Glide.with(this)
                    .asBitmap()
                    .load(image)
                    .into(resulata_image);
        } else {
            // perdu
            image = R.drawable.oops;
            resulat.setText("OOPS!!! \n Game Over ");
            resulat.setTextColor(getResources().getColor(R.color.rouge));
            Glide.with(this)
                    .asBitmap()
                    .load(image)
                    .into(resulata_image);

        }
        // afficher le score
        score.setText(String.valueOf(Score));
        // quitter l application
        quitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ///ne finit pas l'application mais plutot l'activity sur la quelle appliquee
                // getActivity().finish();
                System.exit(0);
            }
        });
        // rejouer
        jouer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), quiz.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        return view;
    }


}
