package custompage.marketing.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class SalesEventListener {

    @KafkaListener(topics = "venta-realizada-topic", groupId = "marketing-group")
    public void escucharVentas(String mensajeVenta) {
        // En un escenario real aquí procesas el JSON para estrategias de remarketing o fidelización
        System.out.println("==========================================================");
        System.out.println("MARKETING CONSUMER: Se ha recibido una nueva venta desde Kafka!");
        System.out.println("Datos del evento procesados para analítica: " + mensajeVenta);
        System.out.println("==========================================================");
    }
}