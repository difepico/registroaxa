package co.dp.registroaxa.inteface;

import java.util.List;

public interface FregistroInterface {

    interface View
    {

        void onSucces(List<String> ciudades);
        void onError();

    }

    interface Presenter
    {

        void onSucces(List<String> ciudades);
        void onError();

        void rConfig();


    }

    interface  Model
    {
        void rConfig();

    }

}
