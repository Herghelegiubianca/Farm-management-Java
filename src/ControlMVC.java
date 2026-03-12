import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class ControlMVC {
    private ModelMVC model;
    private ViewMVC view;

    public ControlMVC(ModelMVC m, ViewMVC v){
        this.model=m;
        this.view=v;

        v.addColectListener(new ColectListener());
        v.addHranesteListener(new HranesteListener());
        v.addMagazinListener(new MagazinListener());
        v.addRundaNouaListener(new RundaNouaListener());
        v.addPlanteazaListener(new PlanteazaListener());
        v.addExitListener(new ExitListener());

    }

    private class ColectListener implements ActionListener{

        public ColectListener(){}

        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                model.colecteazaTot();
                view.updateView();
            }catch(RandInsuficientException rie){
                view.showMessage(rie.getMessage());
            }
        }
    }

    private class HranesteListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            int indexAnimal=view.getListaAnimale().getSelectedIndex();
            if(indexAnimal==-1){
                view.showMessage("Trebuie sa selectati un animal din lista!");
                return;
            }

            int indexResursa=view.getListaResurse().getSelectedIndex();
            if(indexResursa==-1){
                view.showMessage("Trebuie sa selectati o resursa din lista!");
                return;
            }

            String cantitate=view.cantitateHrana();
            if (cantitate == null || cantitate.isEmpty()) {
            return;
            }

            if(indexResursa>=model.getResurse().size()){
                view.showMessage("Animalele nu pot fi hranite cu seminte");
                return;
            }

            try{
                int cant=Integer.parseInt(cantitate);
                model.hranesteAnimal(model.getAnimale().get(indexAnimal),model.getResurse().get(indexResursa),cant);
                view.updateView();
            }
            catch(ResursaInsuficientaException rie){
                view.showMessage(rie.getMessage());
            }
            catch(ResursaNecorespunzatoare ri){
                view.showMessage(ri.getMessage());
            }

        }
    }

    private class RundaNouaListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            ArrayList<String> mesaje=model.rundaNoua();
            view.updateView();

            if(!model.isStillPlaying()){
                int optiune=view.gameOver();
                if(optiune==JOptionPane.YES_OPTION){
                    model.resetJoc();
                    view.updateView();
                }
                else{
                    System.exit(0);
                }
            }
            else {
                for (String s : mesaje) {
                    view.showMessage(s);
                }
                view.updateView();
            }
        }
    }

    private class PlanteazaListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
          try{
              String comanda=view.tipPlanta();
              String cantitate=view.nrSeminte();
              if (comanda==null || cantitate == null || cantitate.isEmpty()) {
                  return;
              }

              int cant=Integer.parseInt(cantitate);
              model.planteaza(comanda,cant);

              view.updateView();

            }
          catch(PlantareImposibilaException pie){
              view.showMessage(pie.getMessage());
          }
        }
    }

    private class MagazinListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            ViewMVC.Magazin m=view.afiseazaMagazin();

            if(m==null)
                return;

            try{
                if(m.getAlegere().equals("cumpara")){
                    if(m.getTip().equals("animal")){
                        model.cumparaAnimal(m.getProdus(),m.getVarianta(),m.getCantitate());
                    }
                    if(m.getTip().equals("planta")){
                        model.cumparaPlante(m.getProdus(),m.getCantitate());
                    }
                    if(m.getTip().equals("hrana")){
                        model.cumparHrana(m.getProdus(),m.getCantitate());
                    }
                }
                else{
                    model.vindeResursa(m.getProdus(),m.getCantitate());
                }

                view.updateView();
            }catch(FonduriInsuficienteException fie){
                view.showMessage(fie.getMessage());
            }
            catch(ObjInexistentException oie){
                view.showMessage(oie.getMessage());
            }

        }
    }

    private class ExitListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }


}
