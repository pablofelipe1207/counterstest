## Requerimientos técnicos para construir el proyecto

- Definición de la arquitectura : Clean Architecture
- Retrofit para Consumo de servicio 
- Room para la Persistencia de Datos
- Navigation para la Navegacion
- Componentes de Android Fragment, Activity
- JETPACK : LifeCycles, LiveData, Navigation, Room, ViewModel
- Soporte Minimo de Api es 21


## Breve descripción de la responsabilidad de cada capa propuesta.

La Capa de Presentación contiene UI (Actividades y Fragmentos) que son coordinados por los ViewModels que ejecutan 1 o múltiples casos de uso. La capa de presentación depende de la capa de dominio.

La Capa de Dominio es la parte más INTERIOR de la cebolla (sin dependencias con otras capas) y contiene Entidades, Casos de Uso e Interfaces de Repositorio. Los casos de uso combinan datos de 1 o múltiples Interfaces de Repositorio.

La capa de datos contiene implementaciones de repositorios y una o varias fuentes de datos. Los Repositorios son responsables de coordinar los datos de las diferentes Fuentes de Datos. La capa de datos depende de la capa de dominio.

## Screenshots 

## Home

![](http://pfmiranda.com/img/home.png)

## Main

![](http://pfmiranda.com/img/main.png)

## Create

![](http://pfmiranda.com/img/create.png)


## Search

![](http://pfmiranda.com/img/search.png)
![](http://pfmiranda.com/img/search1.png)

## Examples

![](http://pfmiranda.com/img/examples.png)


