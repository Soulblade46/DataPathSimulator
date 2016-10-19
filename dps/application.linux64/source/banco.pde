public class Banco extends Rettangolo
{
  private Scritta entrate;
  private Scritta[] uscite;
  public Banco(color col_des,color col_sel,color col_fig_cont,color col_des_scritta,color col_sel_scritta)
  {
    super(col_des,col_sel,col_fig_cont);
    int n_uscite=2;
    //int i;
    //entrate=new String[n_entrate];
    uscite=new Scritta[n_uscite];
    entrate=new Scritta(col_des_scritta,col_sel_scritta);
    for(int i=0;i<uscite.length;i++)
    uscite[i]=new Scritta(col_des_scritta,col_sel_scritta);
    /*for(i=0;i<n_uscite;i++)
    {
      uscite[i]=new Scritta(col_des,col_sel,"Errore");
    }
    entrate=new Scritta(col_des,col_sel,"Errore");*/
  };
  public int len_uscite()
  {
    return uscite.length;
  }
  public void sel_uscite(int i)
  {
    uscite[i].seleziona();
  };
  public void des_uscite(int i)
  {
    uscite[i].deseleziona();
  };
  public void sel_entrate()
  {
    entrate.seleziona();
    //if (green(entrate.colore)!=255)
    //println("colore="+red(entrate.colore));
    //println("red="+red(entrate.colore)+"green="+green(entrate.colore)+"blue="+blue(entrate.colore));
  };
  public void des_entrate()
  {
    entrate.deseleziona();
  };
  public void set_entrate(String s)
  {
    entrate.set_nome(s);
  };
  public void set_uscite(int i,String s)
  {
    uscite[i].set_nome(s);
  };
  public void grass_entrate()
  {
    entrate.grassetto();
  };
  public void no_grass_entrate()
  {
    entrate.no_grassetto();
  };
  public void grass_uscite(int i)
  {
    uscite[i].grassetto();
  };
  public void no_grass_uscite(int i)
  {
    uscite[i].no_grassetto();
  };
  void disegna(int x,int y,int larg,int alt)
  {
    super.disegna(x,y,larg,alt);
    pushMatrix();
    fill(0);
    pushMatrix();
    rectMode(CENTER);
    textAlign(CENTER);
    //text(get_nome(),x,y,larg/2+20,alt/2);
    textFont(get_font());
    text(get_nome(),x,y,larg-20,alt/2);
    popMatrix();
  
    //rectMode(CORNER);
    //text(entrate,x,y-(alt/2),larg/2+larg/2,alt/2);
    rectMode(CENTER);
    textAlign(CENTER,BOTTOM);
    //pushMatrix();
    /*fill(entrate.colore);
    text(entrate.toString(),x,y-(alt/2),larg/2,alt/3);*/
    //fill(255);
    //println("red="+red(entrate.colore)+"green="+green(entrate.colore)+"blue="+blue(entrate.colore));
    fill(255);
    entrate.disegna(x,y-(alt/2),larg/2,alt/3);
    
    
    //popMatrix();
   
    //rectMode(CORNER);
    
    //text(uscite[0],x-(larg/2)+10,y+(alt/2)-20,larg/2,alt/2);
    //text(uscite[1],x+(larg/2)-20,y+(alt/2)-20,larg/2,alt/2);
    rectMode(CORNER);
    textAlign(LEFT,BOTTOM);
    //pushMatrix();
    
    /*fill(uscite[0].colore);
    text(uscite[0].toString(),x-(larg/3),y,larg/2,alt/2);*/
    uscite[0].disegna(x-(larg/3),y,larg/2,alt/2);
    
    //popMatrix();
    
    textAlign(RIGHT,BOTTOM);
    //pushMatrix();
    
    /*fill(uscite[1].get_colore());
    text(uscite[1].toString(),x,y,larg/3,alt/2);*/
    uscite[1].disegna(x,y,larg/3,alt/2);
    
    //popMatrix();
    
    popMatrix();
    rectMode(CENTER);
    textAlign(TOP);
  }
}