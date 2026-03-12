public class Vaca extends Animal{

    public Vaca(){
        int aux=(int)(Math.random()*20 +1);
        super(30,aux);
    }

    public Vaca(int productivitate){
        super(30,productivitate);
    }

    @Override
    public void scadeNivelHrana() {
        int aux=this.getNivelHrana()-10;
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
            return new Resursa("lapte",this.getProductivitate());
        }
        return null;
    }


}
