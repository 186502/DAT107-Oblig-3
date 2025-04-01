package no.hvl.dat107.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.io.Serializable;

@Entity
@Table(schema = "public", name = "prosjektdeltagelse")
@IdClass(ProsjektdeltagelsePK.class)
public class Prosjektdeltagelse {
    
    @Id
    @ManyToOne
    @JoinColumn(name = "ansatt_id")
    private Ansatt ansatt;
    
    @Id
    @ManyToOne
    @JoinColumn(name = "prosjekt_id")
    private Prosjekt prosjekt;
    
    private String rolle;
    private Integer timer;
    
    public Prosjektdeltagelse() {}
    
    public Prosjektdeltagelse(Ansatt ansatt, Prosjekt prosjekt, String rolle, Integer timer) {
        this.ansatt = ansatt;
        this.prosjekt = prosjekt;
        this.rolle = rolle;
        this.timer = timer;
    }
    
    public void skrivUt() {
        System.out.printf("Prosjektdeltagelse: %s %s, Prosjekt: %s, Rolle: %s, Timer: %d%n",
                ansatt.getFornavn(), ansatt.getEtternavn(),
                prosjekt.getNavn(), rolle, timer);
    }
    
    // Get og set metoder
    public Ansatt getAnsatt() {
        return ansatt;
    }

    public void setAnsatt(Ansatt ansatt) {
        this.ansatt = ansatt;
    }

    public Prosjekt getProsjekt() {
        return prosjekt;
    }

    public void setProsjekt(Prosjekt prosjekt) {
        this.prosjekt = prosjekt;
    }

    public String getRolle() {
        return rolle;
    }

    public void setRolle(String rolle) {
        this.rolle = rolle;
    }

    public Integer getTimer() {
        return timer;
    }

    public void setTimer(Integer timer) {
        this.timer = timer;
    }
    
    public void leggTilTimer(int antallTimer) {
        this.timer += antallTimer;
    }
}