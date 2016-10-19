void load()
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