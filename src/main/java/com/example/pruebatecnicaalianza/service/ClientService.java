package com.example.pruebatecnicaalianza.service;

import com.example.pruebatecnicaalianza.entity.Client;
import com.example.pruebatecnicaalianza.repository.ClientRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
public class ClientService {
    private final ClientRepository clientRepository;

    public ClientService( ClientRepository clientRepository ) {
        this.clientRepository = clientRepository;
    }

    public List<Client> getListClient() {
        return this.clientRepository.findAll();
    }

    public List<Client> getClientByAtrribute(Client client) {
        return this.clientRepository.findByAttr( client.getBusinessId(), client.getPhone(), client.getEmail(), client.getStartDate(), client.getEndDate() );
    }

    public List<Client> getClientBySharedKey(String sharedKey){
        return this.clientRepository.findClientBySharedKey( sharedKey );
    }

    public void createClient( Client clientNew ) {
            clientRepository.save(clientNew);
    }

    @Transactional
    public void updateClient( Client client ) {
        clientRepository.updateClient(
                client.getSharedKey(),
                client.getBusinessId(),
                client.getEmail(),
                client.getStartDate(),
                client.getEndDate(),
                client.getPhone(),
                client.getClientId()
        );
    }

    public String generateSharedKey( String businessId ) {
        String normalizeBusinessId = businessId.toLowerCase().trim();
        String[] names = normalizeBusinessId.split(" ");
        String sharedKeyNew = "";

        boolean unique = false;
        int i = 1;

        while(!unique) {
            sharedKeyNew = names[0].substring(0, i) + names[1];
            List<Client> searchClientBySharedKey = getClientBySharedKey( sharedKeyNew );
            if( searchClientBySharedKey.size() == 0) {
              unique = true;
           } else {
              i++;
            }

        }

        return sharedKeyNew;
    }

    public boolean validExistsEmail( Client client ) {
        if (client.getClientId() != null) {
            Client existingClient = clientRepository.findById(client.getClientId()).orElse(null);
            if (existingClient != null) {
                return false;
            }
        }
        List<Client> searchClientToEmail = getClientByAtrribute( client );

        return searchClientToEmail.size() > 0 ? true : false;
    }

    public Client validateBusinessId( Client client ) {
        List<Client> clientResult = getClientBySharedKey( client.getSharedKey() );
        if( clientResult.size() > 0 ) {
            if (!clientResult.get(0).getBusinessId().equals( client.getBusinessId()) ) {
                client.setSharedKey(this.generateSharedKey(client.getBusinessId()));
            }
        }
        return client;
    }

    private Client createClientAux(HashMap<String, String> searchRequest) {
        Client clientAux = new Client();

        String sharedKey = searchRequest.get("sharedKey");
        String businessId = searchRequest.get("businessId");
        String email = searchRequest.get("email");
        String phone = searchRequest.get("phone");

        if( sharedKey != null ) {
            clientAux.setSharedKey(sharedKey);
        }
        if( businessId != null ) {
            clientAux.setBusinessId(businessId);
        }
        if( email != null ) {
            clientAux.setEmail(email);
        }
        if( phone != null ) {
            clientAux.setPhone(phone);
        }

        return clientAux;
    }

}
