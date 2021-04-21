package br.com.lucas.locadora.models;

import javax.persistence.*;

@Entity
@Table(name = "movie")
public class Movie {

    @Id
    @SequenceGenerator(name = "moviesSequence", sequenceName = "movies_id_seq", allocationSize = 1, initialValue = 10)
    @GeneratedValue(generator = "moviesSequence")
    private Integer id;

    private String titulo;

    public Movie(){}

    public Movie(Integer id, String titulo) {
        this.id = id;
        this.titulo = titulo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @Override
    public String toString() {
        return "Pessoa{" +
                "id=" + id +
                ", nome='" + titulo + '\'' +
                '}';
    }
}
