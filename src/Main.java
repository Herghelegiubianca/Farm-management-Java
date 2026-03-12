public class Main{
    public static void main(String[] args){
        ModelMVC m=new ModelMVC();
        ViewMVC v=new ViewMVC(m);
        ControlMVC c=new ControlMVC(m,v);

        v.setVisible(true);
    }
}

