//PShape forma;

public class Trapezio extends Figura
{
  //PShape forma;
  public Trapezio(color col_des,color col_sel,color col_fig_cont)
  {
    super(col_des,col_sel,col_fig_cont);    
    //rectMode(CENTER);
  };
  /*public void disegna(int x,int y,int largM,int largm,int alt)
  {
    super.disegna();
    //rect(x,y,larg,alt);
    //int plus=(largM-largm)/2;//    
    forma = createShape();
    forma.beginShape();
    //forma.fill(0, 0, 255);
    //forma.noStroke();
    //forma.vertex(x, y);
    //forma.vertex(x+largm, y);
    //forma.vertex(x+largm+plus, y+alt);
    //forma.vertex(x-plus, y+alt);
    forma.vertex(x-(largM/2), y-(alt/2));
    forma.vertex(x+(largM/2), y-(alt/2));
    forma.vertex(x+(largm/2), y+(alt/2));
    forma.vertex(x-(largm/2), y+(alt/2));    
    forma.endShape(CLOSE);
    shapeMode(CENTER);
    shape(forma,x, y);
  };*/
  public void disegna(int x,int y,int largM,int largm,int alt)
  {
    super.disegna();   
    quad(x-(largM/2), y-(alt/2),x+(largM/2), y-(alt/2),x+(largm/2), y+(alt/2),x-(largm/2), y+(alt/2));
    //shape(forma,x, y);
  };
}