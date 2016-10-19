protected class Rettangolo extends Figura
{
  public Rettangolo(color col_des,color col_sel,color col_fig_cont)
  {
    super(col_des,col_sel,col_fig_cont);
    rectMode(CENTER);
  };
  public void disegna(int x,int y,int larg,int alt)
  {
    super.disegna();
    rect(x,y,larg,alt);
  };
}