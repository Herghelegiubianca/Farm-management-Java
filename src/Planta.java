public class Planta implements Producator{
       String nume;
       int cantitate;
       boolean aProdus=false;
       int stadiuCrestere=0;
       int nrRundeGata;

       public Planta(){}

    public Planta(String nume, int cantitate){
           this.nume=nume;
           this.cantitate=cantitate;
           if(nume.equals("grau")){
               this.nrRundeGata=3;
           }
        else if(nume.equals("porumb")){
            this.nrRundeGata=4;
        }
        else{
            this.nrRundeGata=2;
           }
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public int getCantitate() {
        return cantitate;
    }

    public void setCantitate(int cantitate) {
        this.cantitate = cantitate;
    }

    public boolean getAProdus() {
        return aProdus;
    }

    public void setAProdus(boolean aProdus) {
        this.aProdus = aProdus;
    }

    public int getStadiuCrestere() {
        return stadiuCrestere;
    }

    public void setStadiuCrestere(int stadiuCrestere) {
        this.stadiuCrestere = stadiuCrestere;
    }

    public int getNrRundeGata() {
        return nrRundeGata;
    }

    public void setNrRundeGata(int nrRundeGata) {
        this.nrRundeGata = nrRundeGata;
    }

    public void creste(){
           if(!this.aProdus){
               this.stadiuCrestere++;
               if(this.stadiuCrestere>=this.nrRundeGata){
                   this.aProdus=true;
               }
           }
    }

    @Override
    public Resursa produce(){
        if(aProdus){
           return new Resursa(this.nume, this.cantitate);
        }
        return null;
    }
}
