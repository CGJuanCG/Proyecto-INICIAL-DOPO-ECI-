import java.util.ArrayList;

/**
 * Solves Problem I (Slot Machine) of the 2025 ICPC World Finals.
 * SlotMachine is used only as the testing tool: SlotMachine(n), spin(wheel, steps)
 * and distinctSymbols(); makeVisible() and makeInvisible() are used only to decide
 * whether the run is shown on screen.
 */
public class SlotMachineContest {

    private static final int MIN_WHEELS = 3;
    private static final int MAX_WHEELS = 50;

    /**
     * Wins the game on a random machine of n wheels and n symbols, keeping the
     * machine invisible, and returns the actions that were needed.
     *
     * @param n number of wheels and symbols (3 to 50)
     * @return an array of actions in the order they were played; each action is
     *         {wheel, steps}. Empty if the machine was already winning.
     * @throws IllegalArgumentException if n is outside the range 3..50
     */
    public static int[][] solve(int n) {
        validarRango(n);
        SlotMachine machine = new SlotMachine(n);
        machine.makeInvisible();
        return solucion(machine, n);
    }

    /**
     * Wins the game on a random machine of n wheels and n symbols with the machine
     * visible, so the whole run can be watched step by step.
     *
     * @param n number of wheels and symbols (3 to 50)
     * @throws IllegalArgumentException if n is outside the range 3..50
     */
    public static void simulate(int n){
        validarRango(n);
        SlotMachine machine = new SlotMachine(n);
        machine.makeVisible();
        solucion(machine, n);
    }

    /**
     * Checks that n is a size the problem allows.
     *
     * @param n number of wheels and symbols
     * @throws IllegalArgumentException if n is outside the range 3..50
     */
    private static void validarRango(int n) {
        if (n < MIN_WHEELS || n > MAX_WHEELS) {
            throw new IllegalArgumentException(
                "El número de ruedas y símbolos debe estar entre " + MIN_WHEELS
                + " y " + MAX_WHEELS + ", pero se recibió " + n);
        }
    }

    /**
     * Wins the game on the given machine, using only the number of distinct symbols
     * shown after each action. The caller decides whether the machine is visible.
     *
     * The machine must have exactly n wheels and n symbols; this method cannot check
     * that precondition, because it is only allowed to use spin and distinctSymbols.
     *
     * @param machine the machine to play on
     * @param n number of wheels and symbols of that machine (3 to 50)
     * @return the actions needed to win, in order; each action is {wheel, steps},
     *         with the wheel from 1 to n and steps between -(n - 1) and n - 1, never
     *         zero. Empty if the machine was already winning.
     */
    static int[][] solucion(SlotMachine machine, int n){
        ArrayList<int[]> actions = new ArrayList<int[]>();

        if (machine.distinctSymbols() == 1) {
            return new int[0][];   // ya estaba ganada
        }

        boolean jackpot = separarRuedas(machine, n, actions);
        if (!jackpot) {
            int[] order = descubrirOrden(machine, n, actions);
            if (order != null) {
                alinearRuedas(machine, n, order, actions);
            }
        }
        return actions.toArray(new int[0][]);
    }

    /**
     * Phase 1: leaves every wheel showing a different symbol. Each wheel is tried
     * in its n positions and left where the most distinct symbols show: that
     * position is not taken by any other wheel.
     *
     * @param machine the machine to play on
     * @param n number of wheels and symbols of that machine
     * @param actions the list where every action played is added
     * @return true when the jackpot came up along the way, so there is nothing
     *         left to do
     */
    private static boolean separarRuedas(SlotMachine machine, int n, ArrayList<int[]> actions) {
        for (int wheel = 1; wheel <= n; wheel++) {
            int[] distinctAt = new int[n];   // distinctAt[t] = símbolos distintos tras girar t posiciones
            distinctAt[0] = machine.distinctSymbols();
            for (int t = 1; t < n; t++) {
                machine.spin(wheel, 1);
                actions.add(new int[] {wheel, 1});
                distinctAt[t] = machine.distinctSymbols();
                if (distinctAt[t] == 1) {
                    return true;   // jackpot: terminamos
                }
            }
            int best = 0;
            for (int t = 1; t < n; t++) {
                if (distinctAt[t] > distinctAt[best]) {
                    best = t;
                }
            }
            int steps = (best + 1) % n;      // estamos en la posición n-1; hasta 'best' faltan best+1
            if (steps != 0) {
                machine.spin(wheel, steps);
                actions.add(new int[] {wheel, steps});
            }
        }
        return false;
    }

    /**
     * Phase 2: finds out the order of the wheels. It needs every wheel showing a
     * different symbol, which is how phase 1 leaves them.
     *
     * @param machine the machine to play on
     * @param n number of wheels and symbols of that machine
     * @param actions the list where every action played is added
     * @return the order found, where order[t] is the wheel that sits t positions
     *         after wheel 1, or null when the jackpot came up along the way
     * @throws IllegalStateException if the next wheel cannot be found
     */
    private static int[] descubrirOrden(SlotMachine machine, int n, ArrayList<int[]> actions) {
        int[] order = new int[n];
        boolean[] placed = new boolean[n + 1];   // placed[w] = ya sabemos dónde va la rueda w
        order[0] = 1;
        placed[1] = true;
        for (int t = 0; t < n - 2; t++) {
            int a = order[t];
            machine.spin(a, 1);                  // a avanza y choca con su vecina: baja a n-1
            actions.add(new int[] {a, 1});
            int found = -1;
            for (int b = 1; b <= n && found == -1; b++) {
                if (!placed[b]) {
                    machine.spin(b, -1);      // b retrocede una posición (n-1 equivale a -1)
                    actions.add(new int[] {b, -1});
                    int distinct = machine.distinctSymbols();
                    if (distinct == 1) {
                        return null;   // jackpot: terminamos
                    }
                    if (distinct == n) {
                        found = b;               // otra vez n distintos: b era la vecina de a
                    } else {
                        machine.spin(b, 1);      // no era: deshacer y probar la siguiente
                        actions.add(new int[] {b, 1});
                    }
                }
            }
            if (found == -1) {
                throw new IllegalStateException(
                    "No se encontró la rueda siguiente: ¿todas las ruedas tienen los mismos n símbolos en el mismo orden?");
            }
            machine.spin(found, 1);              // deshacer el movimiento de la vecina
            actions.add(new int[] {found, 1});
            machine.spin(a, -1);              // deshacer el movimiento de a
            actions.add(new int[] {a, -1});
            order[t + 1] = found;
            placed[found] = true;
        }
        for (int wheel = 1; wheel <= n; wheel++) {   // la última rueda es la única sin ubicar
            if (!placed[wheel]) {
                order[n - 1] = wheel;
            }
        }
        return order;
    }

    /**
     * Phase 3: lines up all the wheels. Wheel order[t] sits t positions ahead of
     * wheel 1, so moving it back t positions (n - t) leaves it equal to wheel 1.
     * The last action gives the jackpot.
     *
     * @param machine the machine to play on
     * @param n number of wheels and symbols of that machine
     * @param order the order found in phase 2
     * @param actions the list where every action played is added
     */
    private static void alinearRuedas(SlotMachine machine, int n, int[] order, ArrayList<int[]> actions) {
        for (int t = 1; t < n; t++) {
            machine.spin(order[t], n - t);
            actions.add(new int[] {order[t], n - t});
        }
    }
}