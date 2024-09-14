package by.it_academy.jd2.controller.servlet;

import by.it_academy.jd2.service.api.IArtistService;
import by.it_academy.jd2.service.factory.ServiceArtistFactory;
import by.it_academy.jd2.util.JspUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static by.it_academy.jd2.util.PathUtil.ARTIST_SERVLET;

@WebServlet(urlPatterns = ARTIST_SERVLET)
public class ArtistServlet extends HttpServlet {
    public static final String ERROR_ARTIST_NAME = "Имя артиста не должно состоять из пробелов";
    public static final String MESSAGE_ARTIST_ADDED = "Исполнитель добавлен в систему под id = ";
    public static final String MESSAGE_ARTIST_NOT_ADDED = "Исполнитель не добавлен";
    public static final String MESSAGE_ARTIST_DELETED = "Исполнитель успешно удален";
    public static final String MESSAGE_ARTIST_NOT_DELETED = "Исполнитель не был удален";

    public static final String ATTRIBUTE_ERROR_ARTIST = "artistErr";
    public static final String ATTRIBUTE_ADD_ARTIST = "artistAdd";
    public static final String ATTRIBUTE_LIST_ARTIST = "artist";
    public static final String PARAMETER_ARTIST = "artist";
    public static final String PARAMETER_DELETE_ARTIST = "deleteArtist";
    public static final String PARAMETER_DELETE_ARTIST_ERROR = "deleteArtistErr";
    public static final String JSP_NAME_ARTIST = "artist";

    private static final IArtistService artistService = ServiceArtistFactory.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(ATTRIBUTE_LIST_ARTIST, artistService.getAll());
        req.getRequestDispatcher(JspUtil.getPath(JSP_NAME_ARTIST)).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String artist = req.getParameter(PARAMETER_ARTIST);
        String deleteArtist = req.getParameter(PARAMETER_DELETE_ARTIST);

        if(artist != null) {
            addingArtist(req, artist);
        }

        if (deleteArtist != null) {
            deletionArtist(req, deleteArtist);
        }

        doGet(req, resp);
    }

    private void addingArtist(HttpServletRequest req, String artist) {
        try {
            Long id = artistService.create(artist);
            req.setAttribute(ATTRIBUTE_ADD_ARTIST, MESSAGE_ARTIST_ADDED + id);
        } catch (IllegalArgumentException e) {
            req.setAttribute(ATTRIBUTE_ERROR_ARTIST, MESSAGE_ARTIST_NOT_ADDED);
        }
    }

    private static void deletionArtist(HttpServletRequest req, String deleteArtist) {
        if(deleteArtist != null) {
            try {
                if(artistService.delete(deleteArtist)) {
                    req.setAttribute(PARAMETER_DELETE_ARTIST, MESSAGE_ARTIST_DELETED);
                } else {
                    req.setAttribute(PARAMETER_DELETE_ARTIST_ERROR, MESSAGE_ARTIST_NOT_DELETED);
                }
            } catch (RuntimeException e) {
                req.setAttribute(PARAMETER_DELETE_ARTIST_ERROR, MESSAGE_ARTIST_NOT_DELETED + " " + e.getMessage());
            }
        }
    }
}
