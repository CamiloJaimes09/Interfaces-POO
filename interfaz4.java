import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Random;

public class interfaz4 extends JFrame {
    private Mundial mundial;

    private JTextArea textArea;
    private JButton btnCrearJugador;
    private JButton btnCrearEquipo;
    private JButton btnCrearEstadio;
    private JButton btnCrearPartido;
    private JButton btnCrearGrupo;
    private JButton btnMostrarFixture;
    private JButton btnMostrarResultado;
    private JButton btnSimularPartidos;
    private JButton btnSalir;

    public interfaz4() {
        super("Mundial de Fútbol");
        mundial = new Mundial();

        // Configuración de la interfaz gráfica
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel principal
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setLayout(new BorderLayout());

        // Área de texto
        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnCrearJugador = new JButton("Crear Jugador");
        btnCrearJugador.addActionListener(this::crearJugador);
        panelBotones.add(btnCrearJugador);

        btnCrearEquipo = new JButton("Crear Equipo");
        btnCrearEquipo.addActionListener(this::crearEquipo);
        panelBotones.add(btnCrearEquipo);

        btnCrearEstadio = new JButton("Crear Estadio");
        btnCrearEstadio.addActionListener(this::crearEstadio);
        panelBotones.add(btnCrearEstadio);

        btnCrearPartido = new JButton("Crear Partido");
        btnCrearPartido.addActionListener(this::crearPartido);
        panelBotones.add(btnCrearPartido);

        btnCrearGrupo = new JButton("Crear Grupo");
        btnCrearGrupo.addActionListener(this::crearGrupo);
        panelBotones.add(btnCrearGrupo);

        btnMostrarFixture = new JButton("Mostrar Fixture");
        btnMostrarFixture.addActionListener(this::mostrarFixture);
        panelBotones.add(btnMostrarFixture);

        btnMostrarResultado = new JButton("Mostrar Resultado");
        btnMostrarResultado.addActionListener(this::mostrarResultado);
        panelBotones.add(btnMostrarResultado);

        btnSimularPartidos = new JButton("Simular Partidos");
        btnSimularPartidos.addActionListener(this::simularPartidos);
        panelBotones.add(btnSimularPartidos);

        btnSalir = new JButton("Salir");
        btnSalir.addActionListener(this::salir);
        panelBotones.add(btnSalir);

        panel.add(panelBotones, BorderLayout.SOUTH);

        add(panel, BorderLayout.CENTER);
    }

    private void crearJugador(ActionEvent e) {
        String nombre = JOptionPane.showInputDialog(this, "Nombre del jugador:");
        if (nombre != null) {
            int edad = Integer.parseInt(JOptionPane.showInputDialog(this, "Edad del jugador:"));
            String posicion = JOptionPane.showInputDialog(this, "Posición del jugador:");
            Jugador jugador = new Jugador(nombre, edad, posicion);
            JOptionPane.showMessageDialog(this, "Jugador Creado:\n" + jugador.mostrarInfo(), "Jugador Creado", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void crearEquipo(ActionEvent e) {
        String nombre = JOptionPane.showInputDialog(this, "Nombre del equipo:");
        if (nombre != null) {
            String entrenador = JOptionPane.showInputDialog(this, "Entrenador del equipo:");
            Equipo equipo = new Equipo(nombre, entrenador);
            mundial.registrarEquipo(equipo);
            JOptionPane.showMessageDialog(this, "Equipo Creado:\n" + equipo.mostrarInfo(), "Equipo Creado", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void crearEstadio(ActionEvent e) {
        String nombre = JOptionPane.showInputDialog(this, "Nombre del estadio:");
        if (nombre != null) {
            String ciudad = JOptionPane.showInputDialog(this, "Ciudad del estadio:");
            int capacidad = Integer.parseInt(JOptionPane.showInputDialog(this, "Capacidad del estadio:"));
            Estadio estadio = new Estadio(nombre, ciudad, capacidad);
            mundial.registrarEstadio(estadio);
            JOptionPane.showMessageDialog(this, "Estadio Creado:\n" + estadio.mostrarInfo(), "Estadio Creado", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void crearPartido(ActionEvent e) {
        ArrayList<Equipo> equiposRegistrados = mundial.getEquipos();
        ArrayList<Estadio> estadiosRegistrados = mundial.getEstadios();

        if (equiposRegistrados.isEmpty() || estadiosRegistrados.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debes crear al menos un equipo y un estadio primero.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[] nombresEquipos = equiposRegistrados.stream().map(Equipo::getNombre).toArray(String[]::new);
        String equipoLocal = (String) JOptionPane.showInputDialog(this, "Selecciona equipo local:", "Crear Partido",
                JOptionPane.PLAIN_MESSAGE, null, nombresEquipos, nombresEquipos[0]);
        String equipoVisitante = (String) JOptionPane.showInputDialog(this, "Selecciona equipo visitante:", "Crear Partido",
                JOptionPane.PLAIN_MESSAGE, null, nombresEquipos, nombresEquipos[1]);
        String estadio = (String) JOptionPane.showInputDialog(this, "Selecciona estadio:", "Crear Partido",
                JOptionPane.PLAIN_MESSAGE, null, estadiosRegistrados.stream().map(Estadio::getNombre).toArray(),
                estadiosRegistrados.get(0).getNombre());

        if (equipoLocal != null && equipoVisitante != null && estadio != null) {
            Equipo local = mundial.getEquipoByName(equipoLocal);
            Equipo visitante = mundial.getEquipoByName(equipoVisitante);
            Estadio selectedEstadio = mundial.getEstadioByName(estadio);

            Partido partido = new Partido(local, visitante, selectedEstadio);
            mundial.registrarPartido(partido);
            JOptionPane.showMessageDialog(this, "Partido Creado:\n" + partido.mostrarResultado(), "Partido Creado", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void crearGrupo(ActionEvent e) {
        String nombre = JOptionPane.showInputDialog(this, "Nombre del grupo:");
        if (nombre != null) {
            Grupo grupo = new Grupo(nombre);
            mundial.registrarGrupo(grupo);
            JOptionPane.showMessageDialog(this, "Grupo Creado:\n" + grupo.mostrarInfo(), "Grupo Creado", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void mostrarFixture(ActionEvent e) {
        String fixture = mundial.generarFixture();
        textArea.setText(fixture);
    }

    private void mostrarResultado(ActionEvent e) {
        ArrayList<Partido> partidos = mundial.getPartidos();
        if (partidos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay partidos registrados aún.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Partido partido = partidos.get(new Random().nextInt(partidos.size()));
        String resultado = JOptionPane.showInputDialog(this, "Ingrese resultado del partido:\n" + partido.mostrarResultado());
        if (resultado != null && !resultado.isEmpty()) {
            partido.jugarPartido(resultado);
            JOptionPane.showMessageDialog(this, "Resultado registrado:\n" + partido.mostrarResultado(), "Resultado Registrado", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void simularPartidos(ActionEvent e) {
        ArrayList<Partido> partidos = mundial.getPartidos();
        if (partidos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay partidos registrados aún.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        for (Partido partido : partidos) {
            String[] resultados = {"1-0", "0-1", "1-1", "2-1", "1-2", "2-2"};
            String resultado = resultados[new Random().nextInt(resultados.length)];
            partido.jugarPartido(resultado);
        }

        JOptionPane.showMessageDialog(this, "Simulación de partidos completa.", "Simulación Completa", JOptionPane.INFORMATION_MESSAGE);
    }

    private void salir(ActionEvent e) {
        System.exit(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            interfaz4 ventana = new interfaz4();
            ventana.setVisible(true);
        });
    }
}

class Mundial {
    private ArrayList<Equipo> equipos;
    private ArrayList<Estadio> estadios;
    private ArrayList<Partido> partidos;
    private ArrayList<Grupo> grupos;

    public Mundial() {
        equipos = new ArrayList<>();
        estadios = new ArrayList<>();
        partidos = new ArrayList<>();
        grupos = new ArrayList<>();
    }

    public void registrarEquipo(Equipo equipo) {
        equipos.add(equipo);
    }

    public void registrarEstadio(Estadio estadio) {
        estadios.add(estadio);
    }

    public void registrarPartido(Partido partido) {
        partidos.add(partido);
    }

    public void registrarGrupo(Grupo grupo) {
        grupos.add(grupo);
    }

    public ArrayList<Equipo> getEquipos() {
        return equipos;
    }

    public ArrayList<Estadio> getEstadios() {
        return estadios;
    }

    public ArrayList<Partido> getPartidos() {
        return partidos;
    }

    public Equipo getEquipoByName(String nombre) {
        for (Equipo equipo : equipos) {
            if (equipo.getNombre().equals(nombre)) {
                return equipo;
            }
        }
        return null;
    }

    public Estadio getEstadioByName(String nombre) {
        for (Estadio estadio : estadios) {
            if (estadio.getNombre().equals(nombre)) {
                return estadio;
            }
        }
        return null;
    }

    public String generarFixture() {
        StringBuilder sb = new StringBuilder();
        sb.append("Fixture del Mundial:\n\n");
        for (Partido partido : partidos) {
            sb.append(partido.mostrarResultado()).append("\n");
        }
        return sb.toString();
    }
}

class Equipo {
    private String nombre;
    private String entrenador;

    public Equipo(String nombre, String entrenador) {
        this.nombre = nombre;
        this.entrenador = entrenador;
    }

    public String getNombre() {
        return nombre;
    }

    public String mostrarInfo() {
        return "Equipo: " + nombre + "\nEntrenador: " + entrenador;
    }
}

class Jugador {
    private String nombre;
    private int edad;
    private String posicion;

    public Jugador(String nombre, int edad, String posicion) {
        this.nombre = nombre;
        this.edad = edad;
        this.posicion = posicion;
    }

    public String mostrarInfo() {
        return "Jugador: " + nombre + "\nEdad: " + edad + "\nPosición: " + posicion;
    }
}

class Estadio {
    private String nombre;
    private String ciudad;
    private int capacidad;

    public Estadio(String nombre, String ciudad, int capacidad) {
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.capacidad = capacidad;
    }

    public String getNombre() {
        return nombre;
    }

    public String mostrarInfo() {
        return "Estadio: " + nombre + "\nCiudad: " + ciudad + "\nCapacidad: " + capacidad;
    }
}

class Grupo {
    private String nombre;

    public Grupo(String nombre) {
        this.nombre = nombre;
    }

    public String mostrarInfo() {
        return "Grupo: " + nombre;
    }
}

class Partido {
    private Equipo equipoLocal;
    private Equipo equipoVisitante;
    private Estadio estadio;
    private String resultado;

    public Partido(Equipo equipoLocal, Equipo equipoVisitante, Estadio estadio) {
        this.equipoLocal = equipoLocal;
        this.equipoVisitante = equipoVisitante;
        this.estadio = estadio;
        this.resultado = "Pendiente";
    }

    public void jugarPartido(String resultado) {
        this.resultado = resultado;
    }

    public String mostrarResultado() {
        return equipoLocal.getNombre() + " vs " + equipoVisitante.getNombre() + " - " + resultado + " @" + estadio.getNombre();
    }
}

