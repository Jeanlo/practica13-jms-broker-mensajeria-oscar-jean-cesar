package main;

import Servicios.ServicioProductor;

import javax.jms.JMSException;

public class GenerarMensajes {
    public static void main(String[] args) throws JMSException {
        String cola = "notificacion_sensores.cola";

        new ServicioProductor().enviarMensaje(cola, Integer.parseInt(args[0]));
    }
}
