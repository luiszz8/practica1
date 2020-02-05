/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica1_compiladores;

/**
 *
 * @author Luis
 */
public class Token {
    
    public enum Tipo{
        ID,
        Llaves_apertura,
        Llaves_cerrar,
        //Parentesis_apertura,
        //Parentesis_cerrar,
        Cadena,
        Punto_Coma,
        Numero,
        Coma,
        Dos_Puntos,
        //Igual,
        //Corchete_apertura,
        //Corchete_cerrar,
        //Negrita,
        //Subrayado,
        Palabra_reservada,
        Asignacion,
        Porcentual,
        Concatenar,
        Disyuncion,
        Interrogacion,
        Asterisco,
        Mas,
        Grupo,
        Signo,
        Error
    }
    private Tipo tipoToken;
    private String valor;
    int fi, co;
    public Token(Tipo tipo, String auxLex, int fila, int columna)
        {
            this.tipoToken = tipo;
            this.valor = auxLex;
            this.fi = fila;
            this.co = columna;
        }
        public String getValor()
        {
            return valor;
        }                                                                              
        public int getfi()
        {
            return fi;
        }
        public int getco()
        {
            return co;
        }
        public String getTipoEnString()
        {
            switch (tipoToken)
            {
                case Palabra_reservada:
                    return "Palabra reservada";
                case Numero:
                    return "Numero";
                case Cadena:
                    return "Cadena";
                case Dos_Puntos:
                    return "Dos Puntos";
                case Punto_Coma:
                    return "Punto y coma";
                case Llaves_apertura:
                    return "Llaves abrir";
                case Llaves_cerrar:
                    return "Llaves cerrar";
                case Coma:
                    return "Coma";
                case ID:
                    return "ID";
                case Error:
                    return "Simbolo desconocido";
                case Asignacion:
                    return "asignacion";
                case Porcentual:
                    return "porcentual";
                case Concatenar:
                    return "Concatenar";
                case Disyuncion:
                    return "Disyuncion";
                case Interrogacion:
                    return "Interrogacion";
                case Asterisco:
                    return "Asterisco";
                case Mas:
                    return "Mas";
                case Grupo:
                    return "Conjunto";
                case Signo:
                    return "Signo";
                default:
                    return "";
            }
        }
}
