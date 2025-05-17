package pedidos;



import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private static int contadorId = 1;
    private int id;
    private List<LineaPedido> lineas;

    public Pedido() {
        this.id = contadorId++;
        this.lineas = new ArrayList<>();
    }

    public void agregarLinea(LineaPedido linea) {
        lineas.add(linea);
    }

    public double calcularTotal() {
        double total = 0;
        for (LineaPedido l : lineas) {
            total += l.calcularSubtotal();
        }
        return total;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Pedido #" + id + "\n");
        for (LineaPedido l : lineas) {
            sb.append(l).append("\n");
        }
        sb.append("Total: $").append(calcularTotal());
        return sb.toString();
    }
}
