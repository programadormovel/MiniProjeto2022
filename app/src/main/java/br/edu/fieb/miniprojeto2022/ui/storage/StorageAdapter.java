package br.edu.fieb.miniprojeto2022.ui.storage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.edu.fieb.miniprojeto2022.R;
import br.edu.fieb.miniprojeto2022.Util;

public class StorageAdapter extends RecyclerView.Adapter<StorageAdapter.StorageViewHolder> {

    List<StorageModel> mLista;

    public StorageAdapter(List<StorageModel> listaStorage) {

        mLista = listaStorage;
    }

    @NonNull
    @Override
    public StorageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_item_storage, parent, false);
        StorageViewHolder sv = new StorageViewHolder(v);
        return sv;
    }

    @Override
    public void onBindViewHolder(@NonNull StorageViewHolder holder, int position) {
        holder.itemView.setId(position);
        holder.arquivo.setText(mLista.get(position).getArquivo());
        holder.imageStorage.setImageBitmap(Util.converterByteToBipmap(
                mLista.get(position).getFoto()));
    }

    @Override
    public int getItemCount() {
        return mLista.size();
    }

    public class StorageViewHolder extends RecyclerView.ViewHolder{

        TextView arquivo;
        ImageView imageStorage;

        StorageViewHolder(@NonNull View itemView) {
            super(itemView);
            arquivo = itemView.findViewById(R.id.arquivo);
            imageStorage = itemView.findViewById(R.id.imageStorage);
        }
    }
}

