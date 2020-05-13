package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Jeu extends AppCompatActivity implements View.OnClickListener {


    private Button[][] buttons = new Button[3][3];

    private boolean tour_joueur1 = true;

    private int roundCount;

    private int joueur1_points;
    private int joueur2_points;

    private TextView textViewJoueur1;
    private TextView textViewJoueur2;

    public static String joueur1;
    public static String joueur2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jeu);

        Intent intent = getIntent();
        String joueur1 = intent.getStringExtra(MainActivity.EXTRA_TEXT1);
        String joueur2 = intent.getStringExtra(MainActivity.EXTRA_TEXT2);

        textViewJoueur1 = findViewById(R.id.tv_joueur1);
        textViewJoueur2 = findViewById(R.id.tv_joueur2);

        textViewJoueur1.setText(joueur1);
        textViewJoueur2.setText(joueur2);

        //parcourir le tableau 2D
        //  x = row
        for (int x = 0; x < 3; x++) {
            // y = column
            for (int y = 0; y < 3; y++) {
                String buttonID = "btn_" + x + y;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                // get reference to all the buttons
                buttons[x][y] = findViewById(resID);
                // set OnClickListener on all buttons
                buttons[x][y].setOnClickListener(this);
            }
        }

        Button buttonReinit = findViewById(R.id.btn_reinit);
        buttonReinit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reinitJeu();
            }
        });
    }


    //ce methode est appellé lorsque un des boutons du grid TicTacToe est sélectionné
    @Override
    public void onClick(View v) {
        // checker si il y as un string vide, pas de X ni O
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }

        // prendre le tour
        if (tour_joueur1) {
            ((Button) v).setText("😫");
        } else {
            ((Button) v).setText("😆");
        }
        roundCount++;

        if(detectGagnant()){
            if(tour_joueur1){
                joueur1Gagne();
            } else {
                joueur2Gagne();
            }
        } else if (roundCount == 9){
            draw();
        } else {
            // switcher tour de joueur
            tour_joueur1 = !tour_joueur1;
        }
    }

    private boolean detectGagnant() {
        //parcourir le tableau de boutons et rentre les dans ce String array
        String[][] champ = new String[3][3];
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                champ[x][y] = buttons[x][y].getText().toString();
            }
        }
        // checker que ce n'est pas 3 champs vide
        for (int x = 0; x < 3; x++) {
            if (champ[x][0].equals(champ[x][1])
                    && champ[x][0].equals(champ[x][2])
                    && !champ[x][0].equals("")) {
                return true;
            }
        }
        // checker colummns
        for (int x = 0; x < 3; x++) {
            if (champ[0][x].equals(champ[1][x])
                    && champ[0][x].equals(champ[2][x])
                    && !champ[0][x].equals("")) {
                return true;
            }
        }
        // check diagonale
        if (champ[0][0].equals(champ[1][1])
                && champ[0][0].equals(champ[2][2])
                && !champ[0][0].equals("")) {
            return true;
        }
        if (champ[0][2].equals(champ[1][1])
                && champ[0][2].equals(champ[2][0])
                && !champ[0][2].equals("")) {
            return true;
        }
        return false;
    }

    private void joueur1Gagne(){
        joueur1_points++;
        Toast.makeText(this,joueur1 + " gagne!",Toast.LENGTH_SHORT).show();
        updatePointsText();
        reinitPlanche();
    }

    private void joueur2Gagne(){
        joueur2_points++;
        Toast.makeText(this,joueur2 + " gagne!",Toast.LENGTH_SHORT).show();
        updatePointsText();
        reinitPlanche();
    }

    private void draw(){
        Toast.makeText(this,"DRAW!",Toast.LENGTH_SHORT).show();
        reinitPlanche();
    }

    private void updatePointsText(){
        textViewJoueur1.setText(joueur1 + " : " + joueur1_points);
        textViewJoueur2.setText(joueur2 + " : " + joueur2_points);
    }

    private void reinitPlanche(){
        for(int x = 0; x < 3; x++){
            for (int y = 0; y < 3; y++){
                buttons[x][y].setText("");
            }
        }

        roundCount = 0;
        tour_joueur1 = true;
    }

    private void  reinitJeu() {
        joueur1_points = 0;
        joueur2_points = 0;
        updatePointsText();
        reinitPlanche();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);

        outState.putInt("roundcount", roundCount);
        outState.putInt("joueur1_points", joueur1_points);
        outState.putInt("joueur2_points", joueur2_points);
        outState.putBoolean("tour_joueur1", tour_joueur1);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        roundCount = savedInstanceState.getInt("roundCount");
        joueur1_points = savedInstanceState.getInt("joueur1_points");
        joueur2_points = savedInstanceState.getInt("joueur2_points");
        tour_joueur1 = savedInstanceState.getBoolean("tour_joueur1");
    }

}

