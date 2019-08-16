package com.marlonncarvalhosa.mamaeeuquero.model;

import java.util.Calendar;
import java.util.Date;

public class Lance {

    private Calendar dataIncial;
    private Calendar dataFinal;

    public void adicionaAno(){
        dataFinal.add(Calendar.DAY_OF_MONTH, 1);
    }

    public Date getDataFinal(){
        return dataFinal.getTime();
    }
}
