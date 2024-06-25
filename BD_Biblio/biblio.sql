-- Crear la base de datos `biblio` si no existe y usarla
IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = 'biblio')
BEGIN
    CREATE DATABASE Biblio;
END;
GO

USE Biblio;
GO

-- Base de datos: `biblio`

-- --------------------------------------------------------
-- Estructura de tabla para la tabla `autor`
-- --------------------------------------------------------

IF OBJECT_ID('autor', 'U') IS NOT NULL
DROP TABLE autor;
GO

CREATE TABLE autor (
  id INT IDENTITY(1,1) PRIMARY KEY,
  autor NVARCHAR(150) NOT NULL
);
GO

-- Volcado de datos para la tabla `autor`
INSERT INTO autor (autor) VALUES
('JOSE ANTONIO GONAZALEZ'),
('SERGIO TALENS'),
('UNIVERSIDAD DE ALICANTE'),
('LINDA OLIVARES');
GO
select*from autor

-- --------------------------------------------------------
-- Estructura de tabla para la tabla `editorial`
-- --------------------------------------------------------

IF OBJECT_ID('editorial', 'U') IS NOT NULL
DROP TABLE editorial;
GO

CREATE TABLE editorial (
  id INT IDENTITY(1,1) PRIMARY KEY,
  editorial NVARCHAR(150) NOT NULL
);
GO

-- Volcado de datos para la tabla `editorial`
INSERT INTO editorial (editorial) VALUES
(N'AMC EDITORES'),
(N'ANDORRA'),
(N'AMORES LIBRO'),
(N'AMASTES DEL PAIS');
GO
select * from editorial

-- --------------------------------------------------------
-- Estructura de tabla para la tabla `estudiantes`
-- --------------------------------------------------------

IF OBJECT_ID('estudiantes', 'U') IS NOT NULL
DROP TABLE estudiantes;
GO

CREATE TABLE estudiantes (
  id INT IDENTITY(1,1) PRIMARY KEY,
  documento VARCHAR(20) NOT NULL,
  codigo VARCHAR(20) NOT NULL,
  nombre VARCHAR(150) NOT NULL,
  telefono VARCHAR(20) NOT NULL,
  nivel VARCHAR(20) NOT NULL,
  grado VARCHAR(20) NOT NULL,
  seccion VARCHAR(20) NOT NULL,
);
GO

-- Volcado de datos para la tabla `estudiantes`
INSERT INTO estudiantes (documento, codigo, nombre, telefono, nivel, grado, seccion) VALUES
('12345678', '02584', 'Sergio Ariza', '900897537','primaria','3','A'),
('32154789', 'p7889', 'Juan Hurtado', '925491523','secundaria','3','B'),
('546645987', 'Po788', 'Ricardo Shinra', '987654123','primaria','4','A');

select * from estudiantes
-- --------------------------------------------------------
-- Estructura de tabla para la tabla `materias`
-- --------------------------------------------------------

IF OBJECT_ID('materias', 'U') IS NOT NULL
DROP TABLE materias;
GO

CREATE TABLE materias (
  id INT IDENTITY(1,1) PRIMARY KEY,
  materia VARCHAR(150) NOT NULL
);
GO

-- Volcado de datos para la tabla `materias`
INSERT INTO materias (materia) VALUES
('BASE DE DATOS'),
('INGLES AVANZADO'),
('DESARROLLO DE SOFTWARE'),
('DISEÑO DE BASE DE DATOS');
GO
select * from materias
-- --------------------------------------------------------
-- Estructura de tabla para la tabla `libros`
-- --------------------------------------------------------

IF OBJECT_ID('libros', 'U') IS NOT NULL
DROP TABLE libros;
GO

CREATE TABLE libros (
  id INT IDENTITY(1,1) PRIMARY KEY,
  titulo VARCHAR(255) NOT NULL,
  id_editorial INT NOT NULL,
  id_autor INT NOT NULL,
  id_materia INT NOT NULL,
  cantidad INT NOT NULL,
  num_pag INT NOT NULL,
  anio_edicion INT NOT NULL,
  FOREIGN KEY (id_editorial) REFERENCES editorial(id),
  FOREIGN KEY (id_autor) REFERENCES autor(id),
  FOREIGN KEY (id_materia) REFERENCES materias(id)
);
GO

-- Volcado de datos para la tabla `libros`
INSERT INTO libros (titulo, id_editorial, id_autor, id_materia, cantidad, num_pag, anio_edicion) VALUES
('EL GRAN LIBRO DE HTML5', 3, 4, 3, 10, 150, 2021),
('APRENDE PYTHON', 2, 2, 3, 20, 230, 1999),
('Javascript Eloquent', 3, 3, 2, 50, 180, 2021),
('LIBRO EDITADO', 2, 2, 2, 20, 150, 2021),
('NUEVO LIBRO', 3, 4, 2, 80, 158, 2020);
GO
select * from libros

-- --------------------------------------------------------
-- Estructura de tabla para la tabla `prestamos`
-- --------------------------------------------------------

IF OBJECT_ID('prestamos', 'U') IS NOT NULL
DROP TABLE prestamos;
GO

CREATE TABLE prestamos (
  id INT IDENTITY(1,1) PRIMARY KEY,
  id_estudiante INT NOT NULL,
  id_libro INT NOT NULL,
  cantidad INT NOT NULL,
  fecha_prestamo NVARCHAR(20) NOT NULL,
  fecha_devolucion NVARCHAR(20) NOT NULL,
  estado INT NOT NULL DEFAULT 1,
  FOREIGN KEY (id_estudiante) REFERENCES estudiantes(id),
  FOREIGN KEY (id_libro) REFERENCES libros(id)
);
GO

-- Volcado de datos para la tabla `prestamos`
INSERT INTO prestamos (id_estudiante, id_libro, cantidad, fecha_prestamo, fecha_devolucion, estado) VALUES
(1, 3, 5, '29/11/2024', '29/11/2024', 0),
(1, 2, 20, '29/11/2024', '30/11/2024', 0),
(1, 5, 20, '29/11/2024', '03/12/2024', 0);
GO
select*from prestamos

-- --------------------------------------------------------
-- Estructura de tabla para la tabla `usuarios`
-- --------------------------------------------------------

IF OBJECT_ID('usuarios', 'U') IS NOT NULL
DROP TABLE usuarios;
GO

CREATE TABLE usuarios (
  id INT IDENTITY(1,1) PRIMARY KEY,
  usuario NVARCHAR(20) NOT NULL,
  nombre NVARCHAR(150) NOT NULL,
  correo NVARCHAR(50) NOT NULL,
  clave NVARCHAR(50) NOT NULL,
);
GO

-- Volcado de datos para la tabla `usuarios`
INSERT INTO usuarios (usuario, nombre, correo, clave) VALUES
('admin', 'Sergio Antonio', 'admin@gmail.com', 'admin'),
('JUAN', 'Juan Carlos', 'juan@gmail.com', '1234');
GO
select * from usuarios