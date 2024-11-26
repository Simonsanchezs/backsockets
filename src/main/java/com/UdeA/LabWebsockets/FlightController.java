package com.UdeA.LabWebsockets;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class FlightController {

    @MessageMapping("/updateFlight") // Canal donde recibe datos del cliente
    @SendTo("/topic/flights") // Canal donde publica datos
    public FlightInfo updateFlight(FlightInfo flightInfo) throws Exception {
        System.out.println("Datos recibidos: " + flightInfo);
        return flightInfo; // Envía datos a los suscriptores
    }
}
