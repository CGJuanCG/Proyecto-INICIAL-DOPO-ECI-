import java.util.ArrayList;

/**
 * Solves Problem I (Slot Machine) of the 2025 ICPC World Finals.
 * SlotMachine is used only as the testing tool: SlotMachine(n), spin(wheel, steps)
 * and distinctSymbols().
 */
public class SlotMachineContest {

    /**
     * Wins the game on a random machine of n wheels and n symbols, using only the
     * number of distinct symbols shown after each action. The machine stays invisible.
     *
     * @param n number of wheels and symbols (3 to 50)
     * @return the actions needed to win, in order; each action is {wheel, steps}
     *         (wheel from 1 to n, steps from 1 to n - 1). Empty if the machine was
     *         already winning.
     */
    public static int[][] solve(int n) {
        SlotMachine machine = new SlotMachine(n);
        machine.makeInvisible();
        ArrayList<int[]> actions = new ArrayList<int[]>();

        if (machine.distinctSymbols() == 1) {
            return new int[0][];   // ya estaba ganada
        }

        // Fase 1: que todas las ruedas muestren símbolos distintos.
        // Cada rueda se prueba en sus n posiciones y se deja donde hay más símbolos
        // distintos: esa posición no la ocupa ninguna otra rueda.
        for (int wheel = 1; wheel <= n; wheel++) {
            int[] distinctAt = new int[n];   // distinctAt[t] = símbolos distintos tras girar t posiciones
            distinctAt[0] = machine.distinctSymbols();
            for (int t = 1; t < n; t++) {
                machine.spin(wheel, 1);
                actions.add(new int[] {wheel, 1});
                distinctAt[t] = machine.distinctSymbols();
                if (distinctAt[t] == 1) {
                    return actions.toArray(new int[0][]);   // jackpot: terminamos
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

        // Fase 2: descubrir el orden de las ruedas.
        // order[t] = rueda que está t posiciones después de la rueda 1.
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
                    machine.spin(b, n - 1);      // b retrocede una posición (n-1 equivale a -1)
                    actions.add(new int[] {b, n - 1});
                    int distinct = machine.distinctSymbols();
                    if (distinct == 1) {
                        return actions.toArray(new int[0][]);   // jackpot: terminamos
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
            machine.spin(a, n - 1);              // deshacer el movimiento de a
            actions.add(new int[] {a, n - 1});
            order[t + 1] = found;
            placed[found] = true;
        }
        for (int wheel = 1; wheel <= n; wheel++) {   // la última rueda es la única sin ubicar
            if (!placed[wheel]) {
                order[n - 1] = wheel;
            }
        }

        // Fase 3: alinear todas las ruedas.
        // La rueda order[t] está t posiciones adelante de la rueda 1: retrocederla t
        // posiciones (n - t) la deja igual a la rueda 1. La última acción da el jackpot.
        for (int t = 1; t < n; t++) {
            machine.spin(order[t], n - t);
            actions.add(new int[] {order[t], n - t});
        }
        return actions.toArray(new int[0][]);
    }
}