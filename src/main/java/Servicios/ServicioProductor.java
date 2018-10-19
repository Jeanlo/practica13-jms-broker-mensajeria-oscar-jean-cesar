package Servicios;

import Modelos.Endpoint;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.json.JSONException;
import org.json.JSONObject;

import javax.jms.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ServicioProductor {
    public ServicioProductor() {

    }

    public void enviarMensaje(String cola, int sensor) throws JMSException {
        ActiveMQConnectionFactory fabricar = new ActiveMQConnectionFactory("tcp://localhost:61616");

        Connection connection = fabricar.createConnection("admin", "admin");
        connection.start();

        Session sesion = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Topic topico = sesion.createTopic(cola);
        MessageProducer productor = sesion.createProducer(topico);

        while (true) {
            try {
                TimeUnit.SECONDS.sleep(1);

                JSONObject json = new JSONObject();

                try {
                    DateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    String fechaGeneracion = formatoFecha.format(new Date());
                    Double temperatura = Math.ceil(Math.random() * 50);
                    Double humedad = Math.ceil(Math.random() * 100);

                    json.put("fechaGeneracion", fechaGeneracion);
                    json.put("idDispositivo", sensor);
                    json.put("temperatura", temperatura);
                    json.put("humedad", humedad);

                    ServicioEndpoint.getInstancia().crear(
                            new Endpoint(
                                    fechaGeneracion,
                                    sensor,
                                    temperatura,
                                    humedad
                            ));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                TextMessage mensaje = sesion.createTextMessage(json.toString());
                productor.send(mensaje);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
