package br.com.lucas.locadora.reactive;

import br.com.lucas.MovieRequest;
import br.com.lucas.MovieResponse;
import br.com.lucas.MutinyMovieGrpc;
import br.com.lucas.locadora.models.Movie;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.hibernate.reactive.mutiny.Mutiny;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

//@Singleton
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MovieServiceReactive extends MutinyMovieGrpc.MovieImplBase {

    @Inject
    private Mutiny.Session mutinySession;

    @Override
    public Uni<MovieResponse> findById(MovieRequest request) {
        return mutinySession
                .find( Movie.class, request.getId() )
                .onItem().transformToUni(getPessoaResponseUniFunction());
    }

    //@Override
    public Multi<MovieResponse> findAll(MovieRequest request) {

        Uni<List<Movie>> resultList = mutinySession.createQuery("SELECT f FROM Movie f ORDER BY f.titulo", Movie.class).getResultList();

        Multi<MovieResponse> movieResponseMulti = resultList.onItem().transformToMulti(entity -> Multi.createFrom().items(() -> {
            List<MovieResponse> responses = new ArrayList<>();
            for (Movie movie : entity) {
                responses.add(MovieResponse.newBuilder().setId(movie.getId().toString()).setTitulo(movie.getTitulo()).build());
            }
            return responses.stream();
        }));

        return movieResponseMulti;

    }


    private Function<Movie, Uni<? extends MovieResponse>> getPessoaResponseUniFunction() {
        return entity -> mutinySession.flush()
                                        .onItem().transform(ignore -> MovieResponse.newBuilder().setId(entity.getId().toString()).setTitulo(entity.getTitulo()).build());
    }

}
