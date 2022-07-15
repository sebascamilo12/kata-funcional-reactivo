package challenge;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;


@ExtendWith(MockitoExtension.class)
class ReactiveExampleTest {

    @InjectMocks
    ReactiveExample reactiveExample;


    @Test
    void sumaDePuntajes() {
        StepVerifier
                .create(reactiveExample.sumaDePuntajes())
                .expectNext(260)
                .verifyComplete();
    }

    @Test
    void mayorPuntajeDeEstudiante() {
        Flux<Estudiante> flux = reactiveExample.mayorPuntajeDeEstudiante(1);
        StepVerifier
                .create(flux)
                .expectNextMatches(estudiante -> estudiante.getNombre().equals("pedro"))
                .verifyComplete();

    }

    @Test
    void totalDeAsisntenciasDeEstudiantesComMayorPuntajeDe() {
        StepVerifier
                .create(reactiveExample.totalDeAsisntenciasDeEstudiantesConMayorPuntajeDe(75))
                .expectNext(43)
                .verifyComplete();

    }

    @Test
    void elEstudianteTieneAsistenciasCorrectas() {
        var valor = reactiveExample.elEstudianteTieneAsistenciasCorrectas(
                new Estudiante("andres", 35, List.of(4, 2, 4, 3, 5)));
        StepVerifier
                .create(valor)
                .expectNext(true)
                .verifyComplete();


    }

    @Test
    void promedioDePuntajesPorEstudiantes() {

        StepVerifier
                .create(reactiveExample.promedioDePuntajesPorEstudiantes())
                .expectNext(52.0)
                .verifyComplete();

    }

    @Test
    void estudiantesAprovados() {

        StepVerifier
                .create(reactiveExample.estudiantesAprovados())
                .expectNext("juan")
                .expectNext("pedro")
                .verifyComplete();
    }

    @Test
    void losNombresDeEstudianteConPuntajeMayorA() {
        StepVerifier
                .create(reactiveExample.losNombresDeEstudianteConPuntajeMayorA(75))
                .expectNext("juan")
                .expectNext("pedro")
                .verifyComplete();

    }
}