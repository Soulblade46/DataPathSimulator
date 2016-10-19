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
  zoomX=zoomX-0.2;
  zoomY=zoomY-0.2;
  //scale(zoomX,zoomY);
} //_CODE_:btn_zoom_out:997960:

public void btn_zoom_in_click(GImageButton source, GEvent event) { //_CODE_:btn_zoom_in:450024:
  println("btn_zoom_in - GImageButton >> GEvent." + event + " @ " + millis());
  zoomX=zoomX+0.2;
  zoomY=zoomY+0.2;
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