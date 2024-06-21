import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

class Profesor {
    private String nombre;
    private String apellido;
    private List<Asignatura> asignaturas;

    public Profesor(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.asignaturas = new ArrayList<>();
    }

    public void agregarAsignatura(Asignatura asignatura) {
        asignaturas.add(asignatura);
    }

    public String mostrarInfo() {
        StringBuilder info = new StringBuilder("Profesor: " + nombre + " " + apellido + "\n");
        for (Asignatura asignatura : asignaturas) {
            info.append(" - Asignatura: ").append(asignatura.getNombre()).append("\n");
        }
        return info.toString();
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }
}

class Estudiante {
    private String nombre;
    private String apellido;
    private String idEstudiante;
    private List<Curso> cursos;

    public Estudiante(String nombre, String apellido, String idEstudiante) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.idEstudiante = idEstudiante;
        this.cursos = new ArrayList<>();
    }

    public void agregarCurso(Curso curso) {
        cursos.add(curso);
    }

    public String mostrarInfo() {
        StringBuilder info = new StringBuilder("Estudiante: " + nombre + " " + apellido + ", ID: " + idEstudiante + "\n");
        for (Curso curso : cursos) {
            info.append(" - Curso: ").append(curso.getNombre()).append("\n");
        }
        return info.toString();
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }
}

class Asignatura {
    private String nombre;
    private Profesor profesor;

    public Asignatura(String nombre, Profesor profesor) {
        this.nombre = nombre;
        this.profesor = profesor;
    }

    public String mostrarInfo() {
        return "Asignatura: " + nombre + ", Profesor: " + profesor.getNombre() + " " + profesor.getApellido() + "\n";
    }

    public String getNombre() {
        return nombre;
    }

    public Profesor getProfesor() {
        return profesor;
    }
}

class Horario {
    private String dia;
    private String horaInicio;
    private String horaFin;

    public Horario(String dia, String horaInicio, String horaFin) {
        this.dia = dia;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    public String mostrarInfo() {
        return "Horario: " + dia + ", " + horaInicio + " - " + horaFin + "\n";
    }
}

class Curso {
    private String nombre;
    private Profesor profesor;
    private List<Estudiante> estudiantes;
    private Horario horario;

    public Curso(String nombre, Profesor profesor, Horario horario) {
        this.nombre = nombre;
        this.profesor = profesor;
        this.estudiantes = new ArrayList<>();
        this.horario = horario;
    }

    public void agregarEstudiante(Estudiante estudiante) {
        estudiantes.add(estudiante);
        estudiante.agregarCurso(this);
    }

    public String mostrarInfo() {
        StringBuilder info = new StringBuilder("Curso: " + nombre + "\n");
        info.append("Profesor: ").append(profesor.getNombre()).append(" ").append(profesor.getApellido()).append("\n");
        info.append("Estudiantes inscritos:\n");
        for (Estudiante estudiante : estudiantes) {
            info.append(" - ").append(estudiante.getNombre()).append(" ").append(estudiante.getApellido()).append("\n");
        }
        info.append(horario.mostrarInfo());
        return info.toString();
    }

    public String getNombre() {
        return nombre;
    }

    public Profesor getProfesor() {
        return profesor;
    }
}

class Evaluacion {
    private Curso curso;
    private Estudiante estudiante;
    private int nota;

    public Evaluacion(Curso curso, Estudiante estudiante, int nota) {
        this.curso = curso;
        this.estudiante = estudiante;
        this.nota = nota;
    }

    public String mostrarInfo() {
        return "Evaluación: Curso: " + curso.getNombre() + ", Estudiante: " + estudiante.getNombre() + " " + estudiante.getApellido() + ", Nota: " + nota + "\n";
    }
}

public class interfaz2 {
    private List<Profesor> profesores = new ArrayList<>();
    private List<Estudiante> estudiantes = new ArrayList<>();
    private List<Curso> cursos = new ArrayList<>();
    private List<Evaluacion> evaluaciones = new ArrayList<>();

    private JTextArea infoText;

    public static void main(String[] args) {
        new interfaz2().createGUI();
    }

    private void createGUI() {
        JFrame frame = new JFrame("Gestión Académica");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 600);

        JPanel panel = new JPanel();
        frame.add(panel);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel buttonPanel = new JPanel();
        panel.add(buttonPanel);
        buttonPanel.setLayout(new FlowLayout());

        JButton addProfesorButton = new JButton("Añadir Profesor");
        addProfesorButton.addActionListener(e -> addProfesor());
        buttonPanel.add(addProfesorButton);

        JButton addEstudianteButton = new JButton("Añadir Estudiante");
        addEstudianteButton.addActionListener(e -> addEstudiante());
        buttonPanel.add(addEstudianteButton);

        JButton addCursoButton = new JButton("Añadir Curso");
        addCursoButton.addActionListener(e -> addCurso());
        buttonPanel.add(addCursoButton);

        JButton addEvaluacionButton = new JButton("Añadir Evaluación");
        addEvaluacionButton.addActionListener(e -> addEvaluacion());
        buttonPanel.add(addEvaluacionButton);

        JButton showInfoButton = new JButton("Mostrar Información");
        showInfoButton.addActionListener(e -> mostrarInfo());
        buttonPanel.add(showInfoButton);

        infoText = new JTextArea(20, 60);
        infoText.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(infoText);
        panel.add(scrollPane);

        frame.setVisible(true);
    }

    private void addProfesor() {
        JTextField nombreField = new JTextField(5);
        JTextField apellidoField = new JTextField(5);

        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("Nombre:"));
        myPanel.add(nombreField);
        myPanel.add(new JLabel("Apellido:"));
        myPanel.add(apellidoField);

        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Añadir Profesor", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String nombre = nombreField.getText();
            String apellido = apellidoField.getText();
            Profesor profesor = new Profesor(nombre, apellido);
            profesores.add(profesor);
            JOptionPane.showMessageDialog(null, "Profesor añadido con éxito.");
        }
    }

    private void addEstudiante() {
        JTextField nombreField = new JTextField(5);
        JTextField apellidoField = new JTextField(5);
        JTextField idField = new JTextField(5);

        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("Nombre:"));
        myPanel.add(nombreField);
        myPanel.add(new JLabel("Apellido:"));
        myPanel.add(apellidoField);
        myPanel.add(new JLabel("ID Estudiante:"));
        myPanel.add(idField);

        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Añadir Estudiante", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String nombre = nombreField.getText();
            String apellido = apellidoField.getText();
            String id = idField.getText();
            Estudiante estudiante = new Estudiante(nombre, apellido, id);
            estudiantes.add(estudiante);
            JOptionPane.showMessageDialog(null, "Estudiante añadido con éxito.");
        }
    }

    private void addCurso() {
        JTextField nombreField = new JTextField(5);
        JTextField profesorField = new JTextField(5);
        JTextField diaField = new JTextField(5);
        JTextField inicioField = new JTextField(5);
        JTextField finField = new JTextField(5);

        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("Nombre Curso:"));
        myPanel.add(nombreField);
        myPanel.add(new JLabel("Nombre Profesor:"));
        myPanel.add(profesorField);
        myPanel.add(new JLabel("Día Horario:"));
        myPanel.add(diaField);
        myPanel.add(new JLabel("Hora Inicio:"));
        myPanel.add(inicioField);
        myPanel.add(new JLabel("Hora Fin:"));
        myPanel.add(finField);

        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Añadir Curso", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String nombre = nombreField.getText();
            String profesorNombre = profesorField.getText();
            String dia = diaField.getText();
            String inicio = inicioField.getText();
            String fin = finField.getText();

            Profesor profesor = profesores.stream()
                    .filter(p -> p.getNombre().equals(profesorNombre))
                    .findFirst()
                    .orElse(null);

            if (profesor == null) {
                JOptionPane.showMessageDialog(null, "Profesor no encontrado.");
                return;
            }

            Horario horario = new Horario(dia, inicio, fin);
            Curso curso = new Curso(nombre, profesor, horario);
            cursos.add(curso);
            JOptionPane.showMessageDialog(null, "Curso añadido con éxito.");
        }
    }

    private void addEvaluacion() {
        JTextField cursoField = new JTextField(5);
        JTextField estudianteField = new JTextField(5);
        JTextField notaField = new JTextField(5);

        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("Nombre Curso:"));
        myPanel.add(cursoField);
        myPanel.add(new JLabel("Nombre Estudiante:"));
        myPanel.add(estudianteField);
        myPanel.add(new JLabel("Nota:"));
        myPanel.add(notaField);

        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Añadir Evaluación", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String cursoNombre = cursoField.getText();
            String estudianteNombre = estudianteField.getText();
            int nota = Integer.parseInt(notaField.getText());

            Curso curso = cursos.stream()
                    .filter(c -> c.getNombre().equals(cursoNombre))
                    .findFirst()
                    .orElse(null);

            Estudiante estudiante = estudiantes.stream()
                    .filter(e -> e.getNombre().equals(estudianteNombre))
                    .findFirst()
                    .orElse(null);

            if (curso == null || estudiante == null) {
                JOptionPane.showMessageDialog(null, "Curso o Estudiante no encontrado.");
                return;
            }

            Evaluacion evaluacion = new Evaluacion(curso, estudiante, nota);
            evaluaciones.add(evaluacion);
            JOptionPane.showMessageDialog(null, "Evaluación añadida con éxito.");
        }
    }

    private void mostrarInfo() {
        StringBuilder info = new StringBuilder();
        for (Curso curso : cursos) {
            info.append(curso.mostrarInfo());
        }
        for (Evaluacion evaluacion : evaluaciones) {
            info.append(evaluacion.mostrarInfo());
        }
        infoText.setText(info.toString());
    }
}
