package com.example;

import com.example.dao.CategoriaDAOImpl;
import com.example.dao.ProductoDAOImpl;
import com.example.model.Categoria;
import com.example.model.Producto;
import com.example.util.Databaseutil;

import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ProductoDAOImpl productoDAO = new ProductoDAOImpl();
        CategoriaDAOImpl categoriaDAO = new CategoriaDAOImpl();

        Databaseutil.initDatabase();

        int opcion;
        do {
            System.out.println("\n=== MENÚ PRINCIPAL ===");
            System.out.println("1. Listar productos");
            System.out.println("2. Crear producto");
            System.out.println("3. Actualizar producto");
            System.out.println("4. Eliminar producto");
            System.out.println("5. Listar categorías");
            System.out.println("6. Crear categoría");
            System.out.println("0. Salir");
            System.out.print("Elegí una opción: ");
            opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 1 -> {
                    List<Producto> productos = productoDAO.listarTodos();
                    productos.forEach(System.out::println);
                }
                case 2 -> {
                    System.out.print("Nombre: ");
                    String nombre = scanner.nextLine();
                    System.out.print("Descripción: ");
                    String desc = scanner.nextLine();
                    System.out.print("Precio: ");
                    double precio = Double.parseDouble(scanner.nextLine());
                    System.out.print("Stock: ");
                    int stock = Integer.parseInt(scanner.nextLine());

                    Producto nuevo = new Producto(nombre, desc, precio, stock);
                    productoDAO.crear(nuevo);
                    logger.info("Producto creado: " + nombre);
                }
                case 3 -> {
                    System.out.print("ID del producto a actualizar: ");
                    int id = Integer.parseInt(scanner.nextLine());
                    Producto prod = productoDAO.buscarPorId(id);
                    if (prod != null) {
                        System.out.print("Nuevo nombre: ");
                        prod.setNombre(scanner.nextLine());
                        System.out.print("Nueva descripción: ");
                        prod.setDescripcion(scanner.nextLine());
                        System.out.print("Nuevo precio: ");
                        prod.setPrecio(Double.parseDouble(scanner.nextLine()));
                        System.out.print("Nuevo stock: ");
                        prod.setStock(Integer.parseInt(scanner.nextLine()));

                        productoDAO.actualizar(prod);
                        logger.info("Producto actualizado: " + id);
                    } else {
                        System.out.println("Producto no encontrado.");
                    }
                }
                case 4 -> {
                    System.out.print("ID del producto a eliminar: ");
                    int id = Integer.parseInt(scanner.nextLine());
                    productoDAO.eliminar(id);
                    logger.info("Producto eliminado: " + id);
                }
                case 5 -> {
                    List<Categoria> categorias = categoriaDAO.listarTodos();
                    categorias.forEach(System.out::println);
                }
                case 6 -> {
                    System.out.print("Nombre de la nueva categoría: ");
                    String nombre = scanner.nextLine();
                    Categoria cat = new Categoria(nombre);
                    categoriaDAO.crear(cat);
                    logger.info("Categoría creada: " + nombre);
                }
                case 0 -> System.out.println("Chau, gracias por usar el sistema.");
                default -> System.out.println("Opción no válida.");
            }

        } while (opcion != 0);
    }
}
