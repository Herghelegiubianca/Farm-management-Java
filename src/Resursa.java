public class Resursa {
    private String nume;
    private int cantitate;
    private int pret; //pretul cu care se vinde

    public Resursa(){}

    public Resursa(String nume, int cantitate){
        this.nume=nume;
        this.cantitate=cantitate;
        if(nume.equals("lapte")){
            this.pret=6;
        }
        else if(nume.equals("oua")){
            this.pret=2;
        }
        else if(nume.equals("lana")){
            this.pret=80;
        }
        else if(nume.equals("grau")){
            this.pret=1;
        }
        else if(nume.equals("porumb")){
            this.pret=2;
        }
        else{
            this.pret=7;
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

    public int getPret() {
        return pret;
    }

    public void setPret(int pret) {
        this.pret = pret;
    }

    public void adaugaCantitate(int cantitate){
        this.cantitate+=cantitate;
    }
}
