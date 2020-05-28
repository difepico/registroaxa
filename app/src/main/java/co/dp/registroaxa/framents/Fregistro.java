package co.dp.registroaxa.framents;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.Validator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import co.dp.registroaxa.MapsActivity;
import co.dp.registroaxa.Pojos.DatosPojo;
import co.dp.registroaxa.R;
import co.dp.registroaxa.Util.ImagePickerActivity;
import co.dp.registroaxa.inteface.FregistroInterface;
import co.dp.registroaxa.presenters.RegistroPresenter;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fregistro extends Fragment implements Validator.ValidationListener , FregistroInterface.View {

    private Validator validator;
    private boolean vand = false;
    private static final String TAG = "Fregistro";
    private final static int Cero = 0;
    private int LAUNCH_ORIGIN = 1;
    private int LAUNCH_DESTINATION = 2;
    private int REQUEST_IMAGE = 100;


    private FregistroInterface.Presenter presenter;

    FirebaseRemoteConfig firebaseRemoteConfig;


    public final Calendar c = Calendar.getInstance();

    //Variables para obtener la fecha
    final int mes = c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio = c.get(Calendar.YEAR);

    private EditText etFecha;

    @NotEmpty(message = "No dejes este campo vacío")
    private EditText etCedula;

    @NotEmpty(message = "No dejes este campo vacío")
    private EditText etNombre;

    @NotEmpty(message = "No dejes este campo vacío")
    private EditText etApellido;

    @Length(max = 3, min = 3, message = "Asegúrate de poner una matricula valida")
    private EditText etMatriculaT;

    @Length(max = 3, min = 3, message = "Asegúrate de poner una matricula valida")
    private EditText etMatriculaN;

    private Button btnFoto;
    private Button btnMapa;
    private Button btnGuardar;
    private Button btnCity;
    private String foto = "";


    //Variables Finales Objeto
    private int cedula;
    private String nombre;
    private String apellido;
    private Uri uri;

    private String fecha_f;
    private String mapaValidar = "";
    private String city = "";
    private String matricula = "";
    private String foto_string = "";


    long time = 0;
    long tresmeses = 7889400000L;

    boolean parametros = false;

    List<String> citys = new ArrayList<String>();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registro, container, false);
        this.initViews(view);

        validator = new com.mobsandgeeks.saripaar.Validator(this);
        validator.setValidationListener(this);

        presenter = new RegistroPresenter(getActivity(),this);

        etFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obtenerFecha();
            }
        });

        btnMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), MapsActivity.class);
                startActivityForResult(i, Cero);

            }

        });

        btnCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                presenter.rConfig();
            }
        });

        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchCameraIntent();

            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validator.validate();
                validar();

                if(vand && parametros){

                    cedula = Integer.valueOf(etCedula.getText().toString());
                    nombre = etNombre.getText().toString();
                    apellido = etApellido.getText().toString();
                    matricula = etMatriculaT.getText().toString()+etMatriculaN.getText().toString();

                    foto_string = uri.toString();

                    DatosPojo datos  = new DatosPojo(cedula,nombre,apellido,foto_string,fecha_f,mapaValidar,matricula,city);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("DATOS", datos );
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    Fcomprar fcomprar = new Fcomprar();
                    fcomprar.setArguments(bundle);
                    transaction.addToBackStack(null);
                    transaction.add(R.id.container, fcomprar);
                    transaction.commit();
                }

                else {
                    return;
                }

            }
        });

        return view;
    }


        private void obtenerFecha(){
        DatePickerDialog recogerFecha = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                //se le suma 1 debido a que entrega valores de 0 a 11
                final int mesActual = month + 1;

                String diaF = (day < 10)? "0" + String.valueOf(day):String.valueOf(day);
                String mesF = (mesActual < 10)? "0" + String.valueOf(mesActual):String.valueOf(mesActual);
                fecha_f = diaF + "/" + mesF + "/" + year;
                etFecha.setText(fecha_f);

                Calendar calendar = new GregorianCalendar(year, month, day);
                time = calendar.getTimeInMillis();
            }
        }, anio, mes, dia);
        recogerFecha.show();
    }

    public void initViews(View view) {

        etCedula = (EditText) view.findViewById(R.id.etCedula);
        etNombre = (EditText) view.findViewById(R.id.etNombre);
        etApellido = (EditText) view.findViewById(R.id.etApellidos);
        etApellido = (EditText) view.findViewById(R.id.etApellidos);
        etMatriculaT = (EditText) view.findViewById(R.id.edTextMatricula);
        etMatriculaN = (EditText) view.findViewById(R.id.edNumberMatricula);
        etFecha = (EditText) view.findViewById(R.id.edTextFecha);

        btnMapa = (Button) view.findViewById(R.id.btnMap);
        btnGuardar = (Button) view.findViewById(R.id.btnGuardar);
        btnFoto = (Button) view.findViewById(R.id.btnFoto);
        btnCity = (Button) view.findViewById(R.id.btnCiudad);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (resultCode == 1) {

            Log.i(TAG, data.getStringExtra("result"));
            mapaValidar = data.getStringExtra("result");
            btnMapa.setText(mapaValidar);
        }

        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                uri = data.getParcelableExtra("path");
                Log.i(TAG, "Imagen"+uri.toString());
                foto = "OK";
                btnFoto.setText(foto);

            }
        }
    }

    @Override
    public void onValidationSucceeded() {
        vand = true;

    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        vand = false;

        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(getContext());

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
            }
        }
    }


    public void validar (){

         long time_actual;

        Calendar calendar = new GregorianCalendar(anio, mes, dia);
        time_actual = calendar.getTimeInMillis();


        Log.i(TAG, ""+time_actual);
        if (mapaValidar.equals(""))
        {
            cuadroDialog("Seleccione su ubicacion de residencia");

        }

        else if (time == 0){

            cuadroDialog("Seleccione una fecha de vencimiento SOAT");

          }

        else if (time_actual-tresmeses>time || time>time_actual+tresmeses){

            cuadroDialog("Su fecha de vencimiento SOAT esta fuera de los 3 meses vigencia");


        }
        else if (city == "")
        {
            cuadroDialog("Seleccione su ciudad");
        }

        else if (foto == "")
        {
            cuadroDialog("Falta Foto");
        }

        else
        {
            parametros = true;

        }

    }

    @Override
    public void onSucces(List<String> ciudades) {

        citys = ciudades;

        Log.i(TAG, citys.toString());

        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog2);
        final Button c1 = (Button) dialog.findViewById(R.id.btnCity1);
        final Button c2 = (Button) dialog.findViewById(R.id.btnCity2);
        final Button c3 = (Button) dialog.findViewById(R.id.btnCity3);
        final Button c4 = (Button) dialog.findViewById(R.id.btnCity4);

        c1.setText(citys.get(0));
        c2.setText(citys.get(1));
        c3.setText(citys.get(2));
        c4.setText(citys.get(3));


        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                city = c1.getText().toString();
                btnCity.setText(city);

                dialog.dismiss();
            }
        });
        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                city = c2.getText().toString();
                btnCity.setText(city);
                dialog.dismiss();
            }
        });
        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                city = c3.getText().toString();
                btnCity.setText(city);
                dialog.dismiss();
            }
        });
        c4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                city = c4.getText().toString();
                btnCity.setText(city);
                dialog.dismiss();
            }
        });

        dialog.show();



    }

    @Override
    public void onError() {

    }

    void cuadroDialog(String mensaje)
    {

        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog);
        TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
        text.setText(mensaje);

        Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void launchCameraIntent() {
        Intent intent = new Intent(getContext(), ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_IMAGE_CAPTURE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);

        // setting maximum bitmap width and height
        intent.putExtra(ImagePickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_WIDTH, 1000);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_HEIGHT, 1000);

        startActivityForResult(intent, REQUEST_IMAGE);
    }


}
