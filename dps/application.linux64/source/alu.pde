PShape trapezio_alu;
PShape triangolo_alu;
PShape forma_alu;

public class Alu extends Figura
{
  private Scritta[] entrate;
  private Scritta uscite;
  public Alu(color col_des,color col_sel,color col_fig_cont,color col_des_scritta,color col_sel_scritta)
  {
    super(col_des,col_sel,col_fig_cont);
    int n_entrate=2;
    entrate=new Scritta[n_entrate];
 
    uscite=new Scritta(col_des_scritta,col_sel_scritta);
    
    for(int i=0;i<entrate.length;i++)
    entrate[i]=new Scritta(col_des_scritta,col_sel_scritta);
    
  };
  //R\\d+ espressione per r92 o R0
  //\d+{1}\(((r|R)\d+){1}\) espressione per 62(r4) 0 3(R90)
  public void sel_uscite()
  {
    uscite.seleziona();
  };
  public void des_uscite()
  {
    uscite.deseleziona();
  };
  public void sel_entrate(int i)
  {
    entrate[i].seleziona();
    //if (green(entrate.colore)!=255)
    //println("colore="+red(entrate.colore));
    //println("red="+red(entrate.colore)+"green="+green(entrate.colore)+"blue="+blue(entrate.colore));
  };
  public void des_entrate(int i)
  {
    entrate[i].deseleziona();
  };
  
  public void set_entrate(int i,String s)
  {
    entrate[i].set_nome(s);
  };
  public void set_uscite(String s)
  {
    uscite.set_nome(s);
  };
  public int len_entrate()
  {
    return entrate.length;
  }
  
  public void grass_entrate(int i)
  {
    entrate[i].grassetto();
  };
  public void no_grass_entrate(int i)
  {
    entrate[i].no_grassetto();
  };
  public void grass_uscite()
  {
    uscite.grassetto();
  };
  public void no_grass_uscite()
  {
    uscite.no_grassetto();
  };
  /*public void disegna(int x,int y,int largM,int largm,int alt,int larg_t,int alt_t)
  {
    super.disegna();
    //rect(x,y,larg,alt);
    //int plus=(largM-largm)/2;// 
    //int x_t=x;
    //int y_t=(y-(alt/2))+h_t;
    //forma_alu = createShape(GROUP);
    trapezio_alu = createShape();
    trapezio_alu.beginShape();
    trapezio_alu.vertex(x-(largM/2), y-(alt/2));
    
    trapezio_alu.vertex(x-(larg_t/2), y-(alt/2));
    trapezio_alu.vertex(x, (y-(alt/2))+alt_t);
    trapezio_alu.vertex(x+(larg_t/2), y-(alt/2));    
    
    trapezio_alu.vertex(x+(largM/2), y-(alt/2));
    trapezio_alu.vertex(x+(largm/2), y+(alt/2));
    trapezio_alu.vertex(x-(largm/2), y+(alt/2));    
    trapezio_alu.endShape(CLOSE);
    shapeMode(CENTER);
    shape(trapezio_alu,x,y);
    
    pushMatrix();
    fill(0);
    rectMode(CENTER);
    text(get_nome(),x+65,y+100,largM/2,alt/2);
  
    rectMode(CENTER);
    text(uscite,x+65,y+130,largM/2,alt/2);
   
    rectMode(CORNER);
    
    text(entrate[0],x-(largM/2)+50,y+(alt/2)+26,largM/2,alt/2);
    text(entrate[1],x+(largM/2)+10,y+(alt/2)+26,largM/2,alt/2);
    popMatrix();
    rectMode(CENTER);
  };*/
  public void disegna(int x,int y,int largM,int largm,int alt,int larg_t,int alt_t)
  {
    super.disegna();
    forma_alu=createShape(GROUP);
    trapezio_alu=createShape();
    trapezio_alu.beginShape(QUAD);
    //quad(x-(largM/2), y-(alt/2),x+(largM/2), y-(alt/2),x+(largm/2), y+(alt/2),x-(largm/2), y+(alt/2));
    trapezio_alu.vertex(x-(largM/2), y-(alt/2));
    trapezio_alu.vertex(x+(largM/2), y-(alt/2));
    trapezio_alu.vertex(x+(largm/2), y+(alt/2));
    trapezio_alu.vertex(x-(largm/2), y+(alt/2));
    /*trapezio_alu.beginContour();
    trapezio_alu.vertex(x-(larg_t/2), y-(alt/2));
    trapezio_alu.vertex(x, y-(alt/2)+alt_t);
    trapezio_alu.vertex(x+(larg_t/2), y-(alt/2));
    trapezio_alu.endContour();*/
    trapezio_alu.strokeJoin(ROUND);
    trapezio_alu.endShape(CLOSE);
    triangolo_alu=createShape();
    triangolo_alu.beginShape(TRIANGLES);
    //triangolo_alu.noStroke();
    triangolo_alu.vertex(x-(larg_t/2), y-(alt/2)-g.strokeWeight+g.strokeWeight/2);
    triangolo_alu.vertex(x, y-(alt/2)+alt_t);
    triangolo_alu.vertex(x+(larg_t/2), y-(alt/2)-g.strokeWeight+g.strokeWeight/2);
    triangolo_alu.strokeJoin(ROUND);
    triangolo_alu.endShape();
    triangolo_alu.setFill(background_color);
    forma_alu.addChild(trapezio_alu);
    forma_alu.addChild(triangolo_alu);
    shapeMode(CORNER);
    shape(forma_alu,0,0);
    pushMatrix();
    fill(0);
    pushMatrix();
    rectMode(CENTER);
    textAlign(CENTER,BOTTOM);
    textFont(get_font());
    text(get_nome(),x,y-alt/6,largM/2,alt/2);
    popMatrix();
   
    rectMode(CENTER);
    textAlign(CENTER,TOP);
    //text(uscite,x,y+alt/2,largM/2,alt/2);
    uscite.disegna(x,y+alt/2,largM/2,alt/2);
    
    rectMode(CORNER);
    textAlign(CENTER,TOP);
    //text(entrate[0],x-largM/2,y-alt/2,largM/2,alt/2);
    entrate[0].disegna(x-largM/2,y-alt/2,largM/2,alt/2);
    
    rectMode(CORNER);
    textAlign(CENTER,TOP);
    //text(entrate[1],x,y-alt/2,largM/2,alt/2);
    entrate[1].disegna(x,y-alt/2,largM/2,alt/2);    
    
    /*rectMode(CORNER);
    textAlign(CENTER,TOP);
    text(entrate[0],x-(largM/2),y-(alt/2),largM/2,alt/2);
     
    rectMode(CENTER);
    textAlign(CENTER,TOP);
    text(entrate[1],x,y-(alt/4),largM/2,alt/2);
     
    rectMode(CORNER);
    textAlign(CENTER,TOP);
    text(entrate[2],x,y-(alt/2),largM/2,alt/2);*/
    
    popMatrix();
    rectMode(CENTER);
    textAlign(TOP);
  };
}