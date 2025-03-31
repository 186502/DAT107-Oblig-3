package no.hvl.dat107.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(schema = "public", name = "avdeling")
public class Avdeling {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer avdeling_id;
    
    private String navn;
    
    @ManyToOne
    @JoinColumn(name = "sjef_id")
    private Ansatt sjef;
    
    @OneToMany(mappedBy = "avdeling")
    private List<Ansatt> ansatte;
    
    public Avdeling() {}
    
    public Avdeling(String navn) {
        this.navn = navn;
    }
    
    public void skrivUt() {
        System.out.printf("Avdeling: %d, %s, Sjef: %s %s%n", 
                avdeling_id, navn, 
                sjef != null ? sjef.getFornavn() : "", 
                sjef != null ? sjef.getEtternavn() : "");
    }
    
    public void skrivUtMedAnsatte() {
        skrivUt();
        System.out.println("Ansatte:");
        for (Ansatt a : ansatte) {
            System.out.printf("  %s %s%s%n", 
                    a.getFornavn(), a.getEtternavn(),
                    a.equals(sjef) ? " (Sjef)" : "");
        }
    }
    
    // Getters og setters
    public Integer getAvdelingId() {
        return avdeling_id;
    }

    public void setAvdelingId(Integer avdeling_id) {
        this.avdeling_id = avdeling_id;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public Ansatt getSjef() {
        return sjef;
    }

    public void setSjef(Ansatt sjef) {
        this.sjef = sjef;
    }

    public List<Ansatt> getAnsatte() {
        return ansatte;
    }
}