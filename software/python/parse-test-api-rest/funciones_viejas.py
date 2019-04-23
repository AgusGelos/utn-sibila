import pickle
import os
import csv

# Pregunta (vieja) con todas los atributos
"""class Pregunta:
    def __init__(self, id_alu, nom_alu, id_examen, nom_examen, id_preg, txt_preg, id_res, txt_res,
                 id_res_doc, txt_res_doc):
        self.id_alumno = id_alu
        self.nom_alumno = nom_alu
        self.id_examen = id_examen
        self.nom_examen = nom_examen
        self.id_pregunta = id_preg
        self.txt_pregunta = txt_preg
        self.id_respuesta = id_res
        self.txt_respuesta = txt_res
        self.id_respuesta_docente = id_res_doc
        self.txt_respuesta_docente = txt_res_doc
"""

# To string clases (vieja)
"""def to_string_respuesta(pregunta):
    r = "ID Alumno: " + str(pregunta.id_alumno) + '\n'
    r += 'Nombre del Alumno: ' + pregunta.nom_alumno + '\n'
    r += 'ID Examen: ' + str(pregunta.id_examen) + '\n'
    r += 'Nombre del Examen: ' + pregunta.nom_examen + '\n'
    r += 'ID Pregunta: ' + str(pregunta.id_pregunta) + '\n'
    r += 'Pregunta: ' + pregunta.txt_pregunta + '\n'
    r += 'ID Respuesta del Alumno: ' + str(pregunta.id_respuesta) + '\n'
    r += 'Respuesta del Alumno: ' + pregunta.txt_respuesta + '\n'
    r += 'ID Respuesta del Profesor: ' + str(pregunta.id_respuesta_docente) + '\n'
    r += 'Respuesta del Profesor: ' + pregunta.txt_respuesta_docente + '\n'
    return r
"""

# Funcion para pasar de vector a una clase
# def vector_a_respuesta(v):
"""def vector_a_respuesta(v):
    return Respuesta(int(v[0]), v[1], int(v[2]), int(v[3]), v[4])
"""

# FUNCIONES DE ARCHIVOS
# Para levantar todo de una
# def leer_arch_txt(fd):
"""
def leer_arch_txt(fd):
    v = []
    if verif_exis_archivo(fd) is False:
        return []
    m = open(fd)
    for line in m:
        # elimina el \n
        if line[-1] == '\n':
            line = line[:-1]
        v.append(vector_a_respuesta(texto_a_vector(line, '|')))
    m.close()
    return v
"""

# Para levantar una linea de texto
# def leer_linea_txt(line):
"""
def leer_linea_txt(line):
    # elimina el \n
    if line[-1] == '\n':
        line = line[:-1]
    return vector_a_respuesta(texto_a_vector(line, ','))
"""

# Lee el archivo de configuracion y extrae los archivos por defecto

# def leer_configuracion(fd):
"""def leer_configuracion(fd):
    v = []
    if verif_exis_archivo(fd) is False:
        return []
    m = open(fd)
    for line in m:
        # elimina el \n
        if line[-1] == '\n':
            line = line[:-1]
        v.append(texto_a_vector(line, ':')[1])
    m.close()
    return v
"""

# MOSTRAR EN COSNSOLA
# Para manejar las preguntas una por una y no ocupar tanta memoria (no se utilizara)
"""def archivo_para_todo(fd):
    while True:
        fd1 = input('IngreseArchivo (' + fd + '): ')
        if fd1 == '':
            fd1 = fd
        if verif_exis_archivo(fd1) is False:
            print("Error: Archivo inexistente, vacio o con extension incorrecta")
            op1 = input("Ingresar de nuevo: \n\t1)Si \n\t2)No \nSeleccione: ")
            if op1 != '1':
                return
            else:
                clear()
                continue
        # Si el archivo es de texto
        if fd1[-3:] == 'txt':
            m = open(fd1)
            for line in m:
                pregunta = leer_linea_txt(line)
                to_string_respuesta(pregunta)
            m.close()

        # Si el archivo es binario
        elif fd1[-3:] == 'bin':
            preguntas = leer_arch_bin(fd1)
        # Si la extension en diferente
        else:
            print('Extension incorrecta')


# Realiza todas las verificaciones pertinentes a la escritura de un archivo incluyendo el tipo
# NOTA: Se puede generalizar a archivos de texto
"""

# ANALISIS DE TEXTO
# Detecta por un separador los distintos campos de una linea y los pasa a un vector

# texto_a_vector (Para configuracion)
"""def texto_a_vector(line, divisor):
    pos_inicio = pos_fin = 0
    v = []
    for car in line:
        pos_fin += 1
        if car == divisor:
            v.append(line[pos_inicio:pos_fin - 1])
            pos_inicio = pos_fin
            while line[pos_inicio] == ' ':
                pos_inicio += 1

    v.append(line[pos_inicio:pos_fin])
    return v
"""
