package com.SafeStreets;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Map;

@Path("/ServerService")
public class Server {
    @GET
    @Produces("text/plain")
    public String test() {
        return "Server Up!";
    }

    @POST
    @Path("/userRegistration")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response userRgistration(InputStream incomingData) {
        StringBuilder objectReceived = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
            String line = null;
            while ((line = in.readLine()) != null) {
                objectReceived.append(line);
            }
        } catch (Exception e) {
            System.out.println("Error Parsing: - ");
        }
        System.out.println("Data Received: " + objectReceived.toString());
        try{
            parse(objectReceived.toString());
        }catch (JsonProcessingException pe){
            System.out.println("Error parsing JSON");
        }

        return Response.status(200).entity(objectReceived.toString()).build();
    }

    private JsonNode parse(String json)  throws JsonProcessingException {
        JsonFactory factory = new JsonFactory();

        ObjectMapper mapper = new ObjectMapper(factory);
        JsonNode rootNode = mapper.readTree(json);

        Iterator<Map.Entry<String,JsonNode>> fieldsIterator = rootNode.fields();
        while (fieldsIterator.hasNext()) {

            Map.Entry<String,JsonNode> field = fieldsIterator.next();
            System.out.println("Key: " + field.getKey() + "\tValue:" + field.getValue());
        }
        return rootNode;
    }
}