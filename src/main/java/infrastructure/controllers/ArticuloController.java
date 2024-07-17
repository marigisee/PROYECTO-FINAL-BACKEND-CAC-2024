package infrastructure.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.models.Articulo;
import services.ArticuloService;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Va a recibir las peticiones de Postman
 */
@WebServlet("/articulo") // Cuando se realice una petición a localhost:8080/users se va a ejecutar este controlador
public class ArticuloController extends HttpServlet {

    // Vamos a darles las funcionalidades adecuadas a los métodos POST y GET

    // Como la petición POST nos llegará con formato json, debemos definir el siguiente atributo
    private ObjectMapper mapper;
    private ArticuloService service;

    public ArticuloController() {
        this.mapper = new ObjectMapper(); // mapea de un json a un objeto java
        this.service = new ArticuloService(); // mecanismos de persistencia
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // configura los encabezados CORS
        configureCorsHeaders(resp);
    }

    private void configureCorsHeaders(HttpServletResponse resp) {
        resp.setHeader("Access-Control-Allow-Origin", "*"); // aca colocan la direccion de donde viene la peticion
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "content-type, authorization");
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        configureCorsHeaders(resp);
        String nombreArticulo= req.getParameter("nombreArticulo");

        if(nombreArticulo != null) {
            Articulo articulo = service.findByname(nombreArticulo);
            if(articulo != null) {
                resp.setStatus(200);
                // configuramos que lo que enviamos es en formato json //
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().write(mapper.writeValueAsString(articulo));
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("Articulo no encontrado");
            }

        }else {
            ArrayList<Articulo> articulos = service.getAllArticulos();
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(mapper.writeValueAsString(articulos));
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Articulo articulo = mapper.readValue(req.getInputStream(), Articulo.class);
        service.saveArticulo(articulo);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

}
