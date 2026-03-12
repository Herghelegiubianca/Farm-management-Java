import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

public class ModelMVC {
    private ArrayList<Resursa>resurse;
    private ArrayList<Animal>animale;
    private ArrayList<Planta>plante;
    private ArrayList<Planta> neplantate;
    private int bani;
    private int runda=1;
    private boolean stillPlaying=true;

    public ModelMVC(){
        this.resurse=new ArrayList<>();
        this.animale=new ArrayList<>();
        this.plante=new ArrayList<>();
        this.neplantate=new ArrayList<>();

        //utilizatorul primeste initial cateva resurse, animale si plante - pt a avea un start
        this.resurse.add(new Resursa("grau",50));
        this.resurse.add(new Resursa("porumb",30));
        this.resurse.add(new Resursa("trifoi",20));
        this.resurse.add(new Resursa("lapte",0));
        this.resurse.add(new Resursa("oua",0));
        this.resurse.add(new Resursa("lana",0));

        this.neplantate.add(new Planta("grau",0));
        this.neplantate.add(new Planta("porumb",0));
        this.neplantate.add(new Planta("trifoi",0));

        this.animale.add(new Vaca());
        this.animale.add(new Gaina());
        this.animale.add(new Gaina());
        this.animale.add(new Oaie());

        this.plante.add(new Planta("grau",30));
        this.plante.add(new Planta("porumb",30));
        this.plante.add(new Planta("trifoi",20));

        //de asemenea, utilizatorul primeste initial si o suma de bani
        this.bani=100;
        this.sorteazaAnimale();

    }

    public ArrayList<Planta> getNeplantate() {
        return neplantate;
    }

    public void setNeplantate(ArrayList<Planta> neplantate) {
        this.neplantate = neplantate;
    }

    public ArrayList<Resursa> getResurse() {
        return resurse;
    }

    public void setResurse(ArrayList<Resursa> resurse) {
        this.resurse = resurse;
    }

    public ArrayList<Animal> getAnimale() {
        return animale;
    }

    public void setAnimale(ArrayList<Animal> animale) {
        this.animale = animale;
    }

    public ArrayList<Planta> getPlante() {
        return plante;
    }

    public void setPlante(ArrayList<Planta> plante) {
        this.plante = plante;
    }

    public int getBani() {
        return bani;
    }

    public void setBani(int bani) {
        this.bani = bani;
    }

    public int getRunda() {
        return runda;
    }

    public void setRunda(int runda) {
        this.runda = runda;
    }

    public boolean isStillPlaying() {
        return stillPlaying;
    }

    public void setStillPlaying(boolean stillPlaying) {
        this.stillPlaying = stillPlaying;
    }

    public ArrayList<String> rundaNoua(){
        this.runda++;

        ArrayList<String>mesaje=new ArrayList<>();
        int nrVaciPierdute=0;
        int nrOiPierdute=0;
        int nrGainiPierdute=0;

        Iterator<Animal> i=this.animale.iterator();
        while(i.hasNext()){
            Animal a=i.next();
            a.scadeNivelHrana();

            if(!a.getEsteViu()){
                i.remove();
                if(a instanceof Vaca){
                    nrVaciPierdute++;
                    this.setBani(this.getBani()-2000);
                }
                else if(a instanceof Gaina){
                    nrGainiPierdute++;
                    this.setBani(this.getBani()-1000);
                }
                else{
                    nrOiPierdute++;
                    this.setBani(this.getBani()-1500);
                }
            }
        }
        if(nrVaciPierdute>0) mesaje.add("Ati pierdut "+nrVaciPierdute+" vaci");
        if(nrOiPierdute>0) mesaje.add("Ati pierdut "+nrOiPierdute+" oi");
        if(nrGainiPierdute>0) mesaje.add("Ati pierdut "+nrGainiPierdute+" gaini");

        Iterator<Planta>j=this.plante.iterator();
        while(j.hasNext()){
            Planta p=j.next();
            p.creste();

            if(p.aProdus){
                mesaje.add("Recolta de "+p.getNume()+", de cantitate="+p.getCantitate()+" kg, este gata!");
            }
        }

        this.sorteazaAnimale();

        this.stopJoc();

        return mesaje;
    }

    public void hranesteAnimal(Animal a, Resursa r, int cantitate) throws ResursaInsuficientaException,ResursaNecorespunzatoare{
        if(a instanceof Vaca){
            if(r.getNume().equals("grau")||r.getNume().equals("porumb")||r.getNume().equals("trifoi")){
                if(r.getCantitate()>0 && cantitate<=r.getCantitate()){
                    a.hraneste(cantitate);
                    r.setCantitate(r.getCantitate()-cantitate);
                }
                else{
                    throw new ResursaInsuficientaException();
                }
            }
            else{
                throw new ResursaNecorespunzatoare();
            }

        }
        else if(a instanceof Gaina){
            if(r.getNume().equals("grau")||r.getNume().equals("porumb")){
                if(r.getCantitate()>0 && cantitate<=r.getCantitate()){
                    a.hraneste(cantitate);
                    r.setCantitate(r.getCantitate()-cantitate);
                }
                else{
                    throw new ResursaInsuficientaException();
                }
            }
            else{
                throw new ResursaNecorespunzatoare();
            }
        }
        else {
            if(r.getNume().equals("trifoi")||r.getNume().equals("porumb")){
                if(r.getCantitate()>0 && cantitate<=r.getCantitate()){
                    a.hraneste(cantitate);
                    r.setCantitate(r.getCantitate()-cantitate);
                }
                else{
                    throw new ResursaInsuficientaException();
                }
            }
            else{
                throw new ResursaNecorespunzatoare();
            }
        }
    }

    public void colecteazaTot() throws RandInsuficientException{
        boolean colected=false;
        for(Animal a: this.animale){
            if(a.getAProdus()){
                Resursa aux=a.produce();
                for(Resursa r: this.resurse){
                    if(a instanceof Vaca && r.getNume().equals("lapte")){
                        r.adaugaCantitate(aux.getCantitate());
                        colected=true;
                    }
                    if(a instanceof Gaina && r.getNume().equals("oua")){
                        r.adaugaCantitate(aux.getCantitate());
                        colected=true;
                    }
                    if(a instanceof Oaie && r.getNume().equals("lana")){
                        r.adaugaCantitate(aux.getCantitate());
                        colected=true;
                    }
                }
            }
        }

        Iterator<Planta>i=this.plante.iterator();
        while(i.hasNext()){
            Planta p=i.next();
            if(p.getAProdus()){
                Resursa aux=p.produce();
                for(Resursa r: this.resurse){
                    if(p.getNume().equals("grau") && r.getNume().equals("grau")){
                        r.adaugaCantitate(aux.getCantitate());
                        i.remove();
                        colected=true;
                        break;
                    }
                    if(p.getNume().equals("porumb") && r.getNume().equals("porumb")){
                        r.adaugaCantitate(aux.getCantitate());
                        i.remove();
                        colected=true;
                        break;
                    }
                    if(p.getNume().equals("trifoi") && r.getNume().equals("trifoi")){
                        r.adaugaCantitate(aux.getCantitate());
                        i.remove();
                        colected=true;
                        break;
                    }
                }
            }
        }

        if(!colected){
           throw new RandInsuficientException();
        }
    }

    public void cumparaAnimal(String nume, int varianta, int nr) throws FonduriInsuficienteException{
           if(nume.equals("vaca")){
               if(varianta==0){
                   int aux=nr*80;
                   if(this.bani-aux>=0){
                       this.bani-=aux;
                       for(int i=0;i<nr;i++){
                           this.animale.add(new Vaca(5));
                       }
                   }
                   else throw new FonduriInsuficienteException();
               }
               else if(varianta==1){
                   int aux=nr*120;
                   if(this.bani-aux>=0){
                       this.bani-=aux;
                       for(int i=0;i<nr;i++){
                           this.animale.add(new Vaca(10));
                       }
                   }
                   else throw new FonduriInsuficienteException();
               }
               else{
                   int aux=nr*100;
                   if(this.bani-aux>=0){
                       this.bani-=aux;
                       for(int i=0;i<nr;i++){
                           this.animale.add(new Vaca());
                       }
                   }
                   else throw new FonduriInsuficienteException();
               }
           }
           else  if(nume.equals("oaie")){
               if(varianta==0){
                   int aux=nr*40;
                   if(this.bani-aux>=0){
                       this.bani-=aux;
                       for(int i=0;i<nr;i++){
                           this.animale.add(new Oaie(3));
                       }
                   }
                   else throw new FonduriInsuficienteException();
               }
               else if(varianta==1){
                   int aux=nr*80;
                   if(this.bani-aux>=0){
                       this.bani-=aux;
                       for(int i=0;i<nr;i++){
                           this.animale.add(new Oaie(7));
                       }
                   }
                   else throw new FonduriInsuficienteException();
               }
               else{
                   int aux=nr*50;
                   if(this.bani-aux>=0){
                       this.bani-=aux;
                       for(int i=0;i<nr;i++){
                           this.animale.add(new Oaie());
                       }
                   }
                   else throw new FonduriInsuficienteException();
               }
           }
           else{
                   if(varianta==0){
                       int aux=nr*15;
                       if(this.bani-aux>=0){
                           this.bani-=aux;
                           for(int i=0;i<nr;i++){
                               this.animale.add(new Gaina(2));
                           }
                       }
                       else throw new FonduriInsuficienteException();
                   }
                   else if(varianta==1){
                       int aux=nr*35;
                       if(this.bani-aux>=0){
                           this.bani-=aux;
                           for(int i=0;i<nr;i++){
                               this.animale.add(new Gaina(5));
                           }
                       }
                       else throw new FonduriInsuficienteException();
                   }
                   else{
                       int aux=nr*22;
                       if(this.bani-aux>=0){
                           this.bani-=aux;
                           for(int i=0;i<nr;i++){
                               this.animale.add(new Gaina());
                           }
                       }
                       else throw new FonduriInsuficienteException();
                   }
           }
           this.sorteazaAnimale();
    }

    //cumpar seminte
    public void cumparaPlante(String nume, int nr) throws FonduriInsuficienteException{
        if(nume.equals("grau")){
            int aux=nr*1;
            if(this.bani-aux>=0){
                this.bani-=aux;
                for(Planta p: this.neplantate){
                    if(p.getNume().equals(nume)){
                        p.setCantitate(p.getCantitate()+nr);
                        break;
                    }
                }
            }
            else {
                throw new FonduriInsuficienteException();
            }
        }
        else  if(nume.equals("porumb")){
            int aux=nr*2;
            if(this.bani-aux>=0){
                this.bani-=aux;
                for(Planta p: this.neplantate){
                    if(p.getNume().equals(nume)){
                        p.setCantitate(p.getCantitate()+nr);
                        break;
                    }
                }
            }
            else {
                throw new FonduriInsuficienteException();
            }
        }
        else {
            int aux=nr*7;
            if(this.bani-aux>=0){
                this.bani-=aux;
                for(Planta p: this.neplantate){
                    if(p.getNume().equals(nume)){
                        p.setCantitate(p.getCantitate()+nr);
                        break;
                    }
                }
            }
            else {
                throw new FonduriInsuficienteException();
            }
        }
    }

    //cumpar hrana
    public void cumparHrana (String nume, int nr) throws FonduriInsuficienteException{
           int aux=0;
        if(nume.equals("grau")){
               aux=nr*10;}
        else if(nume.equals("porumb")){
            aux=nr*15;
        }
        else{
            aux=nr*35;
        }
               if(this.bani-aux>=0){
                   this.bani-=aux;
                   for(Resursa r: this.resurse){
                       if(r.getNume().equals(nume)){
                           r.adaugaCantitate(nr);
                           break;
                       }
                   }
               }
               else{
                   throw new FonduriInsuficienteException();
               }

    }

    public void vindeResursa(String nume, int nr) throws ObjInexistentException{
        for(Resursa r: this.resurse){
            if(r.getNume().equals(nume)){
                if(nr<=r.getCantitate()){
                    r.setCantitate(r.getCantitate()-nr);
                    this.bani+=nr*r.getPret();
                    break;
                }
                else{
                    throw new ObjInexistentException();
                }
            }
        }
    }

    public void sorteazaAnimale(){
        Collections.sort(this.animale, new Comparator<Animal>(){
            @Override
            public int compare(Animal a1, Animal a2) {
                return Integer.compare(a2.getProductivitate(),a1.getProductivitate());
            }
        });
    }

    public void planteaza(String nume, int cantitate) throws PlantareImposibilaException{
        for(Planta p: this.neplantate){
            if(p.getNume().equals(nume)){
                if(cantitate<=p.getCantitate()){
                    p.setCantitate(p.getCantitate()-cantitate);
                    this.plante.add(new Planta(nume,cantitate));
                    break;
                }
                else{
                    throw new PlantareImposibilaException();
                }
            }
        }
    }

    public void resetJoc(){
        this.resurse=new ArrayList<>();
        this.animale=new ArrayList<>();
        this.plante=new ArrayList<>();
        this.neplantate=new ArrayList<>();

        //utilizatorul primeste initial cateva resurse, animale si plante - pt a avea un start
        this.resurse.add(new Resursa("grau",50));
        this.resurse.add(new Resursa("porumb",30));
        this.resurse.add(new Resursa("trifoi",20));
        this.resurse.add(new Resursa("lapte",0));
        this.resurse.add(new Resursa("oua",0));
        this.resurse.add(new Resursa("lana",0));

        this.neplantate.add(new Planta("grau",0));
        this.neplantate.add(new Planta("porumb",0));
        this.neplantate.add(new Planta("trifoi",0));

        this.animale.add(new Vaca());
        this.animale.add(new Gaina());
        this.animale.add(new Gaina());
        this.animale.add(new Oaie());

        this.plante.add(new Planta("grau",30));
        this.plante.add(new Planta("porumb",30));
        this.plante.add(new Planta("trifoi",20));

        this.runda=1;
        this.stillPlaying=true;

        //de asemenea, utilizatorul primeste initial si o suma de bani
        this.bani=100;
        this.sorteazaAnimale();
    }

    public void stopJoc(){
        if(this.bani<15 || this.animale.isEmpty())
            this.stillPlaying=false;
    }

}
