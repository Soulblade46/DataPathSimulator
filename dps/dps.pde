import g4p_controls.*; //<>// //<>//
import java.awt.Font;

/*public class Figura_List
 {
 private Figura[] figura;
 private int num_figure;
 public Figura_List(int num,color col_des,color col_sel,color col_fig_cont)
 {
 int i;
 num_figure=num;
 Figura[] figura=new Figura[num_figure];
 for(i=0;i<num_figure;i++)
 {
 figura[i]=new Figura(col_des,col_sel,col_fig_cont);
 }
 };
 public void des_tutti()
 {
 int i;
 for(i=0;i<num_figure;i++)
 {
 figura[i].deseleziona();
 }
 };
 public void disegna()
 {
 int i;
 for(i=0;i<num_figure;i++)
 {
 figura[i].disegna(100,60+i*60,40,40);
 }
 }
 }*/

import g4p_controls.*;
//420x580

//PGraphics canvas;
boolean wind_about=false;
int puntoX=0, puntoY=0;
//Linea[] linea;
//Figura[] fig;
boolean inizio=false, play=false;
//color col_linea_des=color(100, 255, 0);
//color col_linea_sel=color(100, 0, 255);
color col_linea_des=color(100, 255, 0);
color col_linea_sel=color(255, 255, 0);
color col_fig_des=color(0, 255, 255);
color col_fig_sel=color(255, 0, 255);
color col_fig_cont=color(0, 100, 255);
color col_scritta_des=color(100, 0, 255);
color col_scritta_sel=color(255, 0, 0);

color col_scritta_des_esterna=color(255, 200, 0);
color col_scritta_sel_esterna=color(255, 255, 255);


int background_color=100;

float ZOOM_INIZIALE=1.0;
float zoomX=1.0,zoomY=1.0;
float zoomX_bordo=1.0,zoomY_bordo=1.0;

final int NUM_REGISTRI=5;
final int NUM_MULTIPLATORI=2;
final int NUM_FRECCE=18;
final int NUM_SCRITTE=10;
final int NUM_PARAMETRI=3;
//final int sizeX=600,sizeY=600;

int width_prec=600,height_prec=600;
int width_pre=600,height_pre=600;

Linea[] linea=new Linea[NUM_FRECCE];
Registro[] reg=new Registro[NUM_REGISTRI];
Multiplatore[] mult=new Multiplatore[NUM_MULTIPLATORI];
Alu alu;
Banco banco;
Scritta[] scritta=new Scritta[NUM_SCRITTE];

int frame=0;
boolean finale=false;

public enum Comando {LOAD,STORE,ADD,NULL}

String strings[];
String istruzione=new String();
String[] parametri=new String[NUM_PARAMETRI];
String[] valori=new String[NUM_PARAMETRI];
String NON_INSERITO;
String NON_CORRETTO;
String OPERAZIONE_CORRENTE;
Comando comando= Comando.NULL;
int imgX=0,imgY=0;
int mX=0,mY=0;
boolean click_destro=false;
//Figura_List figura;
void setup()
{
  //customGUI();
  size(600,600);
  surface.setResizable(true);
  //surface.setSize(600,600);
  strings=loadStrings("strings_it.txt");
  NON_INSERITO=strings[17];//Non inserito
  NON_CORRETTO=strings[18];//Non corretto
  OPERAZIONE_CORRENTE=strings[18];
  
  /*Linea[] linea=new Linea[10];
   Figura[] fig=new Figura[10];*/
  //translate(width/2,height/2);
  //background(255);
  //canvas = createGraphics(640, 480, JAVA2D);
  //surface.setResizable(true);
  //figura=new Figura_List(10,col_fig_des,col_fig_sel,col_fig_cont);
  int i=0;
  
  for (i=0; i<parametri.length; i++) 
  {
    parametri[i]=new String(NON_INSERITO);
  }
  
  for (i=0; i<valori.length; i++) 
  {
    valori[i]=new String();
  }
  
  for (i=0; i<reg.length; i++) 
  {
    reg[i]=new Registro(col_fig_des, col_fig_sel, col_fig_cont);
  }
  
  for (i=0; i<linea.length; i++) 
  {
    linea[i]=new Linea(col_linea_des, col_linea_sel);
  }
  
  for (i=0; i<scritta.length; i++) 
  {
    scritta[i]=new Scritta(col_scritta_des_esterna,col_scritta_sel_esterna);;
  }
  
  mult[0]=new Multiplatore(col_fig_des, col_fig_sel, col_fig_cont,2,col_scritta_des,col_scritta_sel);
  mult[1]=new Multiplatore(col_fig_des, col_fig_sel, col_fig_cont,3,col_scritta_des,col_scritta_sel);
  
  alu=new Alu(col_fig_des, col_fig_sel, col_fig_cont,col_scritta_des,col_scritta_sel);
  banco=new Banco(col_fig_des, col_fig_sel, col_fig_cont,col_scritta_des,col_scritta_sel);
  createGUI();
  btn_load.setVisible(false);
  btn_store.setVisible(false);
  btn_add.setVisible(false);
  //frameRate(10);
}

boolean lingua=false;

void draw()
{
  /*canvas.beginDraw();
   fill(255,0,255);
   rect(10,10,50,50);
   canvas.endDraw();
   image(canvas, 0, 0, width, height);
   */
  background(background_color);
  //if (height <128 || width<128)surface.setSize(128,128);
  if (lingua)strings=loadStrings("strings_en.txt");
  else strings=loadStrings("strings_it.txt");
  cambia_lingua_gui();
  //createGUI();
  int i;
  //int offset_inizialeX=width/3,offset_inizialeY=height/10,offset_periodicoY=height/10,lung=width/15,alt=width/15;
  //int offsetX1=180,offsetY1=200,offset_inizialeY=60,offset_periodicoY=60,lung=40,alt=40;
  int offsetX1=180,offsetY1=160,lung=70,alt=30;
  int offsetX2=30,offsetY2=250,offsetX3=100;
  int offsetY3=150;
  int offsetX_banco=200,offsetX_rb_to_ra=90,offsetX_ra_to_banco=40;
  int plus=30;
  int lung_t=10,alt_t=10;
  
  zoomY_bordo=((float)height)/ ((float) height_prec);
  zoomX_bordo=((float)width)/ ((float) width_prec);
  //width_prec=width;
  //height_prec=height;
  //scale(zoomX_bordo,zoomY_bordo);
  /*for (int in=0;in<10;in++) 
   {
   linea[in]=new Linea(col_linea_des,col_linea_sel);
   fig[in]=new Figura(col_fig_des,col_fig_sel,col_fig_cont);
   }*/
  
  /*for (i=0; i<NUM_REGISTRI; i++)
  {
    switch(i)
    {
      case 0:
        reg[i].set_nome("RA");
        break;
      case 1:
        reg[i].set_nome("RB");
        break;
      case 2:
        reg[i].set_nome("RZ");
        break;
      case 3:
        reg[i].set_nome("RM");
        break;
      case 4:
        reg[i].set_nome("RY");
        break;
    }
  }*/
  i=0;
  for (Nome_Registro n : Nome_Registro.values()) 
  {
   if (i <= reg.length-1)
   {
     reg[i].set_nome(n);
     i++;
   }
  }
  
  banco.set_nome(strings[3]);//Banco di registri
  banco.set_entrate(strings[4]);//C
  banco.set_uscite(0,strings[5]);//A
  banco.set_uscite(1,strings[6]);//B
  alu.set_entrate(0,strings[7]);//InA
  alu.set_entrate(1,strings[8]);//InB
  alu.set_uscite(strings[9]);//Uscita
  alu.set_nome(strings[10]);//ALU
  mult[0].set_nome(strings[11]);//MuxB
  mult[1].set_nome(strings[12]);//MuxY
  
  
  /*mult[0].set_entrate(0,"0");
  mult[0].set_entrate(1,"1");*/
  for (int j=0;j<mult.length;j++)
  {
    for(i=0;i<mult[j].len_entrate();i++)
    {
      mult[j].set_entrate(i,Integer.toString(i));
    }
  }
  /*mult[1].set_entrate(0,"0");
  mult[1].set_entrate(1,"1");
  mult[1].set_entrate(2,"2");*/
  
   
  
  /*for (i=0; i<NUM_REGISTRI; i++)
  {
    reg[i].disegna(offset_inizialeX, offset_inizialeY+i*offset_periodicoY, lung,alt);
  }*/
  
  //reg[0].disegna(offsetX1, offsetY1+10, lung,alt);
  pushMatrix();
  //translate(-30,0);
  if(mousePressed){ // is the mousebutton being held?
    if (mouseButton==RIGHT)
    {
      imgX = mouseX-mX;
      imgY = mouseY-mY;
      click_destro=true; 
    }
  }
  translate(-30,0);
  if (click_destro)translate(imgX-418/2,imgY-600/2);
  if ((zoomX_bordo!=1.0 || zoomY_bordo!=1.0)&& (height!=height_pre || width!=width_pre))
  {
    //scale(zoomX_bordo,zoomY_bordo);
    width_pre=width;
    height_pre=height;
    
    pnl_comandi.dispose();
    pnl_comandi = null;
    pnl_comandi = new GPanel(this, (int)((418*width)/600), (int)((1*height)/600), (int)((179*width)/600), (int)((55*height)/600), "Comandi");
    //pnl_comandi.moveTo(width-179,1);
    pnl_comandi.clearDragArea();
    
    pnl_pannello.dispose();
    pnl_pannello = null;
    pnl_pannello = new GPanel(this, 0, 0, (int)((418*width)/600), ((int)(20*height)/600), "PANNELLO");
    //pnl_pannello.moveTo(0, 0); //418:600=x:height
    pnl_pannello.clearDragArea();
    
    pnl_prova.dispose();
    pnl_prova = null;
    pnl_prova = new GPanel(this, (int)((419*width)/600), (int)((55*height)/600), (int)((179*width)/600), (int)((94*height)/600), "CONTROLLI");
    //pnl_prova.moveTo(width-179,55);
    pnl_prova.clearDragArea();
    
    pnl_input.dispose();
    pnl_input = null;
    pnl_input = new GPanel(this, (int)((420*width)/600), (int)((150*height)/600), (int)((179*width)/600), (int)((147*height)/600), "INPUT");
    //pnl_input.moveTo(width-179,147);
    pnl_input.clearDragArea();
    
    pnl_output.dispose();
    pnl_output = null;
    pnl_output = new GPanel(this, (int)((420*width)/600), (int)((299*height)/600), (int)((179*width)/600), (int)((190*height)/600), "OUTPUT");
    //pnl_output.moveTo(width-179,299);
    pnl_output.clearDragArea();
    
    pnl_about.dispose();
    pnl_about = null;
    pnl_about = new GPanel(this, (int)((420*width)/600), (int)((490*height)/600), (int)((177*width)/600), (int)((76*height)/600), "About");    
    //pnl_about.moveTo(width-179,490);
    pnl_about.clearDragArea();
    
    
    
    
    
    
    
    
  
  pnl_comandi.setText("Comandi");
  pnl_comandi.setTextBold();
  pnl_comandi.setTextItalic();
  pnl_comandi.setOpaque(true);
  pnl_comandi.addEventHandler(this, "pnl_comandi_click");// (int)((179*width)/600), (int)((55*height)/600)
  
  btn_en_lang.dispose();
  btn_en_lang = null;  
  btn_en_lang = new GImageButton(this, (int)((134*((179*width)/600))/179), (int)((22*((55*height)/600))/55), (int)((41*((179*width)/600))/179),(int)((28*((55*height)/600))/55), new String[] { "enFlag.png", "enFlag.png", "enFlag.png" } );
  btn_en_lang.addEventHandler(this, "btn_en_lang_click");
  
  //(int)((134*((179*width)/600))/179), (int)((22*((55*height)/600))/55), (int)((41*((179*width)/600))/179),(int)((28*((55*height)/600))/55)
  
  btn_it_lang.dispose();
  btn_it_lang = null; 
  btn_it_lang = new GImageButton(this,(int)((88*((179*width)/600))/179), (int)((22*((55*height)/600))/55), (int)((41*((179*width)/600))/179),(int)((28*((55*height)/600))/55), new String[] { "itFlag.png", "itFlag.png", "itFlag.png" } );
  //btn_it_lang = new GImageButton(this, 88, 22, 41, 28, new String[] { "itFlag.png", "itFlag.png", "itFlag.png" } );
  btn_it_lang.addEventHandler(this, "btn_it_lang_click");
  
  btn_zoom_out.dispose();
  btn_zoom_out = null;
  btn_zoom_out = new GImageButton(this,(int)((6*((179*width)/600))/179), (int)((22*((55*height)/600))/55), (int)((41*((179*width)/600))/179),(int)((28*((55*height)/600))/55), new String[] { "imageedit_5_4106865581 [www.imagesplitter.net]-0-0.png", "imageedit_5_4106865581 [www.imagesplitter.net]-0-0.png", "imageedit_5_4106865581 [www.imagesplitter.net]-0-0.png" } );
  //btn_zoom_out = new GImageButton(this, 6, 22, 41, 28, new String[] { "imageedit_5_4106865581 [www.imagesplitter.net]-0-0.png", "imageedit_5_4106865581 [www.imagesplitter.net]-0-0.png", "imageedit_5_4106865581 [www.imagesplitter.net]-0-0.png" } );
  btn_zoom_out.addEventHandler(this, "btn_zoom_out_click");
  
  btn_zoom_in.dispose();
  btn_zoom_in = null;
  btn_zoom_in = new GImageButton(this,(int)((44*((179*width)/600))/179), (int)((22*((55*height)/600))/55), (int)((41*((179*width)/600))/179),(int)((28*((55*height)/600))/55), new String[] { "imageedit_5_4106865581 [www.imagesplitter.net]-0-1.png", "imageedit_5_4106865581 [www.imagesplitter.net]-0-1.png", "imageedit_5_4106865581 [www.imagesplitter.net]-0-1.png" } );
  //btn_zoom_in = new GImageButton(this, 44, 22, 41, 28, new String[] { "imageedit_5_4106865581 [www.imagesplitter.net]-0-1.png", "imageedit_5_4106865581 [www.imagesplitter.net]-0-1.png", "imageedit_5_4106865581 [www.imagesplitter.net]-0-1.png" } );
  btn_zoom_in.addEventHandler(this, "btn_zoom_in_click");
  
  pnl_comandi.addControl(btn_store);
  pnl_comandi.addControl(btn_load);
  pnl_comandi.addControl(btn_add);
  pnl_comandi.addControl(btn_en_lang);
  pnl_comandi.addControl(btn_it_lang);
  pnl_comandi.addControl(btn_zoom_out);
  pnl_comandi.addControl(btn_zoom_in);
  pnl_pannello.setCollapsible(false);
  pnl_pannello.setDraggable(false);
  pnl_pannello.setText("PANNELLO");
  pnl_pannello.setTextBold();
  pnl_pannello.setTextItalic();
  pnl_pannello.setOpaque(true);
  pnl_pannello.addEventHandler(this, "pnl_pannello_click");
  pnl_prova.setText("CONTROLLI");
  pnl_prova.setTextBold();
  pnl_prova.setTextItalic();
  pnl_prova.setOpaque(true);
  pnl_prova.addEventHandler(this, "pnl_prova_click");
  
  //(int)((419*width)/600), (int)((55*height)/600), (int)((179*width)/600), (int)((94*height)/600)  
  btn_play.dispose();
  btn_play = null;
  btn_play = new GImageButton(this, (int)((80*((419*width)/600))/419), (int)((40*((55*height)/600))/55), (int)((30*((179*width)/600))/179), (int)((30*((94*height)/600))/94), new String[] { "imageedit_5_2662122022 [www.imagesplitter.net]-0-0.gif", "imageedit_5_2662122022 [www.imagesplitter.net]-0-0.gif", "imageedit_5_2662122022 [www.imagesplitter.net]-0-0.gif" } );
  //btn_play = new GImageButton(this, 80, 40, 30, 30, new String[] { "imageedit_5_2662122022 [www.imagesplitter.net]-0-0.gif", "imageedit_5_2662122022 [www.imagesplitter.net]-0-0.gif", "imageedit_5_2662122022 [www.imagesplitter.net]-0-0.gif" } );
  btn_play.addEventHandler(this, "btn_play_click");
  
  btn_pause.dispose();
  btn_pause = null;
  //btn_pause = new GImageButton(this, 50, 40, 30, 30, new String[] { "imageedit_5_2662122022 [www.imagesplitter.net]-1-0.gif", "imageedit_5_2662122022 [www.imagesplitter.net]-1-0.gif", "imageedit_5_2662122022 [www.imagesplitter.net]-1-0.gif" } );
  btn_pause = new GImageButton(this, (int)((50*((419*width)/600))/419), (int)((40*((55*height)/600))/55), (int)((30*((179*width)/600))/179), (int)((30*((94*height)/600))/94), new String[] { "imageedit_5_2662122022 [www.imagesplitter.net]-1-0.gif", "imageedit_5_2662122022 [www.imagesplitter.net]-1-0.gif", "imageedit_5_2662122022 [www.imagesplitter.net]-1-0.gif" } );
  btn_pause.addEventHandler(this, "btn_pause_click");
  
  btn_forward.dispose();
  btn_forward = null;
  //btn_forward = new GImageButton(this, 140, 40, 30, 30, new String[] { "imageedit_5_2662122022 [www.imagesplitter.net]-2-2.gif", "imageedit_5_2662122022 [www.imagesplitter.net]-2-2.gif", "imageedit_5_2662122022 [www.imagesplitter.net]-2-2.gif" } );
  btn_forward = new GImageButton(this, (int)((140*((419*width)/600))/419), (int)((40*((55*height)/600))/55), (int)((30*((179*width)/600))/179), (int)((30*((94*height)/600))/94), new String[] { "imageedit_5_2662122022 [www.imagesplitter.net]-1-2.gif", "imageedit_5_2662122022 [www.imagesplitter.net]-2-2.gif", "imageedit_5_2662122022 [www.imagesplitter.net]-2-2.gif" } );
  btn_forward.addEventHandler(this, "btn_forward_click");
  
  btn_rewind.dispose();
  btn_rewind = null;
  btn_rewind = new GImageButton(this, (int)((20*((419*width)/600))/419), (int)((40*((55*height)/600))/55), (int)((30*((179*width)/600))/179), (int)((30*((94*height)/600))/94), new String[] { "imageedit_5_2662122022 [www.imagesplitter.net]-1-1.gif", "imageedit_5_2662122022 [www.imagesplitter.net]-2-1.gif", "imageedit_5_2662122022 [www.imagesplitter.net]-2-1.gif" } );
  btn_rewind.addEventHandler(this, "btn_rewind_click");
  
  btn_stop.dispose();
  btn_stop = null;
  btn_stop = new GImageButton(this, (int)((110*((419*width)/600))/419), (int)((40*((55*height)/600))/55), (int)((30*((179*width)/600))/179), (int)((30*((94*height)/600))/94), new String[] { "imageedit_5_2662122022 [www.imagesplitter.net]-2-0.gif", "imageedit_5_2662122022 [www.imagesplitter.net]-2-0.gif", "imageedit_5_2662122022 [www.imagesplitter.net]-2-0.gif" } );
  btn_stop.addEventHandler(this, "btn_stop_click");
  
  pnl_prova.addControl(btn_play);
  pnl_prova.addControl(btn_pause);
  pnl_prova.addControl(btn_forward);
  pnl_prova.addControl(btn_rewind);
  pnl_prova.addControl(btn_stop);
  pnl_input.setCollapsible(false);
  pnl_input.setDraggable(true);
  pnl_input.setText("INPUT");
  pnl_input.setTextBold();
  pnl_input.setTextItalic();
  pnl_input.setOpaque(true);
  pnl_input.addEventHandler(this, "pnl_input_click");
  
  txt.dispose();
  txt = null;
  //(int)((420*width)/600), (int)((150*height)/600), (int)((179*width)/600), (int)((147*height)/600)
  //(int)((110*((419*width)/600))/419)
  //txt = new GTextField(this, 9, 58, 160, 30, G4P.SCROLLBARS_NONE);
  txt = new GTextField(this, (int)((9*((420*width)/600))/420), (int)((58*((150*height)/600))/150), (int)((160*((179*width)/600))/179), (int)((30*((147*height)/600))/147), G4P.SCROLLBARS_NONE);
  txt.setFont(new Font("Arial", Font.PLAIN, 12+(int)(zoomX_bordo-1)*2));
  txt.setPromptText("Inserire comando");
  txt.setOpaque(true);
  txt.addEventHandler(this, "txt_enter");
  pnl_input.addControl(txt);
  pnl_output.setText("OUTPUT");
  pnl_output.setTextBold();
  pnl_output.setTextItalic();
  pnl_output.setOpaque(true);
  pnl_output.addEventHandler(this, "pnl_output_click");
  
  //pnl_output = new GPanel(this, 420, 299, 179, 190, "OUTPUT");
  //(int)((9*((420*width)/600))/420)
  //label_parametro1 = new GLabel(this, 2, 23, 150, 20);
  label_parametro1.dispose();
  label_parametro1 = null;
  label_parametro1 = new GLabel(this, (int)((2*((420*width)/600))/420), (int)((23*((299*height)/600))/299), (int)((150*((179*width)/600))/179), (int)((20*((190*height)/600))/190));
  label_parametro1.setFont(new Font("Arial", Font.PLAIN, 12+(int)(zoomX_bordo-1)*3));
  label_parametro1.setText("CIAO1");
  label_parametro1.setTextBold();
  label_parametro1.setTextItalic();
  label_parametro1.setOpaque(false);
  
  label_parametro2.dispose();
  label_parametro2 = null;
  //label_parametro2 = new GLabel(this, 2, 53, 150, 20);
  label_parametro2 = new GLabel(this, (int)((2*((420*width)/600))/420), (int)((53*((299*height)/600))/299), (int)((150*((179*width)/600))/179), (int)((20*((190*height)/600))/190));
  label_parametro2.setFont(new Font("Arial", Font.PLAIN, 12+(int)(zoomX_bordo-1)*3));
  label_parametro2.setText("CIAO2");
  label_parametro2.setTextBold();
  label_parametro2.setTextItalic();
  label_parametro2.setOpaque(false);
  
  label_parametro3.dispose();
  label_parametro3 = null;
  //label_parametro3 = new GLabel(this, 1, 83, 150, 20);
  label_parametro3 = new GLabel(this, (int)((2*((420*width)/600))/420), (int)((83*((299*height)/600))/299), (int)((150*((179*width)/600))/179), (int)((20*((190*height)/600))/190));
  label_parametro3.setFont(new Font("Arial", Font.PLAIN, 12+(int)(zoomX_bordo-1)*3));
  label_parametro3.setText("CIAO3");
  label_parametro3.setTextBold();
  label_parametro3.setTextItalic();
  label_parametro3.setOpaque(false);
  
  label_corrente.dispose();
  label_corrente = null;
  //label_corrente = new GLabel(this, 2, 115, 177, 68);
  label_corrente = new GLabel(this, (int)((2*((420*width)/600))/420), (int)((115*((299*height)/600))/299), (int)((117*((179*width)/600))/179), (int)((68*((190*height)/600))/190));
  label_corrente.setTextAlign(GAlign.CENTER, GAlign.TOP);
  label_corrente.setFont(new Font("Arial", Font.PLAIN, 12+(int)(zoomX_bordo-1)*3));
  label_corrente.setText("Non inserito");
  label_corrente.setTextBold();
  label_corrente.setOpaque(false);
  
  pnl_output.addControl(label_parametro1);
  pnl_output.addControl(label_parametro2);
  pnl_output.addControl(label_parametro3);
  pnl_output.addControl(label_corrente);
  pnl_about.setText("About");
  pnl_about.setTextBold();
  pnl_about.setTextItalic();
  pnl_about.setOpaque(true);
  pnl_about.addEventHandler(this, "pnl_about_click");
  
  //pnl_about = new GPanel(this, 420, 490, 177, 76, "About");
  //btn_about = new GButton(this, 5, 32, 80, 30);
  btn_about.dispose();
  btn_about = null;
  btn_about = new GButton(this, (int)((5*((420*width)/600))/420), (int)((32*((490*height)/600))/490), (int)((80*((177*width)/600))/177), (int)((30*((76*height)/600))/76));
  btn_about.setText("ABOUT");
  btn_about.setTextBold();
  btn_about.addEventHandler(this, "btn_about_click");
  
  //btn_exit = new GButton(this, 89, 32, 80, 30);
  btn_exit.dispose();
  btn_exit = null;
  btn_exit = new GButton(this, (int)((89*((420*width)/600))/420), (int)((32*((490*height)/600))/490), (int)((80*((177*width)/600))/177), (int)((30*((76*height)/600))/76));
  btn_exit.setText("EXIT");
  btn_exit.setTextBold();
  btn_exit.addEventHandler(this, "btn_exit_click");
  pnl_about.addControl(btn_about);
  pnl_about.addControl(btn_exit);
    
    
    
    
    
    
    
    
    
    //println("font= "+g4p_controls.FontManager.getPriorityFont(null,0,12));
  }
  if(zoomX_bordo!=1.0 || zoomY_bordo!=1.0) scale(zoomX_bordo,zoomY_bordo);
  if (zoomX!=0 || zoomY!=0) scale(zoomX,zoomY);
  //println("width= "+width+" height= "+height);
  reg[0].disegna(offsetX_banco-offsetX_ra_to_banco, offsetY1+10, lung,alt);//ra
  reg[1].disegna(offsetX_banco-offsetX_ra_to_banco+offsetX_rb_to_ra, offsetY1+10, lung,alt);//rb
  
  reg[2].disegna(offsetX1+offsetX2, offsetY1+offsetY2, lung,alt);//rz
  reg[3].disegna(offsetX1+offsetX2+offsetX3+30, offsetY1+offsetY2,lung,alt);//rm
  
  reg[4].disegna(offsetX1+offsetX2,offsetY1+offsetY2+offsetY3,lung,alt);//ry  

  mult[0].disegna(offsetX_banco-offsetX_ra_to_banco+offsetX_rb_to_ra, offsetY1+80, lung+30,lung-plus+10,alt+10);
  
  int bancoX=80,bancoY=90;
  //banco.disegna(offsetX_banco, 85, bancoX, bancoY);
  //alu.disegna(offsetX1-20, offsetY1+70, lung+80,lung-plus+30,alt+40,lung_t+20,alt_t+10);
  alu.disegna(offsetX1+offsetX2, offsetY1+offsetY2-80, lung+80,lung-plus+30,alt+40,lung_t+20,alt_t+10);
  
  //banco.disegna(offsetX_banco, 85, bancoX, bancoY);
  mult[1].disegna(offsetX1+offsetX2,offsetY1+offsetY2+90, lung+30,lung-plus+10,alt+10);
  banco.disegna(offsetX_banco, 85, bancoX, bancoY);
  
  linea[0].disegna(offsetX_banco-bancoX/4,85+bancoY/2,10,20,10,90);//da banco a ra
  linea[1].disegna(offsetX_banco+bancoX/4,85+bancoY/2,10,-20,10,90);//da banco a rb
  
  linea[2].disegna(offsetX_banco-offsetX_ra_to_banco,alt/2+(offsetY1+10),90,-10,20,90);//da ra ad alu
  linea[3].disegna(offsetX_banco-offsetX_ra_to_banco+offsetX_rb_to_ra,alt/2+(offsetY1+10),10,25,20,90);//da rb a muxb
  linea[4].disegna(offsetX_banco-offsetX_ra_to_banco+offsetX_rb_to_ra,alt/2+(offsetY1+10),10,-90,200,90);//da rb a rm
  
  linea[5].disegna(offsetX_banco-offsetX_ra_to_banco+offsetX_rb_to_ra+(lung+30)/4,(offsetY1+80)-25-40/6,0,0,10,90);// da valore immediato a muxb
    
  linea[6].disegna(offsetX_banco-offsetX_ra_to_banco+offsetX_rb_to_ra,offsetY1+80+(alt+10)/2,10,5,20,90);//da muxb ad alu
  
  linea[7].disegna(offsetX1+offsetX2,offsetY1+offsetY2-80+(alt+40)/2,0,0,30,90);//da alu a rz
  
  linea[8].disegna(offsetX1+offsetX2,offsetY1+offsetY2+(alt/2),15,25,35,90);//da rz a muxy
  linea[9].disegna(offsetX1+offsetX2,offsetY1+offsetY2+(alt/2),15,-170,90);//da rz a Indirizzo di memoria
  linea[10].disegna(offsetX1+offsetX2+offsetX3+30, offsetY1+offsetY2+(alt/2),30,-40,90);//da rm a Dati in memoria
  linea[11].disegna((offsetX1+offsetX2+offsetX3+30)+80-10-(70/2),offsetY1+offsetY2+(alt/3)+60-15,165,-10,180);//da Dati in memoria a MuxY
  linea[12].disegna((offsetX1+offsetX2+offsetX3+30)-35-(100/2),(offsetY1+offsetY2+90)-15-(40/3),20,-8,180);//da Indirizzo di rientro a MuxY
  
  linea[13].disegna(offsetX1+offsetX2,offsetY1+offsetY2+90+(alt+10)/2,0,0,25,90);//da muxy a ry
  linea[14].disegna(offsetX1+offsetX2,offsetY1+offsetY2+offsetY3+(alt)/2,20,170,-570,-160,10,90);//da ry a banco
  
  linea[15].disegna(offsetX_banco-(bancoX/2)-70+(60/2),85-(40/3),40,0);//da Indirizzo A a banco
  linea[16].disegna(offsetX_banco-(bancoX/2)-70+(60/2),(85+30)-(40/3),40,0);//da Indirizzo B a banco
  linea[17].disegna(offsetX_banco+(bancoX/2)+65-(60/2),85-(40/3),35,180);//da Indirizzo C a banco
  
  
  //Scritta scritta=new Scritta(col_scritta_des,col_scritta_sel,"Prova");
  rectMode(CENTER);
  textAlign(CENTER);
  scritta[0].set_nome(strings[13]);//Valore immediato
  scritta[0].disegna(offsetX_banco-offsetX_ra_to_banco+offsetX_rb_to_ra+(lung+30)/3,(offsetY1+80)-25,150,40);
  
  scritta[1].set_nome(strings[14]);//Indirizzo di memoria
  scritta[1].disegna((offsetX1+offsetX2+offsetX3+30)+80-5, offsetY1+offsetY2+(alt/3)+40-12,80,70);
  
  scritta[2].set_nome(strings[15]);//Dati in memoria
  scritta[2].disegna((offsetX1+offsetX2+offsetX3+30)+80-10,offsetY1+offsetY2+(alt/3)+70,80,80);
  
  scritta[3].set_nome(strings[16]);//Indirizzo di rientro
  scritta[3].disegna((offsetX1+offsetX2+offsetX3+30)-30,(offsetY1+offsetY2+90)-15,200,40);
  
  scritta[4].set_nome(strings[0]);//Indirizzo A
  scritta[4].disegna(offsetX_banco-(bancoX/2)-70,85,80,40);
  
  scritta[5].set_nome(strings[1]);//Indirizzo B
  scritta[5].disegna(offsetX_banco-(bancoX/2)-70,85+30,80,40);
  
  scritta[6].set_nome(strings[2]);//Indirizzo C
  scritta[6].disegna(offsetX_banco+(bancoX/2)+70,85,80,40);
  popMatrix();
  
  //if (play)
  //{
    //timer_anim.start();
    //println("Istruzione="+istruzione);
    /*for (i=0;i<parametri.length;i++)
    println("parametro n."+i+"="+parametri[i]);*/
    label_parametro1.setText(strings[19]+parametri[0]);//parametro1=
    label_parametro2.setText(strings[20]+parametri[1]);//parametro2=
    label_parametro3.setText(strings[21]+parametri[2]);//parametro3=
    
    //if(match(istruzione,"\\(?ism\\)LOAD")!=null)
    if(istruzione.equals("LOAD") || istruzione.equals("load"))
    {
      if (!(parametri[0].equals(NON_CORRETTO) || parametri[0].equals(NON_INSERITO)) && !(parametri[1].equals(NON_CORRETTO) || parametri[1].equals(NON_CORRETTO)) && !parametri[2].equals(NON_CORRETTO))
      comando=Comando.LOAD;
      else comando=Comando.NULL;
    }
    else if (istruzione.equals("STORE") || istruzione.equals("store") || istruzione.equals("Store"))
    {
      if (!(parametri[0].equals(NON_CORRETTO) || parametri[0].equals(NON_INSERITO)) && !(parametri[1].equals(NON_INSERITO) || parametri[1].equals(NON_CORRETTO)) && !parametri[2].equals(NON_CORRETTO))
           comando=Comando.STORE;
      else comando=Comando.NULL;
    }
    else if (istruzione.equals("ADD") || istruzione.equals("add") || istruzione.equals("Add")){
          if (!(parametri[0].equals(NON_CORRETTO) || parametri[0].equals(NON_INSERITO)) && !(parametri[1].equals(NON_CORRETTO) || parametri[1].equals(NON_INSERITO)) && !parametri[2].equals(NON_CORRETTO))
              comando=Comando.ADD;
          else comando=Comando.NULL;
    }
    else comando=Comando.NULL;
      
        
    switch (comando)
    {
      case LOAD:
        load();
        break;
      case STORE:
        store();
        break;
      case ADD:
        add();
        break;
      default:
        frame=0;
        reset_frame();
    //}
    /*println("Frame="+frame);
    println("Play="+play);
    println("Finale="+finale);*/
  }
  /*else
  {
    timer_anim.stop();
    finale=false;
  }*/
}

void des_tutti(Oggetto[] ogg)
{
  int i;
  for (i=0; i<ogg.length; i++)
  {
    ogg[i].deseleziona();
    ogg[i].no_grassetto();
  }
}

void des_tutti(Oggetto ogg)
{  
  ogg.deseleziona();
  ogg.no_grassetto();
}

void reset_frame()
{
  des_tutti(alu);
  des_tutti(reg);
  des_tutti(linea);
  des_tutti(mult);
  des_tutti(scritta);
  des_tutti(banco);
  des_tutte(banco);
  des_tutte(mult);
  des_tutte(alu);
  OPERAZIONE_CORRENTE="Null";
}

void des_tutte(Banco banco)
{
  banco.des_entrate();
  banco.no_grass_entrate();
  for (int i=0;i<banco.len_uscite();i++)
  {
    banco.des_uscite(i);
    banco.no_grass_uscite(i);
  }
}

void des_tutte(Multiplatore[] mult)
{
  for (int j=0;j<mult.length;j++)
  {
    for (int i=0;i<mult[j].len_entrate();i++)
    { 
      mult[j].des_entrate(i);
      mult[j].no_grass_entrate(i);
    }
  }
}

void des_tutte(Alu alu)
{
  alu.des_uscite();
  alu.no_grass_uscite();
  for (int i=0;i<alu.len_entrate();i++)
  {
    alu.deseleziona();
    alu.no_grass_entrate(i);
  }
}

void cambia_lingua_gui()
{
  surface.setTitle(strings[22]);
  btn_store.setText("STORE");
  btn_load.setText("LOAD");
  btn_add.setText("ADD");
  pnl_pannello.setText(strings[23]);
  pnl_pannello.setTextBold();
  pnl_pannello.setTextItalic();
  pnl_prova.setText(strings[25]);
  pnl_prova.setTextBold();
  pnl_prova.setTextItalic();
  pnl_input.setText("INPUT");
  label_parametro1.setText(strings[19]+parametri[0]);
  label_parametro2.setText(strings[20]+parametri[1]);
  label_parametro3.setText(strings[21]+parametri[2]);
  label_parametro1.setTextBold();
  label_parametro1.setTextItalic();
  label_parametro2.setTextBold();
  label_parametro2.setTextItalic();
  label_parametro3.setTextBold();
  label_parametro3.setTextItalic();
  pnl_comandi.setText(strings[30]);
  pnl_comandi.setTextBold();
  pnl_comandi.setTextItalic();
  txt.setPromptText(strings[27]);
  label_corrente.setText(strings[26]+"\n"+OPERAZIONE_CORRENTE);
  label_corrente.setTextItalic();
  label_corrente.setTextBold();
  btn_about.setText(strings[28]);
  btn_about.setTextBold();
  btn_about.setTextItalic();
  btn_exit.setText(strings[29]);
  btn_exit.setTextBold();
  btn_exit.setTextItalic();
  pnl_about.setText(strings[24]);
  pnl_about.setTextBold();
  pnl_about.setTextItalic();
  //txt.setPromptText("ok");
}

void mousePressed()
{ // is the mousebutton being held?
  if (mouseButton == RIGHT)
  {
    imgX = mouseX-mX;
    imgY = mouseY-mY;
  }
}