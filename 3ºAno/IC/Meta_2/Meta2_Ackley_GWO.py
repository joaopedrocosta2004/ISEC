from SwarmPackagePy import gwo
import numpy as np


# Definir a função objetivo, por exemplo, a função Ackley
def ackley_function(x):
    a = 20
    b = 0.2
    c = 2 * np.pi
    d = len(x)  # Dimensão do vetor de entrada
    sum_sq_term = -a * np.exp(-b * np.sqrt(sum(x**2) / d))
    cos_term = -np.exp(sum(np.cos(c * x) / d))
    result = a + np.exp(1) + sum_sq_term + cos_term
    return result


# Parâmetros do GWO
n_agents = 100
n_iterations = 100
dimension = 3  # Duas dimensões
lower_bound = -32.768
upper_bound = 32.768


# Executar o algoritmo GWO
optimizer = gwo(n_agents, ackley_function, lower_bound, upper_bound, dimension, n_iterations)
best_position = optimizer.get_Gbest()


# Exibir os resultados
print(f"Melhor posição: {best_position}")