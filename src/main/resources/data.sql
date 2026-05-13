-- 1. Limpiar datos existentes (RESTART IDENTITY para empezar IDs desde 1)
TRUNCATE TABLE solicitudes, animales, protectoras, adoptantes, cuentas, favoritos
    RESTART IDENTITY CASCADE;

-- ==========================================
-- 2. CUENTAS DE USUARIO
-- ==========================================

-- Protectora 1: Lugo (Password: 'protectora123')
INSERT INTO cuentas (email, contrasena, rol)
VALUES ('contacto@lucuscan.org', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DM99S7shL.mG', 'PROTECTORA');

-- Protectora 2: Pontevedra (Password: 'protectora123')
INSERT INTO cuentas (email, contrasena, rol)
VALUES ('adopta@pontemascotas.es', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DM99S7shL.mG', 'PROTECTORA');

-- Admin General (Password: 'admin123')[cite: 1]
INSERT INTO cuentas (email, contrasena, rol)
VALUES ('admin@geoadopt.es', '$2a$10$QGMWqLZlJia8irHKTqkY0OOo4kCYEa9pPnrqyuDe.hUww7QLpyuxS', 'ADMIN');

-- protectora@test.com
-- adoptante@test.com
--contraseña 1234
INSERT INTO cuentas (email, contrasena, rol)
VALUES ('adoptante@test.com', '$2a$10$2/NDD8Gcp19n4a4Ce5mpAeeeavJOoLn7zqxVEZ0YusX6Fmx62w7/W', 'ADOPTANTE'),
       ('protectora@test.com', '$2a$10$2/NDD8Gcp19n4a4Ce5mpAeeeavJOoLn7zqxVEZ0YusX6Fmx62w7/W', 'PROTECTORA');

INSERT INTO adoptantes (nombre, apellidos, cuenta_id)
VALUES ('Test', 'Adoptante', 4);
INSERT INTO protectoras (nombre, cif, telefono, direccion, provincia, cuenta_id)
VALUES ('Protectora Test', 'B99999999', '600000000', 'Calle Test 1', 'A Coruña', 5);

-- ==========================================
-- 3. ENTIDADES PROTECTORAS
-- ==========================================
-- Protectora en Lugo
INSERT INTO protectoras (nombre, cif, email_contacto, telefono, direccion, provincia, cuenta_id)
VALUES ('Lucus Can', 'G27123456', 'contacto@lucuscan.org', '982111222', 'Rúa da Protectora s/n', 'Lugo', 1);

-- Protectora en Pontevedra
INSERT INTO protectoras (nombre, cif, email_contacto, telefono, direccion, provincia, cuenta_id)
VALUES ('PonteMascotas', 'G36987654', 'adopta@pontemascotas.es', '986555444', 'Av. de Vigo 45', 'Pontevedra', 2);

-- Admin vinculado como protectora en Ourense para pruebas[cite: 1]
INSERT INTO protectoras (nombre, cif, email_contacto, telefono, direccion, provincia, cuenta_id)
VALUES ('Administración Central GeoAdopt', '00000000A', 'admin@geoadopt.es', '900000000', 'Sede Central Ourense',
        'Ourense', 3);

-- ==========================================
-- 4. LISTADO DE ANIMALES
-- ==========================================

-- ANIMALES DE LUCUS CAN (LUGO - ID: 1)
INSERT INTO animales (nombre, especie, raza, sexo, fecha_nacimiento, fecha_ingreso, estado,
                      tamano, peso, foto_principal, esterilizado, vacunado, desparasitado,
                      microchip, necesidades_especiales, nivel_actividad, apto_perros,
                      apto_gatos, apto_ninos, miedos, descripcion, protectora_id)
VALUES
-- Urgente en Lugo
('Thor', 'Perro', 'Pastor Alemán', 'MACHO', '2019-05-20', '2024-01-15', 'URGENTE',
 'Grande', 35.50, 'https://images.pexels.com/photos/434090/pexels-photo-434090.jpeg',
 true, true, true, true, 'Necesita medicación diaria para displasia', 'Medio',
 true, false, true, 'Petardos', 'Thor es un perro noble que busca un hogar sin escaleras.', 1),

-- Disponible en Lugo
('Luna', 'Gato', 'Europeo Común', 'HEMBRA', '2023-06-10', '2024-04-01', 'DISPONIBLE',
 'Pequeño', 3.20, 'https://images.pexels.com/photos/104827/cat-pet-animal-domestic-104827.jpeg',
 true, true, true, true, NULL, 'Alto',
 true, true, true, NULL, 'Luna es una gatita muy sociable y juguetona.', 1),

-- Disponible en Lugo
('Simba', 'Perro', 'Cocker Spaniel', 'MACHO', '2021-11-30', '2024-03-20', 'DISPONIBLE',
 'Mediano', 14.00, 'https://images.pexels.com/photos/4588052/pexels-photo-4588052.jpeg',
 true, true, true, true, NULL, 'Alto',
 true, true, true, 'Agua', 'Simba adora correr pero odia el momento del baño.', 1);

-- ANIMALES DE PONTEMASCOTAS (PONTEVEDRA - ID: 2)
INSERT INTO animales (nombre, especie, raza, sexo, fecha_nacimiento, fecha_ingreso, estado,
                      tamano, peso, foto_principal, esterilizado, vacunado, desparasitado,
                      microchip, necesidades_especiales, nivel_actividad, apto_perros,
                      apto_gatos, apto_ninos, miedos, descripcion, protectora_id)
VALUES
-- Urgente en Pontevedra
('Bimba', 'Perro', 'Bodeguero Mix', 'HEMBRA', '2023-12-01', '2024-04-25', 'URGENTE',
 'Pequeño', 6.50, 'https://images.pexels.com/photos/1108099/pexels-photo-1108099.jpeg',
 false, true, true, true, 'Cachorra muy activa', 'Muy Alto',
 true, true, true, NULL, 'Rescatada de una camada indeseada. Necesita socialización.', 2),

-- Disponible en Pontevedra
('Gris', 'Gato', 'Azul Ruso Mix', 'MACHO', '2020-02-14', '2024-02-10', 'DISPONIBLE',
 'Pequeño', 4.80, 'https://images.pexels.com/photos/1183434/pexels-photo-1183434.jpeg',
 true, true, true, true, NULL, 'Bajo',
 false, true, false, 'Extraños', 'Gris es un gato independiente que busca tranquilidad.', 2),

-- Disponible en Pontevedra
('Rex', 'Perro', 'Labrador Retriever', 'MACHO', '2018-08-05', '2024-04-10', 'DISPONIBLE',
 'Grande', 30.00, 'https://images.pexels.com/photos/1108099/pexels-photo-1108099.jpeg',
 true, true, true, true, NULL, 'Medio',
 true, true, true, 'Coches', 'Rex es el perro ideal para una familia con niños.', 2);

-- ANIMAL ADOPTADO (No se muestra en novedades/urgentes)
INSERT INTO animales (nombre, especie, raza, sexo, fecha_nacimiento, fecha_ingreso, estado,
                      tamano, peso, foto_principal, esterilizado, vacunado, desparasitado,
                      microchip, necesidades_especiales, nivel_actividad, apto_perros,
                      apto_gatos, apto_ninos, miedos, descripcion, protectora_id)
VALUES ('Dante', 'Perro', 'Galgo', 'MACHO', '2020-05-15', '2023-10-10', 'ADOPTADO',
        'Grande', 22.00, 'https://images.pexels.com/photos/12330456/pexels-photo-12330456.jpeg',
        true, true, true, true, NULL, 'Bajo',
        true, true, true, NULL, 'Dante ya disfruta de su nueva vida en Vigo.', 2);