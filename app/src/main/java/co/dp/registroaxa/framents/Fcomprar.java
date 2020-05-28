package co.dp.registroaxa.framents;

import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import co.dp.registroaxa.Pojos.DatosPojo;
import co.dp.registroaxa.R;
import co.dp.registroaxa.inteface.FcomprarInterface;
import co.dp.registroaxa.presenters.ComprarPresenter;


public class Fcomprar extends Fragment implements FcomprarInterface.View {

    private FcomprarInterface.Presenter presenter;

    private ImageView foto;
    private TextView nombre;
    private TextView fechaSoat;
    private TextView direccion;
    private TextView maricula;
    private TextView ciudad;
    private Button comprar;


    public Fcomprar() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comprar, container, false);

        presenter = new ComprarPresenter(getContext(),this);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DatosPojo data = getArguments().getParcelable("DATOS");
        Log.i("comprar", data.toString());
        initViews(view);

        Uri photo;
        photo = Uri.parse(data.getFoto());

        Glide.with(getContext())
                .load(photo)
                .apply(new RequestOptions().circleCrop())
                .into(foto);

        String nombre_completo = data.getApellido()+ " " + data.getNombre();
        nombre.setText(nombre_completo);
        fechaSoat.setText(data.getFecha());
        direccion.setText(data.getUbicacion());
        maricula.setText(data.getMatricula());
        ciudad.setText(data.getCiudad());

        comprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.dialogcomprar);
                TextView text = (TextView) dialog.findViewById(R.id.tvConfirmarCompra);
                text.setText("¿Desea Confirmar la Operación?");

                Button si = (Button) dialog.findViewById(R.id.btnSi);
                si.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        presenter.subir(data);
                        dialog.dismiss();


                    }
                });

                Button no = (Button) dialog.findViewById(R.id.btnNo);
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();

                    }
                });

                dialog.show();


            }
        });


    }

    public void initViews(View view) {
        foto = (ImageView) view.findViewById(R.id.ivFoto);
        nombre = (TextView) view.findViewById(R.id.tvNombreC);
        fechaSoat = (TextView) view.findViewById(R.id.tvSoatC);
        direccion = (TextView) view.findViewById(R.id.tvResidenciaC);
        maricula = (TextView) view.findViewById(R.id.tvMatriculaC);
        ciudad = (TextView) view.findViewById(R.id.tvCiudadC);
        comprar = (Button) view.findViewById(R.id.btnComprar);
    }

    @Override
    public void onSucces(String succes) {

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        Fregistro fregistro = new Fregistro();
        transaction.replace(R.id.container, fregistro);
        transaction.commit();
    }

    @Override
    public void onError() {

    }
}
