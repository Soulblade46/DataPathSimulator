public enum Nome_Registro 
{
  ra("RA"),rb("RB"),rz("RZ"),rm("RM"),ry("RY"),err("ERRORE");
  private final String nome;
  private Nome_Registro(final String s) { nome = s; }
  public String toString() { return nome; }
}

public class Registro extends Rettangolo
{
  private Nome_Registro nome;
  public Registro(color col_des,color col_sel,color col_fig_cont)
  {
    super(col_des,col_sel,col_fig_cont);
    nome=Nome_Registro.err;
  };
  public void set_nome(Nome_Registro name)
  {
      nome=name;
  };
  public void disegna(int x,int y,int larg,int alt)
  {
    super.disegna(x,y,larg,alt);
    pushMatrix();
    fill(0);
    rectMode(CENTER);
    textAlign(CENTER,CENTER);
    textFont(get_font());
    text(nome.toString(),x,y,larg/2,alt);
    popMatrix();
    textAlign(TOP);
  }
}