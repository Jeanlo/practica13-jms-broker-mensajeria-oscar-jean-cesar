package Servicios;

import main.Enrutamiento;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class ServicioConsumidor {
    ActiveMQConnectionFactory fabricar;
    Connection conexion;
    Session sesion;
    Topic topico;
    MessageConsumer consumidor;
    String cola;

    public ServicioConsumidor(String cola) {
        this.cola = cola;
    }

    public void conectar() throws JMSException {
        fabricar = new ActiveMQConnectionFactory("admin", "admin", "failover:tcp://localhost:61616");
        conexion = fabricar.createConnection();
        conexion.start();

        sesion = conexion.createSession(false, Session.AUTO_ACKNOWLEDGE);

        topico = sesion.createTopic(cola);
        consumidor = sesion.createConsumer(topico);

        consumidor.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                try {
                    TextMessage messageTexto = (TextMessage) message;
                    Enrutamiento.enviarMensajeUsuariosConectados(messageTexto.getText());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}
