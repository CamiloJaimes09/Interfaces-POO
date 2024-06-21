import tkinter as tk
from tkinter import messagebox

class Profesor:
    def __init__(self, nombre, apellido):
        self.nombre = nombre
        self.apellido = apellido
        self.asignaturas = []

    def agregar_asignatura(self, asignatura):
        self.asignaturas.append(asignatura)

    def mostrar_info(self):
        info = f"Profesor: {self.nombre} {self.apellido}\n"
        for asignatura in self.asignaturas:
            info += f" - Asignatura: {asignatura.nombre}\n"
        return info


class Estudiante:
    def __init__(self, nombre, apellido, id_estudiante):
        self.nombre = nombre
        self.apellido = apellido
        self.id_estudiante = id_estudiante
        self.cursos = []

    def agregar_curso(self, curso):
        self.cursos.append(curso)

    def mostrar_info(self):
        info = f"Estudiante: {self.nombre} {self.apellido}, ID: {self.id_estudiante}\n"
        for curso in self.cursos:
            info += f" - Curso: {curso.nombre}\n"
        return info


class Asignatura:
    def __init__(self, nombre, profesor):
        self.nombre = nombre
        self.profesor = profesor

    def mostrar_info(self):
        return f"Asignatura: {self.nombre}, Profesor: {self.profesor.nombre} {self.profesor.apellido}\n"


class Horario:
    def __init__(self, dia, hora_inicio, hora_fin):
        self.dia = dia
        self.hora_inicio = hora_inicio
        self.hora_fin = hora_fin

    def mostrar_info(self):
        return f"Horario: {self.dia}, {self.hora_inicio} - {self.hora_fin}\n"


class Curso:
    def __init__(self, nombre, profesor, horario):
        self.nombre = nombre
        self.profesor = profesor
        self.estudiantes = []
        self.horario = horario

    def agregar_estudiante(self, estudiante):
        self.estudiantes.append(estudiante)
        estudiante.agregar_curso(self)

    def mostrar_info(self):
        info = f"Curso: {self.nombre}\n"
        info += f"Profesor: {self.profesor.nombre} {self.profesor.apellido}\n"
        info += "Estudiantes inscritos:\n"
        for estudiante in self.estudiantes:
            info += f" - {estudiante.nombre} {estudiante.apellido}\n"
        info += self.horario.mostrar_info()
        return info


class Evaluacion:
    def __init__(self, curso, estudiante, nota):
        self.curso = curso
        self.estudiante = estudiante
        self.nota = nota

    def mostrar_info(self):
        return f"Evaluación: Curso: {self.curso.nombre}, Estudiante: {self.estudiante.nombre} {self.estudiante.apellido}, Nota: {self.nota}\n"


class App(tk.Tk):
    def __init__(self):
        super().__init__()
        self.title("Gestión Académica")
        self.geometry("600x600")

        self.profesores = []
        self.estudiantes = []
        self.cursos = []
        self.evaluaciones = []

        self.create_widgets()

    def create_widgets(self):
        self.tabs = tk.Frame(self)
        self.tabs.pack()

        self.add_profesor_btn = tk.Button(self.tabs, text="Añadir Profesor", command=self.add_profesor)
        self.add_profesor_btn.pack(side="left")

        self.add_estudiante_btn = tk.Button(self.tabs, text="Añadir Estudiante", command=self.add_estudiante)
        self.add_estudiante_btn.pack(side="left")

        self.add_curso_btn = tk.Button(self.tabs, text="Añadir Curso", command=self.add_curso)
        self.add_curso_btn.pack(side="left")

        self.add_evaluacion_btn = tk.Button(self.tabs, text="Añadir Evaluación", command=self.add_evaluacion)
        self.add_evaluacion_btn.pack(side="left")

        self.show_info_btn = tk.Button(self.tabs, text="Mostrar Información", command=self.mostrar_info)
        self.show_info_btn.pack(side="left")

        self.info_text = tk.Text(self, height=20, width=70)
        self.info_text.pack()

    def add_profesor(self):
        self.new_window("Añadir Profesor", self.add_profesor_action)

    def add_profesor_action(self, nombre, apellido):
        profesor = Profesor(nombre, apellido)
        self.profesores.append(profesor)
        messagebox.showinfo("Éxito", f"Profesor {nombre} {apellido} añadido.")

    def add_estudiante(self):
        self.new_window("Añadir Estudiante", self.add_estudiante_action)

    def add_estudiante_action(self, nombre, apellido, id_estudiante):
        estudiante = Estudiante(nombre, apellido, id_estudiante)
        self.estudiantes.append(estudiante)
        messagebox.showinfo("Éxito", f"Estudiante {nombre} {apellido} con ID {id_estudiante} añadido.")

    def add_curso(self):
        self.new_window("Añadir Curso", self.add_curso_action)

    def add_curso_action(self, nombre, profesor_nombre, horario_dia, horario_inicio, horario_fin):
        profesor = next((p for p in self.profesores if p.nombre == profesor_nombre), None)
        if not profesor:
            messagebox.showerror("Error", f"Profesor {profesor_nombre} no encontrado.")
            return

        horario = Horario(horario_dia, horario_inicio, horario_fin)
        curso = Curso(nombre, profesor, horario)
        self.cursos.append(curso)
        messagebox.showinfo("Éxito", f"Curso {nombre} añadido.")

    def add_evaluacion(self):
        self.new_window("Añadir Evaluación", self.add_evaluacion_action)

    def add_evaluacion_action(self, curso_nombre, estudiante_nombre, nota):
        curso = next((c for c in self.cursos if c.nombre == curso_nombre), None)
        estudiante = next((e for e in self.estudiantes if e.nombre == estudiante_nombre), None)

        if not curso or not estudiante:
            messagebox.showerror("Error", "Curso o Estudiante no encontrado.")
            return

        evaluacion = Evaluacion(curso, estudiante, int(nota))
        self.evaluaciones.append(evaluacion)
        messagebox.showinfo("Éxito", f"Evaluación añadida: {nota} para {estudiante_nombre} en {curso_nombre}.")

    def mostrar_info(self):
        self.info_text.delete(1.0, tk.END)
        for curso in self.cursos:
            self.info_text.insert(tk.END, curso.mostrar_info())
        for evaluacion in self.evaluaciones:
            self.info_text.insert(tk.END, evaluacion.mostrar_info())

    def new_window(self, title, action):
        window = tk.Toplevel(self)
        window.title(title)
        window.geometry("400x300")

        entries = []

        def add_entry(label_text):
            frame = tk.Frame(window)
            frame.pack(pady=5)
            label = tk.Label(frame, text=label_text)
            label.pack(side="left")
            entry = tk.Entry(frame)
            entry.pack(side="left")
            entries.append(entry)

        if action == self.add_profesor_action:
            add_entry("Nombre:")
            add_entry("Apellido:")
        elif action == self.add_estudiante_action:
            add_entry("Nombre:")
            add_entry("Apellido:")
            add_entry("ID Estudiante:")
        elif action == self.add_curso_action:
            add_entry("Nombre Curso:")
            add_entry("Nombre Profesor:")
            add_entry("Día Horario:")
            add_entry("Hora Inicio:")
            add_entry("Hora Fin:")
        elif action == self.add_evaluacion_action:
            add_entry("Nombre Curso:")
            add_entry("Nombre Estudiante:")
            add_entry("Nota:")

        def submit_action():
            values = [e.get() for e in entries]
            action(*values)
            window.destroy()

        submit_btn = tk.Button(window, text="Añadir", command=submit_action)
        submit_btn.pack(pady=20)

if __name__ == "__main__":
    app = App()
    app.mainloop()
