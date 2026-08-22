import java.util.ArrayList;
import javax.swing.JOptionPane;
/**
 * Representa una máquina tragamonedas (slot machine), compuesta por varias
 * ruedas (Wheel).
 *
 * @author (Juan Diego Castaño Parra)
 * @version (21-08-2026)
 */
public class SlotMachine
{
    private ArrayList<Wheel> wheels;
    private boolean isVisible;
    private boolean lastMove;

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
        }
        else {
            if (pos < 1) {
                pos = 1;
            }
            if (pos > wheels.size()) {
                pos = wheels.size();
            }
            int indice = pos - 1;
            wheels.remove(indice);
            lastMove = true;
        }
    }
    
    /**
     * Hace girar la rueda en la posición especifica i de la maquina tragamonedas
     */
    public void spin (int wheel){
        if (wheel < 1 || wheel > wheels.size()){
            JOptionPane.showMessageDialog(null, "Indice de rueda invalido");
            lastMove = false;
            return;
        }
        wheels.get(wheel - 1).spin();
        lastMove = true;
    }
    
    /**
     * Hace girar todas las ruedas de la maquina
     */
    public void spin(){
        for (int i = 0; i <= wheels.size(); i++){
            spin(i);
        }
    }
}