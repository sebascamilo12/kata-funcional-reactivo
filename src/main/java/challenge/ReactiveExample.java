package challenge;

import reactor.core.CoreSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;


public class ReactiveExample {

    public static final int VALOR_PERMITIDO = 15;
    public static final int VALOR_APROBADO = 75;
    private Flux<Estudiante> estudianteList;

    public ReactiveExample() {
        //TODO: convertir los estudiantes a un Flux

        var list = List.of(
                new Estudiante("raul", 30, List.of(1, 2, 1, 4, 5)),
                new Estudiante("andres", 35, List.of(4, 2, 4, 3, 5)),
                new Estudiante("juan", 75, List.of(3, 2, 4, 5, 5)),
                new Estudiante("pedro", 80, List.of(5, 5, 4, 5, 5)),
                new Estudiante("santiago", 40, List.of(4, 5, 4, 5, 5))
        );

        estudianteList = Flux.fromIterable(list);
    }

    //TODO: suma de puntajes
    public Mono<Integer> sumaDePuntajes() {

        return estudianteList.map(Estudiante::getPuntaje)
                .reduce(Integer::sum);
    }


    //TODO: mayor puntaje de estudiante
    public Flux<Estudiante> mayorPuntajeDeEstudiante(int limit) {
        return estudianteList.sort(Comparator.reverseOrder())
                .take(limit);


    }

    //TODO: total de asisntencias de estudiantes con mayor puntaje basado en un  valor
    public Mono<Integer> totalDeAsisntenciasDeEstudiantesConMayorPuntajeDe(int valor) {
        return estudianteList.filter(estudiante -> estudiante.getPuntaje() >= valor)
                .flatMapIterable(estudiante -> estudiante.getAsistencias())
                .reduce(0, Integer::sum);


    }

    //TODO: el estudiante tiene asistencias correctas
    public Mono<Boolean> elEstudianteTieneAsistenciasCorrectas(Estudiante estudiante) {
        return Mono.just(estudiante)
                .filter(estudiante1 -> estudiante1.getAsistencias()
                        .stream()
                        .reduce(0, Integer::sum) >= VALOR_PERMITIDO)
                .map(estudiante1 -> true);


    }

    //TODO: promedio de puntajes por estudiantes
    public Mono<Double> promedioDePuntajesPorEstudiantes() {

        return estudianteList.collect(Collectors.averagingDouble(Estudiante::getPuntaje));
    }


    //TODO: los nombres de estudiante con puntaje mayor a un valor
    public Flux<String> losNombresDeEstudianteConPuntajeMayorA(int valor) {

        return estudianteList
                .filter(estudiante -> estudiante.getPuntaje() >= valor)
                .map(Estudiante::getNombre);
    }


    private Estudiante aprobar(Estudiante estudiante) {
        return Optional.of(estudiante)
                .filter(e -> e.getPuntaje() >= VALOR_APROBADO)
                .map(e -> {
                    var est1 = new Estudiante(e.getNombre(), e.getPuntaje(), e.getAsistencias());
                    est1.setAprobado(true);
                    return est1;
                }).orElseGet(() -> estudiante);
    }

    //TODO: estudiantes aprovados
    public Flux<String> estudiantesAprovados() {
        return estudianteList.map(this::aprobar)
                .filter(Estudiante::isAprobado)
                .map(Estudiante::getNombre)
                ;


    }


}
