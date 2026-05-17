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
VALUES ('admin@patitasgal.es', '$2a$10$QGMWqLZlJia8irHKTqkY0OOo4kCYEa9pPnrqyuDe.hUww7QLpyuxS', 'ADMIN');

-- protectora@test.com
-- adoptante@test.com
--contraseña 12345
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
VALUES ('Administración Central PatitasGal', '00000000A', 'admin@PatitasGal.es', '900000000', 'Sede Central Ourense',
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
('Thor', 'PERRO', 'Pastor Alemán', 'MACHO', '2019-05-20', '2024-01-15', 'URGENTE',
 'GRANDE', 35.50, 'https://images.pexels.com/photos/434090/pexels-photo-434090.jpeg',
 true, true, true, true, 'Necesita medicación diaria para displasia', 'Medio',
 true, false, true, 'Petardos', 'Thor es un perro noble que busca un hogar sin escaleras.', 2),

-- Disponible en Lugo
('Luna', 'GATO', 'Europeo Común', 'HEMBRA', '2023-06-10', '2024-04-01', 'DISPONIBLE',
 'PEQUENO', 3.20, 'https://images.pexels.com/photos/104827/cat-pet-animal-domestic-104827.jpeg',
 true, true, true, true, NULL, 'Alto',
 true, true, true, NULL, 'Luna es una gatita muy sociable y juguetona.', 2),

-- Disponible en Lugo
('Simba', 'PERRO', 'Cocker Spaniel', 'MACHO', '2021-11-30', '2024-03-20', 'DISPONIBLE',
 'MEDIANO', 14.00, 'https://images.pexels.com/photos/4588052/pexels-photo-4588052.jpeg',
 true, true, true, true, NULL, 'Alto',
 true, true, true, 'Agua', 'Simba adora correr pero odia el momento del baño.', 2);

-- ANIMALES DE PONTEMASCOTAS (PONTEVEDRA - ID: 2)
INSERT INTO animales (nombre, especie, raza, sexo, fecha_nacimiento, fecha_ingreso, estado,
                      tamano, peso, foto_principal, esterilizado, vacunado, desparasitado,
                      microchip, necesidades_especiales, nivel_actividad, apto_perros,
                      apto_gatos, apto_ninos, miedos, descripcion, protectora_id)
VALUES
-- Urgente en Pontevedra
('Bimba', 'PERRO', 'Bodeguero Mix', 'HEMBRA', '2023-12-01', '2024-04-25', 'URGENTE',
 'PEQUENO', 6.50, 'https://images.pexels.com/photos/1805164/pexels-photo-1805164.jpeg',
 false, true, true, true, 'Cachorra muy activa', 'Muy Alto',
 true, true, true, NULL, 'Rescatada de una camada indeseada. Necesita socialización.', 3),

-- Disponible en Pontevedra
('Gris', 'GATO', 'Azul Ruso Mix', 'MACHO', '2020-02-14', '2024-02-10', 'DISPONIBLE',
 'PEQUENO', 4.80, 'https://images.pexels.com/photos/1183434/pexels-photo-1183434.jpeg',
 true, true, true, true, NULL, 'Bajo',
 false, true, false, 'Extraños', 'Gris es un gato independiente que busca tranquilidad.', 3),

-- Disponible en Pontevedra
('Rex', 'PERRO', 'Labrador Retriever', 'MACHO', '2018-08-05', '2024-04-10', 'DISPONIBLE',
 'GRANDE', 30.00, 'https://images.pexels.com/photos/2774140/pexels-photo-2774140.jpeg',
 true, true, true, true, NULL, 'Medio',
 true, true, true, 'Coches', 'Rex es el perro ideal para una familia con niños.', 3);

INSERT INTO animales (nombre, especie, raza, sexo, fecha_nacimiento, fecha_ingreso, estado,
                      tamano, peso, foto_principal, esterilizado, vacunado, desparasitado,
                      microchip, necesidades_especiales, nivel_actividad, apto_perros,
                      apto_gatos, apto_ninos, miedos, descripcion, protectora_id)
VALUES ('Nala', 'GATO', 'Siamés Mix', 'HEMBRA', '2020-03-15', '2024-05-01', 'URGENTE',
        'PEQUENO', 3.80, 'https://images.pexels.com/photos/45201/kitty-cat-kitten-pet-45201.jpeg',
        true, true, true, true, 'Necesita medicación para enfermedad renal', 'Bajo',
        false, true, false, 'Ruidos fuertes', 'Nala es una gata tranquila que busca un hogar sin perros.', 2),

       ('Rocky', 'PERRO', 'Boxer Mix', 'MACHO', '2018-07-22', '2024-04-15', 'URGENTE',
        'GRANDE', 28.00, 'https://images.pexels.com/photos/1254140/pexels-photo-1254140.jpeg',
        true, true, true, true, NULL, 'Alto',
        true, false, true, NULL, 'Rocky es un perrazo con mucha energía que necesita espacio.', 3),

       ('Mia', 'PERRO', 'Caniche', 'HEMBRA', '2022-01-10', '2024-05-10', 'URGENTE',
        'PEQUENO', 4.20, 'https://images.pexels.com/photos/1458916/pexels-photo-1458916.jpeg',
        true, true, true, true, NULL, 'Medio',
        true, true, true, 'Tormentas', 'Mia es una perrita muy cariñosa que se lleva bien con todos.', 2),

-- ANIMALES DISPONIBLES ADICIONALES
       ('Coco', 'PERRO', 'Golden Retriever', 'MACHO', '2022-05-20', '2024-03-01', 'DISPONIBLE',
        'GRANDE', 29.00, 'https://images.pexels.com/photos/2253275/pexels-photo-2253275.jpeg',
        true, true, true, true, NULL, 'Alto',
        true, true, true, NULL, 'Coco es un golden muy juguetón ideal para familias activas.', 2),

       ('Lola', 'GATO', 'Persa Mix', 'HEMBRA', '2021-08-15', '2024-02-20', 'DISPONIBLE',
        'PEQUENO', 3.50, 'https://images.pexels.com/photos/617278/pexels-photo-617278.jpeg',
        true, true, true, true, NULL, 'Bajo',
        false, true, true, 'Extraños', 'Lola es una gata tranquila perfecta para un hogar sin mucho ruido.', 3),

       ('Max', 'PERRO', 'Dálmata', 'MACHO', '2021-03-10', '2024-01-30', 'DISPONIBLE',
        'GRANDE', 26.00, 'https://images.pexels.com/photos/1633522/pexels-photo-1633522.jpeg',
        true, true, true, true, NULL, 'Alto',
        true, false, true, 'Coches', 'Max es un dálmata muy activo que necesita mucho ejercicio.', 2),

       ('Kira', 'GATO', 'Maine Coon Mix', 'HEMBRA', '2020-11-05', '2024-03-15', 'DISPONIBLE',
        'MEDIANO', 5.20, 'https://images.pexels.com/photos/596590/pexels-photo-596590.jpeg',
        true, true, true, true, NULL, 'Medio',
        true, true, true, NULL, 'Kira es una gata grande y majestuosa muy sociable.', 3);


-- ANIMAL ADOPTADO (No se muestra en novedades/urgentes)
INSERT INTO animales (nombre, especie, raza, sexo, fecha_nacimiento, fecha_ingreso, estado,
                      tamano, peso, foto_principal, esterilizado, vacunado, desparasitado,
                      microchip, necesidades_especiales, nivel_actividad, apto_perros,
                      apto_gatos, apto_ninos, miedos, descripcion, protectora_id)
VALUES ('Dante', 'PERRO', 'Galgo', 'MACHO', '2020-05-15', '2023-10-10', 'ADOPTADO',
        'GRANDE', 22.00, 'https://images.pexels.com/photos/12330456/pexels-photo-12330456.jpeg',
        true, true, true, true, NULL, 'Bajo',
        true, true, true, NULL, 'Dante ya disfruta de su nueva vida en Vigo.', 3);

INSERT INTO animales (nombre, especie, raza, sexo, fecha_nacimiento, fecha_ingreso, estado,
                      tamano, peso, foto_principal, esterilizado, vacunado, desparasitado,
                      microchip, necesidades_especiales, nivel_actividad, apto_perros,
                      apto_gatos, apto_ninos, miedos, descripcion, protectora_id)
VALUES ('Pipo', 'PERRO', 'Mestizo', 'MACHO', '2022-03-10', '2024-06-01', 'DISPONIBLE',
        'PEQUENO', 5.00, 'https://images.pexels.com/photos/1805164/pexels-photo-1805164.jpeg',
        true, true, true, true, NULL, 'Alto',
        true, true, true, NULL, 'Pipo es un perrito muy alegre y juguetón.', 1),

       ('Canela', 'PERRO', 'Mestiza', 'HEMBRA', '2020-07-15', '2024-05-20', 'DISPONIBLE',
        'MEDIANO', 12.00, 'https://images.pexels.com/photos/1490908/pexels-photo-1490908.jpeg',
        true, true, true, true, NULL, 'Medio',
        true, false, true, 'Truenos', 'Canela es una perra muy cariñosa que busca un hogar tranquilo.', 1),

       ('Michi', 'GATO', 'Europeo Común', 'MACHO', '2023-01-20', '2024-04-10', 'URGENTE',
        'PEQUENO', 2.80, 'https://images.pexels.com/photos/1170986/pexels-photo-1170986.jpeg',
        false, true, true, false, 'Necesita revisión ocular', 'Bajo',
        false, true, false, 'Extraños', 'Michi es un gatito tímido que necesita un hogar tranquilo sin perros.', 1);