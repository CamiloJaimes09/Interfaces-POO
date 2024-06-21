import tkinter as tk
from tkinter import messagebox

class Jugador:
    def __init__(self, nombre, edad, posicion):
        self.nombre = nombre
        self.edad = edad
        self.posicion = posicion

    def mostrar_info(self):
        return f"Jugador: {self.nombre}, Edad: {self.edad}, Posición: {self.posicion}"

class Equipo:
    def __init__(self, nombre, entrenador):
        self.nombre = nombre
        self.entrenador = entrenador
        self.jugadores = []

    def agregar_jugador(self, jugador):
        self.jugadores.append(jugador)

    def mostrar_info(self):
        return f"Equipo: {self.nombre}, Entrenador: {self.entrenador}"

class Partido:
    def __init__(self, equipo_local, equipo_visitante, estadio):
        self.equipo_local = equipo_local
        self.equipo_visitante = equipo_visitante
        self.resultado = None
        self.estadio = estadio

    def jugar_partido(self, resultado):
        self.resultado = resultado

    def mostrar_resultado(self):
        if self.resultado:
            return f"Partido en {self.estadio.nombre}: {self.equipo_local.nombre} {self.resultado} {self.equipo_visitante.nombre}"
        else:
            return "El partido aún no se ha jugado."

class Grupo:
    def __init__(self, nombre):
        self.nombre = nombre
        self.equipos = []

    def agregar_equipo(self, equipo):
        self.equipos.append(equipo)

    def mostrar_info(self):
        return f"Grupo: {self.nombre}"

class Estadio:
    def __init__(self, nombre, ciudad, capacidad):
        self.nombre = nombre
        self.ciudad = ciudad
        self.capacidad = capacidad

    def mostrar_info(self):
        return f"Estadio: {self.nombre}, Ciudad: {self.ciudad}, Capacidad: {self.capacidad}"

class Mundial:
    def __init__(self):
        self.grupos = []
        self.estadios = []

    def registrar_grupo(self, grupo):
        self.grupos.append(grupo)

    def registrar_estadio(self, estadio):
        self.estadios.append(estadio)

    def generar_fixture(self):
        fixture = "Fixture del Mundial:\n"
        for grupo in self.grupos:
            fixture += grupo.mostrar_info() + "\n"
            for equipo in grupo.equipos:
                fixture += "    " + equipo.mostrar_info() + "\n"
                for jugador in equipo.jugadores:
                    fixture += "        " + jugador.mostrar_info() + "\n"
        return fixture

# Interfaz gráfica con Tkinter
class MundialGUI:
    def __init__(self, root):
        self.root = root
        self.root.title("Mundial de Fútbol")

        self.mundial = Mundial()

        self.frame = tk.Frame(self.root)
        self.frame.pack(padx=10, pady=10)

        self.textbox = tk.Text(self.frame, width=60, height=20)
        self.textbox.pack()

        self.btn_crear_jugador = tk.Button(self.frame, text="Crear Jugador", command=self.crear_jugador)
        self.btn_crear_jugador.pack(pady=5)

        self.btn_crear_equipo = tk.Button(self.frame, text="Crear Equipo", command=self.crear_equipo)
        self.btn_crear_equipo.pack(pady=5)

        self.btn_crear_estadio = tk.Button(self.frame, text="Crear Estadio", command=self.crear_estadio)
        self.btn_crear_estadio.pack(pady=5)

        self.btn_crear_partido = tk.Button(self.frame, text="Crear Partido", command=self.crear_partido)
        self.btn_crear_partido.pack(pady=5)

        self.btn_crear_grupo = tk.Button(self.frame, text="Crear Grupo", command=self.crear_grupo)
        self.btn_crear_grupo.pack(pady=5)

        self.btn_mostrar_fixture = tk.Button(self.frame, text="Mostrar Fixture", command=self.mostrar_fixture)
        self.btn_mostrar_fixture.pack(pady=5)

        self.btn_mostrar_resultado = tk.Button(self.frame, text="Mostrar Resultado del Partido", command=self.mostrar_resultado)
        self.btn_mostrar_resultado.pack(pady=5)

        self.btn_salir = tk.Button(self.frame, text="Salir", command=self.salir)
        self.btn_salir.pack(pady=5)

    def crear_jugador(self):
        nombre = simpledialog.askstring("Crear Jugador", "Nombre del jugador:")
        edad = simpledialog.askinteger("Crear Jugador", "Edad del jugador:")
        posicion = simpledialog.askstring("Crear Jugador", "Posición del jugador:")
        jugador = Jugador(nombre, edad, posicion)
        messagebox.showinfo("Jugador Creado", f"Se ha creado el jugador:\n{jugador.mostrar_info()}")

    def crear_equipo(self):
        nombre = simpledialog.askstring("Crear Equipo", "Nombre del equipo:")
        entrenador = simpledialog.askstring("Crear Equipo", "Nombre del entrenador:")
        equipo = Equipo(nombre, entrenador)
        self.mundial.registrar_grupo(equipo)
        messagebox.showinfo("Equipo Creado", f"Se ha creado el equipo:\n{equipo.mostrar_info()}")

    def crear_estadio(self):
        nombre = simpledialog.askstring("Crear Estadio", "Nombre del estadio:")
        ciudad = simpledialog.askstring("Crear Estadio", "Ciudad del estadio:")
        capacidad = simpledialog.askinteger("Crear Estadio", "Capacidad del estadio:")
        estadio = Estadio(nombre, ciudad, capacidad)
        self.mundial.registrar_estadio(estadio)
        messagebox.showinfo("Estadio Creado", f"Se ha creado el estadio:\n{estadio.mostrar_info()}")

    def crear_partido(self):
        equipos = [equipo.nombre for equipo in self.mundial.grupos]
        equipo_local = simpledialog.askstring("Crear Partido", "Equipo local:", initialvalue=equipos[0], 
                                             parent=self.root, list=equipos)
        equipo_visitante = simpledialog.askstring("Crear Partido", "Equipo visitante:", initialvalue=equipos[1], 
                                                  parent=self.root, list=equipos)
        estadios = [estadio.nombre for estadio in self.mundial.estadios]
        estadio = simpledialog.askstring("Crear Partido", "Estadio:", initialvalue=estadios[0], 
                                         parent=self.root, list=estadios)
        partido = Partido(equipo_local, equipo_visitante, estadio)
        messagebox.showinfo("Partido Creado", f"Se ha creado el partido:\n{partido.mostrar_resultado()}")

    def crear_grupo(self):
        nombre = simpledialog.askstring("Crear Grupo", "Nombre del grupo:")
        grupo = Grupo(nombre)
        self.mundial.registrar_grupo(grupo)
        messagebox.showinfo("Grupo Creado", f"Se ha creado el grupo:\n{grupo.mostrar_info()}")

    def mostrar_fixture(self):
        self.textbox.delete(1.0, tk.END)
        fixture = self.mundial.generar_fixture()
        self.textbox.insert(tk.END, fixture)

    def mostrar_resultado(self):
        resultado = simpledialog.askstring("Mostrar Resultado", "Ingrese el resultado del partido (ej. 3-2):")
        messagebox.showinfo("Resultado del Partido", f"Resultado: {resultado}")

    def salir(self):
        self.root.destroy()

if __name__ == "__main__":
    root = tk.Tk()
    app = MundialGUI(root)
    root.mainloop()
