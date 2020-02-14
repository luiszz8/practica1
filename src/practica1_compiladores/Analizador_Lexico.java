/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica1_compiladores;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Luis
 */
public class Analizador_Lexico {
    private List<Token> salida;
    public List<Token> errorres;
    private int estado;
    private String auxLex;
    int contador_Token;
    public List<Token> escanear(String entrada){
        salida = new ArrayList<>();
        errorres = new ArrayList<>();
        estado = 0;
        contador_Token = 0;
        auxLex = "";
        //Boolean comen = false;
        Boolean punto = false; 
        //String auxN = "";
        char c;
        String[] cadaLinea = entrada.split("\n");
        System.out.println(cadaLinea.length);
        String toca = "";
        for (int i = 0; i < entrada.split("\n").length; i++) {
            toca = cadaLinea[i];
            contador_Token = 0;
            if(estado==7){
                estado=0;
            }
            for (int j = 0; j < toca.length(); j++) {
                c = toca.charAt(j);
                switch (estado) {
                    case 0:
                        if (letra(c))//letra o guion
                        {
                            estado = 1;
                            auxLex = auxLex + c;
                        } else if (c == 34 | c == '”')// cadena
                        {
                            estado = 3;
                            auxLex = auxLex + c;
                        } else if (c == 58 | c == 123 | c == 125 | c == 44 | c == 37 | c == 59 | c == 46 | c == 124 | c == 63 | c == 42)//varios
                        {
                            if (c == 58) {
                                salida.add(new Token(Token.Tipo.Dos_Puntos, c + "", i, contador_Token));
                            } else if (c == 123) {
                                salida.add(new Token(Token.Tipo.Llaves_apertura, c + "", i, contador_Token));
                            } else if (c == 125) {
                                salida.add(new Token(Token.Tipo.Llaves_cerrar, c + "", i, contador_Token));
                            } else if (c == 44) {
                                salida.add(new Token(Token.Tipo.Coma, c + "", i, contador_Token));
                            } else if (c == 37) {
                                salida.add(new Token(Token.Tipo.Porcentual, c + "", i, contador_Token));
                            } else if (c == 59) {
                                salida.add(new Token(Token.Tipo.Punto_Coma, c + "", i, contador_Token));
                            } else if (c == 46) {
                                salida.add(new Token(Token.Tipo.Concatenar, c + "", i, contador_Token));
                            } else if (c == 124) {
                                salida.add(new Token(Token.Tipo.Disyuncion, c + "", i, contador_Token));
                            } else if (c == 63) {
                                salida.add(new Token(Token.Tipo.Interrogacion, c + "", i, contador_Token));
                            } else if (c == 42) {
                                salida.add(new Token(Token.Tipo.Asterisco, c + "", i, contador_Token));
                            } else if (c == 43) {
                                salida.add(new Token(Token.Tipo.Mas, c + "", i, contador_Token));
                            }
                            contador_Token++;
                        } else if (digito(c))//digito
                        {
                            estado = 2;
                            auxLex = auxLex + c;
                            punto = true;
                        }else if(c == 45){
                            estado = 4;
                            auxLex = auxLex + c ;
                        } 
                        else if (c == 47) {
                            estado = 6;
                        }
                        else if (c==60) {
                            estado=5;
                            auxLex = auxLex + c;
                        }else if (c>31 && 126<c) {
                            auxLex = auxLex + c;
                            contador_Token++;
                            salida.add(new Token(Token.Tipo.Signo, auxLex, i, contador_Token));
                            auxLex="";
                        }
                        else if (c != 32 & c != 10) {
                            errorres.add(new Token(Token.Tipo.Error, c + "", i, contador_Token));
                            contador_Token++;
                        }
                        break;
                    case 1:
                        if (letra(c) | c == 95 | digito(c) | c == 'ñ' | c == 'Ñ' | c==44)//prueba si es una letra o si es guion bajo
                        {
                            estado = 1;
                            auxLex = auxLex + c;
                            if (c==44) {
                                estado=10;
                            }
                        }else if (c==126) {
                            auxLex=auxLex+c;
                            estado =11;
                        }  else {
                            if (auxLex.toLowerCase().equals("conj")) {
                                salida.add(new Token(Token.Tipo.Palabra_reservada, auxLex, i, contador_Token));
                                contador_Token++;
                                j -= 1;
                                estado = 0;
                                auxLex = "";
                            } else {
                                salida.add(new Token(Token.Tipo.ID, auxLex, i, contador_Token));
                                contador_Token++;
                                j -= 1;
                                estado = 0;
                                auxLex = "";
                            }

                        }
                        break;
                    case 2:
                        if (digito(c))//prueba si es digito 
                        {
                            estado = 2;
                            auxLex = auxLex + c;
                        }else if (c==126) {
                            auxLex=auxLex+c;
                            estado=11;
                        }  
                        else if (c == 46 && punto == true) {
                            estado = 2;
                            auxLex = auxLex + c;
                            punto = false;
                        } else {

                            salida.add(new Token(Token.Tipo.Numero, auxLex, i, contador_Token));
                            contador_Token++;
                            j -= 1;
                            estado = 0;
                            auxLex = "";
                        }
                        break;
                    case 3:
                        if (c == 34 | c == '”') {
                            auxLex = auxLex + c;
                            salida.add(new Token(Token.Tipo.Cadena, auxLex, i, contador_Token));
                            contador_Token++;
                            estado = 0;
                            auxLex = "";
                        } else if(c==126){
                            salida.add(new Token(Token.Tipo.Signo, auxLex, i, contador_Token));
                            auxLex = "" + c;
                            contador_Token++;
                            salida.add(new Token(Token.Tipo.Virgulilla, auxLex, i, contador_Token));
                            estado=0;
                        }
                        else {
                            estado = 3;
                            auxLex = auxLex + c;
                        }
                        break;
                    case 4:
                        if (c==62) {
                            auxLex = auxLex + c;
                            contador_Token++;
                            salida.add(new Token(Token.Tipo.Asignacion, auxLex, i, contador_Token));
                            auxLex="";
                            estado=0;
                        }else{
                            salida.add(new Token(Token.Tipo.Signo, auxLex, i, contador_Token));
                            estado=0;
                            j--;
                        }
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
                        break;
                    case 5:
                        if (c==33) {
                            estado=8;
                            auxLex="";
                        }else{
                            contador_Token++;
                            salida.add(new Token(Token.Tipo.Signo, auxLex, i, contador_Token));
                            auxLex="";
                            j--;
                        }
                        break;
                    case 6:
                        if (c == 47) {
                            estado = 7;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            
                        } else {
                            salida.add(new Token(Token.Tipo.Signo, auxLex, i, contador_Token));
                            contador_Token++;
                            j--;
                            estado = 0;
                        } 
                        break;
                    case 7:
                        
                        break;
                    case 8:
                        if (c==33) {
                            estado=9;
                        }
                        break;
                    case 9:
                        if(c==62){
                            estado=0;
                        }else{
                            estado=8; 
                        }
                        break;
                    case 10:
                        if (letra(c) | digito(c) | c == 'ñ' | c == 'Ñ') {
                            auxLex=auxLex+c;
                        }else if (c==44) {
                            auxLex=auxLex+c;
                        }else{
                            salida.add(new Token(Token.Tipo.Conjunto, auxLex, i, contador_Token));
                            contador_Token++;
                            j -= 1;
                            estado = 0;
                            auxLex = "";
                        }
                        break;
                    case 11:
                        if (letra(c) | digito(c) | c == 'ñ' | c == 'Ñ') {
                            auxLex=auxLex+c;
                            salida.add(new Token(Token.Tipo.Conjunto, auxLex, i, contador_Token));
                            contador_Token++;
                            estado=0;
                            auxLex= "";
                        }
                        break;
                    default:
                        return null;
                }
            }
        }
        System.out.println(salida.size());
        return salida;
    }
    public Boolean letra(char caracter)
        {
            Boolean estado = false;
            if (caracter >= 97 & caracter <= 122 | caracter >= 65 & caracter <= 90)
            {
                return true;
            }
            return estado;
        }
        public Boolean mayus(char caracter)
        {
            Boolean estado = false;
            if (caracter >= 65 & caracter <= 90)
            {
                return true;
            }
            return estado;
        }
        public Boolean digito(char caracter)
        {
            Boolean estado = false;
            if (caracter >= 48 & caracter <= 57)
            {
                return true;
            }
            return estado;
        }
}
