package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String  EXTRA_TEXT1 = "com.example.tictactoe.EXTRA_TEXT1";
    public static final String  EXTRA_TEXT2 = "com.example.tictactoe.EXTRA_TEXT2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button)findViewById(R.id.btn_jouer);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openJeu();
            }
        });
    }
    public void openJeu(){
        //ENVOI vers jeu
        EditText editText1 = (EditText) findViewById(R.id.tbox_nom1);
        String joueur1 = editText1.getText().toString();
        EditText editText2 = (EditText) findViewById(R.id.tbox_nom2);
        String joueur2 = editText2.getText().toString();

        Intent intent = new Intent(this, Jeu.class);
        intent.putExtra(EXTRA_TEXT1, joueur1);
        intent.putExtra(EXTRA_TEXT2, joueur2);
        startActivity(intent);
    }
}
