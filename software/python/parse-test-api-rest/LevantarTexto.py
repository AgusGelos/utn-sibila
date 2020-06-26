from support import *


def test():
    fd_res_txt, fd_todo_bin, fd_res_inc_ort, fd_res_cor, fd_examen, fd_g_res_txt, fd_g_preg_txt \
        = leer_configuracion_csv('config.txt', ':')
    respuestas = []
    preguntas = []


    if not os.path.exists('output'):
        os.mkdir('output')

    while True:
        print('Menu:')
        print('0) Salir')
        print('1) Leer archivo')
        print('2) Mostrar archivo en memoria')
        print('3) Grabar archivo de texto')
        print('4) Grabar archivo Binario')
        print('-------------------------')
        print('5) Generar archivo con "correccion ortografica" o "2da chance"')
        print('6) Generar archivo de evaluacion')
        print('7) Interpretar respuestas como grafos')
        print('8) Probar oracion')

        op = input('Seleccione: ')
        clear()

        # Salir
        if op == '0':
            break

        # Leer archivo
        elif op == '1':
            op1 = input("Leer Archivo: \n\t1)Respuestas \n\t2)Preguntas \n\t*)Salir \nSeleccione: ")

            if op1 == '1':
                respuestas = archivo_para_lectura(fd_res_txt, True)
            elif op1 == '2':
                preguntas = archivo_para_lectura(fd_examen, False)

        # Mostrar archivo
        elif op == '2':
            op1 = input("Mostrar archivo: \n\t1)Respuestas \n\t2)Preguntas \n\t*)Salir \nSeleccione: ")

            if op1 == '1':
                if len(respuestas) != 0:
                    print("Respuesta:\n")
                    show(respuestas)
                else:
                    print("No se han cargando respuestas")

            elif op1 == '2':
                if len(preguntas) != 0:
                    print("Preguntas:\n")
                    show(preguntas)
                else:
                    print("No se han cargando preguntas")

        # Grabar archivo
        elif op == '3':
            op1 = input("Grabar Archivo: \n\t1)Respuestas \n\t2)Preguntas \n\t*)Salir \nSeleccione: ")
            if op1 == '1':
                archivo_para_grabacion(respuestas, fd_g_res_txt, True)
            elif op1 == '2':
                archivo_para_grabacion(preguntas, fd_g_preg_txt, False)

        # Archivo binario
        elif op == '4':
            op1 = input("Archivo Binario: \n\t1)Grabar \n\t2)Leer \n\t*)Salir \nSeleccione: ")
            if op1 == '1':
                archivo_para_grabacion([respuestas, preguntas], fd_todo_bin)

            elif op1 == '2':
                respuestas, preguntas = archivo_para_lectura(fd_todo_bin)

        # Generar archivo con correcciones ortograficas
        elif op == '5':
            if len(respuestas) == 0:
                print("Selecciona un archivo de respuestas.")
                respuestas = archivo_para_lectura(fd_res_txt, True)
                print(len(respuestas))

            print('Correccion iniciada.')
            generar_archivo_correccion_ortografica(respuestas)
            print("Listo.")

        # Correccion numerica
        elif op == '6':
            while len(preguntas) == 0:
                print("Selecciona un archivo de preguntas.")
                preguntas = archivo_para_lectura(fd_examen, False)
                print(len(preguntas))

            print('')

            while len(respuestas) == 0:
                print("Selecciona un archivo de respuestas.")
                respuestas = archivo_para_lectura(fd_res_txt, True)
                print(len(respuestas))

            print('Evaluacion iniciada.')
            generar_archivo_evaluacion(preguntas, respuestas)
            print("Listo.")

        elif op == '7':
            while len(preguntas) == 0:
                print("Selecciona un archivo de preguntas.")
                preguntas = archivo_para_lectura(fd_examen, False)
                print(len(preguntas))

            grabar_respuestas_profesor_a_DB(preguntas)

        elif op == '8':
            oracion = input('Oracion? ')
            grabar_respuesta_a_DB(oracion)

        else:
            print('Seleccion Incorrecta!')
        input()
        clear()

test()
