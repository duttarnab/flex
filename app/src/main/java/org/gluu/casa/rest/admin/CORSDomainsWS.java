package org.gluu.casa.rest.admin;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.gluu.casa.misc.Utils;
import org.gluu.casa.rest.ProtectedApi;

import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.net.URL;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;
import static javax.ws.rs.core.Response.Status.OK;

@ApplicationScoped
@Path("/cors")
public class CORSDomainsWS extends BaseWS {
	
    @Inject
    private Logger logger;
    
    private ObjectMapper mapper;

    @PostConstruct
    private void init() {
        mapper = new ObjectMapper();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    //@ProtectedApi
    public Response list() {
    	
        Response.Status httpStatus;
        String json = null;
        
        logger.trace("CORSDomainsWS list operation called");
    	try {       		
			json = Utils.jsonFromObject(mainSettings.getCorsDomains());
			httpStatus = OK;
        } catch (Exception e) {
    		logger.error(e.getMessage(), e);
        	json = jsonString(e.getMessage());
        	httpStatus = INTERNAL_SERVER_ERROR;
        }
		return Response.status(httpStatus).entity(json).build();
		
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    //@ProtectedApi
    public Response replace(String body) {
    	
        Response.Status httpStatus;
        String json = null;
        List<String> values = mainSettings.getCorsDomains();
        
        logger.trace("CORSDomainsWS replace operation called");
    	try {
    		List<String> domains = mapper.readValue(body, new TypeReference<List<String>>(){});
    		Set<String> domainSet = new TreeSet();
    		
    		for (String dom : domains) {
    			try {
    				URL url = new URL(dom);
    				if (url.getProtocol().equals("http") || url.getProtocol().equals("https")) {
    					domainSet.add(dom);
    				}
    			} catch (Exception e) {
    				logger.error("Error: " + e.getMessage());
    			}
    		}
    		logger.trace("Resulting domains set: {}", domainSet);
    		
    		mainSettings.setCorsDomains(new ArrayList(domainSet));
			logger.trace("Persisting CORS domains in configuration");
			confHandler.saveSettings();
			httpStatus = OK;
    		
        } catch (Exception e) {
        	mainSettings.setCorsDomains(values);
        	json = jsonString(e.getMessage());
        	httpStatus = INTERNAL_SERVER_ERROR;
        }
        
		return Response.status(httpStatus).entity(json).build();
    }
    
}
