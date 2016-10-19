protected class Figura extends Oggetto
{
  private String nome;
  private PFont font;
  private color colore_contorno; 
  public Figura(color col_des,color col_sel,color col_fig_cont)
  {
    super(col_des,col_sel);
    colore_contorno=col_fig_cont;
    font=createFont("Arial",12);
    //rectMode(CENTER);
  };
  public void grassetto()
  {
    font=createFont("Arial Black",12);
  };
  PFont get_font(){return font;}
  public void no_grassetto()
  {
    font=createFont("Arial",12);
  };
  public void set_nome(String name)
  {
    nome=name;
  };
  public String get_nome()
  {
    return nome;
  };
  public void disegna()
  {
    /*stroke(0);
    fill(colore);*/
    super.disegna();
    //fill(colore);
    //rectMode(CENTER);
    stroke(colore_contorno);
    //rect(x,y,larg,alt);
  };
}