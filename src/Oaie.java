public class Oaie extends Animal{

    public Oaie(){
        int aux=(int)(Math.random()*10+1);
        super(20,aux);
    }

    public Oaie(int productivitate){
        super(20,productivitate);
    }


    @Override
    public void scadeNivelHrana() {
        int aux=this.getNivelHrana()-5;
        if(aux<0)
        {
            this.setNivelHrana(0);
            this.setEsteViu(false);
        }
        else{
            this.setNivelHrana(aux);
            this.setAProdus(true);
        }
    }

    @Override
    public Resursa produce() {
        if(this.getEsteViu()){
            this.setAProdus(false);
            return new Resursa("lana",this.getProductivitate());
        }
        return null;
    }
}
