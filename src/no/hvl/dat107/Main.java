package no.hvl.dat107;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import no.hvl.dat107.dao.AnsattDAO;
import no.hvl.dat107.dao.AvdelingDAO;
import no.hvl.dat107.dao.ProsjektDAO;
import no.hvl.dat107.entity.Ansatt;
import no.hvl.dat107.entity.Avdeling;
import no.hvl.dat107.entity.Prosjekt;

public class Main {
    
    private static Scanner scanner = new Scanner(System.in);
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    
    private static AnsattDAO ansattDAO = new AnsattDAO();
    private static AvdelingDAO avdelingDAO = new AvdelingDAO();
    private static ProsjektDAO prosjektDAO = new ProsjektDAO();
    private static Integer max_ansatt_nr;
    private static Integer max_avdeling_nr;
    private static Integer max_prosjekt_nr;
    
    public static void main(String[] args) {
        boolean fortsett = true;
        
        while (fortsett) {
        	//Finner max nummeret til ansatt for å gjøre valg lettere i menyen
        	List<Ansatt> ansatte = ansattDAO.finnAlleAnsatte();
            for (Ansatt a : ansatte) {
                max_ansatt_nr = a.getAnsattId();
            }
            
            //Finner max avdelingsnummer for å gjøre det lettere å velge i menyen
            List<Avdeling> avdelinger = avdelingDAO.finnAlleAvdelinger();
            for (Avdeling a : avdelinger) {
                max_avdeling_nr = a.getAvdelingId();
            }
            
            //Finner max prosjektnummer for å gjøre det lettere å velge i menyen
            List<Prosjekt> prosjekter = prosjektDAO.finnAlleProsjekter();
            for (Prosjekt p : prosjekter) {
                max_prosjekt_nr = p.getProsjektId();
            }
            
            visHovedmeny();
            int valg = lesInn("Velg en handling", 0, 15);
            
            switch (valg) {
                case 1: sokAnsattPaId(); break;
                case 2: sokAnsattPaBrukernavn(); break;
                case 3: visAlleAnsatte(); break;
                case 4: oppdaterStillingOgLonn(); break;
                case 5: leggTilNyAnsatt(); break;
                case 6: sokAvdelingPaId(); break;
                case 7: visAnsattePaAvdeling(); break;
                case 8: byttAvdeling(); break;
                case 9: leggTilNyAvdeling(); break;
                case 10: leggTilNyttProsjekt(); break;
                case 11: registrerProsjektdeltagelse(); break;
                case 12: registrerTimer(); break;
                case 13: visProsjektInfo(); break;
                case 14: slettObjekt(); break;
                case 0: fortsett = false; break;
                default: System.out.println("Ugyldig valg. Prøv igjen!");
            }
            
            System.out.println("\nTrykk ENTER for å fortsette...");
            scanner.nextLine();
        }
        
        System.out.println("Programmet avsluttes.");
        scanner.close();
    }
    
    private static void visHovedmeny() {
        System.out.println("\n--- FIRMAADMINISTRASJON ---");
        System.out.println("1. Finn ansatt på ID");
        System.out.println("2. Finn ansatt på brukernavn");
        System.out.println("3. Vis alle ansatte");
        System.out.println("4. Oppdater stilling og/eller lønn");
        System.out.println("5. Legg til ny ansatt");
        System.out.println("6. Finn avdeling på ID");
        System.out.println("7. Vis ansatte på avdeling");
        System.out.println("8. Bytt avdeling for en ansatt");
        System.out.println("9. Legg til ny avdeling");
        System.out.println("10. Legg til nytt prosjekt");
        System.out.println("11. Registrer prosjektdeltagelse");
        System.out.println("12. Før timer på prosjekt");
        System.out.println("13. Vis prosjektinformasjon");
        System.out.println("14. Slett (ansatt/avdeling/prosjekt)");
        System.out.println("0. Avslutt");
        System.out.println("-----------------------------");
    }
    
    // Hjelpemetoder for menyvalg
    
    private static void sokAnsattPaId() {
        System.out.println("\n--- SØK ANSATT PÅ ID ---");
        int id = lesInn("Ansatt-ID", 1, max_ansatt_nr);
        
        Ansatt ansatt = ansattDAO.finnAnsattMedId(id);
        if (ansatt != null) {
            ansatt.skrivUtMedProsjekter();
        } else {
            System.out.println("Fant ingen ansatt med ID: " + id);
        }
    }
    
    private static void sokAnsattPaBrukernavn() {
        System.out.println("\n--- SØK ANSATT PÅ BRUKERNAVN ---");
        System.out.print("Brukernavn: ");
        String brukernavn = scanner.nextLine().trim();
        
        try {
            Ansatt ansatt = ansattDAO.finnAnsattMedBrukernavn(brukernavn);
            ansatt.skrivUtMedProsjekter();
        } catch (Exception e) {
            System.out.println("Fant ingen ansatt med brukernavn: " + brukernavn);
        }
    }
    
    private static void visAlleAnsatte() {
        System.out.println("\n--- ALLE ANSATTE ---");
        List<Ansatt> ansatte = ansattDAO.finnAlleAnsatte();
        
        for (Ansatt a : ansatte) {
            a.skrivUt();
        }
        System.out.println("Totalt " + ansatte.size() + " ansatte");
    }
    
    private static void oppdaterStillingOgLonn() {
        System.out.println("\n--- OPPDATER STILLING OG LØNN ---");
        int id = lesInn("Ansatt-ID", 1, max_ansatt_nr);
        
        Ansatt ansatt = ansattDAO.finnAnsattMedId(id);
        if (ansatt == null) {
            System.out.println("Fant ingen ansatt med ID: " + id);
            return;
        }
        
        ansatt.skrivUt();
        
        System.out.print("Ny stilling (blank = uendret): ");
        String nyStilling = scanner.nextLine().trim();
        if (nyStilling.isBlank()) {
            nyStilling = null;
        }
        
        BigDecimal nyLonn = null;
        System.out.print("Ny månedslønn (blank = uendret): ");
        String lonnInput = scanner.nextLine().trim();
        if (!lonnInput.isBlank()) {
            try {
                nyLonn = new BigDecimal(lonnInput);
            } catch (NumberFormatException e) {
                System.out.println("Ugyldig beløp. Lønn blir uendret.");
            }
        }
        
        ansattDAO.oppdaterStillingOgLonn(id, nyStilling, nyLonn);
        System.out.println("Ansatt oppdatert!");
    }
    
    private static void leggTilNyAnsatt() {
        System.out.println("\n--- LEGG TIL NY ANSATT ---");
        
        System.out.print("Brukernavn (3-4 bokstaver): ");
        String brukernavn = scanner.nextLine().trim();
        
        System.out.print("Fornavn: ");
        String fornavn = scanner.nextLine().trim();
        
        System.out.print("Etternavn: ");
        String etternavn = scanner.nextLine().trim();
        
        LocalDate ansettelsesdato = null;
        while (ansettelsesdato == null) {
            System.out.print("Ansettelsesdato (dd.mm.yyyy): ");
            String datoInput = scanner.nextLine().trim();
            try {
                ansettelsesdato = LocalDate.parse(datoInput, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Ugyldig datoformat. Vennligst bruk dd.mm.yyyy");
            }
        }
        
        System.out.print("Stilling: ");
        String stilling = scanner.nextLine().trim();
        
        BigDecimal manedslonn = null;
        while (manedslonn == null) {
            System.out.print("Månedslønn: ");
            try {
                manedslonn = new BigDecimal(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Ugyldig beløp. Prøv igjen!");
            }
        }
        
        // Vis avdelinger
        System.out.println("\nTilgjengelige avdelinger:");
        List<Avdeling> avdelinger = avdelingDAO.finnAlleAvdelinger();
        for (Avdeling a : avdelinger) {
            a.skrivUt();
        }
        
        int avdelingId = lesInn("Velg avdelings-ID", 1, max_avdeling_nr);
        
        Ansatt nyAnsatt = new Ansatt(brukernavn, fornavn, etternavn, ansettelsesdato, stilling, manedslonn);
        ansattDAO.leggTilAnsatt(nyAnsatt, avdelingId);
        
        System.out.println("Ny ansatt registrert!");
    }
    
    private static void sokAvdelingPaId() {
        System.out.println("\n--- SØK AVDELING PÅ ID ---");
        int id = lesInn("Avdeling-ID", 1, max_avdeling_nr);
        
        Avdeling avdeling = avdelingDAO.finnAvdelingMedId(id);
        if (avdeling != null) {
            avdeling.skrivUtMedAnsatte();
        } else {
            System.out.println("Fant ingen avdeling med ID: " + id);
        }
    }
    
    private static void visAnsattePaAvdeling() {
        System.out.println("\n--- ANSATTE PÅ AVDELING ---");
        int id = lesInn("Avdeling-ID", 1, max_avdeling_nr);
        
        Avdeling avdeling = avdelingDAO.finnAvdelingMedId(id);
        if (avdeling != null) {
            avdeling.skrivUtMedAnsatte();
        } else {
            System.out.println("Fant ingen avdeling med ID: " + id);
        }
    }
    
    private static void byttAvdeling() {
        System.out.println("\n--- BYTT AVDELING FOR ANSATT ---");
        int ansattId = lesInn("Ansatt-ID", 1, max_ansatt_nr);
        
        Ansatt ansatt = ansattDAO.finnAnsattMedId(ansattId);
        if (ansatt == null) {
            System.out.println("Fant ingen ansatt med ID: " + ansattId);
            return;
        }
        
        ansatt.skrivUt();
        
        if (ansatt.erSjef()) {
            System.out.println("Denne ansatte er sjef og kan ikke bytte avdeling!");
            return;
        }
        
        // Vis avdelinger
        System.out.println("\nTilgjengelige avdelinger:");
        List<Avdeling> avdelinger = avdelingDAO.finnAlleAvdelinger();
        for (Avdeling a : avdelinger) {
            a.skrivUt();
        }
        
        int nyAvdelingId = lesInn("Velg ny avdelings-ID", 1, max_avdeling_nr);
        
        ansattDAO.byttAvdeling(ansattId, nyAvdelingId);
        System.out.println("Ansatt flyttet til ny avdeling!");
    }
    
    private static void leggTilNyAvdeling() {
        System.out.println("\n--- LEGG TIL NY AVDELING ---");
        
        System.out.print("Avdelingsnavn: ");
        String navn = scanner.nextLine().trim();
        
        // Vis ansatte
        System.out.println("\nVelg sjef for den nye avdelingen:");
        List<Ansatt> ansatte = ansattDAO.finnAlleAnsatte();
        for (Ansatt a : ansatte) {
            System.out.printf("ID: %d, %s %s, Avdeling: %s%n", 
                    a.getAnsattId(), a.getFornavn(), a.getEtternavn(), 
                    a.getAvdeling().getNavn());
        }
        
        int sjefId = lesInn("Velg ansatt-ID for sjef", 1, max_ansatt_nr);
        
        avdelingDAO.leggTilAvdeling(navn, sjefId);
        System.out.println("Ny avdeling opprettet!");
    }
    
    private static void leggTilNyttProsjekt() {
        System.out.println("\n--- LEGG TIL NYTT PROSJEKT ---");
        
        System.out.print("Prosjektnavn: ");
        String navn = scanner.nextLine().trim();
        
        System.out.print("Beskrivelse: ");
        String beskrivelse = scanner.nextLine().trim();
        
        prosjektDAO.leggTilProsjekt(navn, beskrivelse);
        System.out.println("Nytt prosjekt opprettet!");
    }
    
    private static void registrerProsjektdeltagelse() {
        System.out.println("\n--- REGISTRER PROSJEKTDELTAGELSE ---");
        
        // Vis ansatte
        System.out.println("\nTilgjengelige ansatte:");
        List<Ansatt> ansatte = ansattDAO.finnAlleAnsatte();
        for (Ansatt a : ansatte) {
            System.out.printf("ID: %d, %s %s%n", 
                    a.getAnsattId(), a.getFornavn(), a.getEtternavn());
        }
        
        int ansattId = lesInn("Velg ansatt-ID", 1, max_ansatt_nr);
        
        // Vis prosjekter
        System.out.println("\nTilgjengelige prosjekter:");
        List<Prosjekt> prosjekter = prosjektDAO.finnAlleProsjekter();
        for (Prosjekt p : prosjekter) {
            p.skrivUt();
        }
        
        int prosjektId = lesInn("Velg prosjekt-ID", 1, max_prosjekt_nr);
        
        System.out.print("Rolle i prosjektet: ");
        String rolle = scanner.nextLine().trim();
        
        prosjektDAO.registrerProsjektdeltagelse(ansattId, prosjektId, rolle);
        System.out.println("Prosjektdeltagelse registrert!");
    }
    
    private static void registrerTimer() {
        System.out.println("\n--- REGISTRER TIMER PÅ PROSJEKT ---");
        
        // Vis ansatte
        System.out.println("\nTilgjengelige ansatte:");
        List<Ansatt> ansatte = ansattDAO.finnAlleAnsatte();
        for (Ansatt a : ansatte) {
            System.out.printf("ID: %d, %s %s%n", 
                    a.getAnsattId(), a.getFornavn(), a.getEtternavn());
        }
        
        int ansattId = lesInn("Velg ansatt-ID", 1, max_ansatt_nr);
        
        // Vis prosjekter
        System.out.println("\nTilgjengelige prosjekter:");
        List<Prosjekt> prosjekter = prosjektDAO.finnAlleProsjekter();
        for (Prosjekt p : prosjekter) {
            p.skrivUt();
        }
        
        int prosjektId = lesInn("Velg prosjekt-ID", 1, max_prosjekt_nr);
        
        int timer = lesInn("Antall timer", 1, 1000);
        
        prosjektDAO.registrerTimer(ansattId, prosjektId, timer);
        System.out.println("Timer registrert!");
    }
    
    private static void visProsjektInfo() {
        System.out.println("\n--- PROSJEKTINFORMASJON ---");
        int id = lesInn("Prosjekt-ID", 1, max_prosjekt_nr);
        
        Prosjekt prosjekt = prosjektDAO.finnProsjektMedId(id);
        if (prosjekt != null) {
            prosjekt.skrivUtMedDeltagelser();
        } else {
            System.out.println("Fant ingen prosjekt med ID: " + id);
        }
    }
    
    private static void slettObjekt() {
        System.out.println("\n--- SLETT OBJEKT ---");
        System.out.println("1. Slett ansatt");
        System.out.println("2. Slett avdeling");
        System.out.println("3. Slett prosjekt");
        
        int valg = lesInn("Velg hva som skal slettes", 1, 3);
        
        switch (valg) {
            case 1:
                int ansattId = lesInn("Ansatt-ID", 1, max_ansatt_nr);
                try {
                    ansattDAO.slettAnsatt(ansattId);
                    System.out.println("Ansatt slettet!");
                } catch (Exception e) {
                    System.out.println("Kunne ikke slette ansatt: " + e.getMessage());
                }
                break;
                
            case 2:
                int avdelingId = lesInn("Avdeling-ID", 1, max_avdeling_nr);
                try {
                    avdelingDAO.slettAvdeling(avdelingId);
                    System.out.println("Avdeling slettet!");
                } catch (Exception e) {
                    System.out.println("Kunne ikke slette avdeling: " + e.getMessage());
                }
                break;
                
            case 3:
                int prosjektId = lesInn("Prosjekt-ID", 1, max_prosjekt_nr);
                try {
                    prosjektDAO.slettProsjekt(prosjektId);
                    System.out.println("Prosjekt slettet!");
                } catch (Exception e) {
                    System.out.println("Kunne ikke slette prosjekt: " + e.getMessage());
                }
                break;
        }
    }
    
    // Hjelpemetode for å lese inn heltall med validering
    private static int lesInn(String ledetekst, int min, int max) {
        int tall = 0;
        boolean gyldig = false;
        
        while (!gyldig) {
            System.out.print(ledetekst + " (" + min + "-" + max + "): ");
            try {
                tall = Integer.parseInt(scanner.nextLine().trim());
                if (tall >= min && tall <= max) {
                    gyldig = true;
                } else {
                    System.out.println("Tallet må være mellom " + min + " og " + max);
                }
            } catch (NumberFormatException e) {
                System.out.println("Ugyldig input. Må være et heltall.");
            }
        }
        
        return tall;
    }
}