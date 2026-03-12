public class Gaina extends Animal{

    public Gaina(){
        int aux=(int)(Math.random()*5 +1);
        super(10,aux);
    }

    public Gaina(int productivitate){
        super(10,productivitate);
    }

    @Override
    public void scadeNivelHrana() {
        int aux=this.getNivelHrana()-3;
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
            return new Resursa("oua",this.getProductivitate());
        }
        return null;
    }
}
