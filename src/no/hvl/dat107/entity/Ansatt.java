package no.hvl.dat107.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(schema = "public", name = "ansatt")
public class Ansatt {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ansatt_id;
    
    private String brukernavn;
    private String fornavn;
    private String etternavn;
    private LocalDate ansettelsesdato;
    private String stilling;
    private BigDecimal manedslonn;
    
    @ManyToOne
    @JoinColumn(name = "avdeling_id")
    private Avdeling avdeling;
    
    @OneToMany(mappedBy = "sjef")
    private List<Avdeling> lederFor;
    
    @OneToMany(mappedBy = "ansatt")
    private List<Prosjektdeltagelse> prosjektdeltagelser;
    
    public Ansatt() {}
    
    public Ansatt(String brukernavn, String fornavn, String etternavn, 
            LocalDate ansettelsesdato, String stilling, BigDecimal manedslonn) {
        this.brukernavn = brukernavn;
        this.fornavn = fornavn;
        this.etternavn = etternavn;
        this.ansettelsesdato = ansettelsesdato;
        this.stilling = stilling;
        this.manedslonn = manedslonn;
    }
    
    public void skrivUt() {
        System.out.printf("Ansatt: %d, %s, %s %s, Ansatt: %s, Stilling: %s, Lønn: %.2f, Avdeling: %s%n", 
                ansatt_id, brukernavn, fornavn, etternavn, 
                ansettelsesdato, stilling, manedslonn, 
                avdeling != null ? avdeling.getNavn() : "Ingen");
    }
    
    public void skrivUtMedProsjekter() {
        skrivUt();
        if (prosjektdeltagelser != null && !prosjektdeltagelser.isEmpty()) {
            System.out.println("Prosjekter:");
            for (Prosjektdeltagelse pd : prosjektdeltagelser) {
                System.out.printf("  %s, Rolle: %s, Timer: %d%n", 
                        pd.getProsjekt().getNavn(), pd.getRolle(), pd.getTimer());
            }
        } else {
            System.out.println("Ingen prosjektdeltagelser.");
        }
    }
    
    // Get og set metoder
    public Integer getAnsattId() {
        return ansatt_id;
    }

    public void setAnsattId(Integer ansatt_id) {
        this.ansatt_id = ansatt_id;
    }

    public String getBrukernavn() {
        return brukernavn;
    }

    public void setBrukernavn(String brukernavn) {
        this.brukernavn = brukernavn;
    }

    public String getFornavn() {
        return fornavn;
    }

    public void setFornavn(String fornavn) {
        this.fornavn = fornavn;
    }

    public String getEtternavn() {
        return etternavn;
    }

    public void setEtternavn(String etternavn) {
        this.etternavn = etternavn;
    }

    public LocalDate getAnsettelsesdato() {
        return ansettelsesdato;
    }

    public void setAnsettelsesdato(LocalDate ansettelsesdato) {
        this.ansettelsesdato = ansettelsesdato;
    }

    public String getStilling() {
        return stilling;
    }

    public void setStilling(String stilling) {
        this.stilling = stilling;
    }

    public BigDecimal getManedslonn() {
        return manedslonn;
    }

    public void setManedslonn(BigDecimal manedslonn) {
        this.manedslonn = manedslonn;
    }

    public Avdeling getAvdeling() {
        return avdeling;
    }

    public void setAvdeling(Avdeling avdeling) {
        this.avdeling = avdeling;
    }

    public List<Prosjektdeltagelse> getProsjektdeltagelser() {
        return prosjektdeltagelser;
    }

    public boolean erSjef() {
        return lederFor != null && !lederFor.isEmpty();
    }
}
