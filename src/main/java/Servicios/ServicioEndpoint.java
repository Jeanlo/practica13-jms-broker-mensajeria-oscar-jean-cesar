package Servicios;

import Modelos.Endpoint;

public class ServicioEndpoint extends ServicioBaseDatos<Endpoint> {
    private static ServicioEndpoint instancia;

    private ServicioEndpoint() {
        super(Endpoint.class);
    }

    public static ServicioEndpoint getInstancia() {
        if (instancia == null) {
            instancia = new ServicioEndpoint();
        }
        return instancia;
    }
}
