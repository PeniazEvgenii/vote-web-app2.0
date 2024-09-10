package by.it_academy.jd2.controller.servlet;

import by.it_academy.jd2.service.api.IGenreService;
import by.it_academy.jd2.service.factory.ServiceGenreFactory;
import by.it_academy.jd2.util.JspUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static by.it_academy.jd2.controller.servlet.FormForVoteServlet.ATTRIBUTE_REQUEST_GENRES;
import static by.it_academy.jd2.controller.servlet.FormForVoteServlet.PARAMETER_GENRE;
import static by.it_academy.jd2.util.PathUtil.*;

@WebServlet(urlPatterns = GENRE_SERVLET)
public class GenreServlet extends HttpServlet {
    public static final String ERROR_GENRE_NAME = "Название жанра не должно состоять из пробелов";
    public static final String MESSAGE_GENRE_ADDED = "Жанр добавлен в систему под id = ";
    public static final String MESSAGE_GENRE_NOT_ADDED = "Жанр не добавлен";
    public static final String MESSAGE_GENRE_DELETED = "Жанр успешно удален";
    public static final String MESSAGE_GENRE_NOT_DELETED = "Жанр не был удален";

    public static final String PARAMETER_DELETE_GENRE_ERROR = "deleteGenreErr";
    public static final String PARAMETER_DELETE_GENRE = "deleteGenre";
    public static final String ATTRIBUTE_ERROR_GENRE = "janreErr";
    public static final String ATTRIBUTE_ADD_GENRE = "janreAdd";
    public static final String JSP_NAME_GENRE = "janre";

    IGenreService genreService = ServiceGenreFactory.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setAttribute(ATTRIBUTE_REQUEST_GENRES, genreService.getAll());
        req.getRequestDispatcher(JspUtil.getPath(JSP_NAME_GENRE)).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String genre = req.getParameter(PARAMETER_GENRE);
        String deleteGenre = req.getParameter(PARAMETER_DELETE_GENRE);

        if(genre != null) {
            addingGenre(req, genre);
        }

        if(deleteGenre != null) {
            deletionGenre(req, deleteGenre);
        }

        doGet(req, resp);
    }


    private void addingGenre(HttpServletRequest req, String genre) {
        try {
            if(genre.isBlank()) {
                throw new IllegalArgumentException(ERROR_GENRE_NAME);
            }

            Long id = genreService.create(genre);
            req.setAttribute(ATTRIBUTE_ADD_GENRE, MESSAGE_GENRE_ADDED + id);

        } catch (IllegalArgumentException e) {
            req.setAttribute(ATTRIBUTE_ERROR_GENRE, MESSAGE_GENRE_NOT_ADDED + e.getMessage());
        }
    }

    private void deletionGenre(HttpServletRequest req, String deleteGenre) {
        try {
            boolean delete = genreService.delete(Long.valueOf(deleteGenre));

            if (delete) {
                req.setAttribute(PARAMETER_DELETE_GENRE, MESSAGE_GENRE_DELETED);
            } else {
                req.setAttribute(PARAMETER_DELETE_GENRE_ERROR, MESSAGE_GENRE_NOT_DELETED);
            }

        } catch (RuntimeException e) {
            req.setAttribute(PARAMETER_DELETE_GENRE_ERROR, MESSAGE_GENRE_NOT_DELETED + " " + e.getMessage());
        }
    }
}
