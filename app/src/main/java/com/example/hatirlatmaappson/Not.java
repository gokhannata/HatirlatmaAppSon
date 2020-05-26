package com.example.hatirlatmaappson;

public class Not {
    public Not() {

    }

    public Not(String id, String notBaslik, String notAciklama, String notTarih,String notSaat, String notDurumu) {
        this.id = id;
        this.notBaslik = notBaslik;
        this.notAciklama = notAciklama;
        this.notTarih = notTarih;
        this.notSaat = notSaat;
        this.notDurumu = notDurumu;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNotBaslik() {
        return notBaslik;
    }

    public void setNotBaslik(String notBaslik) {
        this.notBaslik = notBaslik;
    }

    public String getNotAciklama() {
        return notAciklama;
    }

    public void setNotAciklama(String notAciklama) {
        this.notAciklama = notAciklama;
    }

    public String getNotTarih() {
        return notTarih;
    }

    public void setNotTarih(String notTarih) {
        this.notTarih = notTarih;
    }

    public String getNotSaat() {
        return notSaat;
    }

    public void setNotSaat(String notSaat) {
        this.notSaat = notSaat;
    }

    public String getNotDurumu() {
        return notDurumu;
    }

    public void setNotDurumu(String notDurumu) {
        this.notDurumu = notDurumu;
    }

    private String id;
    private String notBaslik;
    private String notAciklama;
    private String notTarih;
    private String notSaat;
    private String notDurumu;
}
