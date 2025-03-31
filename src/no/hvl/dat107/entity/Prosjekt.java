package no.hvl.dat107.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(schema = "public", name = "prosjekt")
public class Prosjekt {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer prosjekt_id;
    
    private String navn;
    private String beskrivelse;
    
    @OneToMany(mappedBy = "prosjekt")
    private List<Prosjektdeltagelse> deltagelser;
    
    public Prosjekt() {}
    
    public Prosjekt(String navn, String beskrivelse) {
        this.navn = navn;
        this.beskrivelse = beskrivelse;
    }
    
    public void skrivUt() {
        System.out.printf("Prosjekt: %d, %s, %s%n", 
                prosjekt_id, navn, beskrivelse);
    }
    
    public void skrivUtMedDeltagelser() {
        skrivUt();
        System.out.println("Deltagere:");
        int totalTimer = 0;
        
        for (Prosjektdeltagelse pd : deltagelser) {
            Ansatt a = pd.getAnsatt();
            System.out.printf("  %s %s, Rolle: %s, Timer: %d%n", 
                    a.getFornavn(), a.getEtternavn(), pd.getRolle(), pd.getTimer());
            totalTimer += pd.getTimer();
        }
        
        System.out.println("Totalt antall timer: " + totalTimer);
    }
    
    // Getters og setters
    public Integer getProsjektId() {
        return prosjekt_id;
    }

    public void setProsjektId(Integer prosjekt_id) {
        this.prosjekt_id = prosjekt_id;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public String getBeskrivelse() {
        return beskrivelse;
    }

    public void setBeskrivelse(String beskrivelse) {
        this.beskrivelse = beskrivelse;
    }

    public List<Prosjektdeltagelse> getDeltagelser() {
        return deltagelser;
    }
}