public abstract class Animal implements Growable, Producator {
    int nivelHrana;
   private  int productivitate;
   private  boolean esteViu=true;
    private boolean aProdus=false;

    public Animal(){};

    public Animal(int nivelHrana, int productivitate){
        this.nivelHrana=nivelHrana;
        this.productivitate=productivitate;
    }

    public int getNivelHrana() {
        return nivelHrana;
    }

    public void setNivelHrana(int nivelHrana) {
        this.nivelHrana = nivelHrana;
    }

    public int getProductivitate() {
        return productivitate;
    }

    public void setProductivitate(int productivitate) {
        this.productivitate = productivitate;
    }

    public boolean getEsteViu() {
        return esteViu;
    }

    public void setEsteViu(boolean esteViu) {
        this.esteViu = esteViu;
    }

    public boolean getAProdus() {
        return aProdus;
    }

    public void setAProdus(boolean aProdus) {
        this.aProdus = aProdus;
    }

    public abstract void scadeNivelHrana();

    public void hraneste(int cantitate){
        this.nivelHrana+=cantitate;
    }

    public abstract Resursa produce();
}
