from typing import List


class Termino:
    TIPO_CONCEPTO = "C"
    TIPO_RELACION = "R"
    TIPO_IGNORAR = "I"
    ADD_CONCEPTO = "+C"
    ADD_RELACION = "+R"

    Nombre : str
    Vista : str
    Tipo : str
    Accion : str
    ErrorAccion : str
    Peso : float
    SugerenciaTipo : str

    Delta : int     # Desplazamiento del concepto con respecto al mismo concepto en la respuesta base
    Raiz : str      

    SugerenciasCorreccion : List
    PosicionInicioEnTexto : int
    PosicionFinEnTexto : int
    MensajeDeError : str

    def __init__ (self, nombre: str, vista: str=None):
        self.Nombre = nombre
        self.Vista = nombre if vista is None else vista 
        self.Peso = 1

    def __toString__ (self) -> str:
        return "[{} [{}]]".format(self.Nombre,self.Tipo)

    def __str__(self) -> str:
        return self.toString()
    
    def hasErrors (self) -> bool:
        return (len(self.SugerenciasCorreccion) > 0)

    def toString (self,full: bool=False) -> str:
        if (not full):
            return self.toString()
        else:
            return "[%s [%s]] - Vista:%s A:%s E:%s P:%f" % (self.Nombre,self.Tipo,self.Vista,self.Accion,self.ErrorAccion,self.Peso)

    def toStringDelta(self):
        return "[%s Δ=%d]" % (self.Nombre,self.Delta)

class Respuesta (Termino):
    pass

class Concepto (Termino):
    ID: str
    Usuario: str
    Actualizado = None

    def __init__ (self, nombre: str, vista: str=None):
        super().__init__(nombre,vista)

class Relacion:
    ID: str
    Usuario: str
    Actualizado = None
    Class: str

    def __init__ (self, classname: str):
        self.Class = classname