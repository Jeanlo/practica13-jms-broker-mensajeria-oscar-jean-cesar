package main;

import org.apache.activemq.broker.BrokerService;
import org.h2.tools.Server;

import java.sql.SQLException;

public class BrokerMensajeria {
    public static void main(String[] args) {
        try {
            iniciarBaseDatos();
            BrokerService brokerService = new BrokerService();
            brokerService.addConnector("tcp://localhost:61616");
            brokerService.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void iniciarBaseDatos() {
        try {
            Server.createTcpServer("-tcpPort", "9092", "-tcpAllowOthers").start();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
