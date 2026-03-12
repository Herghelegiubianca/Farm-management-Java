import java.util.ArrayList;

public class Teste {

    public static void main(String[] args) {

        //scenariile
        testInitializareJoc();
        testCumparareAnimalReusita();
        testCumparareAnimalFonduriInsuficiente();
        testHranireAnimal();
        testPlantareSeminte();
        testVanzareResurse();
        testSortareAnimale();
    }

    // stare initiala
    public static void testInitializareJoc() {
        System.out.println("Running: testInitializareJoc...");
        ModelMVC model = new ModelMVC();

        //banii (Expected: 100)
        assertEqualsInt(100, model.getBani(), "Verificare Bani Initiali");

        //nr animale (1 Vaca + 2 Gaini + 1 Oaie = 4)
        assertEqualsInt(4, model.getAnimale().size(), "Verificare Nr. Animale Start");
    }

    // Cumpărare ---
    public static void testCumparareAnimalReusita() {
        System.out.println("\nRunning: testCumparareAnimalReusita...");
        ModelMVC model = new ModelMVC();

        // Initial 100 lei. Cumparam o gaina varianta 0 (cea mai ieftina - 15 lei)
        try {
            model.cumparaAnimal("gaina", 0, 1);

            // 100 - 15 = 85
            assertEqualsInt(85, model.getBani(), "Scadere bani dupa cumparare");

            // 4 initiale + 1 noua = 5
            assertEqualsInt(5, model.getAnimale().size(), "Crestere lista animale");

        } catch (FonduriInsuficienteException e) {
            fail("A aruncat exceptie desi avea bani suficienti: " + e.getMessage());
        }
    }

    // excepții
    public static void testCumparareAnimalFonduriInsuficiente() {
        System.out.println("\nRunning: testCumparareAnimalFonduriInsuficiente...");
        ModelMVC model = new ModelMVC();

        // =10 vaci = aprox 800-1000 lei, avem doar 100.
        try {
            model.cumparaAnimal("vaca", 0, 10);
            // trebuia sa dea eroare
            fail("Nu a aruncat FonduriInsuficienteException!");
        } catch (FonduriInsuficienteException e) {
            // CORECT
            pass("A aruncat corect exceptia de fonduri insuficiente.");
        }
    }

    // Hrănire, consumare resurse
    public static void testHranireAnimal() {
        System.out.println("\nRunning: testHranireAnimal...");
        ModelMVC model = new ModelMVC();

        try {
            // Vaca - Grau - 50kg)
            Animal a = model.getAnimale().get(0);
            Resursa r = model.getResurse().get(0);

            int nivelHranaInitial = a.getNivelHrana();
            int cantitateResursaInitial = r.getCantitate();
            int cantitateHranire = 10;
            model.hranesteAnimal(a, r, cantitateHranire);


            assertEqualsInt(nivelHranaInitial + cantitateHranire, a.getNivelHrana(), "Crestere nivel hrana animal");
            assertEqualsInt(cantitateResursaInitial - cantitateHranire, r.getCantitate(), "Scadere stoc resurse");

        } catch (Exception e) {
            fail("Eroare neasteptata la hranire: " + e.getMessage());
        }
    }

    // Plantare
    public static void testPlantareSeminte() {
        System.out.println("\nRunning: testPlantareSeminte...");
        ModelMVC model = new ModelMVC();

        try {

            // Trebuie cumparate seminte
            model.setBani(200);
            model.cumparaPlante("grau", 10);

            model.planteaza("grau", 5);

            // Lista initiala plante are 3 elemente - dupa plantare  4.
            assertEqualsInt(4, model.getPlante().size(), "Adaugare in lista de culturi");

            Planta ultimaPlanta = model.getPlante().get(model.getPlante().size() - 1);
            assertEqualsString("grau", ultimaPlanta.getNume(), "Nume planta corect");
            assertEqualsInt(5, ultimaPlanta.getCantitate(), "Cantitate plantata corect");

        } catch (Exception e) {
            fail("Eroare la plantare: " + e.getMessage());
        }
    }

    // Vanzare
    public static void testVanzareResurse() {
        System.out.println("\nRunning: testVanzareResurse...");
        ModelMVC model = new ModelMVC();

        // Avem Grau 50kg. Incercam sa vindem 100kg.
        try {
            model.vindeResursa("grau", 100);
            fail("A permis vanzarea a mai mult decat detinem!");
        } catch (ObjInexistentException e) {
            pass("A blocat corect vanzarea cantitatii prea mari.");
        }
    }

    // Sortare Animale
    public static void testSortareAnimale() {
        System.out.println("\nRunning: testSortareAnimale...");
        ModelMVC model = new ModelMVC();
        model.getAnimale().clear();

        Vaca v1 = new Vaca(5);  // Prod 5
        Vaca v2 = new Vaca(100); // Prod 100 (super vaca)
        Gaina g1 = new Gaina(1); // Prod 1

        model.getAnimale().add(v1);
        model.getAnimale().add(v2);
        model.getAnimale().add(g1);

        model.sorteazaAnimale();

        assertEqualsInt(100, model.getAnimale().get(0).getProductivitate(), "Primul animal (cel mai productiv)");
        assertEqualsInt(1, model.getAnimale().get(2).getProductivitate(), "Ultimul animal (cel mai putin productiv)");
    }


    public static void assertEqualsInt(int expected, int actual, String message) {
        if (expected == actual) {
            System.out.println("   [PASS] " + message + " (Expected: " + expected + ", Got: " + actual + ")");
        } else {
            System.err.println("   [FAIL] " + message + " (Expected: " + expected + ", BUT GOT: " + actual + ")");
        }
    }

    public static void assertEqualsString(String expected, String actual, String message) {
        if (expected.equals(actual)) {
            System.out.println("   [PASS] " + message);
        } else {
            System.err.println("   [FAIL] " + message + " (Expected: " + expected + ", BUT GOT: " + actual + ")");
        }
    }

    public static void fail(String message) {
        System.err.println("   [FAIL] " + message);
    }

    public static void pass(String message) {
        System.out.println("   [PASS] " + message);
    }
}