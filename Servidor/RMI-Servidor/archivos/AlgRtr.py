
"""
Algoritmo de retropropagación

Función no lineal: XOR

@author: Rodolfo Romero 
"""
import numpy as np

class MLP():
    def __init__(self, xi, d, tasa_aprendizaje=0.1, epocas=10000):
        """
        Inicializa una instancia de la red neuronal.

        Parámetros:
        - xi: Datos de entrada, una matriz numpy donde cada fila representa una entrada.
        - d: Salida deseada, un vector numpy que contiene las salidas deseadas correspondientes a cada entrada.
        - tasa_aprendizaje: Tasa de aprendizaje para el algoritmo de retropropagación.
        - epocas: Número de iteraciones sobre el conjunto de entrenamiento.

        """
        # Inicialización de la red neuronal
        self.xi = xi
        self.d = d
        self.tasa_aprendizaje = tasa_aprendizaje
        self.epocas = epocas
        self.input_size = len(xi[0])
        self.output_size = 1
        self.hidden_size = 2

        # Inicialización de pesos y sesgos de manera eficiente
        np.random.seed(42)  # Semilla para reproducibilidad
        self.W_input_hidden = np.random.rand(self.input_size, self.hidden_size)
        self.b_hidden = np.zeros((1, self.hidden_size))
        self.W_hidden_output = np.random.rand(self.hidden_size, self.output_size)
        self.b_output = np.zeros((1, self.output_size))

    def sigmoid(self, x):
        """
        Función de activación sigmoidal.

        Parámetros:
        - x: Valor de entrada.

        Retorna:
        - Salida después de aplicar la función sigmoidal.

        """
        return 1 / (1 + np.exp(-x))

    def sigmoid_derivative(self, x):
        """
        Derivada de la función de activación sigmoidal.

        Parámetros:
        - x: Valor de entrada.

        Retorna:
        - Derivada de la función sigmoidal.

        """
        return x * (1 - x)

    def forward(self, x):
        """
        Realiza la propagación hacia adelante (forward pass) en la red neuronal.

        Parámetros:
        - x: Entrada a la red.

        Retorna:
        - Salida de la red después de aplicar las funciones de activación.

        """
        # Capa oculta
        self.hidden_input = np.dot(x, self.W_input_hidden) + self.b_hidden
        self.hidden_output = self.sigmoid(self.hidden_input)

        # Capa de salida
        self.output_input = np.dot(self.hidden_output, self.W_hidden_output) + self.b_output
        self.output = self.sigmoid(self.output_input)

        return self.output

    def backward(self, x, y, output):
        """
        Realiza la retropropagación (backward pass) y actualiza los pesos y sesgos de la red.

        Parámetros:
        - x: Entrada a la red.
        - y: Salida deseada.
        - output: Salida de la red después del forward pass.

        """
        # Calcular gradientes
        error = y - output
        output_delta = error * self.sigmoid_derivative(output)

        error_hidden = output_delta.dot(self.W_hidden_output.T)
        hidden_delta = error_hidden * self.sigmoid_derivative(self.hidden_output)

        # Actualizar pesos y sesgos
        self.W_hidden_output += self.hidden_output.T.dot(output_delta) * self.tasa_aprendizaje
        self.b_output += np.sum(output_delta, axis=0, keepdims=True) * self.tasa_aprendizaje
        self.W_input_hidden += x.T.dot(hidden_delta) * self.tasa_aprendizaje
        self.b_hidden += np.sum(hidden_delta, axis=0, keepdims=True) * self.tasa_aprendizaje

    def entrenar(self):
        """
        Entrena la red neuronal utilizando el algoritmo de retropropagación.

        Imprime el error cada 1000 épocas para monitorear el progreso.

        """
        for epoch in range(self.epocas):
            for i in range(len(self.xi)):
                x = np.array([self.xi[i]])
                y = np.array([[self.d[i]]])

                output = self.forward(x)
                self.backward(x, y, output)

            if epoch % 1000 == 0:
                error = np.mean(np.square(self.d - self.forward(self.xi)))
                print(f"Época {epoch}, Error: {error}")

if __name__ == "__main__":
    # Datos de entrada para XOR
    xi = np.array([[0, 0], [0, 1], [1, 0], [1, 1]])
    d = np.array([0, 1, 1, 0])

    # Parámetros de la red neuronal
    tasa_aprendizaje = 0.1
    epocas = 10000

    # Crear una instancia de la red neuronal
    red_neuronal = MLP(xi, d, tasa_aprendizaje, epocas)

    # Entrenar la red neuronal
    red_neuronal.entrenar()

    # Imprimir resultados o realizar otras operaciones
    for i in range(len(xi)):
        x = np.array([xi[i]])
        print(f"Entrada: {x}, Salida de la red: {red_neuronal.forward(x)}")


