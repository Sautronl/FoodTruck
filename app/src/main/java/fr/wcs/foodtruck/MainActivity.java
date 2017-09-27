package fr.wcs.foodtruck;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import static fr.wcs.foodtruck.R.id.boutonTest;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent2 = getIntent();

        final Button boutonTest = (Button) findViewById(R.id.boutonTest);

        boutonTest.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ContactPrivatisation.class);
                startActivity(intent);
            }
        });

    }
}
