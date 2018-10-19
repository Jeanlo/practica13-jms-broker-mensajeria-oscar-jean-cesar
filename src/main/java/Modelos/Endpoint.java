package Modelos;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Endpoint implements Serializable {
    @Id
    @GeneratedValue
    private long id;
    private String fechaGeneracion;
    private int idDispositivo;
    private Double temperatura;
    private Double humedad;

    public Endpoint() {
    }

    public Endpoint(String fechaGeneracion, int idDispositivo, Double temperatura, Double humedad) {
        this.fechaGeneracion = fechaGeneracion;
        this.idDispositivo = idDispositivo;
        this.temperatura = temperatura;
        this.humedad = humedad;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFechaGeneracion() {
        return fechaGeneracion;
    }

    public void setFechaGeneracion(String fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }

    public int getIdDispositivo() {
        return idDispositivo;
    }

    public void setIdDispositivo(int idDispositivo) {
        idDispositivo = idDispositivo;
    }

    public Double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(Double temperatura) {
        this.temperatura = temperatura;
    }

    public Double getHumedad() {
        return humedad;
    }

    public void setHumedad(Double humedad) {
        this.humedad = humedad;
    }

    @Override
    public String toString() {
        return "EndPoint{" +
                "id=" + id +
                ", fechaGeneracion='" + fechaGeneracion + '\'' +
                ", idDispositivo='" + idDispositivo + '\'' +
                ", temperatura='" + temperatura + '\'' +
                ", humedad='" + humedad + '\'' +
                '}';
    }
}
