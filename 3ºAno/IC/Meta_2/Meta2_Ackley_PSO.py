import pyswarms as ps
import numpy as np


def ackley_function(x):
    a = 20
    b = 0.2
    c = 2 * np.pi
    d = x.shape[1]  # Número de dimensões (colunas da matriz x)

    sum_sq_term = -a * np.exp(-b * np.sqrt(np.sum(x**2, axis=1) / d))
    cos_term = -np.exp(np.sum(np.cos(c * x), axis=1) / d)
    result = sum_sq_term + cos_term + a + np.exp(1)
    return result


# Definindo os limites do espaço de busca para a função de Ackley
bounds = (np.array([-32.768] * 2), np.array([32.768] * 2))

#bounds = (np.array([-32.768] * 3), np.array([32.768] * 3))

# Configurações de PSO
options = {'c1': 1.0, 'c2': 1.0, 'w': 0.9}

# Inicialização do PSO
optimizer = ps.single.GlobalBestPSO(n_particles=100,
                                    dimensions=2,
                                    options=options,
                                    bounds=bounds)

# Execução do PSO
cost, pos = optimizer.optimize(ackley_function, iters=100)

# Resultado
print(f"Melhor custo encontrado: {cost}")
print(f"Melhor posição encontrada: {pos}")
