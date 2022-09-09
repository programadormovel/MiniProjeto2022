package br.edu.fieb.miniprojeto2022.ui.storage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import br.edu.fieb.miniprojeto2022.R;

public class StorageActivity extends AppCompatActivity {

    FirebaseStorage storage = FirebaseStorage.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);


        FileInputStream serviceAccount =
                null;
        try {
            serviceAccount = new FileInputStream("app/miniprojeto2022-firebase-adminsdk-swrgg-9b7d00a82d.json");

//            FirebaseOptions options = new FirebaseOptions.Builder()
//                    .setStorageBucket();
//
//            FirebaseApp.initializeApp(options);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }




        RecyclerView lista = findViewById(R.id.lista_storage);
        StorageAdapter adapter;
        List<StorageModel> listaCapturada = null;

        // Create a storage reference from our app
        StorageReference storageRef = storage.getReference();
        // Create a child reference
        // imagesRef now points to "images"
        StorageReference imagesRef = storageRef.child("/");

        // Child references can also take paths
        // spaceRef now points to "images/space.jpg
        // imagesRef still points to "images"
        //StorageReference spaceRef = storageRef.child("images/space.jpg");

        //StorageReference listRef = storage.getReference().child("files/uid");

        imagesRef.listAll()
                .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                    @Override
                    public void onSuccess(ListResult listResult) {
                        for (StorageReference prefix : listResult.getPrefixes()) {
                            // All the prefixes under listRef.
                            // You may call listAll() recursively on them.
                        }

                        for (StorageReference item : listResult.getItems()) {
                            // All the items under listRef.
                            listaCapturada.add(new StorageModel(item.getName()));
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Uh-oh, an error occurred!
                    }
                });

        lista.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new StorageAdapter(listaCapturada);
        lista.setAdapter(adapter);
    }
}