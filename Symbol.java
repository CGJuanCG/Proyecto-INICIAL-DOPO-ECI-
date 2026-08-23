
/**
 * Representa el simbolo (symbol) adentro de la rueda, un simbolo es diferente si su color cambia
 * incluso si poseen la misma forma.
 *
 * @author Juan Diego Castaño Parra - Juan Diego Carreño Gutierrez
 * @version 22-08-2026
 */
public class Symbol{
    
    private String color;
    private Rectangle figura;
    
    /**
     * Constructor de clase Symbol
     * @param color es el color CSS que identifica al simbolo
     */
    public Symbol(String color){
        this.color = color.toLowerCase();
    }
    
    /**
     * Metodo get para que otras clases puedan acceder a el de manera controlada
     * @return retorna el color
     */
    public String getColor(){
        return color;
    }
    
    /**
     * Se redefine el metodo equals de la clase padre Object, para que compare si los 2 colores son iguales ("red" igual a "red")
     * Tuvo que redefinirse debido a que equals de una clase compara en memoria y no contenido debido a su clase padre Object
     * @param o es el objeto al que se quiere verificar si es igual para luego hacer el casteo de la clase correspondiente
     * @return Retorna booleano, para identificar si es el mismo color o no
     */
    @Override
    public boolean equals(Object o){
        if (this == o){
            return true;
        }
        if (o == null || this.getClass() != o.getClass()){
            return false;
        }
        Symbol otro = (Symbol) o;
        return color.equals(otro.color);
    }
    
    
    /**
     * Se redefine el metodo hashCode de la clase padre Object, ya que si se redefine equals se debe redefinir hashCode
     * lo que hace es retornar un numero de referencia para su busqueda, si son el mismo objeto, tienen el mismo hashCode
     * @return retorna un entero
     */
    @Override
    public int hashCode(){
        return color.hashCode();
    }
}