package com.test.marocourses;

public class Produit {

    private Long id;
    private String designation;
    private int quantite;

    public Produit(Long id, String designation, int quantite) {
        this.id = id;
        this.designation = designation;
        this.quantite = quantite;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }
}