package main;

import Servicios.ServicioConsumidor;
import Servicios.ServicioEndpoint;
import Servicios.ServicioWebSocket;
import Utilidades.JSON;
import org.eclipse.jetty.websocket.api.Session;

import javax.jms.JMSException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;
import java.io.StringWriter;
import java.util.Map;

import static spark.Spark.*;

public class Enrutamiento {
    public static List<Session> usuariosConectados = new ArrayList<>();

    public static void crearRutas() throws JMSException {
        final Configuration configuration = new Configuration(new Version(2, 3, 23));
        configuration.setClassForTemplateLoading(Main.class, "/");
        staticFiles.location("/publico");

        String cola = "notificacion_sensores.cola";

        webSocket("/mensajeServidor", ServicioWebSocket.class);

        get("/", (req, res) -> {
            StringWriter writer = new StringWriter();
            Map<String, Object> atributos = new HashMap<>();
            Template template = configuration.getTemplate("plantillas/index.ftl");

            atributos.put("registros", "{\"registros\":" + JSON.toJson(ServicioEndpoint.getInstancia().listar()) + "}");

            template.process(atributos, writer);

            return writer;
        });

        ServicioConsumidor servicioConsumidor = new ServicioConsumidor(cola);
        servicioConsumidor.conectar();
    }

    public static void enviarMensajeUsuariosConectados(String mensaje){
        for(Session sesionConectada : usuariosConectados){
            try {
                sesionConectada.getRemote().sendString(mensaje);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
