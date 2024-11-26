package com.UdeA.LabWebsockets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/gates")
@CrossOrigin(origins = "http://localhost:3000")
public class GateController {

    private final SimpMessagingTemplate messagingTemplate;
    private final Map<String, GateInfo> gateData = new ConcurrentHashMap<>();

    @Autowired
    public GateController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    /**
     * Handles WebSocket messages from clients to update gate information
     * and broadcasts the updates to all subscribers.
     */
    @MessageMapping("/updateGate")
    @SendTo("/topic/gates")
    public GateInfo updateGateInfo(GateInfo gateInfo) throws Exception {
        System.out.println("Updating gate information: " + gateInfo.getGate());

        // Broadcast updated gate information to all subscribers
        messagingTemplate.convertAndSend("/topic/gates", gateInfo);

        // Return the sanitized gate information
        return new GateInfo(
                HtmlUtils.htmlEscape(gateInfo.getGate()),
                HtmlUtils.htmlEscape(gateInfo.getFlightNumber()),
                HtmlUtils.htmlEscape(gateInfo.getDestination()),
                HtmlUtils.htmlEscape(gateInfo.getDepartureTime()),
                HtmlUtils.htmlEscape(gateInfo.getStatus())
        );
    }

    /**
     * Sends updates programmatically or from another service.
     * @param gateInfo The updated gate information to send.
     */
    public void sendUpdate(GateInfo gateInfo) {
        messagingTemplate.convertAndSend("/topic/gates", gateInfo);
    }

    /**
     * Updates the information for a specific gate using a POST request.
     * @param gate The gate information to update.
     * @return A response indicating success.
     */
    @PostMapping("/update")
    public ResponseEntity<String> updateGate(@RequestBody GateInfo gate) {
        // Update gate data in the map
        gateData.put(gate.getGate(), gate);

        // Broadcast the update to all WebSocket subscribers
        messagingTemplate.convertAndSend("/topic/gates", gate);

        return ResponseEntity.ok("Gate information updated successfully");
    }

    /**
     * Retrieves the information for a specific gate using a GET request.
     * @param gateNumber The gate number to retrieve information for.
     * @return The information of the requested gate.
     */
    @GetMapping("/{gateNumber}")
    public ResponseEntity<GateInfo> getGateInfo(@PathVariable String gateNumber) {
        GateInfo gate = gateData.get(gateNumber);
        return ResponseEntity.ok(gate);



    }
}
