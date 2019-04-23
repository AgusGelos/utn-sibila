from random import *
import pickle
import os.path


# Lee Archivos Binarios


def leer_b(fd):
    # Verifica que el archivo exista
    if not os.path.exists(fd):
        print('Error archivo inexistente')
        return
    # Tamaño de un archivo
    t = os.path.getsize(fd)
    # Verifica que el archivo contenga algo
    if t == 0:
        print('Error archivo vacio')
        return
    # Abre el archivo binario para lectura
    m = open(fd, 'rb')
    # Lee y muestra cada registro guardado
    # m.tell muestra la posicion del puntero
    while m.tell() < t:
        print(to_string(pickle.load(m)))
    m.close()


# Ejemplo de guardar datos en un archivo binario


def read_random(n, fd):
    nom = 'abcdefghijlkmnopqrstuvwxyz'
    m = open(fd, 'wb')
    for i in range(n):
        numero = round(random() * 10000)
        nombre = choice(nom)
        precio = round(random() * 10000, 2)
        nacionalidad = randint(0, 20)
        tipo = randint(0, 30)
        pickle.dump(Plato(numero, nombre, precio, nacionalidad, tipo), m)
        m.flush()
    m.close()

# Ejemplo Definicion de una clase


class Plato:
    def __init__(self, num, nom, precio, nac, tipo):
        self.numero = num
        self.nombre = nom
        self.precio = precio
        self.nacionalidad = nac
        self.tipo = tipo

# Ejemplo mostrar un objeto


def to_string(plato):
    nac = 'ABCDEFGHIJKLMNOPQRSTUVWXYZÁÉÍÓÚ'
    tipo = 'abcdefghijklmnopqrstuvwxyzáéíóú12345678910'
    r = '{:<35}'.format('Numero de identificacion: ' + str(plato.numero))
    r += '{:<30}'.format('Nombre del plato: ' + plato.nombre)
    r += '{:<20}'.format('Precio: ' + str(plato.precio))
    r += '{:<20}'.format('Nacionalidad: ' + nac[plato.nacionalidad])
    r += '{:<10}'.format('Tipo: ' + tipo[plato.tipo])


def show(v):
    for i in range(len(v)):
        print(to_string(v[i]))

# Ejemplo leer una matriz


def leer_mat(mat):
    for i in range(len(mat)):
        for j in range(len(mat[0])):
            if mat[i][j] != 0:
                print('Nacionalidad: ' + str(i) + ' Tipo: ' + str(j) +
                      ' Cant: ' + str(mat[i][j]))

# LEER ARCHIVOS DE TEXTO

def pos_punteros():
    m = open('prueba', 'wt')
    m.write('Universidad')
    pos = m.tell()
    print('Posicion del file pointer / Longitud del archivo:', pos)

    # reposicionamiento correcto...
    m.seek(4, io.SEEK_SET)

    pos = m.tell()
    print('Posicion del file pointer ahora:', pos)

    # reposicionamiento correcto...
    # m.seek(0, io.SEEK_END)

    pos = m.tell()
    print('Posicion del file pointer ahora:', pos)

    # lo siguiente provoca una excepcion...
    m.seek(0, io.SEEK_CUR)

    print('Posicion del file pointer ahora:', pos)
