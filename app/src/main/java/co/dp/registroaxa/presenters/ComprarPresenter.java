package co.dp.registroaxa.presenters;

import android.app.Activity;
import android.content.Context;

import java.util.List;

import co.dp.registroaxa.Pojos.DatosPojo;
import co.dp.registroaxa.inteface.FcomprarInterface;
import co.dp.registroaxa.inteface.FregistroInterface;
import co.dp.registroaxa.modelos.ComprarModel;
import co.dp.registroaxa.modelos.RegistroModel;

public class ComprarPresenter implements FcomprarInterface.Presenter {

    private Context context;
    private FcomprarInterface.View view;
    private FcomprarInterface.Model model;

    public ComprarPresenter(Context context, FcomprarInterface.View view) {
        this.context = context;
        this.view = view;
        this.model = new ComprarModel(context, this);
    }

    @Override
    public void onSucces(String succes) {
        if (view != null)
        {
            view.onSucces(succes);
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
    public void subir(DatosPojo datos) {

        model.subir(datos);

    }


}
