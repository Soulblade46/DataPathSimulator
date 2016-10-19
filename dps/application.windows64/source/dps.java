import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import g4p_controls.*; 
import java.awt.Font; 
import g4p_controls.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class dps extends PApplet {

 //<>//


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


//420x580

//PGraphics canvas;
boolean wind_about=false;
int puntoX=0, puntoY=0;
//Linea[] linea;
//Figura[] fig;
boolean inizio=false, play=false;
//color col_linea_des=color(100, 255, 0);
//color col_linea_sel=color(100, 0, 255);
int col_linea_des=color(100, 255, 0);
int col_linea_sel=color(255, 255, 0);
int col_fig_des=color(0, 255, 255);
int col_fig_sel=color(255, 0, 255);
int col_fig_cont=color(0, 100, 255);
int col_scritta_des=color(100, 0, 255);
int col_scritta_sel=color(255, 0, 0);

int col_scritta_des_esterna=color(255, 200, 0);
int col_scritta_sel_esterna=color(255, 255, 255);


int background_color=100;

float ZOOM_INIZIALE=1.0f;
float zoomX=1.0f,zoomY=1.0f;
float zoomX_bordo=1.0f,zoomY_bordo=1.0f;

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
public void setup()
{
  //customGUI();
  
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

public void draw()
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
  if ((zoomX_bordo!=1.0f || zoomY_bordo!=1.0f)&& (height!=height_pre || width!=width_pre))
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
  if(zoomX_bordo!=1.0f || zoomY_bordo!=1.0f) scale(zoomX_bordo,zoomY_bordo);
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

public void des_tutti(Oggetto[] ogg)
{
  int i;
  for (i=0; i<ogg.length; i++)
  {
    ogg[i].deseleziona();
    ogg[i].no_grassetto();
  }
}

public void des_tutti(Oggetto ogg)
{  
  ogg.deseleziona();
  ogg.no_grassetto();
}

public void reset_frame()
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

public void des_tutte(Banco banco)
{
  banco.des_entrate();
  banco.no_grass_entrate();
  for (int i=0;i<banco.len_uscite();i++)
  {
    banco.des_uscite(i);
    banco.no_grass_uscite(i);
  }
}

public void des_tutte(Multiplatore[] mult)
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

public void des_tutte(Alu alu)
{
  alu.des_uscite();
  alu.no_grass_uscite();
  for (int i=0;i<alu.len_entrate();i++)
  {
    alu.deseleziona();
    alu.no_grass_entrate(i);
  }
}

public void cambia_lingua_gui()
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

public void mousePressed()
{ // is the mousebutton being held?
  if (mouseButton == RIGHT)
  {
    imgX = mouseX-mX;
    imgY = mouseY-mY;
  }
}
public void add()
{
  //final int n_frame=4;
  switch (frame)
  {
  case 0:
    reset_frame();
    scritta[4].seleziona();
    scritta[4].grassetto();
    scritta[5].seleziona();
    scritta[5].grassetto();
    
    scritta[6].seleziona();
    scritta[6].grassetto();
    OPERAZIONE_CORRENTE=strings[35];//OPERAZIONE_CORRENTE="Impostazione indirizzi del banco";
    break;
  case 1:
    reset_frame();
    scritta[6].seleziona();
    scritta[6].grassetto();
    
    linea[15].seleziona();
    linea[15].grassetto();
    linea[16].seleziona();
    linea[16].grassetto();
    OPERAZIONE_CORRENTE=strings[35];//OPERAZIONE_CORRENTE="Impostazione indirizzi del banco";
    break;
  case 2:
    reset_frame();
    scritta[6].seleziona();
    scritta[6].grassetto();
    
    banco.seleziona();
    banco.grassetto();
    OPERAZIONE_CORRENTE=strings[36];//OPERAZIONE_CORRENTE="Lettura registri del banco";
    break;
  case 3:
    reset_frame();
    scritta[6].seleziona();
    scritta[6].grassetto();
    
    banco.sel_uscite(0);
    banco.grass_uscite(0);
    banco.sel_uscite(1);
    banco.grass_uscite(1);
    
    OPERAZIONE_CORRENTE="RA"+"<--"+"["+parametri[1]+"]"+"RB"+"<--"+"["+parametri[2]+"]";
    break;
  case 4:
    reset_frame();
    scritta[6].seleziona();
    scritta[6].grassetto();
    
    linea[0].seleziona();
    linea[0].grassetto();
    linea[1].seleziona();
    linea[1].grassetto();
    OPERAZIONE_CORRENTE="RA"+"<--"+"["+parametri[1]+"]"+"RB"+"<--"+"["+parametri[2]+"]";
    break;
  case 5:
    reset_frame();
    scritta[6].seleziona();
    scritta[6].grassetto();
    
    reg[0].seleziona();
    reg[0].grassetto();
    reg[1].seleziona();
    reg[1].grassetto();
    OPERAZIONE_CORRENTE="RA"+"<--"+"["+parametri[1]+"]"+"RB"+"<--"+"["+parametri[2]+"]";
    break;
  case 6:
    reset_frame();
    scritta[6].seleziona();
    scritta[6].grassetto();
    
    linea[3].seleziona();
    linea[3].grassetto();
    OPERAZIONE_CORRENTE=strings[37];//OPERAZIONE_CORRENTE="Selezione ingressi MuXB";
    break;
  case 7:
    reset_frame();
    scritta[6].seleziona();
    scritta[6].grassetto();
    
    mult[0].sel_entrate(0);
    mult[0].grass_entrate(0);
    OPERAZIONE_CORRENTE=strings[38];//OPERAZIONE_CORRENTE="Selezione ingresso 0 MuXB";
    break;
  case 8:
    reset_frame();
    scritta[6].seleziona();
    scritta[6].grassetto();
    
    mult[0].seleziona();
    mult[0].grassetto();
    OPERAZIONE_CORRENTE=strings[38];//OPERAZIONE_CORRENTE="Selezione ingresso 0 MuXB";
    break;
  case 9:
    reset_frame();
    scritta[6].seleziona();
    scritta[6].grassetto();
    
    linea[2].seleziona();
    linea[2].grassetto();
    linea[6].seleziona();
    linea[6].grassetto();
    OPERAZIONE_CORRENTE=strings[40];//OPERAZIONE_CORRENTE="Impostazione ingressi Alu";
    break;
  case 10:
    reset_frame();
    scritta[6].seleziona();
    scritta[6].grassetto();
    
    alu.sel_entrate(0);
    alu.grass_entrate(0);
    alu.sel_entrate(1);
    alu.grass_entrate(1);
    OPERAZIONE_CORRENTE=strings[40];//OPERAZIONE_CORRENTE="Impostazione ingressi Alu";
    break;
  case 11:
    reset_frame();
    scritta[6].seleziona();
    scritta[6].grassetto();
    
    alu.seleziona();
    alu.grassetto();
    OPERAZIONE_CORRENTE="[RA]+[RB]";
    break;
  case 12:
    reset_frame();
    scritta[6].seleziona();
    scritta[6].grassetto();
    
    alu.sel_uscite();
    alu.grass_uscite();
    OPERAZIONE_CORRENTE="RZ<--[RA]+[RB]";
    break;
  case 13:
    reset_frame();
    scritta[6].seleziona();
    scritta[6].grassetto();
    
    linea[7].seleziona();
    linea[7].grassetto();
    OPERAZIONE_CORRENTE="RZ<--[RA]+[RB]";
    break;
  case 14:
    reset_frame();
    scritta[6].seleziona();
    scritta[6].grassetto();
    
    reg[2].seleziona();
    reg[2].grassetto();
    OPERAZIONE_CORRENTE="RZ<--[RA]+[RB]";
    break;
  case 15:
    reset_frame();
    scritta[6].seleziona();
    scritta[6].grassetto();
    
    linea[8].seleziona();
    linea[8].grassetto();
    OPERAZIONE_CORRENTE=strings[41];//OPERAZIONE_CORRENTE="Selezione ingressi 0 MuXY";
    break;
  case 16:
    reset_frame();
    scritta[6].seleziona();
    scritta[6].grassetto();
    
    mult[1].sel_entrate(0);
    mult[1].grass_entrate(0);
    OPERAZIONE_CORRENTE=strings[41];//OPERAZIONE_CORRENTE="Selezione ingressi 0 MuXY";
    break;
  case 17:
    reset_frame();
    scritta[6].seleziona();
    scritta[6].grassetto();
    
    mult[1].seleziona();
    mult[1].grassetto();
    OPERAZIONE_CORRENTE=strings[41];//OPERAZIONE_CORRENTE="Selezione ingressi 0 MuXY";
    break;
  case 18:
    reset_frame();
    scritta[6].seleziona();
    scritta[6].grassetto();
    
    linea[13].seleziona();
    linea[13].grassetto();
    OPERAZIONE_CORRENTE="[RY]<--[RZ]";
    break;
  case 19:
    reset_frame();
    scritta[6].seleziona();
    scritta[6].grassetto();
    
    reg[4].seleziona();
    reg[4].grassetto();
    OPERAZIONE_CORRENTE="[RY]<--[RZ]";
    break;
  case 20:
    reset_frame();
 
    linea[14].seleziona();
    linea[14].grassetto();
    linea[17].seleziona();
    linea[17].grassetto();
    OPERAZIONE_CORRENTE=parametri[0]+"<--[RY]";
    break;
  case 21:
    reset_frame();    
    banco.sel_entrate();
    banco.grass_entrate();
    OPERAZIONE_CORRENTE=parametri[0]+"<--[RY]";
    break;
  case 22:
    reset_frame();
    banco.seleziona();
    banco.grassetto();
    OPERAZIONE_CORRENTE=parametri[0]+"<--[RY]";
    timer_anim.stop();
    finale=true;
    break;
  default:
    /*timer_anim.stop();
    finale=true;*/
    //if (frame>0)
    //{
      //timer_anim.stop();
      //finale=true;
      
      //frame=0;
      //fig[0].deseleziona();
      //des_tutti(fig);
      //play=false;
      //if (frame > n_frame) frame=n_frame;
      //timer_anim.stop();
      frame=0;
    //}
    //else frame=0;
  }
}
PShape trapezio_alu;
PShape triangolo_alu;
PShape forma_alu;

public class Alu extends Figura
{
  private Scritta[] entrate;
  private Scritta uscite;
  public Alu(int col_des,int col_sel,int col_fig_cont,int col_des_scritta,int col_sel_scritta)
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
public class Banco extends Rettangolo
{
  private Scritta entrate;
  private Scritta[] uscite;
  public Banco(int col_des,int col_sel,int col_fig_cont,int col_des_scritta,int col_sel_scritta)
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
  public void disegna(int x,int y,int larg,int alt)
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
protected class Figura extends Oggetto
{
  private String nome;
  private PFont font;
  private int colore_contorno; 
  public Figura(int col_des,int col_sel,int col_fig_cont)
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
  public PFont get_font(){return font;}
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
/* =========================================================
 * ====                   WARNING                        ===
 * =========================================================
 * The code in this tab has been generated from the GUI form
 * designer and care should be taken when editing this file.
 * Only add/edit code inside the event handlers i.e. only
 * use lines between the matching comment tags. e.g.

 void myBtnEvents(GButton button) { //_CODE_:button1:12356:
     // It is safe to enter your event code here  
 } //_CODE_:button1:12356:
 
 * Do not rename this tab!
 * =========================================================
 */

public void pnl_comandi_click(GPanel source, GEvent event) { //_CODE_:pnl_comandi:486289:
  println("panel1 - GPanel >> GEvent." + event + " @ " + millis());
} //_CODE_:pnl_comandi:486289:

public void btn_store_click(GButton source, GEvent event) { //_CODE_:btn_store:723271:
  println("button1 - GButton >> GEvent." + event + " @ " + millis());
} //_CODE_:btn_store:723271:

public void btn_load_click(GButton source, GEvent event) { //_CODE_:btn_load:393455:
  println("button2 - GButton >> GEvent." + event + " @ " + millis());
  //comando=Comando.LOAD;
  comando=Comando.LOAD;
  timer_anim.stop();
  frame=0;
  finale=false;
} //_CODE_:btn_load:393455:

public void btn_add_click(GButton source, GEvent event) { //_CODE_:btn_add:553967:
  println("button3 - GButton >> GEvent." + event + " @ " + millis());
} //_CODE_:btn_add:553967:

public void btn_en_lang_click(GImageButton source, GEvent event) { //_CODE_:btn_en_lang:744671:
  println("btn_en_lang - GImageButton >> GEvent." + event + " @ " + millis());
  lingua=true;
} //_CODE_:btn_en_lang:744671:

public void btn_it_lang_click(GImageButton source, GEvent event) { //_CODE_:btn_it_lang:904538:
  println("btn_it_lang - GImageButton >> GEvent." + event + " @ " + millis());
  lingua=false;
} //_CODE_:btn_it_lang:904538:

public void btn_zoom_out_click(GImageButton source, GEvent event) { //_CODE_:btn_zoom_out:997960:
  println("imgButton1 - GImageButton >> GEvent." + event + " @ " + millis());
  zoomX=zoomX-0.2f;
  zoomY=zoomY-0.2f;
  //scale(zoomX,zoomY);
} //_CODE_:btn_zoom_out:997960:

public void btn_zoom_in_click(GImageButton source, GEvent event) { //_CODE_:btn_zoom_in:450024:
  println("btn_zoom_in - GImageButton >> GEvent." + event + " @ " + millis());
  zoomX=zoomX+0.2f;
  zoomY=zoomY+0.2f;
  //scale(zoomX,zoomY);
} //_CODE_:btn_zoom_in:450024:

public void pnl_pannello_click(GPanel source, GEvent event) { //_CODE_:pnl_pannello:696196:
  println("panel2 - GPanel >> GEvent." + event + " @ " + millis());
} //_CODE_:pnl_pannello:696196:

public void pnl_prova_click(GPanel source, GEvent event) { //_CODE_:pnl_prova:300082:
  println("panel3 - GPanel >> GEvent." + event + " @ " + millis());
} //_CODE_:pnl_prova:300082:

public void btn_play_click(GImageButton source, GEvent event) { //_CODE_:btn_play:442785:
  println("imgButton1 - GImageButton >> GEvent." + event + " @ " + millis());
  play=true;
  timer_anim.start();
} //_CODE_:btn_play:442785:

public void btn_pause_click(GImageButton source, GEvent event) { //_CODE_:btn_pause:284553:
  println("imgButton1 - GImageButton >> GEvent." + event + " @ " + millis());
  //play=false;
  timer_anim.stop();
} //_CODE_:btn_pause:284553:

public void btn_forward_click(GImageButton source, GEvent event) { //_CODE_:btn_forward:505491:
  println("imgButton2 - GImageButton >> GEvent." + event + " @ " + millis());
  if (!timer_anim.isRunning() && (!istruzione.equals(0)) && !finale)
  {
    frame++;
    reset_frame();
  }
} //_CODE_:btn_forward:505491:

public void btn_rewind_click(GImageButton source, GEvent event) { //_CODE_:btn_rewind:407228:
  println("imgButton3 - GImageButton >> GEvent." + event + " @ " + millis());
  if (!timer_anim.isRunning() && (comando!=Comando.NULL) && frame!=0)
  {
    frame--;
    if (finale) {
      finale=false;
    }
    reset_frame();
  }
} //_CODE_:btn_rewind:407228:

public void btn_stop_click(GImageButton source, GEvent event) { //_CODE_:btn_stop:736460:
  println("imgButton1 - GImageButton >> GEvent." + event + " @ " + millis());
  frame=0;
  reset_frame();
  timer_anim.stop();
  finale=false;
} //_CODE_:btn_stop:736460:

public void pnl_input_click(GPanel source, GEvent event) { //_CODE_:pnl_input:741392:
  println("output - GPanel >> GEvent." + event + " @ " + millis());
} //_CODE_:pnl_input:741392:

public void txt_enter(GTextField source, GEvent event) { //_CODE_:txt:616830:
  String string=new String();
  String[] commands;
  String[] temp;
  println("txt - GTextField >> GEvent." + event + " @ " + millis());
  if (event==GEvent.ENTERED)
  {
    string=source.getText();
    OPERAZIONE_CORRENTE="Null";
  for (int i =0;i<parametri.length;i++)
  parametri[i]="Null";
  }
  commands=splitTokens(string, ", ");
  

  /*if (commands[0].equals("LOAD") || commands[0].equals("load"))
   {
   if (!(parametri[0].equals(NON_CORRETTO) || parametri[0].equals(NON_INSERITO)) && !(parametri[1].equals(NON_CORRETTO) || parametri[1].equals(NON_CORRETTO)) && !parametri[2].equals(NON_CORRETTO))
   comando=Comando.LOAD;
   else comando=Comando.NULL;
   } else if (istruzione.equals("STORE") || istruzione.equals("store") || istruzione.equals("Store"))
   {
   if (!(parametri[0].equals(NON_CORRETTO) || parametri[0].equals(NON_INSERITO)) && !(parametri[1].equals(NON_INSERITO) || parametri[1].equals(NON_CORRETTO)) && !parametri[2].equals(NON_CORRETTO))
   comando=Comando.STORE;
   else comando=Comando.NULL;
   } else if (istruzione.equals("ADD") || istruzione.equals("add") || istruzione.equals("Add")) {
   if (!(parametri[0].equals(NON_CORRETTO) || parametri[0].equals(NON_INSERITO)) && !(parametri[1].equals(NON_CORRETTO) || parametri[1].equals(NON_INSERITO)) && !parametri[2].equals(NON_CORRETTO))
   comando=Comando.ADD;
   else comando=Comando.NULL;
   } else comando=Comando.NULL;*/

  for (int i=0; i<commands.length; i++) 
  {
    if (i==0)
    {
      istruzione=commands[i];
      //println("Istruzione="+istruzione);
    } else
    {
      if (istruzione.equals("LOAD") || istruzione.equals("load"))
      {
        switch (i)
        {
        case 1:
          if (match(commands[i], "r|R\\d+")==null) 
          {
            parametri[i-1]=NON_CORRETTO;
          } else
          {
            parametri[i-1]=commands[i];
          } 
          break;
        case 2:
          if (match(commands[i], "\\d+{1}\\(((r|R)\\d+){1}\\)")!=null) 
          {
            //parametri[i-1]=commands[i];
            temp=splitTokens(commands[i], "() ");
            //parametri[i]=temp[0];
            /*for (int j=0;j<temp.length;j++)
             parametri[i-1]=temp[j];*/
            parametri[i-1]=temp[1];
            parametri[i]=temp[0];
          } else if (match(commands[i], "{1}\\(((r|R)\\d+){1}\\)")!=null)
          {
            temp=splitTokens(commands[i], "() ");
            parametri[i-1]=temp[0];
          } else if (match(commands[i], "(r|R)\\d+")!=null)
          {
            parametri[i-1]=commands[i];
          } else
          {
            parametri[i-1]=NON_CORRETTO;
          } 
          break;
        case 3:
          if (match(commands[i], "#\\d+")==null) 
          {
            parametri[i-1]=NON_CORRETTO;
          } else
          {
            temp=splitTokens(commands[i], "#");
            parametri[i-1]=temp[0];
          } 
          break;
        }
      }
      else if (istruzione.equals("STORE") || istruzione.equals("store"))
      {
        switch (i)
        {
        case 1:
          if (match(commands[i], "r|R\\d+")==null) 
          {
            parametri[i-1]=NON_CORRETTO;
          } else
          {
            parametri[i-1]=commands[i];
          } 
          break;
        case 2:
          if (match(commands[i], "\\d+{1}\\(((r|R)\\d+){1}\\)")!=null) 
          {
            //parametri[i-1]=commands[i];
            temp=splitTokens(commands[i], "() ");
            //parametri[i]=temp[0];
            /*for (int j=0;j<temp.length;j++)
             parametri[i-1]=temp[j];*/
            parametri[i-1]=temp[1];
            parametri[i]=temp[0];
          } else if (match(commands[i], "{1}\\(((r|R)\\d+){1}\\)")!=null)
          {
            temp=splitTokens(commands[i], "() ");
            parametri[i-1]=temp[0];
          } else if (match(commands[i], "(r|R)\\d+")!=null)
          {
            parametri[i-1]=commands[i];
          } else
          {
            parametri[i-1]=NON_CORRETTO;
          } 
          break;
        case 3:
            if (match(commands[i], "#\\d+")==null) 
          {
            parametri[i-1]=NON_CORRETTO;
          } else
          {
            temp=splitTokens(commands[i], "#");
            parametri[i-1]=temp[0];
          } 
          break;
        }
      }
      if (istruzione.equals("ADD") || istruzione.equals("add"))
      {
        switch (i)
        {
        case 1:
          if (match(commands[i], "r|R\\d+")==null) 
          {
            parametri[i-1]=NON_CORRETTO;
          } else
          {
            parametri[i-1]=commands[i];
          } 
          break;
        case 2:
         if (match(commands[i], "r|R\\d+")==null) 
          {
            parametri[i-1]=NON_CORRETTO;
          } else
          {
            parametri[i-1]=commands[i];
          }
        case 3:
          if (match(commands[i], "#\\d+")!=null) 
          {
            
            temp=splitTokens(commands[i], "#");
            parametri[i-1]=temp[0];
          }
          else if(match(commands[i], "r|R\\d+")!=null)
          {
            parametri[i-1]=commands[i];
          }
          else
          {
            parametri[i-1]=NON_CORRETTO;
          } 
          break;
        }
      }
      //println("parametro n."+i+"="+parametri[i-1]);
    }
  }
  //for (int i=0;i<parametri.length;i++)
  //println("parametro n."+i+"="+parametri[i]);
  //label1.setText("parametro n."+i+"="+parametri[i]);
  /*if (commands.length >= 2)
   {
   if(match(commands[1],"R\\d+")==null) {parametri[0]="Non corretto";}
   }
   if (commands.length >= 3)
   {
   if(match(commands[2],"\\d+{1}\\(((r|R)\\d+){1}\\)")==null) {parametri[1]="Non corretto";}
   }
   if (parametri.length==4)
   {
   if(match(parametri[3],"#\\d+")==null) {parametri[2]="Non corretto";}
   else ;
   }*/
} //_CODE_:txt:616830:

public void pnl_output_click(GPanel source, GEvent event) { //_CODE_:pnl_output:747906:
  println("pnl_output - GPanel >> GEvent." + event + " @ " + millis());
} //_CODE_:pnl_output:747906:

public void pnl_about_click(GPanel source, GEvent event) { //_CODE_:pnl_about:241399:
  println("pnl_about - GPanel >> GEvent." + event + " @ " + millis());
} //_CODE_:pnl_about:241399:

public void btn_about_click(GButton source, GEvent event) { //_CODE_:btn_about:632680:
  println("btn_about - GButton >> GEvent." + event + " @ " + millis());
  if (wind_about==false)
  {
    window2 = GWindow.getWindow(this, "About", 200, 200, 300, 120, JAVA2D);
    window2.noLoop();
    window2.setAlwaysOnTop(true); 
    window2.setActionOnClose(G4P.CLOSE_WINDOW);
    window2.addDrawHandler(this, "about_draw");
    window2.addOnCloseHandler(this, "about_close");
    window2.loop();
    wind_about=true;
  }  
} //_CODE_:btn_about:632680:

public void btn_exit_click(GButton source, GEvent event) { //_CODE_:btn_exit:525566:
  println("btn_exit - GButton >> GEvent." + event + " @ " + millis());
  exit();
} //_CODE_:btn_exit:525566:

public void timer_anim_action(GTimer source) { //_CODE_:timer_anim:563156:
  println("timer_anim - GTimer >> an event occured @ " + millis());
  //frame++;
  if (!finale)
  {
    frame++;
    //des_tutti(reg);
  }
} //_CODE_:timer_anim:563156:

synchronized public void about_draw(PApplet appc, GWinData data) { //_CODE_:window1:970145:
  appc.background(200);
  window2.setTitle(strings[28]);
  appc.fill(0);
  appc.text(strings[31]+'\n'+strings[32]+'\n'+strings[33]+'\n',20,20);
} //_CODE_:window1:970145:

public void about_close(PApplet applet, GWinData windata) { //_CODE_:window1:986551:
  println("window1 - window closed at " + millis());
  //window.setVisible(false);
  wind_about=false;
} //_CODE_:window1:986551:



// Create all the GUI controls. 
// autogenerated do not edit
public void createGUI(){
  G4P.messagesEnabled(false);
  G4P.setGlobalColorScheme(GCScheme.BLUE_SCHEME);
  G4P.setCursor(ARROW);
  surface.setTitle("Progetto");
  pnl_comandi = new GPanel(this, 418, 1, 179, 55, "Comandi");
  pnl_comandi.setText("Comandi");
  pnl_comandi.setTextBold();
  pnl_comandi.setTextItalic();
  pnl_comandi.setOpaque(true);
  pnl_comandi.addEventHandler(this, "pnl_comandi_click");
  btn_store = new GButton(this, 2, 28, 80, 30);
  btn_store.setText("STORE");
  btn_store.addEventHandler(this, "btn_store_click");
  btn_load = new GButton(this, 2, 59, 80, 30);
  btn_load.setText("LOAD");
  btn_load.addEventHandler(this, "btn_load_click");
  btn_add = new GButton(this, 2, 91, 80, 30);
  btn_add.setText("ADD");
  btn_add.addEventHandler(this, "btn_add_click");
  btn_en_lang = new GImageButton(this, 134, 22, 41, 28, new String[] { "enFlag.png", "enFlag.png", "enFlag.png" } );
  btn_en_lang.addEventHandler(this, "btn_en_lang_click");
  btn_it_lang = new GImageButton(this, 88, 22, 41, 28, new String[] { "itFlag.png", "itFlag.png", "itFlag.png" } );
  btn_it_lang.addEventHandler(this, "btn_it_lang_click");
  btn_zoom_out = new GImageButton(this, 6, 22, 41, 28, new String[] { "imageedit_5_4106865581 [www.imagesplitter.net]-0-0.png", "imageedit_5_4106865581 [www.imagesplitter.net]-0-0.png", "imageedit_5_4106865581 [www.imagesplitter.net]-0-0.png" } );
  btn_zoom_out.addEventHandler(this, "btn_zoom_out_click");
  btn_zoom_in = new GImageButton(this, 44, 22, 41, 28, new String[] { "imageedit_5_4106865581 [www.imagesplitter.net]-0-1.png", "imageedit_5_4106865581 [www.imagesplitter.net]-0-1.png", "imageedit_5_4106865581 [www.imagesplitter.net]-0-1.png" } );
  btn_zoom_in.addEventHandler(this, "btn_zoom_in_click");
  pnl_comandi.addControl(btn_store);
  pnl_comandi.addControl(btn_load);
  pnl_comandi.addControl(btn_add);
  pnl_comandi.addControl(btn_en_lang);
  pnl_comandi.addControl(btn_it_lang);
  pnl_comandi.addControl(btn_zoom_out);
  pnl_comandi.addControl(btn_zoom_in);
  pnl_pannello = new GPanel(this, 0, 0, 418, 20, "PANNELLO");
  pnl_pannello.setCollapsible(false);
  pnl_pannello.setDraggable(false);
  pnl_pannello.setText("PANNELLO");
  pnl_pannello.setTextBold();
  pnl_pannello.setTextItalic();
  pnl_pannello.setOpaque(true);
  pnl_pannello.addEventHandler(this, "pnl_pannello_click");
  pnl_prova = new GPanel(this, 419, 55, 179, 94, "CONTROLLI");
  pnl_prova.setText("CONTROLLI");
  pnl_prova.setTextBold();
  pnl_prova.setTextItalic();
  pnl_prova.setOpaque(true);
  pnl_prova.addEventHandler(this, "pnl_prova_click");
  btn_play = new GImageButton(this, 80, 40, 30, 30, new String[] { "imageedit_5_2662122022 [www.imagesplitter.net]-0-0.gif", "imageedit_5_2662122022 [www.imagesplitter.net]-0-0.gif", "imageedit_5_2662122022 [www.imagesplitter.net]-0-0.gif" } );
  btn_play.addEventHandler(this, "btn_play_click");
  btn_pause = new GImageButton(this, 50, 40, 30, 30, new String[] { "imageedit_5_2662122022 [www.imagesplitter.net]-1-0.gif", "imageedit_5_2662122022 [www.imagesplitter.net]-1-0.gif", "imageedit_5_2662122022 [www.imagesplitter.net]-1-0.gif" } );
  btn_pause.addEventHandler(this, "btn_pause_click");
  btn_forward = new GImageButton(this, 140, 40, 30, 30, new String[] { "imageedit_5_2662122022 [www.imagesplitter.net]-1-2.gif", "imageedit_5_2662122022 [www.imagesplitter.net]-2-2.gif", "imageedit_5_2662122022 [www.imagesplitter.net]-2-2.gif" } );
  btn_forward.addEventHandler(this, "btn_forward_click");
  btn_rewind = new GImageButton(this, 20, 40, 30, 30, new String[] { "imageedit_5_2662122022 [www.imagesplitter.net]-1-1.gif", "imageedit_5_2662122022 [www.imagesplitter.net]-2-1.gif", "imageedit_5_2662122022 [www.imagesplitter.net]-2-1.gif" } );
  btn_rewind.addEventHandler(this, "btn_rewind_click");
  btn_stop = new GImageButton(this, 110, 40, 30, 30, new String[] { "imageedit_5_2662122022 [www.imagesplitter.net]-2-0.gif", "imageedit_5_2662122022 [www.imagesplitter.net]-2-0.gif", "imageedit_5_2662122022 [www.imagesplitter.net]-2-0.gif" } );
  btn_stop.addEventHandler(this, "btn_stop_click");
  pnl_prova.addControl(btn_play);
  pnl_prova.addControl(btn_pause);
  pnl_prova.addControl(btn_forward);
  pnl_prova.addControl(btn_rewind);
  pnl_prova.addControl(btn_stop);
  pnl_input = new GPanel(this, 420, 150, 179, 147, "INPUT");
  pnl_input.setCollapsible(false);
  pnl_input.setDraggable(true);
  pnl_input.setText("INPUT");
  pnl_input.setTextBold();
  pnl_input.setTextItalic();
  pnl_input.setOpaque(true);
  pnl_input.addEventHandler(this, "pnl_input_click");
  txt = new GTextField(this, 9, 58, 160, 30, G4P.SCROLLBARS_NONE);
  txt.setPromptText("Inserire comando");
  txt.setOpaque(true);
  txt.addEventHandler(this, "txt_enter");
  pnl_input.addControl(txt);
  pnl_output = new GPanel(this, 420, 299, 179, 190, "OUTPUT");
  pnl_output.setText("OUTPUT");
  pnl_output.setTextBold();
  pnl_output.setTextItalic();
  pnl_output.setOpaque(true);
  pnl_output.addEventHandler(this, "pnl_output_click");
  label_parametro1 = new GLabel(this, 2, 23, 150, 20);
  label_parametro1.setText("CIAO1");
  label_parametro1.setTextBold();
  label_parametro1.setTextItalic();
  label_parametro1.setOpaque(false);
  label_parametro2 = new GLabel(this, 2, 53, 150, 20);
  label_parametro2.setText("CIAO2");
  label_parametro2.setTextBold();
  label_parametro2.setTextItalic();
  label_parametro2.setOpaque(false);
  label_parametro3 = new GLabel(this, 1, 83, 150, 20);
  label_parametro3.setText("CIAO3");
  label_parametro3.setTextBold();
  label_parametro3.setTextItalic();
  label_parametro3.setOpaque(false);
  label_corrente = new GLabel(this, 2, 115, 177, 68);
  label_corrente.setTextAlign(GAlign.CENTER, GAlign.TOP);
  label_corrente.setText("Non inserito");
  label_corrente.setTextBold();
  label_corrente.setOpaque(false);
  pnl_output.addControl(label_parametro1);
  pnl_output.addControl(label_parametro2);
  pnl_output.addControl(label_parametro3);
  pnl_output.addControl(label_corrente);
  pnl_about = new GPanel(this, 420, 490, 177, 76, "About");
  pnl_about.setText("About");
  pnl_about.setTextBold();
  pnl_about.setTextItalic();
  pnl_about.setOpaque(true);
  pnl_about.addEventHandler(this, "pnl_about_click");
  btn_about = new GButton(this, 5, 32, 80, 30);
  btn_about.setText("ABOUT");
  btn_about.setTextBold();
  btn_about.addEventHandler(this, "btn_about_click");
  btn_exit = new GButton(this, 89, 32, 80, 30);
  btn_exit.setText("EXIT");
  btn_exit.setTextBold();
  btn_exit.addEventHandler(this, "btn_exit_click");
  pnl_about.addControl(btn_about);
  pnl_about.addControl(btn_exit);
  timer_anim = new GTimer(this, this, "timer_anim_action", 1000);
}

// Variable declarations 
// autogenerated do not edit
GPanel pnl_comandi; 
GButton btn_store; 
GButton btn_load; 
GButton btn_add; 
GImageButton btn_en_lang; 
GImageButton btn_it_lang; 
GImageButton btn_zoom_out; 
GImageButton btn_zoom_in; 
GPanel pnl_pannello; 
GPanel pnl_prova; 
GImageButton btn_play; 
GImageButton btn_pause; 
GImageButton btn_forward; 
GImageButton btn_rewind; 
GImageButton btn_stop; 
GPanel pnl_input; 
GTextField txt; 
GPanel pnl_output; 
GLabel label_parametro1; 
GLabel label_parametro2; 
GLabel label_parametro3; 
GLabel label_corrente; 
GPanel pnl_about; 
GButton btn_about; 
GButton btn_exit; 
GTimer timer_anim; 
GWindow window2;
public class Linea extends Oggetto
{
  private int weight;
  public Linea(int col_des,int col_sel)
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
public void load()
{
  //final int n_frame=4;
  switch (frame)
  {
  case 0:
    reset_frame();
    scritta[4].seleziona();
    scritta[4].grassetto();
    scritta[6].seleziona();
    scritta[6].grassetto();
    OPERAZIONE_CORRENTE=strings[35];//OPERAZIONE_CORRENTE="Impostazione indirizzi del banco";
    break;
  case 1:
    reset_frame();
    scritta[6].seleziona();
    scritta[6].grassetto();
    linea[15].seleziona();
    linea[15].grassetto();
    OPERAZIONE_CORRENTE=strings[35];//OPERAZIONE_CORRENTE="Impostazione indirizzi del banco";
    //linea[16].seleziona();
    //linea[16].grassetto();
    break;
  case 2:
    reset_frame();
    scritta[6].seleziona();
    scritta[6].grassetto();
    banco.seleziona();
    banco.grassetto();
    OPERAZIONE_CORRENTE=strings[36];//OPERAZIONE_CORRENTE="Lettura registri del banco";
    break;
  case 3:
    reset_frame();
    scritta[6].seleziona();
    scritta[6].grassetto();
    banco.sel_uscite(0);
    banco.grass_uscite(0);
    OPERAZIONE_CORRENTE="RA<--["+parametri[1]+"]";
    //banco.sel_uscite(1);
    break;
  case 4:
    reset_frame();
    scritta[6].seleziona();
    scritta[6].grassetto();
    linea[0].seleziona();
    linea[0].grassetto();
    OPERAZIONE_CORRENTE="RA<--["+parametri[1]+"]";
    //linea[1].seleziona();
    break;
  case 5:
    reset_frame();
    scritta[6].seleziona();
    scritta[6].grassetto();
    reg[0].seleziona();
    reg[0].grassetto();
    OPERAZIONE_CORRENTE="RA<--["+parametri[1]+"]";
    //reg[1].seleziona();
    break;
  case 6:
    reset_frame();
    scritta[6].seleziona();
    scritta[6].grassetto();
    scritta[0].seleziona();
    scritta[0].grassetto();
    OPERAZIONE_CORRENTE=strings[37];//OPERAZIONE_CORRENTE="Selezione ingressi MuXB";
    break;
  case 7:
    reset_frame();
    scritta[6].seleziona();
    scritta[6].grassetto();
    //linea[2].seleziona();
    //linea[2].grassetto();
    linea[5].seleziona();
    linea[5].grassetto();
    OPERAZIONE_CORRENTE=strings[37];//OPERAZIONE_CORRENTE="Selezione ingressi MuXB";
    break;
  case 8:
    reset_frame();
    scritta[6].seleziona();
    scritta[6].grassetto();
    mult[0].sel_entrate(1);
    mult[0].grass_entrate(1);
    OPERAZIONE_CORRENTE=strings[39];//OPERAZIONE_CORRENTE="Selezione ingresso 1 MuXB";
    break;
  case 9:
    reset_frame();
    scritta[6].seleziona();
    scritta[6].grassetto();
    mult[0].seleziona();
    mult[0].grassetto();
    OPERAZIONE_CORRENTE=strings[39];//OPERAZIONE_CORRENTE="Selezione ingresso 1 MuXB";
    break;
  case 10:
    reset_frame();
    scritta[6].seleziona();
    scritta[6].grassetto();
    linea[6].seleziona();
    linea[6].grassetto();
    linea[2].seleziona();
    linea[2].grassetto();
    OPERAZIONE_CORRENTE=strings[40];//OPERAZIONE_CORRENTE="Impostazione ingressi Alu";
    break;
  case 11:
    reset_frame();
    scritta[6].seleziona();
    scritta[6].grassetto();
    alu.sel_entrate(0);
    alu.grass_entrate(0);
    alu.sel_entrate(1);
    alu.grass_entrate(1);
    OPERAZIONE_CORRENTE=strings[40];//OPERAZIONE_CORRENTE="Impostazione ingressi Alu";
    break;
  case 12:
    reset_frame();
    scritta[6].seleziona();
    scritta[6].grassetto();
    alu.seleziona();
    alu.grassetto();
    OPERAZIONE_CORRENTE="[RA]+"+parametri[2];
    break;
  case 13:
    reset_frame();
    scritta[6].seleziona();
    scritta[6].grassetto();
    alu.sel_uscite();
    alu.grass_uscite();
    OPERAZIONE_CORRENTE="[RA]+"+parametri[2];
    break;
  case 14:
    reset_frame();
    scritta[6].seleziona();
    scritta[6].grassetto();
    linea[7].seleziona();
    linea[7].grassetto();
    OPERAZIONE_CORRENTE="RZ<--"+"[RA]+"+parametri[2];
    break;
  case 15:
    reset_frame();
    scritta[6].seleziona();
    scritta[6].grassetto();
    reg[2].seleziona();
    reg[2].grassetto();
    OPERAZIONE_CORRENTE="RZ<--"+"[RA]+"+parametri[2];
    break;
  case 16:
    reset_frame();
    scritta[6].seleziona();
    scritta[6].grassetto();
    linea[9].seleziona();
    linea[9].grassetto();
    OPERAZIONE_CORRENTE=strings[14]+"<--[RZ]";
    break;
  case 17:
    reset_frame();
    scritta[6].seleziona();
    scritta[6].grassetto();
    scritta[1].seleziona();
    scritta[1].grassetto();
    OPERAZIONE_CORRENTE=strings[14]+"<--[RZ]";
    break;
  case 18:
    reset_frame();
    scritta[6].seleziona();
    scritta[6].grassetto();
    scritta[2].seleziona();
    scritta[2].grassetto();
    OPERAZIONE_CORRENTE="RY<--"+strings[15];
    break;
  case 19:
    reset_frame();
    scritta[6].seleziona();
    scritta[6].grassetto();
    linea[11].seleziona();
    linea[11].grassetto();
    OPERAZIONE_CORRENTE="RY<--"+strings[15];
    break;
  case 20:
    reset_frame();
    scritta[6].seleziona();
    scritta[6].grassetto();
    mult[1].sel_entrate(1);
    mult[1].grass_entrate(1);
    OPERAZIONE_CORRENTE=strings[42];//OPERAZIONE_CORRENTE="Selezione ingresso 1 MuxY";
    break;
  case 21:
    reset_frame();
    scritta[6].seleziona();
    scritta[6].grassetto();
    mult[1].seleziona();
    mult[1].grassetto();
    OPERAZIONE_CORRENTE=strings[42];//OPERAZIONE_CORRENTE="Selezione ingresso 1 MuxY";
    break;
  case 22:
    reset_frame();
    scritta[6].seleziona();
    scritta[6].grassetto();
    linea[13].seleziona();
    linea[13].grassetto();
    OPERAZIONE_CORRENTE="RY"+"<--"+strings[15];
    break;
  case 23:
    reset_frame();
    scritta[6].seleziona();
    scritta[6].grassetto();
    reg[4].seleziona();
    reg[4].grassetto();
    OPERAZIONE_CORRENTE="RY"+"<--"+strings[15];
    break;
  case 24:
    reset_frame();
    linea[17].seleziona();
    linea[17].grassetto();
    linea[14].seleziona();
    linea[14].grassetto();
    OPERAZIONE_CORRENTE=parametri[0]+"<--[RY]";
    break;
  case 25:
    reset_frame();
    banco.sel_entrate();
    banco.grass_entrate();
    OPERAZIONE_CORRENTE=parametri[0]+"<--[RY]";
    break;
  case 26:
    reset_frame();
    banco.seleziona();
    banco.grassetto();
    OPERAZIONE_CORRENTE=parametri[0]+"<--[RY]";
    timer_anim.stop();
    finale=true;
    break;
  default:
    /*timer_anim.stop();
    finale=true;*/
    //if (frame>0)
    //{
      //timer_anim.stop();
      //finale=true;
      
      //frame=0;
      //fig[0].deseleziona();
      //des_tutti(fig);
      //play=false;
      //if (frame > n_frame) frame=n_frame;
      //timer_anim.stop();
      frame=0;
    //}
    //else frame=0;
  }
}
public class Multiplatore extends Trapezio
{
  private Scritta[] entrate;
  
  public Multiplatore(int col_des,int col_sel,int col_fig_cont,int n_entrate,int col_des_scritta,int col_sel_scritta)
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
protected class Oggetto
{
  private int colore_deselezionato;
  private int colore_selezionato;
  private int colore;
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
  public Oggetto(int col_des,int col_sel)
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
  public int get_colore(){return colore;}
  public boolean is_selezionato(){return selezionato;};
  public void cambia_colore()
  {
    if (selezionato) deseleziona();
    else seleziona();
  };
}
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
  public Registro(int col_des,int col_sel,int col_fig_cont)
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
protected class Rettangolo extends Figura
{
  public Rettangolo(int col_des,int col_sel,int col_fig_cont)
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
public class Scritta extends Oggetto
{
  private String nome;
  private PFont font;
  public Scritta(int col_des,int col_sel)
  {
    super(col_des,col_sel);
    font=createFont("Arial",12,true);
    //no_grassetto();
  };
  public void set_nome(String s){nome=s;};
  public void disegna(int x,int y,int larg,int alt)
  {
    super.disegna();
    //rectMode(CENTER);
    //textAlign(CENTER,BOTTOM);
    //pushMatrix();
    //fill(colore);    
    textFont(font);
    text(nome,x,y,larg,alt);
    //popMatrix();
  };
  public void grassetto()
  {
    font=createFont("Arial Black",12);
  };
  public void no_grassetto()
  {
    font=createFont("Arial",12);
  };
}

///* =========================================================
// * ====                   WARNING                        ===
// * =========================================================
// * The code in this tab has been generated from the GUI form
// * designer and care should be taken when editing this file.
// * Only add/edit code inside the event handlers i.e. only
// * use lines between the matching comment tags. e.g.
 
// void myBtnEvents(GButton button) { //_CODE_:button1:12356:
// // It is safe to enter your event code here  
// } //_CODE_:button1:12356:
 
// * Do not rename this tab!
// * =========================================================
// */

//public void pnl_comandi_click(GPanel source, GEvent event) { //_CODE_:pnl_comandi:486289:
//  println("panel1 - GPanel >> GEvent." + event + " @ " + millis());
//} //_CODE_:pnl_comandi:486289:

//public void btn_store_click(GButton source, GEvent event) { //_CODE_:btn_store:723271:
//  println("button1 - GButton >> GEvent." + event + " @ " + millis());
//} //_CODE_:btn_store:723271:

//public void btn_load_click(GButton source, GEvent event) { //_CODE_:btn_load:393455:
//  println("button2 - GButton >> GEvent." + event + " @ " + millis());
//  //comando=Comando.LOAD;
//  comando=Comando.LOAD;
//  timer_anim.stop();
//  frame=0;
//  finale=false;
//} //_CODE_:btn_load:393455:

//public void btn_add_click(GButton source, GEvent event) { //_CODE_:btn_add:553967:
//  println("button3 - GButton >> GEvent." + event + " @ " + millis());
//} //_CODE_:btn_add:553967:

//public void btn_en_lang_click(GImageButton source, GEvent event) { //_CODE_:btn_en_lang:744671:
//  println("btn_en_lang - GImageButton >> GEvent." + event + " @ " + millis());
//  lingua=true;
//} //_CODE_:btn_en_lang:744671:

//public void btn_it_lang_click(GImageButton source, GEvent event) { //_CODE_:btn_it_lang:904538:
//  println("btn_it_lang - GImageButton >> GEvent." + event + " @ " + millis());
//  lingua=false;
//} //_CODE_:btn_it_lang:904538:

//public void pnl_pannello_click(GPanel source, GEvent event) { //_CODE_:pnl_pannello:696196:
//  println("panel2 - GPanel >> GEvent." + event + " @ " + millis());
//} //_CODE_:pnl_pannello:696196:

//public void pnl_prova_click(GPanel source, GEvent event) { //_CODE_:pnl_prova:300082:
//  println("panel3 - GPanel >> GEvent." + event + " @ " + millis());
//} //_CODE_:pnl_prova:300082:

//public void btn_play_click(GImageButton source, GEvent event) { //_CODE_:btn_play:442785:
//  println("imgButton1 - GImageButton >> GEvent." + event + " @ " + millis());
//  play=true;
//  timer_anim.start();
//} //_CODE_:btn_play:442785:

//public void btn_pause_click(GImageButton source, GEvent event) { //_CODE_:btn_pause:284553:
//  println("imgButton1 - GImageButton >> GEvent." + event + " @ " + millis());
//  //play=false;
//  timer_anim.stop();
//} //_CODE_:btn_pause:284553:

//public void btn_forward_click(GImageButton source, GEvent event) { //_CODE_:btn_forward:505491:
//  println("imgButton2 - GImageButton >> GEvent." + event + " @ " + millis());
//  if (!timer_anim.isRunning() && (!istruzione.equals(0)) && !finale)
//  {
//    frame++;
//    reset_frame();
//  }
//} //_CODE_:btn_forward:505491:

//public void btn_rewind_click(GImageButton source, GEvent event) { //_CODE_:btn_rewind:407228:
//  println("imgButton3 - GImageButton >> GEvent." + event + " @ " + millis());
//  if (!timer_anim.isRunning() && (comando!=Comando.NULL) && frame!=0)
//  {
//    frame--;
//    if (finale) {
//      finale=false;
//    }
//    reset_frame();
//  }
//} //_CODE_:btn_rewind:407228:

//public void btn_stop_click(GImageButton source, GEvent event) { //_CODE_:btn_stop:736460:
//  println("imgButton1 - GImageButton >> GEvent." + event + " @ " + millis());
//  frame=0;
//  reset_frame();
//  timer_anim.stop();
//  finale=false;
//} //_CODE_:btn_stop:736460:

//public void pnl_input_click(GPanel source, GEvent event) { //_CODE_:pnl_input:741392:
//  println("output - GPanel >> GEvent." + event + " @ " + millis());
//} //_CODE_:pnl_input:741392:

//public void txt_enter(GTextField source, GEvent event) { //_CODE_:txt:616830:
//  String string=new String();
//  String[] commands;
//  String[] temp;
//  println("txt - GTextField >> GEvent." + event + " @ " + millis());
//  if (event==GEvent.ENTERED)
//  {
//    string=source.getText();
//    OPERAZIONE_CORRENTE="Null";
//  for (int i =0;i<parametri.length;i++)
//  parametri[i]="Null";
//  }
//  commands=splitTokens(string, ", ");
  

//  /*if (commands[0].equals("LOAD") || commands[0].equals("load"))
//   {
//   if (!(parametri[0].equals(NON_CORRETTO) || parametri[0].equals(NON_INSERITO)) && !(parametri[1].equals(NON_CORRETTO) || parametri[1].equals(NON_CORRETTO)) && !parametri[2].equals(NON_CORRETTO))
//   comando=Comando.LOAD;
//   else comando=Comando.NULL;
//   } else if (istruzione.equals("STORE") || istruzione.equals("store") || istruzione.equals("Store"))
//   {
//   if (!(parametri[0].equals(NON_CORRETTO) || parametri[0].equals(NON_INSERITO)) && !(parametri[1].equals(NON_INSERITO) || parametri[1].equals(NON_CORRETTO)) && !parametri[2].equals(NON_CORRETTO))
//   comando=Comando.STORE;
//   else comando=Comando.NULL;
//   } else if (istruzione.equals("ADD") || istruzione.equals("add") || istruzione.equals("Add")) {
//   if (!(parametri[0].equals(NON_CORRETTO) || parametri[0].equals(NON_INSERITO)) && !(parametri[1].equals(NON_CORRETTO) || parametri[1].equals(NON_INSERITO)) && !parametri[2].equals(NON_CORRETTO))
//   comando=Comando.ADD;
//   else comando=Comando.NULL;
//   } else comando=Comando.NULL;*/

//  for (int i=0; i<commands.length; i++) 
//  {
//    if (i==0)
//    {
//      istruzione=commands[i];
//      //println("Istruzione="+istruzione);
//    } else
//    {
//      if (istruzione.equals("LOAD") || istruzione.equals("load"))
//      {
//        switch (i)
//        {
//        case 1:
//          if (match(commands[i], "r|R\\d+")==null) 
//          {
//            parametri[i-1]=NON_CORRETTO;
//          } else
//          {
//            parametri[i-1]=commands[i];
//          } 
//          break;
//        case 2:
//          if (match(commands[i], "\\d+{1}\\(((r|R)\\d+){1}\\)")!=null) 
//          {
//            //parametri[i-1]=commands[i];
//            temp=splitTokens(commands[i], "() ");
//            //parametri[i]=temp[0];
//            /*for (int j=0;j<temp.length;j++)
//             parametri[i-1]=temp[j];*/
//            parametri[i-1]=temp[1];
//            parametri[i]=temp[0];
//          } else if (match(commands[i], "{1}\\(((r|R)\\d+){1}\\)")!=null)
//          {
//            temp=splitTokens(commands[i], "() ");
//            parametri[i-1]=temp[0];
//          } else if (match(commands[i], "(r|R)\\d+")!=null)
//          {
//            parametri[i-1]=commands[i];
//          } else
//          {
//            parametri[i-1]=NON_CORRETTO;
//          } 
//          break;
//        case 3:
//          if (match(commands[i], "#\\d+")==null) 
//          {
//            parametri[i-1]=NON_CORRETTO;
//          } else
//          {
//            temp=splitTokens(commands[i], "#");
//            parametri[i-1]=temp[0];
//          } 
//          break;
//        }
//      }
//      else if (istruzione.equals("STORE") || istruzione.equals("store"))
//      {
//        switch (i)
//        {
//        case 1:
//          if (match(commands[i], "r|R\\d+")==null) 
//          {
//            parametri[i-1]=NON_CORRETTO;
//          } else
//          {
//            parametri[i-1]=commands[i];
//          } 
//          break;
//        case 2:
//          if (match(commands[i], "\\d+{1}\\(((r|R)\\d+){1}\\)")!=null) 
//          {
//            //parametri[i-1]=commands[i];
//            temp=splitTokens(commands[i], "() ");
//            //parametri[i]=temp[0];
//            /*for (int j=0;j<temp.length;j++)
//             parametri[i-1]=temp[j];*/
//            parametri[i-1]=temp[1];
//            parametri[i]=temp[0];
//          } else if (match(commands[i], "{1}\\(((r|R)\\d+){1}\\)")!=null)
//          {
//            temp=splitTokens(commands[i], "() ");
//            parametri[i-1]=temp[0];
//          } else if (match(commands[i], "(r|R)\\d+")!=null)
//          {
//            parametri[i-1]=commands[i];
//          } else
//          {
//            parametri[i-1]=NON_CORRETTO;
//          } 
//          break;
//        case 3:
//            if (match(commands[i], "#\\d+")==null) 
//          {
//            parametri[i-1]=NON_CORRETTO;
//          } else
//          {
//            temp=splitTokens(commands[i], "#");
//            parametri[i-1]=temp[0];
//          } 
//          break;
//        }
//      }
//      if (istruzione.equals("ADD") || istruzione.equals("add"))
//      {
//        switch (i)
//        {
//        case 1:
//          if (match(commands[i], "r|R\\d+")==null) 
//          {
//            parametri[i-1]=NON_CORRETTO;
//          } else
//          {
//            parametri[i-1]=commands[i];
//          } 
//          break;
//        case 2:
//         if (match(commands[i], "r|R\\d+")==null) 
//          {
//            parametri[i-1]=NON_CORRETTO;
//          } else
//          {
//            parametri[i-1]=commands[i];
//          }
//        case 3:
//          if (match(commands[i], "#\\d+")!=null) 
//          {
            
//            temp=splitTokens(commands[i], "#");
//            parametri[i-1]=temp[0];
//          }
//          else if(match(commands[i], "r|R\\d+")!=null)
//          {
//            parametri[i-1]=commands[i];
//          }
//          else
//          {
//            parametri[i-1]=NON_CORRETTO;
//          } 
//          break;
//        }
//      }
//      //println("parametro n."+i+"="+parametri[i-1]);
//    }
//  }
//  //for (int i=0;i<parametri.length;i++)
//  //println("parametro n."+i+"="+parametri[i]);
//  //label1.setText("parametro n."+i+"="+parametri[i]);
//  /*if (commands.length >= 2)
//   {
//   if(match(commands[1],"R\\d+")==null) {parametri[0]="Non corretto";}
//   }
//   if (commands.length >= 3)
//   {
//   if(match(commands[2],"\\d+{1}\\(((r|R)\\d+){1}\\)")==null) {parametri[1]="Non corretto";}
//   }
//   if (parametri.length==4)
//   {
//   if(match(parametri[3],"#\\d+")==null) {parametri[2]="Non corretto";}
//   else ;
//   }*/
//} //_CODE_:txt:616830:

//public void pnl_output_click(GPanel source, GEvent event) { //_CODE_:pnl_output:747906:
//  println("pnl_output - GPanel >> GEvent." + event + " @ " + millis());
//} //_CODE_:pnl_output:747906:

//public void timer_anim_action(GTimer source) { //_CODE_:timer_anim:563156:
//  println("timer_anim - GTimer >> an event occured @ " + millis());
//  //frame++;
//  if (!finale)
//  {
//    frame++;
//    //des_tutti(reg);
//  }
//} //_CODE_:timer_anim:563156:



//// Create all the GUI controls. 
//// autogenerated do not edit
//public void createGUI() {
//  G4P.messagesEnabled(false);
//  G4P.setGlobalColorScheme(GCScheme.BLUE_SCHEME);
//  G4P.setCursor(ARROW);
//  surface.setTitle("Progetto");
//  pnl_comandi = new GPanel(this, 419, 0, 179, 55, "LINGUA");
//  pnl_comandi.setDraggable(false);
//  pnl_comandi.setText("LINGUA");
//  pnl_comandi.setTextBold();
//  pnl_comandi.setTextItalic();
//  pnl_comandi.setOpaque(true);
//  pnl_comandi.addEventHandler(this, "pnl_comandi_click");
//  btn_store = new GButton(this, 2, 28, 80, 30);
//  btn_store.setText("STORE");
//  btn_store.addEventHandler(this, "btn_store_click");
//  btn_load = new GButton(this, 2, 59, 80, 30);
//  btn_load.setText("LOAD");
//  btn_load.addEventHandler(this, "btn_load_click");
//  btn_add = new GButton(this, 2, 91, 80, 30);
//  btn_add.setText("ADD");
//  btn_add.addEventHandler(this, "btn_add_click");
//  btn_en_lang = new GImageButton(this, 107, 25, new String[] { "enFlag.png", "enFlag.png", "enFlag.png" } );
//  btn_en_lang.addEventHandler(this, "btn_en_lang_click");
//  btn_it_lang = new GImageButton(this, 32, 25, new String[] { "itFlag.png", "itFlag.png", "itFlag.png" } );
//  btn_it_lang.addEventHandler(this, "btn_it_lang_click");
//  pnl_comandi.addControl(btn_store);
//  pnl_comandi.addControl(btn_load);
//  pnl_comandi.addControl(btn_add);
//  pnl_comandi.addControl(btn_en_lang);
//  pnl_comandi.addControl(btn_it_lang);
//  pnl_pannello = new GPanel(this, 0, 0, 418, 20, "COMANDI");
//  pnl_pannello.setCollapsible(false);
//  pnl_pannello.setDraggable(false);
//  pnl_pannello.setText(strings[23]);
//  pnl_pannello.setTextBold();
//  pnl_pannello.setTextItalic();
//  pnl_pannello.setOpaque(true);
//  pnl_pannello.addEventHandler(this, "pnl_pannello_click");
//  pnl_prova = new GPanel(this, 419, 55, 179, 94, "CONTROLLI");
//  pnl_prova.setText(strings[25]);
//  pnl_prova.setTextBold();
//  pnl_prova.setTextItalic();
//  pnl_prova.setOpaque(true);
//  pnl_prova.addEventHandler(this, "pnl_prova_click");
//  btn_play = new GImageButton(this, 80, 40, 30, 30, new String[] { "imageedit_5_2662122022 [www.imagesplitter.net]-0-0.gif", "imageedit_5_2662122022 [www.imagesplitter.net]-0-0.gif", "imageedit_5_2662122022 [www.imagesplitter.net]-0-0.gif" } );
//  btn_play.addEventHandler(this, "btn_play_click");
//  btn_pause = new GImageButton(this, 50, 40, 30, 30, new String[] { "imageedit_5_2662122022 [www.imagesplitter.net]-1-0.gif", "imageedit_5_2662122022 [www.imagesplitter.net]-1-0.gif", "imageedit_5_2662122022 [www.imagesplitter.net]-1-0.gif" } );
//  btn_pause.addEventHandler(this, "btn_pause_click");
//  btn_forward = new GImageButton(this, 140, 40, 30, 30, new String[] { "imageedit_5_2662122022 [www.imagesplitter.net]-2-2.gif", "imageedit_5_2662122022 [www.imagesplitter.net]-2-2.gif", "imageedit_5_2662122022 [www.imagesplitter.net]-2-2.gif" } );
//  btn_forward.addEventHandler(this, "btn_forward_click");
//  btn_rewind = new GImageButton(this, 20, 40, 30, 30, new String[] { "imageedit_5_2662122022 [www.imagesplitter.net]-2-1.gif", "imageedit_5_2662122022 [www.imagesplitter.net]-2-1.gif", "imageedit_5_2662122022 [www.imagesplitter.net]-2-1.gif" } );
//  btn_rewind.addEventHandler(this, "btn_rewind_click");
//  btn_stop = new GImageButton(this, 110, 40, 30, 30, new String[] { "imageedit_5_2662122022 [www.imagesplitter.net]-2-0.gif", "imageedit_5_2662122022 [www.imagesplitter.net]-2-0.gif", "imageedit_5_2662122022 [www.imagesplitter.net]-2-0.gif" } );
//  btn_stop.addEventHandler(this, "btn_stop_click");
//  pnl_prova.addControl(btn_play);
//  pnl_prova.addControl(btn_pause);
//  pnl_prova.addControl(btn_forward);
//  pnl_prova.addControl(btn_rewind);
//  pnl_prova.addControl(btn_stop);
//  pnl_input = new GPanel(this, 420, 150, 179, 147, "INPUT");
//  pnl_input.setCollapsible(false);
//  pnl_input.setDraggable(false);
//  pnl_input.setText("INPUT");
//  pnl_input.setTextBold();
//  pnl_input.setTextItalic();
//  pnl_input.setOpaque(true);
//  pnl_input.addEventHandler(this, "pnl_input_click");
//  txt = new GTextField(this, 9, 58, 160, 30, G4P.SCROLLBARS_NONE);
//  txt.setPromptText(strings[27]);
//  txt.setOpaque(true);
//  txt.addEventHandler(this, "txt_enter");
//  pnl_input.addControl(txt);
//  pnl_output = new GPanel(this, 420, 299, 179, 190, "OUTPUT");
//  pnl_output.setText("OUTPUT");
//  pnl_output.setTextBold();
//  pnl_output.setTextItalic();
//  pnl_output.setOpaque(true);
//  pnl_output.addEventHandler(this, "pnl_output_click");
//  label_parametro1 = new GLabel(this, 2, 23, 150, 20);
//  label_parametro1.setText(strings[19]+NON_INSERITO);
//  label_parametro1.setTextBold();
//  label_parametro1.setTextItalic();
//  label_parametro1.setOpaque(false);
//  label_parametro2 = new GLabel(this, 2, 53, 150, 20);
//  label_parametro2.setText(strings[20]+NON_INSERITO);
//  label_parametro2.setTextBold();
//  label_parametro2.setTextItalic();
//  label_parametro2.setOpaque(false);
//  label_parametro3 = new GLabel(this, 1, 83, 150, 20);
//  label_parametro3.setText(strings[21]+NON_INSERITO);
//  label_parametro3.setTextBold();
//  label_parametro3.setTextItalic();
//  label_parametro3.setOpaque(false);
//  label_corrente = new GLabel(this, 2, 115, 177, 68);
//  label_corrente.setTextAlign(GAlign.CENTER, GAlign.TOP);
//  label_corrente.setText(strings[26]);
//  label_corrente.setTextBold();
//  label_corrente.setOpaque(false);
//  pnl_output.addControl(label_parametro1);
//  pnl_output.addControl(label_parametro2);
//  pnl_output.addControl(label_parametro3);
//  pnl_output.addControl(label_corrente);
//  timer_anim = new GTimer(this, this, "timer_anim_action", 1000);
//}

//// Variable declarations 
//// autogenerated do not edit
//GPanel pnl_comandi; 
//GButton btn_store; 
//GButton btn_load; 
//GButton btn_add; 
//GImageButton btn_en_lang; 
//GImageButton btn_it_lang; 
//GPanel pnl_pannello; 
//GPanel pnl_prova; 
//GImageButton btn_play; 
//GImageButton btn_pause; 
//GImageButton btn_forward; 
//GImageButton btn_rewind; 
//GImageButton btn_stop; 
//GPanel pnl_input; 
//GTextField txt; 
//GPanel pnl_output; 
//GLabel label_parametro1; 
//GLabel label_parametro2; 
//GLabel label_parametro3; 
//GLabel label_corrente; 
//GTimer timer_anim; 
public void store()
{
  switch (frame)
  {
  case 0:
    reset_frame();
    scritta[4].seleziona();
    scritta[4].grassetto();
    scritta[5].seleziona();
    scritta[5].grassetto();
    OPERAZIONE_CORRENTE=strings[35];//OPERAZIONE_CORRENTE="Impostazione indirizzi del banco";
    break;
  case 1:
    reset_frame();
    linea[15].seleziona();
    linea[15].grassetto();
    linea[16].seleziona();
    linea[16].grassetto();
    OPERAZIONE_CORRENTE=strings[35];//OPERAZIONE_CORRENTE="Impostazione indirizzi del banco";
    break;
  case 2:
    reset_frame();
    banco.seleziona();
    banco.grassetto();
    OPERAZIONE_CORRENTE=strings[36];//OPERAZIONE_CORRENTE="Lettura registri del banco";
    break;
  case 3:
    reset_frame();
    banco.sel_uscite(0);
    banco.grass_uscite(0);
    banco.sel_uscite(1);
    banco.grass_uscite(1);
    OPERAZIONE_CORRENTE="RA<--["+parametri[1]+"]"+"\n"+"RB<--"+parametri[0];
    break;
  case 4:
    reset_frame();
    linea[0].seleziona();
    linea[0].grassetto();
    linea[1].seleziona();
    linea[1].grassetto();
    OPERAZIONE_CORRENTE="RA<--["+parametri[1]+"]"+"\n"+"RB<--"+parametri[0];
    break;
  case 5:
    reset_frame();
    reg[0].seleziona();
    reg[0].grassetto();
    reg[1].seleziona();
    reg[1].grassetto();
    OPERAZIONE_CORRENTE="RA<--["+parametri[1]+"]"+"\n"+"RB<--"+parametri[0];
    break;
  case 6:
    reset_frame();
    //linea[2].seleziona();
    //linea[2].grassetto();
    
    //linea[3].seleziona();
    
    //scritta[0].seleziona();
    //scritta[0].grassetto();
    linea[4].seleziona();
    linea[4].grassetto();
    OPERAZIONE_CORRENTE="RM<--[RB]";
    break;
  case 7:
    reset_frame();
    //linea[5].seleziona();
    //linea[5].grassetto();
    
    //mult[0].sel_entrate(1);
    //mult[0].grass_entrate(1);
    
    reg[3].seleziona();
    reg[3].grassetto();
    OPERAZIONE_CORRENTE="RM<--[RB]";
    break;
  case 8:
    reset_frame();
    //linea[2].seleziona();
    //linea[2].grassetto();
    
    //linea[3].seleziona();
    
    scritta[0].seleziona();
    scritta[0].grassetto();
    OPERAZIONE_CORRENTE=strings[37];//OPERAZIONE_CORRENTE="Selezione ingressi MuXB";
    break;
   case 9:
    reset_frame();
    linea[5].seleziona();
    linea[5].grassetto();
    OPERAZIONE_CORRENTE=strings[37];//OPERAZIONE_CORRENTE="Selezione ingressi MuXB";
   break;
    case 10:
    reset_frame();
    mult[0].sel_entrate(1);
    mult[0].grass_entrate(1);
    OPERAZIONE_CORRENTE=strings[39];//OPERAZIONE_CORRENTE="Selezione ingresso 1 MuXB";
    break; 
  case 11:
    reset_frame();
    mult[0].seleziona();
    mult[0].grassetto();
    OPERAZIONE_CORRENTE=strings[39];//OPERAZIONE_CORRENTE="Selezione ingresso 1 MuXB";
    break;
  case 12:
    reset_frame();
    linea[2].seleziona();
    linea[2].grassetto();
    linea[6].seleziona();
    linea[6].grassetto();
    OPERAZIONE_CORRENTE=strings[40];//OPERAZIONE_CORRENTE="Impostazione ingressi Alu";
    break;
  case 13:
    reset_frame();
    alu.sel_entrate(0);
    alu.grass_entrate(0);
    alu.sel_entrate(1);
    alu.grass_entrate(1);
    OPERAZIONE_CORRENTE=strings[40];//OPERAZIONE_CORRENTE="Impostazione ingressi Alu";
    break;
  case 14:
    reset_frame();
    alu.seleziona();
    alu.grassetto();
    OPERAZIONE_CORRENTE="[RA]+"+parametri[2];
    break;
  case 15:
    reset_frame();
    alu.sel_uscite();
    alu.grass_uscite();
    OPERAZIONE_CORRENTE="RZ<--"+"[RA]+"+parametri[2];
    break;
  case 16:
    reset_frame();
    linea[7].seleziona();
    linea[7].grassetto();
    OPERAZIONE_CORRENTE="RZ<--"+"[RA]+"+parametri[2];
    break;
  case 17:
    reset_frame();
    reg[2].seleziona();
    reg[2].grassetto();
    OPERAZIONE_CORRENTE="RZ<--"+"[RA]+"+parametri[2];
    break;
  case 18:
    reset_frame();
    linea[9].seleziona();
    linea[9].grassetto();
    linea[10].seleziona();
    linea[10].grassetto();
    OPERAZIONE_CORRENTE=strings[14]+"<--RZ"+"\n"+strings[15]+"<--RM";
    break;
  case 19:
    reset_frame();
    scritta[1].seleziona();
    scritta[1].grassetto();
    scritta[2].seleziona();
    scritta[2].grassetto();
    OPERAZIONE_CORRENTE=strings[14]+"<--RZ"+"\n"+strings[15]+"<--RM";
    timer_anim.stop();
    finale=true;
    break;
  default:    
    frame=0;
  }
}
//PShape forma;

public class Trapezio extends Figura
{
  //PShape forma;
  public Trapezio(int col_des,int col_sel,int col_fig_cont)
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
  public void settings() {  size(600,600); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "dps" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
