public class Multiplatore extends Trapezio
{
  private Scritta[] entrate;
  
  public Multiplatore(color col_des,color col_sel,color col_fig_cont,int n_entrate,color col_des_scritta,color col_sel_scritta)
  {
    super(col_des,col_sel,col_fig_cont);
    final int max_entrate=3;
    if (n_entrate > max_entrate)n_entrate=max_entrate;
    entrate=new Scritta[n_entrate];
    for(int i=0;i<entrate.length;i++)
    entrate[i]=new Scritta(col_des_scritta,col_sel_scritta);
  };
  public void set_entrate(int i,String s)
  {
    entrate[i].set_nome(s);
  };
  public int len_entrate()
  {
    return entrate.length;
  }
  
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
  
  public void grass_entrate(int i)
  {
    entrate[i].grassetto();
  };
  public void no_grass_entrate(int i)
  {
    entrate[i].no_grassetto();
  };
  
  
  
  public void disegna(int x,int y,int largM,int largm,int alt)
  {
    super.disegna(x,y,largM,largm,alt);
    pushMatrix();
    fill(0);
    pushMatrix();
    rectMode(CENTER);
    textAlign(CENTER,BOTTOM);
    textFont(get_font());
    text(get_nome(),x,y,largM/2,alt/2);
    popMatrix();

    //rectMode(CORNER);
    //text(uscite,x+60,y+60,largM/2,alt/2);
   
    rectMode(CORNER);
    //textAlign(LEFT);
    if (entrate.length==1)
    {
      
      rectMode(CENTER);
      textAlign(CENTER,TOP);
      //text(entrate[0],x,y-(alt/4),largM/2,alt/2);
      entrate[0].disegna(x,y-(alt/4),largM/2,alt/2);
    }
    else if (entrate.length==2)
    {
      rectMode(CORNER);
      textAlign(CENTER,TOP);
      //text(entrate[0],x-(largM/2),y-(alt/2),largM/2,alt/2);
      entrate[0].disegna(x-(largM/2),y-(alt/2),largM/2,alt/2);
      
      rectMode(CORNER);
      textAlign(CENTER,TOP);
      //text(entrate[1],x,y-(alt/2),largM/2,alt/2);
      entrate[1].disegna(x,y-(alt/2),largM/2,alt/2);
    }
    else
    {
      rectMode(CORNER);
      textAlign(CENTER,TOP);
      //text(entrate[0],x-(largM/2),y-(alt/2),largM/2,alt/2);
      entrate[0].disegna(x-(largM/2),y-(alt/2),largM/2,alt/2);
      
      rectMode(CENTER);
      textAlign(CENTER,TOP);
      //text(entrate[1],x,y-(alt/4),largM/2,alt/2);
      entrate[1].disegna(x,y-(alt/4),largM/2,alt/2);
      
      rectMode(CORNER);
      textAlign(CENTER,TOP);
      //text(entrate[2],x,y-(alt/2),largM/2,alt/2);
      entrate[2].disegna(x,y-(alt/2),largM/2,alt/2);
    }
    
    //text(uscite[1],x+(larg/2)-20,y+(alt/2)-20,larg/2,alt/2);
    popMatrix();
    rectMode(CENTER);
    textAlign(TOP);
  }
}