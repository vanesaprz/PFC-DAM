-- 1. Limpiar datos existentes (TRUNCATE con CASCADE borra las tablas y sus relaciones)
TRUNCATE TABLE solicitudes, animales, protectoras, adoptantes, cuentas, favoritos
    RESTART IDENTITY CASCADE;

-- 2. Insertar Protectora
INSERT INTO cuentas (email, contrasena, rol)
VALUES ('info@huellas.org', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DM99S7shL.mG', 'PROTECTORA');

INSERT INTO protectoras (nombre, cif, email_contacto, telefono, direccion, provincia, cuenta_id)
VALUES ('Huellas Felices', 'B47357428', 'info@huellas.org', '600111222', 'Calle Principal 1', 'Pontevedra', 1);


-- Insertar Cuenta Admin (Password: 'admin123')
INSERT INTO cuentas (email, contrasena, rol)
VALUES ('admin@geoadopt.es', '$2a$10$QGMWqLZlJia8irHKTqkY0OOo4kCYEa9pPnrqyuDe.hUww7QLpyuxS', 'ADMIN');

-- Si quieres que el Admin también sea una protectora para probar el panel:
INSERT INTO protectoras (nombre, cif, email_contacto, telefono, direccion, provincia, cuenta_id)
VALUES ('Administración General', '00000000A', 'admin@geoadopt.es', '900000000', 'Sede Central', 'Pontevedra',
        (SELECT id FROM cuentas WHERE email = 'admin@geoadopt.es'));


-- 1. CASOS URGENTES (4 animales) - Aparecerán en la sección SOS
INSERT INTO animales (nombre, especie, raza, sexo, fecha_nacimiento, fecha_ingreso, estado,
                      tamano, peso, foto_principal, esterilizado, vacunado, desparasitado,
                      microchip, necesidades_especiales, nivel_actividad, apto_perros,
                      apto_gatos, apto_ninos, miedos, descripcion, protectora_id)
VALUES ('Rocco', 'Perro', 'Mastín Español', 'MACHO', '2017-04-12', '2024-01-20', 'URGENTE',
        'Grande', 52.40, 'https://images.unsplash.com/photo-1537151608828-ea2b11777ee8',
        true, true, true, true, 'Artrosis leve, toma condroprotectores', 'Bajo',
        true, false, true, 'Truenos',
        'Rocco es un abuelito bondadoso que ha pasado toda su vida en una finca. Merece un sofá caliente.', 1),
       ('Nala', 'Gato', 'Siamés', 'HEMBRA', '2023-11-05', '2024-03-02', 'URGENTE',
        'Pequeño', 2.80, 'https://images.unsplash.com/photo-1513360371669-4adaa10f762b',
        true, true, true, true, 'Alimentación especial para digestión sensible', 'Alto',
        false, true, false, 'Ruidos fuertes',
        'Nala es muy nerviosa y el refugio la estresa mucho. Necesita salir pronto.', 1),
       ('Bruno', 'Perro', 'Bodeguero', 'MACHO', '2021-06-15', '2024-02-10', 'URGENTE',
        'Mediano', 12.50, 'https://images.unsplash.com/photo-1583337130417-3346a1be7dee',
        true, true, true, true, NULL, 'Muy Alto',
        true, true, true, 'Hombres desconocidos',
        'Bruno es energía pura. Se marca como urgente porque lleva mucho tiempo esperando y se está deprimiendo.', 1),
       ('Cloe', 'Gato', 'Persa', 'HEMBRA', '2019-09-30', '2024-03-25', 'URGENTE',
        'Pequeño', 4.10, 'https://images.unsplash.com/photo-1514888286974-6c03e2ca1dba',
        true, true, true, true, 'Requiere limpieza de ojos diaria', 'Bajo',
        false, false, true, NULL, 'Cloe es una gata de hogar que fue abandonada por fallecimiento de su dueño.', 1);

-- 2. DISPONIBLES (6 animales) - Aparecerán en Novedades (según fecha de ingreso)
INSERT INTO animales (nombre, especie, raza, sexo, fecha_nacimiento, fecha_ingreso, estado,
                      tamano, peso, foto_principal, esterilizado, vacunado, desparasitado,
                      microchip, necesidades_especiales, nivel_actividad, apto_perros,
                      apto_gatos, apto_ninos, miedos, descripcion, protectora_id)
VALUES ('Zas', 'Perro', 'Border Collie', 'MACHO', '2022-08-10', '2024-04-18', 'DISPONIBLE',
        'Mediano', 19.00, 'https://images.unsplash.com/photo-1503256207526-0d5d80fa2f47',
        true, true, true, true, NULL, 'Muy Alto',
        true, true, true, NULL, 'El más reciente en llegar. Inteligente, ágil y listo para aprender trucos.', 1),
       ('Mora', 'Gato', 'Europeo Negro', 'HEMBRA', '2023-12-15', '2024-04-15', 'DISPONIBLE',
        'Pequeño', 3.00, 'https://images.unsplash.com/photo-1548247416-ec66f4900b2e',
        false, true, true, false, NULL, 'Medio',
        true, true, true, NULL, 'Cachorrita juguetona rescatada de un motor de coche.', 1),
       ('Balto', 'Perro', 'Husky Siberiano', 'MACHO', '2020-02-28', '2024-04-10', 'DISPONIBLE',
        'Grande', 28.50, 'https://images.unsplash.com/photo-1537815781210-d6694ad87fe9',
        true, true, true, true, NULL, 'Alto',
        true, false, false, 'Calor excesivo', 'Un perro precioso que necesita largos paseos por el monte.', 1),
       ('Kira', 'Perro', 'Galgo', 'HEMBRA', '2021-05-22', '2024-04-05', 'DISPONIBLE',
        'Grande', 21.00, 'https://images.unsplash.com/photo-1597113366853-9a93ad3afecb',
        true, true, true, true, NULL, 'Medio',
        true, false, true, 'Coches', 'Elegante y silenciosa. Le encanta dormir en camas acolchadas.', 1),
       ('Pipo', 'Perro', 'Beagle', 'MACHO', '2023-01-14', '2024-03-28', 'DISPONIBLE',
        'Mediano', 14.20, 'https://images.unsplash.com/photo-1537151608828-ea2b11777ee8',
        false, true, true, true, NULL, 'Muy Alto',
        true, false, true, NULL, 'Rastreador incansable. Siempre tiene la nariz pegada al suelo.', 1),
       ('Sombra', 'Gato', 'Azul Ruso Mix', 'HEMBRA', '2021-10-10', '2024-03-20', 'DISPONIBLE',
        'Pequeño', 4.20, 'https://images.unsplash.com/photo-1513245543132-31f507417b26',
        true, true, true, true, NULL, 'Bajo',
        false, false, false, 'Extraños', 'Sombra busca una persona tranquila, es algo desconfiada al inicio.', 1);

-- 3. ADOPTADO (1 animal) - No saldrá en el Home
INSERT INTO animales (nombre, especie, raza, sexo, fecha_nacimiento, fecha_ingreso, estado,
                      tamano, peso, foto_principal, esterilizado, vacunado, desparasitado,
                      microchip, necesidades_especiales, nivel_actividad, apto_perros,
                      apto_gatos, apto_ninos, miedos, descripcion, protectora_id)
VALUES ('Lucky', 'Perro', 'Labrador', 'MACHO', '2018-03-10', '2023-11-10', 'ADOPTADO',
        'Grande', 32.00, 'https://images.unsplash.com/photo-1552053831-71594a27632d',
        true, true, true, true, NULL, 'Medio',
        true, true, true, NULL, 'Lucky ya vive feliz con su nueva familia en el campo.', 1);


-- Actualización de fotos para Balto, Kira y Nala con enlaces nuevos
UPDATE animales
SET foto_principal = 'https://images.pexels.com/photos/3663082/pexels-photo-3663082.jpeg'
WHERE nombre = 'Balto';
UPDATE animales
SET foto_principal = 'https://cdn.pixabay.com/photo/2014/11/30/14/11/cat-551554_1280.jpg'
WHERE nombre = 'Nala';

UPDATE animales
SET foto_principal = 'https://cdn.pixabay.com/photo/2017/09/25/13/12/dog-2785074_1280.jpg'
WHERE nombre = 'Kira';