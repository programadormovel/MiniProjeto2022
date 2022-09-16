package br.edu.fieb.miniprojeto2022.ui.storage;

import java.sql.Blob;

public class StorageModel {
    private String arquivo;
    private byte[] foto;

    public StorageModel() {

    }

    public StorageModel(String name) {
        this.arquivo = name;
    }

    public StorageModel(String name, byte[] foto) {
        this.arquivo = name;
        this.foto = foto;
    }

    public String getArquivo() {
        return arquivo;
    }

    public void setArquivo(String arquivo) {
        this.arquivo = arquivo;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }
}
