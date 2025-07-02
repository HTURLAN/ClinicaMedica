package com.axelT.Medclinic.infra.errores;

public class ValidacionDeIntegridad extends RuntimeException{

    public ValidacionDeIntegridad(String s){
        super(s) ;
    }

}
