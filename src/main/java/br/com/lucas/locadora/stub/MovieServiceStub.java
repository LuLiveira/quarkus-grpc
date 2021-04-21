package br.com.lucas.locadora.stub;

import br.com.lucas.MovieAllRequest;
import br.com.lucas.MovieAllResponse;
import br.com.lucas.MovieGrpc;
import br.com.lucas.MovieResponse;
import io.grpc.stub.StreamObserver;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Singleton
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MovieServiceStub extends MovieGrpc.MovieImplBase {


    @Override
    public void findAll(MovieAllRequest request, StreamObserver<MovieAllResponse> responseObserver) {

        MovieAllResponse.Builder builder = MovieAllResponse.newBuilder();
        for (int i = 0; i< 100; i++){
            responseObserver.onNext(builder.addMovies(MovieResponse.newBuilder().setTitulo(""+i).build()).build());
        }
    }
}
