from typing import Dict, List
class Grafo:
	# Constructor
	def __init__(self, aristas : Dict[int, List[int]]):
		self.lista_adyacencia : Dict = dict()
		
		# recorre todas las aristas, guardando el nodo inicial y el destino en una tupla
		for (src, end) in aristas:

			# si no se encuentra el nodo inicial
			if not src in self.lista_adyacencia.keys():
				self.lista_adyacencia.update({ src : list() })

			# si no se encuentra el nodo inicial (final, ya que es un grafo no dirigido)
			if not end in self.lista_adyacencia.keys():
				self.lista_adyacencia.update( {end : list()} )

			# cada nodo (key) tiene una lista (value) de nodos adyacentes:

			# se anade a la lista de nodos adyacentes
			self.lista_adyacencia.get(src).append(end)
			self.lista_adyacencia.get(end).append(src)


def DFS(grafo : Grafo, inicial : int, descubiertos : List[int]):
	# crea una lista para simular una PILA
	q = list()

	# anade el nodo inicial como descubierto
	descubiertos.append(inicial)
	
	# apila el primer elemento
	q.append(inicial)

	# se ejecuta mientras la pila no este vacia
	while len(q) != 0:

		# saca el nodo de la pila
		nodo = q.pop(-1)

		print(nodo, end='-')

		# si el nodo no tiene hijos, entonces salta hacia el siguiente ciclo, para no generar una excepcion
		if not nodo in grafo.lista_adyacencia.keys():
			continue

		# para cada arista (u, v)
		for u in grafo.lista_adyacencia.get(nodo):

			# si u no ha sido descubierto
			if not u in descubiertos:
				
				#se apila y agrega a la lista de descubiertos
				descubiertos.append(u)
				q.append(u)
		

if __name__ == '__main__':
	# aristas entre nodos
	aristas = [
	        (66, 44), (44, 22), (22, 9), (22, 37), (37, 39), (44, 50),
	        (50, 47), (66, 85), (85, 73), (85, 90), (90, 88), (90, 94)
	    ]

	# instancia de grafo con la lista de aristas y total de nodos
	grafo = Grafo(aristas)

	# realiza el algoritmo DFS
	DFS(grafo=grafo, inicial=66, descubiertos=list())
