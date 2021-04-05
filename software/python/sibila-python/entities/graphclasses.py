from typing import List
from typing import Optional
from enum import Enum
from pydantic import BaseModel

class TipoTermino(str,Enum):
    CONCEPTO = 'C'
    RELACION = 'R'
    IGNORAR = 'I'

class Termino(BaseModel):
    Nombre: str
    Vista: Optional[str]
    Peso: Optional[float]
    
    #def __init__ (self, nombre: str, vista: str=None):
    #    self.Nombre = nombre
    #    self.Vista = nombre if vista is None else vista 
    #    self.Peso = 1

class Equivalencia(BaseModel):
    Nombre: str
    Peso: float

class Concepto(Termino):
    rid: Optional[str]
    equivalencias: Optional[List[Equivalencia]]
    tipo_termino = TipoTermino.CONCEPTO
    
    #def __init__ (self, nombre: str, vista: str=None):
    #    super().__init__(nombre,vista)


'''
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
'''

class Respuesta (Termino):
    pass

'''
class Concepto (Termino):
    ID: str
    Usuario: str
    Actualizado:str = None

    def __init__ (self, nombre: str, vista: str=None):
        super().__init__(nombre,vista)
'''
class Relacion(Termino):
    rid: Optional[str]
    Usuario: Optional[str]
    Actualizado: Optional[str]
    Class: str
    tipo_termino = TipoTermino.RELACION

    #def __init__ (self, classname: str):
    #    self.Class = classname