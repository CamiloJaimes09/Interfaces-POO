import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

class Categoria {
    private String nombre;

    public Categoria(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}

class Producto {
    private String nombre;
    private double precio;
    private Categoria categoria;

    public Producto(String nombre, double precio, Categoria categoria) {
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public Categoria getCategoria() {
        return categoria;
    }
}

class Cliente {
    private String nombre;
    private String apellido;
    private String idCliente;

    public Cliente(String nombre, String apellido, String idCliente) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getIdCliente() {
        return idCliente;
    }
}

class ItemOrden {
    private Producto producto;
    private int cantidad;

    public ItemOrden(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public double calcularSubtotal() {
        return producto.getPrecio() * cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }
}

class Orden {
    private Cliente cliente;
    private List<ItemOrden> items;
    private double total;

    public Orden(Cliente cliente) {
        this.cliente = cliente;
        this.items = new ArrayList<>();
        this.total = 0;
    }

    public void agregarItem(ItemOrden item) {
        items.add(item);
        calcularTotal();
    }

    private void calcularTotal() {
        total = 0;
        for (ItemOrden item : items) {
            total += item.calcularSubtotal();
        }
    }

    public Cliente getCliente() {
        return cliente;
    }

    public List<ItemOrden> getItems() {
        return items;
    }

    public double getTotal() {
        return total;
    }
}

class Tienda {
    private List<Producto> productos;
    private List<Cliente> clientes;
    private List<Orden> ordenes;
    private List<Categoria> categorias;

    public Tienda() {
        this.productos = new ArrayList<>();
        this.clientes = new ArrayList<>();
        this.ordenes = new ArrayList<>();
        this.categorias = new ArrayList<>();
    }

    public void registrarProducto(Producto producto) {
        productos.add(producto);
    }

    public void registrarCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    public Orden crearOrden(Cliente cliente) {
        Orden orden = new Orden(cliente);
        ordenes.add(orden);
        return orden;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public List<Orden> getOrdenes() {
        return ordenes;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }
}

class TiendaGUI extends JFrame {
    private Tienda tienda;

    private DefaultListModel<String> productosListModel;
    private DefaultListModel<String> clientesListModel;
    private DefaultListModel<String> ordenesListModel;

    private JList<String> productosList;
    private JList<String> clientesList;
    private JList<String> ordenesList;

    public TiendaGUI() {
        super("Gestión de Tienda");
        tienda = new Tienda();

        // Categorías y productos iniciales
        Categoria categoria1 = new Categoria("Electrónica");
        Categoria categoria2 = new Categoria("Ropa");
        tienda.getCategorias().add(categoria1);
        tienda.getCategorias().add(categoria2);

        // Crear cliente y tienda
        Cliente cliente1 = new Cliente("Laura", "García", "C001");
        tienda.registrarCliente(cliente1);

        // Configuración de la interfaz gráfica
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel de productos
        JPanel panelProductos = new JPanel();
        panelProductos.setBorder(BorderFactory.createTitledBorder("Productos en la tienda"));
        panelProductos.setLayout(new BorderLayout());
        productosListModel = new DefaultListModel<>();
        productosList = new JList<>(productosListModel);
        panelProductos.add(new JScrollPane(productosList), BorderLayout.CENTER);
        JButton btnMostrarProductos = new JButton("Mostrar Productos");
        btnMostrarProductos.addActionListener(this::mostrarProductos);
        panelProductos.add(btnMostrarProductos, BorderLayout.SOUTH);
        add(panelProductos, BorderLayout.WEST);

        // Panel de clientes
        JPanel panelClientes = new JPanel();
        panelClientes.setBorder(BorderFactory.createTitledBorder("Clientes en la tienda"));
        panelClientes.setLayout(new BorderLayout());
        clientesListModel = new DefaultListModel<>();
        clientesList = new JList<>(clientesListModel);
        panelClientes.add(new JScrollPane(clientesList), BorderLayout.CENTER);
        JButton btnMostrarClientes = new JButton("Mostrar Clientes");
        btnMostrarClientes.addActionListener(this::mostrarClientes);
        panelClientes.add(btnMostrarClientes, BorderLayout.SOUTH);
        add(panelClientes, BorderLayout.CENTER);

        // Panel de órdenes
        JPanel panelOrdenes = new JPanel();
        panelOrdenes.setBorder(BorderFactory.createTitledBorder("Órdenes"));
        panelOrdenes.setLayout(new BorderLayout());
        ordenesListModel = new DefaultListModel<>();
        ordenesList = new JList<>(ordenesListModel);
        panelOrdenes.add(new JScrollPane(ordenesList), BorderLayout.CENTER);
        JButton btnMostrarOrdenes = new JButton("Mostrar Órdenes");
        btnMostrarOrdenes.addActionListener(this::mostrarOrdenes);
        panelOrdenes.add(btnMostrarOrdenes, BorderLayout.SOUTH);
        JButton btnCrearOrden = new JButton("Crear Orden");
        btnCrearOrden.addActionListener(this::crearOrden);
        panelOrdenes.add(btnCrearOrden, BorderLayout.NORTH);
        add(panelOrdenes, BorderLayout.EAST);
    }

    private void mostrarProductos(ActionEvent event) {
        productosListModel.clear();
        for (Producto producto : tienda.getProductos()) {
            productosListModel.addElement(producto.getNombre() + " - $" + producto.getPrecio());
        }
    }

    private void mostrarClientes(ActionEvent event) {
        clientesListModel.clear();
        for (Cliente cliente : tienda.getClientes()) {
            clientesListModel.addElement(cliente.getNombre() + " " + cliente.getApellido() + " - ID: " + cliente.getIdCliente());
        }
    }

    private void mostrarOrdenes(ActionEvent event) {
        ordenesListModel.clear();
        for (Orden orden : tienda.getOrdenes()) {
            ordenesListModel.addElement("Orden para: " + orden.getCliente().getNombre() + " " + orden.getCliente().getApellido() + " - Total: $" + orden.getTotal());
        }
    }

    private void crearOrden(ActionEvent event) {
        String idCliente = JOptionPane.showInputDialog(this, "Ingrese el ID del Cliente:");
        Cliente cliente = tienda.getClientes().stream()
                .filter(c -> c.getIdCliente().equals(idCliente))
                .findFirst()
                .orElse(null);
        if (cliente != null) {
            Orden orden = tienda.crearOrden(cliente);
            JOptionPane.showMessageDialog(this, "Orden creada para: " + cliente.getNombre() + " " + cliente.getApellido());
            mostrarOrdenes(null);
        } else {
            JOptionPane.showMessageDialog(this, "Cliente no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TiendaGUI tiendaGUI = new TiendaGUI();
            tiendaGUI.setVisible(true);
        });
    }
}
