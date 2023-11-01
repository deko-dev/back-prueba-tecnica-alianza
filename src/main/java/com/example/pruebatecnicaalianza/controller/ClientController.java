package com.example.pruebatecnicaalianza.controller;

import com.example.pruebatecnicaalianza.entity.Client;
import com.example.pruebatecnicaalianza.repository.ClientRepository;
import com.example.pruebatecnicaalianza.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping( path = "api/v3/clients")
@CrossOrigin("*")
public class ClientController {

    private ClientService clientService;

    public ClientController(ClientRepository clientRepository) {
        this.clientService = new ClientService(clientRepository);
    }

    @CrossOrigin
    @GetMapping()
    public ResponseEntity<HashMap<String, Object>> getListClient() {
        List<Client> clients = this.clientService.getListClient();

        HashMap<String, Object> responseCreated = new HashMap<>();
        responseCreated.put("success", true);
        responseCreated.put("message", "Clientes en desarrollo encontrados!!");
        responseCreated.put("data", clients);
        return ResponseEntity.status(HttpStatus.OK).body(responseCreated);
    }

    @CrossOrigin
    @PostMapping()
    public ResponseEntity<HashMap<String, Object>> createClient(@RequestBody Client client){
        String sharedKey = this.clientService.generateSharedKey( client.getBusinessId() );
        client.setSharedKey( sharedKey );
        // Validamos si existe el email
        if( clientService.validExistsEmail( client ) ) {
            HashMap<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "El email ingresado ya está en uso por otro cliente.");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }

        this.clientService.createClient( client);
        HashMap<String, Object> responseCreated = new HashMap<>();
        responseCreated.put("success", true);
        responseCreated.put("message", "Cliente creado correctamente!!");
        responseCreated.put("data", client);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseCreated);
    }

    @CrossOrigin
    @PutMapping()
    @Transactional
    public ResponseEntity<HashMap<String, Object>> updateClient(@RequestBody Client client) {

        client = this.clientService.validateBusinessId( client );
        this.clientService.updateClient( client);
        HashMap<String, Object> responseCreated = new HashMap<>();
        responseCreated.put("success", true);
        responseCreated.put("message", "Cliente actualizado correctamente!!");
        responseCreated.put("data", client);
        return ResponseEntity.status(HttpStatus.OK).body(responseCreated);
    }

    @CrossOrigin
    @PostMapping( path = "/getClientByAtrribute")
    public ResponseEntity<HashMap<String, Object>> getClientByAttribute( @RequestBody Client client) {
        List<Client> resultsClient = this.clientService.getClientByAtrribute( client );

        HashMap<String, Object> responseCreated = new HashMap<>();
        responseCreated.put("success", true);
        responseCreated.put("message", "Cliente encontrado!!");
        responseCreated.put("data", resultsClient);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseCreated);
    }

    @CrossOrigin
    @GetMapping(path = "getClientBySharedKey/{sharedKey}")
    public ResponseEntity<HashMap<String, Object>> getClientBySharedKey(@PathVariable("sharedKey") String sharedKey) {
        List<Client> resultsClient = this.clientService.getClientBySharedKey( sharedKey );

        HashMap<String, Object> responseCreated = new HashMap<>();
        responseCreated.put("success", true);
        responseCreated.put("message", "Cliente encontrado!!");
        responseCreated.put("data", resultsClient);
        return ResponseEntity.status(HttpStatus.OK).body(responseCreated);
    }
}
