import tkinter as tk
from tkinter import messagebox

class Categoria:
    def __init__(self, nombre):
        self.nombre = nombre

class Producto:
    def __init__(self, nombre, precio, categoria):
        self.nombre = nombre
        self.precio = precio
        self.categoria = categoria

class Cliente:
    def __init__(self, nombre, apellido, id_cliente):
        self.nombre = nombre
        self.apellido = apellido
        self.id_cliente = id_cliente

class ItemOrden:
    def __init__(self, producto, cantidad):
        self.producto = producto
        self.cantidad = cantidad

    def calcular_subtotal(self):
        return self.producto.precio * self.cantidad

class Orden:
    def __init__(self, cliente):
        self.cliente = cliente
        self.items = []
        self.total = 0

    def agregar_item(self, item):
        self.items.append(item)
        self.calcular_total()

    def calcular_total(self):
        self.total = sum(item.calcular_subtotal() for item in self.items)

class Tienda:
    def __init__(self):
        self.productos = []
        self.clientes = []
        self.ordenes = []
        self.categorias = []

    def registrar_producto(self, producto):
        self.productos.append(producto)

    def registrar_cliente(self, cliente):
        self.clientes.append(cliente)

    def crear_orden(self, cliente):
        orden = Orden(cliente)
        self.ordenes.append(orden)
        return orden

class TiendaGUI:
    def __init__(self, root):
        self.root = root
        self.root.title("Gestión de Tienda")

        self.tienda = Tienda()

        # Categorías y productos iniciales
        categoria1 = Categoria("Electrónica")
        categoria2 = Categoria("Ropa")
        self.tienda.categorias.append(categoria1)
        self.tienda.categorias.append(categoria2)

        # Crear cliente y tienda
        cliente1 = Cliente("Laura", "García", "C001")
        self.tienda.registrar_cliente(cliente1)

        # Panel de productos
        self.frame_productos = tk.Frame(self.root)
        self.frame_productos.pack(padx=20, pady=10)
        
        self.label_productos = tk.Label(self.frame_productos, text="Productos en la tienda:")
        self.label_productos.pack()
        
        self.lista_productos = tk.Listbox(self.frame_productos, width=50)
        self.lista_productos.pack(pady=10)
        
        self.btn_mostrar_productos = tk.Button(self.frame_productos, text="Mostrar Productos", command=self.mostrar_productos)
        self.btn_mostrar_productos.pack(pady=5)

        self.btn_agregar_producto = tk.Button(self.frame_productos, text="Agregar Producto", command=self.agregar_producto)
        self.btn_agregar_producto.pack(pady=5)

        # Panel de clientes
        self.frame_clientes = tk.Frame(self.root)
        self.frame_clientes.pack(padx=20, pady=10)
        
        self.label_clientes = tk.Label(self.frame_clientes, text="Clientes en la tienda:")
        self.label_clientes.pack()
        
        self.lista_clientes = tk.Listbox(self.frame_clientes, width=50)
        self.lista_clientes.pack(pady=10)
        
        self.btn_mostrar_clientes = tk.Button(self.frame_clientes, text="Mostrar Clientes", command=self.mostrar_clientes)
        self.btn_mostrar_clientes.pack(pady=5)

        self.btn_agregar_cliente = tk.Button(self.frame_clientes, text="Agregar Cliente", command=self.agregar_cliente)
        self.btn_agregar_cliente.pack(pady=5)

        # Panel de órdenes
        self.frame_ordenes = tk.Frame(self.root)
        self.frame_ordenes.pack(padx=20, pady=10)
        
        self.label_ordenes = tk.Label(self.frame_ordenes, text="Órdenes:")
        self.label_ordenes.pack()
        
        self.lista_ordenes = tk.Listbox(self.frame_ordenes, width=50)
        self.lista_ordenes.pack(pady=10)
        
        self.btn_mostrar_ordenes = tk.Button(self.frame_ordenes, text="Mostrar Órdenes", command=self.mostrar_ordenes)
        self.btn_mostrar_ordenes.pack(pady=5)
        
        self.btn_crear_orden = tk.Button(self.frame_ordenes, text="Crear Orden", command=self.crear_orden)
        self.btn_crear_orden.pack(pady=5)
    
    def mostrar_productos(self):
        self.lista_productos.delete(0, tk.END)
        for producto in self.tienda.productos:
            self.lista_productos.insert(tk.END, f"{producto.nombre} - {producto.precio}")

    def agregar_producto(self):
        def guardar_producto():
            nombre = entry_nombre.get()
            precio = float(entry_precio.get())
            categoria_nombre = entry_categoria.get()
            categoria = next((cat for cat in self.tienda.categorias if cat.nombre == categoria_nombre), None)
            if not categoria:
                messagebox.showerror("Error", "Categoría no encontrada")
                return
            producto = Producto(nombre, precio, categoria)
            self.tienda.registrar_producto(producto)
            self.mostrar_productos()
            popup.destroy()

        popup = tk.Toplevel()
        popup.title("Agregar Producto")

        tk.Label(popup, text="Nombre:").pack(pady=5)
        entry_nombre = tk.Entry(popup)
        entry_nombre.pack(pady=5)

        tk.Label(popup, text="Precio:").pack(pady=5)
        entry_precio = tk.Entry(popup)
        entry_precio.pack(pady=5)

        tk.Label(popup, text="Categoría:").pack(pady=5)
        entry_categoria = tk.Entry(popup)
        entry_categoria.pack(pady=5)

        tk.Button(popup, text="Guardar", command=guardar_producto).pack(pady=10)

    def mostrar_clientes(self):
        self.lista_clientes.delete(0, tk.END)
        for cliente in self.tienda.clientes:
            self.lista_clientes.insert(tk.END, f"{cliente.nombre} {cliente.apellido} - ID: {cliente.id_cliente}")

    def agregar_cliente(self):
        def guardar_cliente():
            nombre = entry_nombre.get()
            apellido = entry_apellido.get()
            id_cliente = entry_id.get()
            cliente = Cliente(nombre, apellido, id_cliente)
            self.tienda.registrar_cliente(cliente)
            self.mostrar_clientes()
            popup.destroy()

        popup = tk.Toplevel()
        popup.title("Agregar Cliente")

        tk.Label(popup, text="Nombre:").pack(pady=5)
        entry_nombre = tk.Entry(popup)
        entry_nombre.pack(pady=5)

        tk.Label(popup, text="Apellido:").pack(pady=5)
        entry_apellido = tk.Entry(popup)
        entry_apellido.pack(pady=5)

        tk.Label(popup, text="ID Cliente:").pack(pady=5)
        entry_id = tk.Entry(popup)
        entry_id.pack(pady=5)

        tk.Button(popup, text="Guardar", command=guardar_cliente).pack(pady=10)

    def mostrar_ordenes(self):
        self.lista_ordenes.delete(0, tk.END)
        for orden in self.tienda.ordenes:
            self.lista_ordenes.insert(tk.END, f"Orden para: {orden.cliente.nombre} {orden.cliente.apellido} - Total: {orden.total}")

    def crear_orden(self):
        def guardar_orden():
            id_cliente = entry_id_cliente.get()
            cliente = next((cli for cli in self.tienda.clientes if cli.id_cliente == id_cliente), None)
            if not cliente:
                messagebox.showerror("Error", "Cliente no encontrado")
                return
            orden = self.tienda.crear_orden(cliente)
            self.mostrar_ordenes()
            popup.destroy()

        popup = tk.Toplevel()
        popup.title("Crear Orden")

        tk.Label(popup, text="ID Cliente:").pack(pady=5)
        entry_id_cliente = tk.Entry(popup)
        entry_id_cliente.pack(pady=5)

        tk.Button(popup, text="Guardar", command=guardar_orden).pack(pady=10)

# Crear tienda y ejecutar interfaz gráfica
root = tk.Tk()
tienda_gui = TiendaGUI(root)
root.mainloop()
