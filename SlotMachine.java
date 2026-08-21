import java.util.ArrayList;

/**
 * Representa una máquina tragamonedas (slot machine), compuesta por varias
 * ruedas (Wheel). Permite crear la máquina, agregar y eliminar ruedas,
 * respetando las reglas de posición definidas en el enunciado.
 *
 * @author (tu nombre)
 * @version (fecha)
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
}