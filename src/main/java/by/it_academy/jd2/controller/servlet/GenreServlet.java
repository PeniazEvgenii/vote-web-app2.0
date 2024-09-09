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

        try {
            if(genre.isBlank()) {
                throw new IllegalArgumentException();
            }

            genreService.create(genre);
            req.setAttribute(ATTRIBUTE_ADD_GENRE, "Жанр добавлен в систему");
            doGet(req, resp);
        } catch (IllegalArgumentException e) {
            req.setAttribute(ATTRIBUTE_ERROR_GENRE, "Жанр не добавлен");
            doGet(req, resp);
        }
    }
}
