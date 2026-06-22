package org.example.hardwareshop;

// Usado para atualizar o status de um pedido (PUT /pedidos/{id}/status)
public class StatusRequest {

    private String status;

    public String getStatus()            { return status; }
    public void setStatus(String status) { this.status = status; }
}
