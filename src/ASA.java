import java.util.List;
import java.util.Stack;

public class ASA implements Parser{
    private int i = 0;
    private boolean hayErrores = false;
    private final List<Token> tokens;
    Stack<Integer> pila = new Stack<>();
    Object sa;//simbolo actual

    public ASA(List<Token> tokens){

        this.tokens = tokens;
    }

    @Override
    public boolean parse() {
        pila.push(0);

        int estadoActual;
        sa = tokens.get(i).tipo;
        //bandera
        boolean b=true;

        while(b){
            estadoActual = pila.peek();
            System.out.println("Estado: " + estadoActual + "\nSimbolo: " + sa + "\n");
            switch (estadoActual){
                case 0:
                    if(sa==TipoToken.SELECT){
                        s(2);
                    }else if(sa == "Q"){
                        s(1);
                    }else{
                        hayErrores=true;
                        b=false;
                    }
                    break;
                case 1:
                    if(sa==TipoToken.EOF){
                        b=false;
                    }else{
                        hayErrores=true;
                        b=false;
                    }
                    break;
                case 2:
                    if(sa==TipoToken.DISTINCT){
                        s(5);
                    }else if(sa==TipoToken.ASTERISCO){
                        s(8);
                    }else if(sa==TipoToken.IDENTIFICADOR){
                        s(11);
                    }else if(sa=="D"){
                        s(3);
                    }else if(sa=="P"){
                        s(6);
                    }else if(sa=="A"){
                        s(9);
                    }else if(sa=="A1"){
                        s(10);
                    }else{
                        hayErrores=true;
                        b=false;
                    }
                    break;
                case 3:
                    if(sa==TipoToken.FROM){
                        s(4);
                    }else{
                        hayErrores=true;
                        b=false;
                    }
                    break;
                case 4:
                    if(sa==TipoToken.IDENTIFICADOR){
                        s(20);
                    }else if(sa=="T"){
                        s(15);
                    }else if(sa=="T1"){
                        s(16);
                    }else{
                        hayErrores=true;
                        b=false;
                    }
                    break;
                case 5:
                    if(sa==TipoToken.ASTERISCO){
                        s(8);
                    }else if(sa==TipoToken.IDENTIFICADOR){
                        s(11);
                    }else if(sa=="P"){
                        s(7);
                    }else if(sa=="A"){
                        s(9);
                    }else if(sa=="A1"){
                        s(10);
                    }else{
                        hayErrores=true;
                        b=false;
                    }
                    break;
                case 6:
                    if(sa==TipoToken.FROM){//Reducir a D->P
                        r(1,"D");
                    }else{
                        hayErrores=true;
                        b=false;
                    }
                    break;
                case 7:
                    if(sa==TipoToken.FROM){
                        r(2, "D");
                    }else{
                        hayErrores=true;
                        b=false;
                    }
                    break;
                case 8:
                    if(sa==TipoToken.FROM){//r4
                        r(1,"P");
                    }else{
                        hayErrores=true;
                        b=false;
                    }
                    break;
                case 9:
                    if(sa==TipoToken.FROM){
                        r(1,"P");
                    }else if(sa==TipoToken.COMA){
                        s(14);
                    }else{
                        hayErrores=true;
                        b=false;
                    }
                    break;
                case 10:
                    if(sa==TipoToken.FROM || sa==TipoToken.COMA){
                        r(1,"A");
                    }else{
                        hayErrores=true;
                        b=false;
                    }
                    break;
                case 11:
                    //Estado de epsilon
                    //Caso .id, que hacer
                    if(sa==TipoToken.PUNTO){
                        s(12);
                    }else if(sa=="A2"){
                        s(23);
                    }else if(sa==TipoToken.EOF || sa==TipoToken.FROM){
                        r(0,"A2");
                    }
                    else{
                        hayErrores=true;
                        b=false;
                    }
                    break;
                case 12:
                    if(sa==TipoToken.IDENTIFICADOR){
                        s(25);
                    }else{
                        hayErrores=true;
                        b=false;
                    }
                    break;
                case 13://Se puede quitar
                    if(sa==TipoToken.FROM || sa==TipoToken.COMA){
                        r(1,"A2");
                    }else{
                        hayErrores=true;
                        b=false;
                    }
                    break;
                case 14:
                    if(sa==TipoToken.IDENTIFICADOR){
                        s(11);
                    }else if(sa=="A1"){
                        s(18);
                    }else{
                        hayErrores=true;
                        b=false;
                    }
                    break;
                case 15:
                    if(sa==TipoToken.COMA){
                        s(17);
                    }else if(sa==TipoToken.EOF){
                        r(4,"Q");
                    }else{
                        hayErrores=true;
                        b=false;
                    }
                    break;
                case 16:
                    if(sa==TipoToken.COMA || sa==TipoToken.EOF){
                        r(1,"T");
                    }else{
                        hayErrores=true;
                        b=false;
                    }
                    break;
                case 17:
                    if(sa==TipoToken.IDENTIFICADOR){
                        s(20);
                    }else if(sa=="T1"){
                        s(19);
                    }else{
                        hayErrores=true;
                        b=false;
                    }
                    break;
                case 18:
                    if(sa==TipoToken.FROM || sa==TipoToken.COMA){
                        r(3,"A");
                    }else{
                        hayErrores=true;
                        b=false;
                    }
                    break;
                case 19:
                    if(sa==TipoToken.COMA || sa==TipoToken.EOF){
                        r(3,"T");
                    }else{
                        hayErrores=true;
                        b=false;
                    }
                    break;
                case 20:
                    if(sa==TipoToken.IDENTIFICADOR){
                        s(21);
                    }else if(sa=="T2"){
                        s(24);
                    }else if(sa==TipoToken.COMA){
                        r(0,"T2");
                    }
                    else if(sa==TipoToken.EOF ){
                        r(0,"T2");

                    }
                    else{
                        hayErrores=true;
                        b=false;
                    }
                    break;
                case 21:
                    if(sa==TipoToken.COMA){
                        r(1,"T2");
                    }else{
                        hayErrores=true;
                        b=false;
                    }
                    break;
                case 22://Se puede quitar
                    if(sa==TipoToken.COMA){
                        r(1,"T2");
                    }else{
                        hayErrores=true;
                        b=false;
                    }
                    break;
                case 23:
                    if(sa==TipoToken.FROM || sa==TipoToken.COMA){
                        r(2,"A1");
                    }else{
                        hayErrores=true;
                        b=false;
                    }
                    break;
                case 24:
                    if(sa==TipoToken.COMA || sa==TipoToken.EOF){
                        r(2,"T1");
                    }else{
                        hayErrores=true;
                        b=false;
                    }
                    break;
                case 25:
                    if(sa==TipoToken.FROM || sa==TipoToken.COMA){
                        r(2,"A2");
                    }else{
                        hayErrores=true;
                        b=false;
                    }
                    break;
            }
        }
        if(hayErrores){
            System.out.println("Se encontraron errores");
            return false;
        }
        else{
            System.out.println("Consulta correcta");
            return true;
        }
    }
    public void s(int estadoSiguiente){
        pila.push(estadoSiguiente);
        i++;
        sa = tokens.get(i).tipo;
    }
    public void r(int borrar, String simbolo){
        sa = simbolo;
        i--;
        for(int k = 0; k<borrar; k++)
            pila.pop();
    }
}
