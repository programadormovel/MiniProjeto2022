package br.edu.fieb.miniprojeto2022.ui.storage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import br.edu.fieb.miniprojeto2022.R;

public class StorageActivity extends AppCompatActivity {

    FirebaseStorage storage = FirebaseStorage.getInstance("gs://miniprojeto2022.appspot.com");
    StorageAdapter adapter = null;
    StorageReference listRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);

        RecyclerView lista = findViewById(R.id.lista_storage);
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                this, LinearLayoutManager.VERTICAL, false);

        StorageModel storageModel = new StorageModel();
        List<StorageModel> listaStorage = new ArrayList<StorageModel>();
        listaStorage.add(new StorageModel("Teste 1"));
        listaStorage.add(new StorageModel("Teste 2"));

        listRef = storage.getReference(); //.child("images");

        listRef.listAll()
                .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                    @Override
                    public void onSuccess(ListResult listResult) {

                        for (StorageReference prefix : listResult.getPrefixes()) {
                            // All the prefixes under listRef.
                            // You may call listAll() recursively on them.

                        }

                        for (StorageReference item : listResult.getItems()) {
                            // All the items under listRef.
                            StorageModel modelTemp = buscarImagem(new StorageModel(item.getName()));
                            listaStorage.add(modelTemp);
                        }

                        lista.setLayoutManager(layoutManager);
                        adapter = new StorageAdapter(listaStorage);
                        adapter.notifyDataSetChanged();
                        lista.setAdapter(adapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Uh-oh, an error occurred!

                    }
                });
    }

    private StorageModel buscarImagem(StorageModel model) {

        listRef.child("/" + model.getArquivo()).getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // Use the bytes to display the image
                model.setFoto(bytes);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });

        return model;
    }
}