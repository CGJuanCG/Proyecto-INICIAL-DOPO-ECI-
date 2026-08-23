import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
/**
 * Representa una máquina tragamonedas (slot machine), compuesta por varias
 * ruedas (Wheel).
 *
 * @author (Juan Diego Castaño Parra - Juan Diego Carreño)
 * @version (22-08-2026)
 */
public class SlotMachine
{
    private List<Wheel> wheels;
    private boolean isVisible;
    private boolean lastMove;
    private Cinta cinta;

    /**
     * Crea una máquina tragamonedas sin ruedas, visible por defecto.
     */
    public SlotMachine()
    {
        wheels = new ArrayList<>();
        isVisible = true;
        lastMove = false;
    }

    /**
     * Agrega una rueda nueva en la posición indicada.
     * Si pos es menor a 1, se usa la posición 1.
     * Si pos es mayor al máximo posible, se usa el máximo (al final de la lista).
     *
     * @param pos posición (empezando en 1) donde insertar la rueda nueva
     */
    public void addWheel(int pos)
    {
        if (pos < 1) {
            pos = 1;
        }
        if (pos > wheels.size() + 1) {
            pos = wheels.size() + 1;
        }
        int indice = pos - 1;
        wheels.add(indice, new Wheel());
        lastMove = true;
    }

    /**
     * Elimina la rueda en la posición indicada.
     * Si pos es menor a 1, se usa la posición 1.
     * Si pos es mayor al máximo existente, se usa el máximo.
     * Si no hay ruedas, la operación falla (lastMove queda en false).
     *
     * @param pos posición (empezando en 1) de la rueda a eliminar
     */
    public void delWheel(int pos)
    {
        if (wheels.isEmpty()) {
            lastMove = false;
            JOptionPane.showMessageDialog(null,"La lista de ruedas esta vacia,por favor cree una rueda e intente nuevamente");
        }
        else {
            int indice = ajustar(pos) - 1;
            wheels.remove(indice);
            lastMove = true;
        }
    }
    
    /**
     * Hace girar la rueda en la posición especifica i de la maquina tragamonedas
     */
    public void spin (int wheel){
        if (wheels.isEmpty()){
            lastMove = false;
            JOptionPane.showMessageDialog(null,"La lista de ruedas esta vacia,por favor cree una rueda e intente nuevamente");
        }
        else{
            wheels.get(ajustar(wheel) - 1).spin();
            lastMove = true;
            }
        }
    
    /**
     * Hace girar todas las ruedas de la maquina
     */
    public void spin(){
        if (wheels.isEmpty()){
            lastMove = false;
            JOptionPane.showMessageDialog(null,"La lista de ruedas esta vacia,por favor cree una rueda e intente nuevamente");
        }
        else{
            for (Wheel w: wheels){
                w.spin();
                lastMove = true;
            }
        }
    }
    
    /**
     *  Ubica el ofsett (se muestra en la pantalla) en el simbolo deseado en la rueda deseada 
     *  
     *  @param int wheel es el indice de la rueda deseada
     *  @param String symbol es el simbolo deseado
     */
    public void placeSymbol(int wheel,String symbol){
        if (wheels.isEmpty()){
            lastMove = false;
            JOptionPane.showMessageDialog(null,"La lista de ruedas esta vacia,por favor cree una rueda e intente nuevamente");
        }
        else{
            wheels.get(ajustar(wheel)-1).placeSymbol(symbol);
            lastMove = true;
        }
    }

    /**
     * Metodo privado que valida que no se pase del maximo de la lista
     * o del minimo de la lista (si la posicion es menor a 1, se usa la posicion 1
     * si la posicion es mayor al maximo numero de elementos, se usa el maximo)
     * 
     * @param int pos es el numero a verificar
     */
    
    private int ajustar(int pos){
        if (pos<1){
            return 1;
        }
        if (pos>wheels.size()){
            return wheels.size();
        }
        return pos;
        }
    }