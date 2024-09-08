package by.it_academy.jd2.controller.servlet;

import by.it_academy.jd2.dto.InfoFromUserDTO;
import by.it_academy.jd2.service.api.IGenreService;
import by.it_academy.jd2.service.api.IServiceVote;
import by.it_academy.jd2.service.api.IArtistService;
import by.it_academy.jd2.service.factory.ServiceArtistFactory;
import by.it_academy.jd2.service.factory.ServiceGenreFactory;
import by.it_academy.jd2.service.factory.ServiceVoteFactory;
import by.it_academy.jd2.util.JspUtil;
import by.it_academy.jd2.validation.ValidFormException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

import static by.it_academy.jd2.util.PathUtil.RESULT_VOTE_SERVLET;
import static by.it_academy.jd2.util.PathUtil.VOTE_SERVLET;

@WebServlet(urlPatterns = VOTE_SERVLET)
public class FormForVoteServlet extends HttpServlet {
    public static final String PARAMETER_GENRE = "janre";
    public static final String PARAMETER_SINGER = "singer";
    public static final String PARAMETER_INFO_ABOUT_MYSESLF = "info";
    public static final String ATTRIBUTE_REQUEST_GENRES = "janres";
    public static final String ATTRIBUTE_REQUEST_SINGERS = "singers";
    public static final String ATTRIBUTE_REQUEST_VOTE_ERRORS = "voteErrors";
    public static final String JSP_PAGE_WITH_FORM = "vote";

    private final IServiceVote serviceVote = ServiceVoteFactory.getInstance();
    private final IArtistService singerService = ServiceArtistFactory.getInstance();
    private final IGenreService genreService = ServiceGenreFactory.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setAttribute(ATTRIBUTE_REQUEST_SINGERS, singerService.getAll());
        req.setAttribute(ATTRIBUTE_REQUEST_GENRES, genreService.getAll());

        req.getRequestDispatcher(JspUtil.getPath(JSP_PAGE_WITH_FORM)).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        InfoFromUserDTO infoFromUserDto = InfoFromUserDTO.builder()
                .setSinger(req.getParameter(PARAMETER_SINGER))
                .setJanres(req.getParameterValues(PARAMETER_GENRE))
                .setDateTime(LocalDateTime.now())
                .setInfo(req.getParameter(PARAMETER_INFO_ABOUT_MYSESLF))
                .build();

        try {
            serviceVote.create(infoFromUserDto);
            resp.sendRedirect(req.getContextPath() + RESULT_VOTE_SERVLET);
        } catch (ValidFormException validationException) {
            req.setAttribute(ATTRIBUTE_REQUEST_VOTE_ERRORS, validationException.getErrors());
            doGet(req, resp);
        } catch (IllegalArgumentException e) {
            try (PrintWriter writer = resp.getWriter()) {
                writer.write("<p>error: " + e.getMessage() + "</p>");
            }
        }

    }
}
