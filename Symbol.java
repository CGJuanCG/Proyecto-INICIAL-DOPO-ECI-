/**
* Representa un símbolo de la máquina tragamonedas. Un símbolo se identifica
* únicamente por su color, expresado con un nombre del estándar CSS: dos
* símbolos son iguales si y sólo si comparten el color.
*
* @author Juan Diego Castaño Parra - Juan Diego Carreño Gutierrez
* @version 23-08-2026
*/
public class Symbol
{
    private String color;
 
    /**
     * Crea un símbolo del color indicado.
     *
     * @param color nombre CSS del color que identifica al símbolo
     */
    public Symbol(String color)
    {
        this.color = color;
    }
 
    /**
     * Entrega el color que identifica al símbolo.
     *
     * @return el nombre CSS del color
     */
    public String getColor()
    {
        return color;
    }
 
    /**
     * Compara este símbolo con otro objeto. Dos símbolos son iguales cuando
     * tienen el mismo color, aunque sean objetos distintos.
     *
     * @param o el objeto con el que se compara
     * @return true si o es un símbolo del mismo color
     */
    @Override
    public boolean equals(Object o)
    {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Symbol otro = (Symbol) o;
        return color.equals(otro.color);
    }
 
    /**
     * Calcula el código hash a partir del color, de modo que dos símbolos
     * iguales produzcan siempre el mismo valor. El hash indica en qué grupo
     * buscar dentro de las colecciones basadas en tablas hash; la igualdad la
     * decide equals dentro de ese grupo.
     *
     * @return el código hash del símbolo
     */
    @Override
    public int hashCode()
    {
        return color.hashCode();
    }
}