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
import java.nio.charset.StandardCharsets;

import static by.it_academy.jd2.util.PathUtil.ARTIST_SERVLET;

@WebServlet(urlPatterns = ARTIST_SERVLET)
public class ArtistServlet extends HttpServlet {
    public static final String ATTRIBUTE_ERROR_ARTIST = "artistErr";
    public static final String ATTRIBUTE_ADD_ARTIST = "artistAdd";
    public static final String ATTRIBUTE_LIST_ARTIST = "artist";
    public static final String PARAMETER_ARTIST = "artist";
    public static final String JSP_NAME_ARTIST = "artist";

    private static final IArtistService singerService = ServiceArtistFactory.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(ATTRIBUTE_LIST_ARTIST, singerService.getAll());
        req.getRequestDispatcher(JspUtil.getPath(JSP_NAME_ARTIST)).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String artist = req.getParameter(PARAMETER_ARTIST);

        try {
            if(artist.isBlank()) {
                throw new IllegalArgumentException();
            }
            Long id = singerService.create(artist);
            req.setAttribute(ATTRIBUTE_ADD_ARTIST, "Исполнитель добавлен в систему под id = " + id);
            doGet(req, resp);
        } catch (IllegalArgumentException e) {
            req.setAttribute(ATTRIBUTE_ERROR_ARTIST, "Исполнитель не добавлен");
            doGet(req, resp);
        }
    }
}
