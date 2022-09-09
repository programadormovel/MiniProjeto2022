package br.edu.fieb.miniprojeto2022.ui.storage;

public class StorageModel {
    private String arquivo;

    public StorageModel(String name) {
        this.arquivo = name;
    }

    public String getArquivo() {
        return arquivo;
    }

    public void setArquivo(String arquivo) {
        this.arquivo = arquivo;
    }
}
