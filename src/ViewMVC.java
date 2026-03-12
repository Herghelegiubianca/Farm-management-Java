import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ViewMVC extends JFrame {
    ModelMVC model;
    private JButton colect;
    private JButton hraneste;
    private JButton magazin;
    private JButton planteaza;
    private JButton rundaUrmatoare;
    private JButton exit;

    private DefaultListModel<String> listMAnimale=new DefaultListModel<>();
    private JList<String> listaAnimale=new JList<>(listMAnimale);

    private DefaultListModel<String> listMPlante=new DefaultListModel<>();
    private JList<String> listaPlante=new JList<>(listMPlante);

    private DefaultListModel<String> listMResurse=new DefaultListModel<>();
    private JList<String> listaResurse=new JList<>(listMResurse);

    private JLabel bani=new JLabel("Portofel: 100 lei");
    private JLabel runda= new JLabel("Runda nr. 1");


    public ViewMVC(ModelMVC model){

        this.model=model;

        setTitle("MY FARM");
        setSize(800,800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        this.colect=new JButton("Recolteaza");
        this.hraneste=new JButton("Hraneste...");
        this.magazin=new JButton("Magazin");
        this.planteaza=new JButton("Planteaza...");
        this.rundaUrmatoare=new JButton("Runda urmatoare!");
        this.exit=new JButton("End Game");

        JPanel panel1=new JPanel();
        panel1.setLayout(new GridLayout(2,3));

        panel1.add(colect);
        panel1.add(hraneste);
        panel1.add(planteaza);
        panel1.add(magazin);
        panel1.add(rundaUrmatoare);
        panel1.add(exit);

        this.add(panel1,BorderLayout.SOUTH);

        JPanel panel2=new JPanel();
        panel2.setLayout(new FlowLayout());
        panel2.add(runda);
        panel2.add(bani);
        this.add(panel2,BorderLayout.NORTH);

        JPanel panel3=new JPanel();
        panel3.setLayout(new GridLayout(1,3,10,10));
        panel3.add(newPanelList("Animale",listaAnimale));
        panel3.add(newPanelList("Plante",listaPlante));
        panel3.add(newPanelList("Inventar",listaResurse));

        this.add(panel3,BorderLayout.CENTER);

        updateView();
        setVisible(true);

    }

    public class Magazin{
        private String alegere;
        private String produs;
        private String tip;
        private int varianta;
        private int cantitate;

        public Magazin(){}

        public Magazin(String alegere, String produs, int cantitate, String tip, int varianta){
            this.alegere=alegere;
            this.produs=produs;
            this.cantitate=cantitate;
            this.tip=tip;
            this.varianta=varianta;
        }

        public String getTip() {
            return tip;
        }

        public void setTip(String tip) {
            this.tip = tip;
        }

        public int getVarianta() {
            return varianta;
        }

        public void setVarianta(int varianta) {
            this.varianta = varianta;
        }

        public String getAlegere() {
            return alegere;
        }

        public void setAlegere(String alegere) {
            this.alegere = alegere;
        }

        public String getProdus() {
            return produs;
        }

        public void setProdus(String produs) {
            this.produs = produs;
        }

        public int getCantitate() {
            return cantitate;
        }

        public void setCantitate(int cantitate) {
            this.cantitate = cantitate;
        }

    }

    public JList<String> getListaAnimale() {
        return listaAnimale;
    }

    public void setListaAnimale(JList<String> listaAnimale) {
        this.listaAnimale = listaAnimale;
    }

    public JList<String> getListaPlante() {
        return listaPlante;
    }

    public void setListaPlante(JList<String> listaPlante) {
        this.listaPlante = listaPlante;
    }

    public JList<String> getListaResurse() {
        return listaResurse;
    }

    public void setListaResurse(JList<String> listaResurse) {
        this.listaResurse = listaResurse;
    }

    // metoda existenta este GENERICĂ
// <T> înseamnă că metoda acceptă o JList de ORICE tip (String, Integer, Animal etc.)
    public <T> JPanel newPanelList(String nume, JList<T> l){
        JPanel panel=new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(nume));

        panel.add(new JScrollPane(l),BorderLayout.CENTER);

        return panel;
    }

    public void updateView(){
        this.bani.setText("Portofel: "+this.model.getBani()+" lei");
        this.runda.setText("Runda nr. "+this.model.getRunda());

        listMAnimale.clear();
        int nrVaca=0;
        int nrGaina=0;
        int nrOaie=0;
        String aux=" ";

        for(Animal a: this.model.getAnimale()){
            if(a instanceof Vaca){
                nrVaca++;
                aux="Vaca "+nrVaca;
            }
            else if(a instanceof Gaina){
                nrGaina++;
                aux="Gaina "+nrGaina;
            }
            else{
                    nrOaie++;
                    aux="Oaia "+nrOaie;
            }
            String aux2="NU";

            if(a.getAProdus()){
                aux2="DA!";
            }
            aux+=", productivitate= "+a.getProductivitate()+", nivel hrana="+a.getNivelHrana()+", se poate recolta? "+aux2;
            listMAnimale.addElement(aux);
        }

        listMPlante.clear();
        for(Planta p: this.model.getPlante()){
            String aux2="NU";

            if(p.getAProdus()){
                aux2="DA!";
            }
            aux=p.getNume()+", cantitate asteptata="+p.getCantitate()+", se poate recolta? "+aux2;
            listMPlante.addElement(aux);
        }

        listMResurse.clear();
        for(Resursa r: this.model.getResurse()){
            aux=r.getNume()+", cantitate="+r.getCantitate();
            listMResurse.addElement(aux);
        }

        for(Planta p: this.model.getNeplantate()){
            aux="Seminte de "+p.getNume()+", cantitate="+p.getCantitate();
            listMResurse.addElement(aux);
        }
    }


    public void addColectListener(ActionListener a){
        this.colect.addActionListener(a);
    }

    public void addRundaNouaListener(ActionListener a){
        this.rundaUrmatoare.addActionListener(a);
    }

    public void addMagazinListener(ActionListener a){
        this.magazin.addActionListener(a);
    }

    public void addPlanteazaListener(ActionListener a){
        this.planteaza.addActionListener(a);
    }

    public void addHranesteListener(ActionListener a){
        this.hraneste.addActionListener(a);
    }

    public void addExitListener(ActionListener a){this.exit.addActionListener(a);}

    public void showMessage(String message){
        JOptionPane.showMessageDialog(this,message);
    }

    public Magazin afiseazaMagazin(){
        String [] optiuni={"Cumpara","Vinde"};
        int alegere=JOptionPane.showOptionDialog(this,"Welcome to the store! Ce doresti!","Magazin",JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE,null,optiuni,optiuni[0]);

        if(alegere==-1)
            return null;

        String actiune=optiuni[alegere].toLowerCase();

        String [] produse;
        if(actiune.equals("cumpara")){
            produse = new String[]{
                    "Vaca - productivitate=5 - pret=80 lei",
                    "Vaca - productivitate=10 - pret=120 lei",
                    "Vaca - productivitate=??? - pret=100 lei",

                    "Oaie - productivitate=3 - pret=40 lei",
                    "Oaie - productivitate=7 - pret=80 lei",
                    "Oaie - productivitate=??? - pret=50 lei",

                    "Gaina - productivitate=2 - pret=15 lei",
                    "Gaina - productivitate=5 - pret=35 lei",
                    "Gaina - productivitate=??? - pret=22 lei",

                    "Grau(seminte) - pret=1 leu",
                    "Porumb(seminte) - pret=2 lei",
                    "Trifoi(seminte) - pret=7 lei",

                    "Grau - pret=10 lei",
                    "Porumb - pret=15 lei",
                    "Trifoi - pret=35 lei"
            };
        }
        else{
            produse=new String[]{
                    "LAPTE - pret=6 lei",
                    "OUA - pret=2 lei",
                    "LANA - pret=80 lei",
                    "GRAU - pret=1 leu",
                    "PORUMB - pret=2 lei",
                    "TRIFOI - pret=7 lei"
            };
        }

        JPanel panel=new JPanel();
        panel.setLayout(new GridLayout(4,1,5,5));
        JComboBox<String> c1=new JComboBox<>(produse);

        //pt a nu mai trebui sa facem verificari de tipul: nr introdus de utilizator>0 etc
        JSpinner cant=new JSpinner(new SpinnerNumberModel(1,1,100,1));
        panel.add(new JLabel("Selecteaza produsul:"));
        panel.add(c1);
        panel.add(new JLabel("Cantitate:"));
        panel.add(cant);

        int rezultat = JOptionPane.showConfirmDialog(
                this, panel, "Finalizeaza " + actiune,
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
        );

        if (rezultat == JOptionPane.OK_OPTION) {
            String selectie = (String) c1.getSelectedItem();
            int cantitate = (Integer) cant.getValue();

            return proceseazaSelectia(actiune, selectie, cantitate);
        }
        return null;
    }

    public Magazin proceseazaSelectia(String actiune, String selectie, int cantitate){
        String produs= "";
        String tip="";
        int varianta=0;

        if(actiune.equalsIgnoreCase("vinde")){
            selectie=selectie.toLowerCase();

            if(selectie.contains("lapte")) produs="lapte";
            else if(selectie.contains("lana")) produs="lana";
            else if(selectie.contains("oua")) produs="oua";
            else if(selectie.contains("porumb")) produs="porumb";
            else if(selectie.contains("grau")) produs="grau";
            else produs="trifoi";
            return new Magazin(actiune.toLowerCase(),produs,cantitate,"resursa",0);
        }
        else{
            if(selectie.contains("Vaca")){
                produs="vaca";
                tip="animal";
                if(selectie.contains("productivitate=5 - pret=80 lei"))
                    varianta=0;
                else if (selectie.contains("productivitate=10 - pret=120 lei"))
                varianta=1;
                else varianta=2;
            }
            else if(selectie.contains("Oaie")){
                produs="oaie";
                tip="animal";
                if(selectie.contains("productivitate=3 - pret=40 lei"))
                    varianta=0;
                else if (selectie.contains("productivitate=7 - pret=80 lei"))
                    varianta=1;
                else varianta=2;
            }
            else if(selectie.contains("Gaina")){
                produs="gaina";
                tip="animal";
                if(selectie.contains("productivitate=2 - pret=15 lei"))
                    varianta=0;
                else if (selectie.contains("productivitate=5 - pret=35 lei"))
                    varianta=1;
                else varianta=2;
            }
            else if(selectie.contains("(seminte)")){
                tip="planta";
                if(selectie.contains("Grau")){
                    produs="grau";

                }
                else if(selectie.contains("Porumb")){
                    produs="porumb";
                }
                else{
                    produs="trifoi";
                }
            }
            else{
                tip="hrana";
                if(selectie.contains("Grau")){
                    produs="grau";

                }
                else if(selectie.contains("Porumb")){
                    produs="porumb";
                }
                else{
                    produs="trifoi";
                }
            }

        }
        return new Magazin(actiune.toLowerCase(),produs,cantitate,tip,varianta);

    }

    public String cantitateHrana(){
        return citesteNr("Introdu cantitatea de hrana:");
    }

    public String tipPlanta(){
        String []optiuni={"grau","porumb","trifoi"};

        String raspuns = (String) JOptionPane.showInputDialog(this,
                "Ce seminte vrei sa plantezi?","Planteaza",JOptionPane.QUESTION_MESSAGE,null,optiuni,optiuni[0]);
             return raspuns;
    }

    public String nrSeminte(){
        return citesteNr("Introdu cantitatea de seminte:");
    }

    public String citesteNr(String msg){
        while(true){
            String aux=JOptionPane.showInputDialog(this,msg);
            if(aux==null) return null;
            if(aux.trim().isEmpty()) return null;

                try{
                    int val=Integer.parseInt(aux.trim());
                    if(val<0){
                        showMessage("Introduceti un numar pozitiv!");
                    }
                    else return aux;
                }catch(NumberFormatException e){
                    showMessage("Introduceti un numar valid!");
                }
        }
    }

    public int gameOver(){
        Object[] optiuni={"Incepe din nou","Iesi din joc"};
        return JOptionPane.showOptionDialog(this,"GAME OVER! Ce doresti sa faci?", "STOP",JOptionPane.YES_NO_OPTION,JOptionPane.ERROR_MESSAGE,null,optiuni,optiuni[0]);
    }


}
