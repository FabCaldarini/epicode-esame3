package it.epicode;

import it.epicode.entities.Libro;
import it.epicode.entities.Rivista;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

public class MainTest {

    public static void main(String[] args) {
        // Make sure you have correct Jakarta EE JPA configuration in your project
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu"); // "pu" is the name of the persistence unit
        EntityManager em = emf.createEntityManager();

        // Example usage
        em.getTransaction().begin();

        // Adding a book
        Libro libro = new Libro("AutoreLibro", "GenereLibro");
        libro.setCodiceISBN(123456789L);
        libro.setTitolo("TitoloLibro");
        libro.setAnnoPubblicazione(2022);
        libro.setNumeroPagine(200);
        em.persist(libro);

        // Adding a magazine
        Rivista rivista = new Rivista();
        rivista.setCodiceISBN(987654321L);
        rivista.setTitolo("TitoloRivista");
        rivista.setAnnoPubblicazione(2021);
        rivista.setNumeroPagine(50);
        rivista.setPeriodicita(Rivista.Periodicita.MENSILE);
        em.persist(rivista);

        em.getTransaction().commit();

        // Retrieve entities
        em.getTransaction().begin();

        // Search by ISBN
        Libro foundLibro = em.find(Libro.class, 123456789L);
        System.out.println(foundLibro);

        // Search by publication year
        List<Libro> libriPerAnno = em.createQuery("SELECT l FROM Libro l WHERE l.annoPubblicazione = :anno", Libro.class)
                .setParameter("anno", 2022)
                .getResultList();
        libriPerAnno.forEach(System.out::println);

        // Search by author
        List<Libro> libriPerAutore = em.createQuery("SELECT l FROM Libro l WHERE l.autore = :autore", Libro.class)
                .setParameter("autore", "AutoreLibro")
                .getResultList();
        libriPerAutore.forEach(System.out::println);

        // Search by title
        List<Libro> libriPerTitolo = em.createQuery("SELECT l FROM Libro l WHERE LOWER(l.titolo) LIKE :titolo", Libro.class)
                .setParameter("titolo", "%Titolo%")
                .getResultList();
        libriPerTitolo.forEach(System.out::println);

        em.getTransaction().commit();

        // Close the EntityManager
        em.close();
        emf.close();
    }
}


