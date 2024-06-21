import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class Autor {
    private String nombre;
    private String apellido;

    public Autor(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public String mostrarInfo() {
        return "Autor: " + nombre + " " + apellido;
    }
}

class Categoria {
    private String nombre;

    public Categoria(String nombre) {
        this.nombre = nombre;
    }

    public String mostrarInfo() {
        return "Categoría: " + nombre;
    }
}

class Libro {
    private String titulo;
    private String isbn;
    private Autor autor;
    private Categoria categoria;

    public Libro(String titulo, String isbn, Autor autor, Categoria categoria) {
        this.titulo = titulo;
        this.isbn = isbn;
        this.autor = autor;
        this.categoria = categoria;
    }

    public String mostrarInfo() {
        return "Libro: " + titulo + ", ISBN: " + isbn + "\n" + autor.mostrarInfo() + "\n" + categoria.mostrarInfo();
    }

    public String getTitulo() {
        return titulo;
    }
}

class Usuario {
    private String nombre;
    private String apellido;
    private String idUsuario;

    public Usuario(String nombre, String apellido, String idUsuario) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.idUsuario = idUsuario;
    }

    public String mostrarInfo() {
        return "Usuario: " + nombre + " " + apellido + ", ID: " + idUsuario;
    }

    public String getIdUsuario() {
        return idUsuario;
    }
}

class Prestamo {
    private Libro libro;
    private Usuario usuario;
    private Date fechaPrestamo;
    private Date fechaDevolucion;

    public Prestamo(Libro libro, Usuario usuario, Date fechaPrestamo) {
        this.libro = libro;
        this.usuario = usuario;
        this.fechaPrestamo = fechaPrestamo;
    }

    public String mostrarInfo() {
        String info = "Préstamo del libro '" + libro.getTitulo() + "' a " + usuario.mostrarInfo() + "\n";
        info += "Fecha de préstamo: " + fechaPrestamo + "\n";
        if (fechaDevolucion != null) {
            info += "Fecha de devolución: " + fechaDevolucion + "\n";
        } else {
            info += "El libro no ha sido devuelto.\n";
        }
        return info;
    }

    public Libro getLibro() {
        return libro;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void devolver() {
        fechaDevolucion = new Date();
    }
}

class Biblioteca {
    private List<Libro> libros;
    private List<Usuario> usuarios;
    private List<Prestamo> prestamos;

    public Biblioteca() {
        libros = new ArrayList<>();
        usuarios = new ArrayList<>();
        prestamos = new ArrayList<>();
    }

    public void registrarLibro(Libro libro) {
        libros.add(libro);
        JOptionPane.showMessageDialog(null, "Libro '" + libro.getTitulo() + "' registrado en la biblioteca.");
    }

    public void registrarUsuario(Usuario usuario) {
        usuarios.add(usuario);
        JOptionPane.showMessageDialog(null, "Usuario '" + usuario.mostrarInfo() + "' registrado en la biblioteca.");
    }

    public void realizarPrestamo(Libro libro, Usuario usuario) {
        Prestamo prestamo = new Prestamo(libro, usuario, new Date());
        prestamos.add(prestamo);
        JOptionPane.showMessageDialog(null, "Préstamo realizado: '" + libro.getTitulo() + "' a " + usuario.mostrarInfo());
    }

    public void devolverLibro(Prestamo prestamo) {
        prestamo.devolver();
        JOptionPane.showMessageDialog(null, "Libro '" + prestamo.getLibro().getTitulo() + "' devuelto por " + prestamo.getUsuario().mostrarInfo());
    }

    public void mostrarLibros() {
        StringBuilder info = new StringBuilder();
        if (!libros.isEmpty()) {
            info.append("Libros en la biblioteca:\n");
            for (Libro libro : libros) {
                info.append(libro.mostrarInfo()).append("\n\n");
            }
            JOptionPane.showMessageDialog(null, info.toString());
        } else {
            JOptionPane.showMessageDialog(null, "No hay libros registrados en la biblioteca.");
        }
    }

    public Libro buscarLibro(String titulo) {
        for (Libro libro : libros) {
            if (libro.getTitulo().equalsIgnoreCase(titulo)) {
                return libro;
            }
        }
        return null;
    }

    public Usuario buscarUsuario(String idUsuario) {
        for (Usuario usuario : usuarios) {
            if (usuario.getIdUsuario().equalsIgnoreCase(idUsuario)) {
                return usuario;
            }
        }
        return null;
    }

    public Prestamo buscarPrestamo(String titulo, String idUsuario) {
        for (Prestamo prestamo : prestamos) {
            if (prestamo.getLibro().getTitulo().equalsIgnoreCase(titulo) && prestamo.getUsuario().getIdUsuario().equalsIgnoreCase(idUsuario)) {
                return prestamo;
            }
        }
        return null;
    }
}

public class interfaz1 extends JFrame {
    private Biblioteca biblioteca;

    private JTextField tituloLibroField, isbnField, autorField, categoriaField;
    private JTextField nombreUsuarioField, apellidoUsuarioField, idUsuarioField;
    private JTextField tituloPrestamoField, idUsuarioPrestamoField;
    private JTextField tituloDevolucionField, idUsuarioDevolucionField;

    public interfaz1() {
        biblioteca = new Biblioteca();

        setTitle("Biblioteca");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(20, 2));

        // Widgets para registrar libros
        add(new JLabel("Registrar Libro"));
        add(new JLabel(""));
        add(new JLabel("Título"));
        tituloLibroField = new JTextField();
        add(tituloLibroField);
        add(new JLabel("ISBN"));
        isbnField = new JTextField();
        add(isbnField);
        add(new JLabel("Autor"));
        autorField = new JTextField();
        add(autorField);
        add(new JLabel("Categoría"));
        categoriaField = new JTextField();
        add(categoriaField);
        JButton registrarLibroBtn = new JButton("Registrar Libro");
        registrarLibroBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarLibro();
            }
        });
        add(registrarLibroBtn);
        add(new JLabel(""));

        // Widgets para registrar usuarios
        add(new JLabel("Registrar Usuario"));
        add(new JLabel(""));
        add(new JLabel("Nombre"));
        nombreUsuarioField = new JTextField();
        add(nombreUsuarioField);
        add(new JLabel("Apellido"));
        apellidoUsuarioField = new JTextField();
        add(apellidoUsuarioField);
        add(new JLabel("ID Usuario"));
        idUsuarioField = new JTextField();
        add(idUsuarioField);
        JButton registrarUsuarioBtn = new JButton("Registrar Usuario");
        registrarUsuarioBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarUsuario();
            }
        });
        add(registrarUsuarioBtn);
        add(new JLabel(""));

        // Widgets para realizar préstamos
        add(new JLabel("Realizar Préstamo"));
        add(new JLabel(""));
        add(new JLabel("Título Libro"));
        tituloPrestamoField = new JTextField();
        add(tituloPrestamoField);
        add(new JLabel("ID Usuario"));
        idUsuarioPrestamoField = new JTextField();
        add(idUsuarioPrestamoField);
        JButton realizarPrestamoBtn = new JButton("Realizar Préstamo");
        realizarPrestamoBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarPrestamo();
            }
        });
        add(realizarPrestamoBtn);
        add(new JLabel(""));

        // Widgets para devolver libros
        add(new JLabel("Devolver Libro"));
        add(new JLabel(""));
        add(new JLabel("Título Libro"));
        tituloDevolucionField = new JTextField();
        add(tituloDevolucionField);
        add(new JLabel("ID Usuario"));
        idUsuarioDevolucionField = new JTextField();
        add(idUsuarioDevolucionField);
        JButton devolverLibroBtn = new JButton("Devolver Libro");
        devolverLibroBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                devolverLibro();
            }
        });
        add(devolverLibroBtn);
        add(new JLabel(""));

        // Widget para mostrar libros
        JButton mostrarLibrosBtn = new JButton("Mostrar Libros");
        mostrarLibrosBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarLibros();
            }
        });
        add(mostrarLibrosBtn);
        add(new JLabel(""));
    }

    private void registrarLibro() {
        String titulo = tituloLibroField.getText();
        String isbn = isbnField.getText();
        String[] autorNombreApellido = autorField.getText().split(" ");
        String categoriaNombre = categoriaField.getText();

        Autor autor = new Autor(autorNombreApellido[0], autorNombreApellido[1]);
        Categoria categoria = new Categoria(categoriaNombre);
        Libro libro = new Libro(titulo, isbn, autor, categoria);

        biblioteca.registrarLibro(libro);
    }

    private void registrarUsuario() {
        String nombre = nombreUsuarioField.getText();
        String apellido = apellidoUsuarioField.getText();
        String idUsuario = idUsuarioField.getText();

        Usuario usuario = new Usuario(nombre, apellido, idUsuario);

        biblioteca.registrarUsuario(usuario);
    }

    private void realizarPrestamo() {
        String titulo = tituloPrestamoField.getText();
        String idUsuario = idUsuarioPrestamoField.getText();

        Libro libro = biblioteca.buscarLibro(titulo);
        Usuario usuario = biblioteca.buscarUsuario(idUsuario);

        if (libro != null && usuario != null) {
            biblioteca.realizarPrestamo(libro, usuario);
        } else {
            JOptionPane.showMessageDialog(this, "Libro o Usuario no encontrado.");
        }
    }

    private void devolverLibro() {
        String titulo = tituloDevolucionField.getText();
        String idUsuario = idUsuarioDevolucionField.getText();

        Prestamo prestamo = biblioteca.buscarPrestamo(titulo, idUsuario);

        if (prestamo != null) {
            biblioteca.devolverLibro(prestamo);
        } else {
            JOptionPane.showMessageDialog(this, "Préstamo no encontrado.");
        }
    }

    private void mostrarLibros() {
        biblioteca.mostrarLibros();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new interfaz1().setVisible(true);
            }
        });
    }
}
