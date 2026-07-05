# TRABAJO_GRUPO

## Integrantes

- Rodríguez Esteban
- Rodríguez Mateo
- Rodríguez Fernanda
- Perugachi Melany
- Pilatuña David
- Pillajo Alessandro
- Quiguango Alisson
- Tenelanda Shirley

---

## Base de Datos

### Creación de la Base de Datos

```sql
CREATE DATABASE db_electrodomesticos;
USE db_electrodomesticos;

CREATE TABLE electrodomesticos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    stock INT NOT NULL,
    precio DECIMAL(10,2) NOT NULL
);

INSERT INTO electrodomesticos (nombre, stock, precio) VALUES
('Refrigeradora', 10, 850.50),
('Microondas', 15, 180.00),
('Televisor', 8, 650.99),
('Lavadora', 5, 720.75),
('Licuadora', 20, 65.50);
```

### Descripción

Este proyecto implementa una base de datos denominada **db_electrodomesticos**, que permite almacenar información sobre productos electrodomésticos incluyendo:

- Identificador único.
- Nombre del producto.
- Stock disponible.
- Precio del producto.

### Datos de Ejemplo

| ID | Nombre | Stock | Precio |
|----|---------|--------|---------|
| 1 | Refrigeradora | 10 | 850.50 |
| 2 | Microondas | 15 | 180.00 |
| 3 | Televisor | 8 | 650.99 |
| 4 | Lavadora | 5 | 720.75 |
| 5 | Licuadora | 20 | 65.50 |

---

**Escuela Politécnica Nacional**  
**Trabajo Grupal**
