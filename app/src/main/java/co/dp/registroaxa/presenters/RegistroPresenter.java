package co.dp.registroaxa.presenters;

import android.app.Activity;
import android.content.Context;

import java.util.List;

import co.dp.registroaxa.inteface.FregistroInterface;
import co.dp.registroaxa.modelos.RegistroModel;

public class RegistroPresenter implements FregistroInterface.Presenter {

    private Context context;
    private FregistroInterface.View view;
    private FregistroInterface.Model model;


    public RegistroPresenter(Activity context, FregistroInterface.View view) {
        this.context = context;
        this.view = view;
        this.model = new RegistroModel(context, this);
    }

    @Override
    public void onSucces(List<String> ciudades) {
        if (view != null)
        {
            view.onSucces(ciudades);
        }

    }

    @Override
    public void onError() {

        if (view != null)
        {
            view.onError();
        }

    }

    @Override
    public void rConfig() {

        model.rConfig();

    }
}
