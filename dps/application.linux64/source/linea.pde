public class Linea extends Oggetto
{
  private int weight;
  public Linea(color col_des,color col_sel)
  {
    super(col_des,col_sel);
    weight=4;
  };
  public void grassetto()
  {
    weight=6;
  }
  public void no_grassetto()
  {
    weight=4;
  }
  public void disegna(int cx, int cy, int len1, int len2, int len3, int len4, int len5, float angle)
  {
    int lung_ala=5;
    pushMatrix();
    strokeWeight(weight);
    stroke(get_colore());
    translate(cx, cy);
    rotate(radians(angle));
    noFill();
    strokeJoin(ROUND);
    line(0,0,len1, 0);
    line(len1,0,len1,len2);
    line(len1,len2,len1+len3,len2);
    line(len1+len3,len2,len1+len3,len2+len4);
    line(len1+len3,len2+len4,len1+len3+len5,len2+len4);    
    
    line(len1+len3+len5,len2+len4, len1+len3+len5 - lung_ala, len2+len4 -lung_ala);    
    line(len1+len3+len5, len2+len4, len1+len3+len5 - lung_ala, len2+len4 + lung_ala);    
    popMatrix();
  };
  public void disegna(int cx, int cy, int len1, int len2, int len3, float angle)
  {
    int lung_ala=5;
    pushMatrix();
    strokeWeight(weight);
    stroke(get_colore());
    translate(cx, cy);
    rotate(radians(angle));
    //spezzata=createShape(LINES);
    noFill();
    strokeJoin(ROUND);
    //beginShape();
    //line(0,0,len, 0);
    //line(len, 0, len - 8, -8);
    //line(len, 0, len - 8, 8);
    /*vertex(0,0);
    vertex(len1,0);
    vertex(len1,len2);
    vertex(len3,len2);
    vertex( len3 - lung_ala, len2-lung_ala);
    vertex(len3,len2);
    vertex( len3 - lung_ala, len2+lung_ala);*/
    line(0,0,len1, 0);
    line(len1,0,len1,len2);
    line(len1,len2,len1+len3,len2);
    
    line(len1+len3,len2, len1+len3 - lung_ala, len2 -lung_ala);    
    line(len1+len3, len2, len1+len3 - lung_ala, len2 + lung_ala);
    //endShape();
    popMatrix();
  };
  public void disegna(int cx, int cy, int len1, int len2, float angle)
  {
    int lung_ala=5;
    pushMatrix();
    strokeWeight(weight);
    stroke(get_colore());
    translate(cx, cy);
    rotate(radians(angle));
    noFill();
    strokeJoin(ROUND);    
    line(0,0,len1, 0);
    line(len1,0,len1,len2);    
    
    //rotate(radians(90));
    line(len1,len2, len1 - lung_ala, len2 + lung_ala);    
    line(len1, len2, len1 + lung_ala, len2 + lung_ala);    
    popMatrix();
  };
  public void disegna(int cx, int cy, int len, float angle)
  {
    int lung_ala=5;
    pushMatrix();
    strokeWeight(weight);
    stroke(get_colore());
    translate(cx, cy);
    rotate(radians(angle));
    noFill();
    strokeJoin(ROUND);    
    line(0,0,len, 0);    
    
    //rotate(radians(90));
    line(len, 0, len - lung_ala, -lung_ala);
    line(len, 0, len - lung_ala, lung_ala);    
    popMatrix();
  }; 
}