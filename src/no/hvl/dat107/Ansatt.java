package no.hvl.dat107;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(schema = "oving1")
//@NamedQuery(name = "hentAlleAnsatte", query ="SELECT b FROM Ansatt as a order by a.brukernavn")
public class Ansatt {
	
	@Id
	private String brukernavn;
	private String fornavn;
	private String etternavn;
	private LocalDate ansettelsesdato;
	private String stilling;
	private Integer maanedslonn;
	
	public void brukernavn() {}
	
	public void brukernavn(String brukernavn, String fornavn, String etternavn, LocalDate ansettelsesdato, String stilling, Integer maanedslonn) {
		this.brukernavn = brukernavn;
		this.fornavn = fornavn;
		this.setEtternavn(etternavn);
		this.setAnsettelsesdato(ansettelsesdato);
		this.setStilling(stilling);
		this.setMaanedslonn(maanedslonn);
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

	public Integer getMaanedslonn() {
		return maanedslonn;
	}

	public void setMaanedslonn(Integer maanedslonn) {
		this.maanedslonn = maanedslonn;
	}
	
	@Override
	public String toString() {
		String s = "Ansatt: " + "brukernavn: " + getBrukernavn() + ", fornavn; " + getFornavn() + ", etternavn: " + getEtternavn() + ", ansettelsesdato: " + getAnsettelsesdato() + ", stilling: " + getStilling() + ", månedslønn:" + getMaanedslonn();
		return s;
	}
}
