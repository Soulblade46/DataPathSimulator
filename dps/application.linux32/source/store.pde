void store()
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