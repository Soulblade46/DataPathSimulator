protected class Oggetto
{
  private color colore_deselezionato;
  private color colore_selezionato;
  private color colore;
  private boolean selezionato;
  public void seleziona()
  {
    colore=colore_selezionato;
    selezionato=true;
  };
  public void grassetto(){};
  public void no_grassetto(){};
  public void disegna()
  {
    strokeWeight(4);
    fill(colore);
  };
  public Oggetto(color col_des,color col_sel)
  {
    colore_deselezionato=col_des;
    colore_selezionato=col_sel;
    colore=colore_deselezionato;
    selezionato=false;
  }
  public void deseleziona()
  {
    colore=colore_deselezionato;
    selezionato=false;
  };
  public color get_colore(){return colore;}
  public boolean is_selezionato(){return selezionato;};
  public void cambia_colore()
  {
    if (selezionato) deseleziona();
    else seleziona();
  };
}