import tkinter as tk
from tkinter import messagebox
from datetime import date

class Autor:
    def __init__(self, nombre, apellido):
        self.nombre = nombre
        self.apellido = apellido

    def mostrar_info(self):
        return f"Autor: {self.nombre} {self.apellido}"

class Categoria:
    def __init__(self, nombre):
        self.nombre = nombre

    def mostrar_info(self):
        return f"Categoría: {self.nombre}"

class Libro:
    def __init__(self, titulo, isbn, autor, categoria):
        self.titulo = titulo
        self.isbn = isbn
        self.autor = autor
        self.categoria = categoria

    def mostrar_info(self):
        return f"Libro: {self.titulo}, ISBN: {self.isbn}\n{self.autor.mostrar_info()}\n{self.categoria.mostrar_info()}"

class Usuario:
    def __init__(self, nombre, apellido, id_usuario):
        self.nombre = nombre
        self.apellido = apellido
        self.id_usuario = id_usuario

    def mostrar_info(self):
        return f"Usuario: {self.nombre} {self.apellido}, ID: {self.id_usuario}"

class Prestamo:
    def __init__(self, libro, usuario, fecha_prestamo, fecha_devolucion=None):
        self.libro = libro
        self.usuario = usuario
        self.fecha_prestamo = fecha_prestamo
        self.fecha_devolucion = fecha_devolucion

    def mostrar_info(self):
        info = f"Préstamo del libro '{self.libro.titulo}' a {self.usuario.nombre} {self.usuario.apellido}\n"
        info += f"Fecha de préstamo: {self.fecha_prestamo}\n"
        if self.fecha_devolucion:
            info += f"Fecha de devolución: {self.fecha_devolucion}\n"
        else:
            info += "El libro no ha sido devuelto.\n"
        return info

class Biblioteca:
    def __init__(self):
        self.libros = []
        self.usuarios = []
        self.prestamos = []

    def registrar_libro(self, libro):
        self.libros.append(libro)
        messagebox.showinfo("Registro de Libro", f"Libro '{libro.titulo}' registrado en la biblioteca.")

    def registrar_usuario(self, usuario):
        self.usuarios.append(usuario)
        messagebox.showinfo("Registro de Usuario", f"Usuario '{usuario.nombre} {usuario.apellido}' registrado en la biblioteca.")

    def realizar_prestamo(self, libro, usuario):
        prestamo = Prestamo(libro, usuario, date.today())
        self.prestamos.append(prestamo)
        messagebox.showinfo("Préstamo Realizado", f"Préstamo realizado: '{libro.titulo}' a {usuario.nombre} {usuario.apellido}")

    def devolver_libro(self, prestamo):
        prestamo.fecha_devolucion = date.today()
        messagebox.showinfo("Devolución de Libro", f"Libro '{prestamo.libro.titulo}' devuelto por {prestamo.usuario.nombre} {prestamo.usuario.apellido}")

    def mostrar_libros(self):
        if self.libros:
            info = "Libros en la biblioteca:\n"
            for libro in self.libros:
                info += libro.mostrar_info() + "\n\n"
            messagebox.showinfo("Libros en la Biblioteca", info)
        else:
            messagebox.showinfo("Libros en la Biblioteca", "No hay libros registrados en la biblioteca.")

# Crear la interfaz gráfica
class BibliotecaApp:
    def __init__(self, root):
        self.biblioteca = Biblioteca()
        self.root = root
        self.root.title("Biblioteca")

        # Widgets para registrar libros
        tk.Label(root, text="Registrar Libro").grid(row=0, column=0, columnspan=2)
        tk.Label(root, text="Título").grid(row=1, column=0)
        self.titulo_entry = tk.Entry(root)
        self.titulo_entry.grid(row=1, column=1)
        tk.Label(root, text="ISBN").grid(row=2, column=0)
        self.isbn_entry = tk.Entry(root)
        self.isbn_entry.grid(row=2, column=1)
        tk.Label(root, text="Autor").grid(row=3, column=0)
        self.autor_entry = tk.Entry(root)
        self.autor_entry.grid(row=3, column=1)
        tk.Label(root, text="Categoría").grid(row=4, column=0)
        self.categoria_entry = tk.Entry(root)
        self.categoria_entry.grid(row=4, column=1)
        tk.Button(root, text="Registrar Libro", command=self.registrar_libro).grid(row=5, column=0, columnspan=2)

        # Widgets para registrar usuarios
        tk.Label(root, text="Registrar Usuario").grid(row=6, column=0, columnspan=2)
        tk.Label(root, text="Nombre").grid(row=7, column=0)
        self.nombre_usuario_entry = tk.Entry(root)
        self.nombre_usuario_entry.grid(row=7, column=1)
        tk.Label(root, text="Apellido").grid(row=8, column=0)
        self.apellido_usuario_entry = tk.Entry(root)
        self.apellido_usuario_entry.grid(row=8, column=1)
        tk.Label(root, text="ID Usuario").grid(row=9, column=0)
        self.id_usuario_entry = tk.Entry(root)
        self.id_usuario_entry.grid(row=9, column=1)
        tk.Button(root, text="Registrar Usuario", command=self.registrar_usuario).grid(row=10, column=0, columnspan=2)

        # Widgets para realizar préstamos
        tk.Label(root, text="Realizar Préstamo").grid(row=11, column=0, columnspan=2)
        tk.Label(root, text="Título Libro").grid(row=12, column=0)
        self.titulo_prestamo_entry = tk.Entry(root)
        self.titulo_prestamo_entry.grid(row=12, column=1)
        tk.Label(root, text="ID Usuario").grid(row=13, column=0)
        self.id_usuario_prestamo_entry = tk.Entry(root)
        self.id_usuario_prestamo_entry.grid(row=13, column=1)
        tk.Button(root, text="Realizar Préstamo", command=self.realizar_prestamo).grid(row=14, column=0, columnspan=2)

        # Widgets para devolver libros
        tk.Label(root, text="Devolver Libro").grid(row=15, column=0, columnspan=2)
        tk.Label(root, text="Título Libro").grid(row=16, column=0)
        self.titulo_devolucion_entry = tk.Entry(root)
        self.titulo_devolucion_entry.grid(row=16, column=1)
        tk.Label(root, text="ID Usuario").grid(row=17, column=0)
        self.id_usuario_devolucion_entry = tk.Entry(root)
        self.id_usuario_devolucion_entry.grid(row=17, column=1)
        tk.Button(root, text="Devolver Libro", command=self.devolver_libro).grid(row=18, column=0, columnspan=2)

        # Widget para mostrar libros
        tk.Button(root, text="Mostrar Libros", command=self.mostrar_libros).grid(row=19, column=0, columnspan=2)

    def registrar_libro(self):
        titulo = self.titulo_entry.get()
        isbn = self.isbn_entry.get()
        autor_nombre, autor_apellido = self.autor_entry.get().split()
        categoria_nombre = self.categoria_entry.get()

        autor = Autor(autor_nombre, autor_apellido)
        categoria = Categoria(categoria_nombre)
        libro = Libro(titulo, isbn, autor, categoria)

        self.biblioteca.registrar_libro(libro)

    def registrar_usuario(self):
        nombre = self.nombre_usuario_entry.get()
        apellido = self.apellido_usuario_entry.get()
        id_usuario = self.id_usuario_entry.get()

        usuario = Usuario(nombre, apellido, id_usuario)
        self.biblioteca.registrar_usuario(usuario)

    def realizar_prestamo(self):
        titulo = self.titulo_prestamo_entry.get()
        id_usuario = self.id_usuario_prestamo_entry.get()

        libro = next((l for l in self.biblioteca.libros if l.titulo == titulo), None)
        usuario = next((u for u in self.biblioteca.usuarios if u.id_usuario == id_usuario), None)

        if libro and usuario:
            self.biblioteca.realizar_prestamo(libro, usuario)
        else:
            messagebox.showerror("Error", "Libro o Usuario no encontrado.")

    def devolver_libro(self):
        titulo = self.titulo_devolucion_entry.get()
        id_usuario = self.id_usuario_devolucion_entry.get()

        prestamo = next((p for p in self.biblioteca.prestamos if p.libro.titulo == titulo and p.usuario.id_usuario == id_usuario), None)

        if prestamo:
            self.biblioteca.devolver_libro(prestamo)
        else:
            messagebox.showerror("Error", "Préstamo no encontrado.")

    def mostrar_libros(self):
        self.biblioteca.mostrar_libros()

if __name__ == "__main__":
    root = tk.Tk()
    app = BibliotecaApp(root)
    root.mainloop()
