package co.dp.registroaxa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import co.dp.registroaxa.framents.Fregistro;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new Fregistro()).commitAllowingStateLoss();

        }
    }


}
