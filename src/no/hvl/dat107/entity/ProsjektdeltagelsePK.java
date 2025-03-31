package no.hvl.dat107.entity;

import java.io.Serializable;
import java.util.Objects;

public class ProsjektdeltagelsePK implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Integer ansatt;
    private Integer prosjekt;
    
    public ProsjektdeltagelsePK() {}
    
    public ProsjektdeltagelsePK(Integer ansatt, Integer prosjekt) {
        this.ansatt = ansatt;
        this.prosjekt = prosjekt;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProsjektdeltagelsePK that = (ProsjektdeltagelsePK) o;
        return Objects.equals(ansatt, that.ansatt) &&
               Objects.equals(prosjekt, that.prosjekt);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(ansatt, prosjekt);
    }
}
