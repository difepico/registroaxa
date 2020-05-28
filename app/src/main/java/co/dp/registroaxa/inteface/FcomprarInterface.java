package co.dp.registroaxa.inteface;

import java.util.List;

import co.dp.registroaxa.Pojos.DatosPojo;

public interface FcomprarInterface {

    interface View
    {

        void onSucces(String succes);
        void onError();

    }

    interface Presenter
    {

        void onSucces(String succes);

        void onError();

        void subir(DatosPojo datos);


    }

    interface  Model
    {
        void subir(DatosPojo datos);

    }
}
