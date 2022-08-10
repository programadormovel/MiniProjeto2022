package br.edu.fieb.miniprojeto2022;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import br.edu.fieb.miniprojeto2022.ui.login.LoginActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Transição de tela
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Ir para a tela do Menu
                Intent it = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(it);
            }
        }, 2000);
    }
}