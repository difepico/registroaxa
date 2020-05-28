package co.dp.registroaxa.modelos;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.dp.registroaxa.Pojos.DatosPojo;
import co.dp.registroaxa.inteface.FcomprarInterface;
import co.dp.registroaxa.inteface.FregistroInterface;
import co.dp.registroaxa.presenters.ComprarPresenter;

public class ComprarModel implements FcomprarInterface.Model {

    private Context context;
    private FcomprarInterface.Presenter presenter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private StorageReference storageRef;
    private UploadTask uploadTask;

    public ComprarModel(Context context, FcomprarInterface.Presenter presenter) {
        this.context = context;
        this.presenter = presenter;
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

    }

    @Override
    public void subir(DatosPojo datos) {



        db.collection("datos")
                .add(datos)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        presenter.onSucces("ok");
                        addphoto(documentReference.getId(),datos.getFoto());

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        presenter.onError();;
                    }
                });
    }

   private void addphoto(String idDocumento, String foto){

        Uri uri = Uri.parse(foto);
       StorageReference photosRef = storageRef.child("images/"+idDocumento);
       uploadTask = photosRef.putFile(uri);

       // Register observers to listen for when the download is done or if it fails
       uploadTask.addOnFailureListener(new OnFailureListener() {
           @Override
           public void onFailure(@NonNull Exception exception) {


           }
       }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
           @Override
           public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

               obtenerurlFoto(photosRef,idDocumento);
           }
       });
   }

    public void obtenerurlFoto(StorageReference photosRef, String idDocumento){


        photosRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                updateDocument(uri.toString(),idDocumento);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    public void updateDocument(String urlFoto,String idDocument){

        Map<String,Object> params = new HashMap<>();
        params.put("foto",urlFoto);
        db.collection("datos")
                .document(idDocument)
                .update(params).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });




    }
}
