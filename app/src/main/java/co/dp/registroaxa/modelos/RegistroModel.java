package co.dp.registroaxa.modelos;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.dp.registroaxa.inteface.FregistroInterface;

public class RegistroModel  implements FregistroInterface.Model {

    private static final String TAG = "RegistroModel";


    private Activity context;
    private FregistroInterface.Presenter presenter;
    FirebaseRemoteConfig firebaseRemoteConfig;
    FirebaseRemoteConfigSettings configSettings;




    public RegistroModel(Activity context, FregistroInterface.Presenter presenter) {
        this.context = context;
        this.presenter = presenter;


    }

    @Override
    public void rConfig() {

        firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        configSettings = new  FirebaseRemoteConfigSettings.Builder().build();


        // iniciamos remote
        firebaseRemoteConfig.setConfigSettingsAsync(configSettings);

        Map<String, Object> defaultdata = new HashMap<>();
        defaultdata.put("city1", "1");
        defaultdata.put("city2", "2");
        defaultdata.put("city3", "3");
        defaultdata.put("city4", "4");

        firebaseRemoteConfig.setDefaultsAsync(defaultdata);

        firebaseRemoteConfig.fetch(0).addOnCompleteListener(context, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                List<String> citys = new ArrayList<String>();
                if (task.isSuccessful()) {

                    firebaseRemoteConfig.fetchAndActivate();

                    citys.add(firebaseRemoteConfig.getString("city1"));
                    citys.add(firebaseRemoteConfig.getString("city2"));
                    citys.add(firebaseRemoteConfig.getString("city3"));
                    citys.add(firebaseRemoteConfig.getString("city4"));
                    presenter.onSucces(citys);

                } else {

                }


            }
        });


    }
}
