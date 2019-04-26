import pickle
import os
import csv
import requests

# FUNCIONES BASICAS

# Limpiar pantalla


def clear():
    os.system('cls')


# FUNCIONES DEL PROGRAMA

# CLASES

class Respuesta:
    def __init__(self, id_alu, nom_alu, id_examen, id_preg, txt_res):
        self.id_alumno = id_alu
        self.nom_alumno = nom_alu
        self.id_examen = id_examen
        self.id_pregunta = id_preg
        self.txt_respuesta = txt_res


class Pregunta:
    def __init__(self, id_examen, id_preg, txt_preg, txt_res, v_res):
        self.id_examen = id_examen
        self.id_pregunta = id_preg
        self.txt_pregunta = txt_preg
        self.txt_inicio_res = txt_res
        self.v_respuestas = v_res


def to_string_respuesta(respuesta):
    r = "ID Alumno: " + str(respuesta.id_alumno) + '\n'
    r += 'Nombre del Alumno: ' + respuesta.nom_alumno + '\n'
    r += 'ID Examen: ' + str(respuesta.id_examen) + '\n'
    r += 'ID Pregunta: ' + str(respuesta.id_pregunta) + '\n'
    r += 'Respuesta del Alumno: ' + respuesta.txt_respuesta + '\n'
    return r


def to_string_pregunta(pregunta):
    r = 'ID Examen: ' + str(pregunta.id_examen) + '\n'
    r += 'ID Pregunta: ' + str(pregunta.id_pregunta) + '\n'
    r += 'Pregunta del profesor: ' + pregunta.txt_pregunta + '\n'
    for i in range(len(pregunta.v_respuestas)):
        r += 'Respuesta del profesor: ' + pregunta.txt_inicio_res + ' | ' + pregunta.v_respuestas[i] + '\n'
    return r


def show_respuestas(v):
    for i in range(len(v)):
        print(to_string_respuesta(v[i]))


def show_preguntas(v):
    for i in range(len(v)):
        print(to_string_pregunta(v[i]))


# Funcion para pasar de vector a una clase y de una clase a un vector

def vector_a_respuesta(v):
    return Respuesta(int(v[0]), v[1], int(v[2]), int(v[3]), v[4])


def vector_a_pregunta(res, respuestas):
    # Busca si ya existe la pregunta y si existe le agrega el nuevo final de respuesta
    for i in range(len(respuestas)):
        if respuestas[i].id_examen == int(res[0]) and \
                        respuestas[i].id_pregunta == int(res[1]):
            respuestas[i].v_respuestas.append(res[4])
            return

    # Crea una nueva pregunta con todos los datos
    return Pregunta(int(res[0]), int(res[1]), res[2], res[3], [res[4]])


def respuesta_a_vector(res):
    return [res.id_alumno, res.nom_alumno, res.id_examen, res.id_pregunta, res.txt_respuesta]


def pregunta_a_vector(pregunta, j):
    return [pregunta.id_examen, pregunta.id_pregunta, pregunta.txt_pregunta,
            pregunta.txt_inicio_res, pregunta.v_respuestas[j]]

# FUNCIONES DE ARCHIVOS


def verif_exis_archivo(fd):
    if not os.path.exists(fd):
        return False
    # Tamaño de un archivo
    t = os.path.getsize(fd)
    # Verifica que el archivo contenga algo
    if t == 0:
        return False
    return True

# ARCHIVOS DE TEXTO
# FUNCIONES CSV


def leer_mostrar_csv(fd, divisor):
    with open(fd, newline='') as csvfile:
        spamreader = csv.reader(csvfile, delimiter=divisor, quotechar='|')
        for row in spamreader:
            print(', '.join(row))


def leer_respuestas_csv(fd, divisor):
    v = []
    flag = True
    with open(fd, newline='', encoding='UTF-8') as csvfile:
        spamreader = csv.reader(csvfile, delimiter=divisor, quotechar='|')
        for row in spamreader:
            if flag:
                flag = False
            else:
                v.append(vector_a_respuesta(row))
    return v


def leer_preguntas_csv(fd, divisor):
    v = []
    flag = True
    with open(fd, newline='', encoding='UTF-8') as csvfile:
        spamreader = csv.reader(csvfile, delimiter=divisor, quotechar='|')
        for row in spamreader:
            if flag:
                flag = False
            else:
                res = vector_a_pregunta(row, v)
                if res:
                    v.append(res)

    return v


def leer_configuracion_csv(fd, divisor):
    v = []
    with open(fd, newline='') as csvfile:
        spamreader = csv.reader(csvfile, delimiter=divisor, quotechar='|')
        for row in spamreader:
            v.append(row[1])
    return v


def grabar_respuestas_csv(fd, divisor, respuestas):
    with open(fd, 'w', newline='') as csvfile:
        spamwriter = csv.writer(csvfile, delimiter=divisor,
                                quotechar='|', quoting=csv.QUOTE_MINIMAL)
        for i in range(len(respuestas)):
            spamwriter.writerow(respuesta_a_vector(respuestas[i]))


def grabar_preguntas_csv(fd, divisor, preguntas):
    with open(fd, 'w', newline='') as csvfile:
        spamwriter = csv.writer(csvfile, delimiter=divisor,
                                quotechar='|', quoting=csv.QUOTE_MINIMAL)
        for i in range(len(preguntas)):
            for j in range(len(preguntas[i].v_respuestas)):
                spamwriter.writerow(pregunta_a_vector(preguntas[i], j))

# ARCHIVOS BINARIOS


def grabar_arch_bin(v, fd_b):
    m = open(fd_b, 'wb')
    for i in range(len(v)):
        pickle.dump(v[i], m)
    m.close()


def leer_arch_bin(fd_b):
    v = []
    if verif_exis_archivo(fd_b) is False:
        return []
    t = os.path.getsize(fd_b)
    m = open(fd_b, 'rb')
    while m.tell() < t:
        v.append(pickle.load(m))
    m.close()
    return v


# MOSTRAR EN COSNSOLA
# Realiza todas las verificaciones pertinentes a la lectura de un archivo incluyendo el tipo


def archivo_para_lectura(fd, op_res=True):
    preguntas = []

    while True:
        fd1 = input('IngreseArchivo (' + fd + '): ')

        if fd1 == '':
            fd1 = fd

        # Si el archivo es de texto
        if fd1[-4:] == '.txt':
            if op_res:
                preguntas = leer_respuestas_csv(fd1, '|')
            else:
                preguntas = leer_preguntas_csv(fd1, '|')

        # Si el archivo es binario
        elif fd1[-4:] == '.bin':
            preguntas = leer_arch_bin(fd1)

        # Si la extension en diferente
        if len(preguntas) == 0:
            print("Error: Archivo inexistente, vacio o con extension incorrecta")
            op1 = input("Ingresar de nuevo: \n\t1)Si \n\t2)No \nSeleccione: ")

            if op1 != '1':
                break

            else:
                clear()
        else:
            print("El archivo se ha leido exitosamente")
            break

    return preguntas


def archivo_para_grabacion(v, fd, op=True):
    ext = fd[-4:]
    if len(v) != 0:
        fd1 = input('IngreseArchivo (' + fd + '): ')

        if fd1 == '':
            fd1 = fd

        if fd1[-4:] != ext:
            fd1 += ext
            print("El archivo se ha modificado a " + fd1)

        if ext == '.txt':
            if op:
                grabar_respuestas_csv(fd, '|', v)
            else:
                grabar_preguntas_csv(fd, '|', v)
        else:
            # Este se puede usar para grabar todos los arch binarios
            grabar_arch_bin(v, fd1)
        print("El archivo se ha grabado exitosamente")

    else:
        print("No se han cargado preguntas")

# A partir del set de respuestas, genera un archivo de esas mismas respuestas, corregidas
def generar_archivo_correccion_ortografica(respuestas):
    with open('output/respuestas_correccion_ortografica.csv', newline='', mode='w') as respuestas_file:
        respuestas_writer = csv.writer(respuestas_file, delimiter='|', quotechar='"', quoting=csv.QUOTE_MINIMAL)
        respuestas_writer.writerow(['Legajo alumno', 'Alumno nombre y apellido', 'IdExamen', 'IdPregunta', 'Respuesta alumno'])

        for entrada in respuestas:
            respuesta = entrada.txt_respuesta
            respuesta_corregida = ''
            
            # Enviamos la respuesta a corregir
            rqst = requests.post('http://localhost:8080/respuesta/corregir', data = {'respuesta':respuesta})

            # Tomamos los terminos
            terminos = rqst.json()['datos']['terminos']
            for termino in terminos:
                # Si hay un error ortografico
                if termino['error']['error']:
                    respuesta_corregida += termino['error']['sugerencias'][0]
                else:
                    respuesta_corregida += termino['nombre']
                respuesta_corregida += ' '
            respuestas_writer.writerow([entrada.id_alumno, entrada.nom_alumno, entrada.id_examen, entrada.id_pregunta, respuesta_corregida])

            print(respuesta + ' -> ' + respuesta_corregida)

def generar_archivo_evaluacion(preguntas, respuestas):
    with open('output/respuestas_evaluacion_errores.csv', newline='', mode='w') as errores_file:
        errores_writer = csv.writer(errores_file, delimiter='|', quotechar='"', quoting=csv.QUOTE_MINIMAL)
        errores_writer.writerow(['Legajo alumno', 'IdExamen', 'IdPregunta', 'Respuesta Base', 'Respuesta Alumno', 'Codigo de Error', 'Mensaje'])

        with open('output/respuestas_evaluacion.csv', newline='', mode='w') as respuestas_file:
            respuestas_writer = csv.writer(respuestas_file, delimiter='|', quotechar='"', quoting=csv.QUOTE_MINIMAL)
            respuestas_writer.writerow(['Legajo alumno', 'IdExamen', 'IdPregunta', 'Respuesta Base', 'Respuesta Alumno', 'Calificacion'])

            print('\n')
            for entrada in respuestas:
                respuestaAlumno = entrada.txt_respuesta

                # Buscamos la pregunta correspondiente
                preguntas_correspondientes = [x for x in preguntas if (x.id_examen == entrada.id_examen) and (x.id_pregunta == entrada.id_pregunta)]

                # print("para " + str(entrada.id_examen) + ", " + str(entrada.id_pregunta) + " " + str(len(preguntas_correspondientes)))

                for pregunta in preguntas_correspondientes:
                    for respuestaBase in pregunta.v_respuestas:
                        rqst = requests.post('http://localhost:8080/respuesta/evaluar', data = {'respuestaAlumno':respuestaAlumno, 'respuestaBase':respuestaBase})

                        if rqst.status_code == 200:
                            # Si se proceso correctamente, va al archivo de calificacion
                            calificacion = rqst.json()['datos']['calificacion']
                            respuestas_writer.writerow([entrada.id_alumno, entrada.id_examen, entrada.id_pregunta, respuestaBase, respuestaAlumno, calificacion])

                            print(respuestaBase)
                            print(respuestaAlumno)
                            print(calificacion)
                            print('\n')
                        else:
                            # De lo contrario, al archivo de errores
                            error_code = rqst.status_code
                            
                            mensaje = ''
                            if error_code == 500:
                                mensaje = rqst.json()['mensaje']

                            errores_writer.writerow([entrada.id_alumno, entrada.id_examen, entrada.id_pregunta, respuestaBase, respuestaAlumno, error_code, mensaje])

                            print(respuestaBase)
                            print(respuestaAlumno)
                            print(error_code)
                            print(mensaje)
                            print('\n')

                


