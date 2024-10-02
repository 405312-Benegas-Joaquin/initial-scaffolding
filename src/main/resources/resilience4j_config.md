## Resilience4j Config

### 1. `resilience4j.circuitbreaker.configs.default.register-health-indicator=true`:
Registra el estado del circuit breaker en el sistema de salud de la aplicación (por ejemplo, en /actuator/health). Así, puedes monitorear el estado del circuito directamente desde los endpoints del actuator.

- **Ejemplo**: Si tu circuito está "abierto" (open), puedes verificarlo en /actuator/circuitbreakers o /actuator/health.

### 2. `resilience4j.circuitbreaker.configs.default.sliding-window-type=COUNT_BASED`:
Indica que el circuito va a contar los fallos y éxitos en función de una cantidad de llamadas (count-based), no de un período de tiempo.

- **Ejemplo**: Si el tamaño de la ventana deslizante es de 10 llamadas, el circuito analizará las últimas 10 llamadas para decidir si se debe abrir o cerrar el circuito, basándose en cuántas de esas llamadas fallaron.

### 3. `resilience4j.circuitbreaker.configs.default.sliding-window-size=10`:
Define el tamaño de la ventana deslizante (sliding window), en este caso, 10 llamadas. El circuito analizará las últimas 10 llamadas para determinar si debe abrirse o cerrarse.

- **Ejemplo**: Si en las últimas 10 llamadas 6 fallaron, y tu umbral de fallos es del 50%, el circuito se abrirá (es decir, bloqueará las siguientes llamadas).

### 4. `resilience4j.circuitbreaker.configs.default.minimum-number-of-calls=5`:
Define el número mínimo de llamadas que deben registrarse antes de que el circuit breaker empiece a evaluar el estado de fallos. En este caso, se necesitan al menos 5 llamadas para comenzar a calcular el estado del circuito.

- **Ejemplo**: Si tu servicio solo ha recibido 4 llamadas, el circuit breaker no evaluará si está fallando, ya que aún no alcanzó el mínimo de 5.

### 5. `resilience4j.circuitbreaker.configs.default.permitted-number-of-calls-in-half-open-state=3`:
Define cuántas llamadas se permitirán en el estado half-open (medio abierto) para probar si el servicio ha vuelto a estar disponible. En este caso, cuando el circuito está medio abierto, permitirá 3 llamadas para ver si son exitosas o fallidas antes de decidir si volver a abrirse o cerrarse.

- **Ejemplo**: Si el circuito está medio abierto, permitirá 3 llamadas. Si 2 de esas 3 llamadas fallan, el circuito volverá a abrirse.

### 6. `resilience4j.circuitbreaker.configs.default.automatic-transition-from-open-to-half-open-enabled=true`:
Habilita la transición automática del estado open (abierto) al estado half-open (medio abierto) después de un período de espera, sin intervención manual.

- **Ejemplo**: Después de que el circuito ha estado abierto (bloqueando llamadas) por el tiempo configurado (por ejemplo, 5 segundos), pasará automáticamente al estado medio abierto para verificar si el servicio se ha recuperado.

### 7. `resilience4j.circuitbreaker.configs.default.wait-duration-in-open-state=5s`:
Define el tiempo que el circuito permanecerá en el estado open (abierto) antes de pasar al estado half-open (medio abierto). En este caso, el circuito se mantendrá abierto durante 5 segundos.

- **Ejemplo**: Después de detectar fallos y abrir el circuito, bloqueará todas las solicitudes durante 5 segundos antes de intentar nuevamente, permitiendo un pequeño número de solicitudes en estado medio abierto.

### 8. `resilience4j.circuitbreaker.configs.default.failure-rate-threshold=50`:
Define el porcentaje de fallos permitido antes de que el circuito se abra. En este caso, si más del 50% de las llamadas fallan en la ventana deslizante (10 llamadas), el circuito se abrirá.

- **Ejemplo**: Si en las últimas 10 llamadas 6 fallaron (60%), el circuito se abrirá, bloqueando las solicitudes hasta que expire el período de espera.

### 9. `resilience4j.circuitbreaker.configs.default.event-consumer-buffer-size=10`:
Configura el tamaño del buffer de eventos que Resilience4j utiliza para almacenar los eventos generados por el circuit breaker (como cuándo cambia de estado). En este caso, mantendrá un buffer de 10 eventos.

- Ejemplo: Si el circuito se abre, cierra o pasa a medio abierto, esos eventos se registrarán hasta un máximo de 10 en el buffer, y podrás visualizarlos en herramientas de monitoreo o logs.

### Resumen de la funcionalidad general:
Esta configuración establece un patrón de circuit breaker que monitorea un número fijo de llamadas (10) y decide si debe abrir o cerrar el circuito basado en la tasa de fallos.
Si el 50% o más de las llamadas fallan en una ventana de 10, el circuito se abre, bloqueando las llamadas durante 5 segundos.
Después de este tiempo, se permiten 3 llamadas en estado half-open para probar si el servicio se ha recuperado.
Todo el comportamiento del circuit breaker se puede monitorear mediante los endpoints de salud y actuator.