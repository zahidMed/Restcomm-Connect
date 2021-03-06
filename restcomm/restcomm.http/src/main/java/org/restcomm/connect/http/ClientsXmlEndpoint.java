/*
 * TeleStax, Open Source Cloud Communications
 * Copyright 2011-2014, Telestax Inc and individual contributors
 * by the @authors tag.
 *
 * This program is free software: you can redistribute it and/or modify
 * under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation; either version 3 of
 * the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 *
 */
package org.restcomm.connect.http;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static javax.ws.rs.core.MediaType.APPLICATION_XML_TYPE;
import static javax.ws.rs.core.Response.ok;
import static javax.ws.rs.core.Response.status;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.restcomm.connect.commons.annotations.concurrency.ThreadSafe;
import org.restcomm.connect.dao.entities.Account;
import org.restcomm.connect.dao.entities.Client;
import org.restcomm.connect.dao.entities.Sid;

/**
 * @author quintana.thomas@gmail.com (Thomas Quintana)
 */
@Path("/Accounts/{accountSid}/Clients")
@ThreadSafe
public final class ClientsXmlEndpoint extends ClientsEndpoint {
    public ClientsXmlEndpoint() {
        super();
    }

    private Response deleteClient(final String accountSid, final String sid) {
        Account operatedAccount =super.accountsDao.getAccount(accountSid);
        secure(operatedAccount, "RestComm:Delete:Clients");
        Client client = dao.getClient(new Sid(sid));
        if (client != null) {
            secure(operatedAccount, client.getAccountSid(), SecuredType.SECURED_STANDARD);
            dao.removeClient(new Sid(sid));
            return ok().build();
        } else {
            return status(Response.Status.NOT_FOUND).build();
        }
    }

    @Path("/{sid}.json")
    @DELETE
    public Response deleteClientAsJson(@PathParam("accountSid") final String accountSid, @PathParam("sid") final String sid) {
        return deleteClient(accountSid, sid);
    }

    @Path("/{sid}")
    @DELETE
    public Response deleteClientAsXml(@PathParam("accountSid") final String accountSid, @PathParam("sid") final String sid) {
        return deleteClient(accountSid, sid);
    }

    @Path("/{sid}.json")
    @GET
    public Response getClientAsJson(@PathParam("accountSid") final String accountSid, @PathParam("sid") final String sid) {
        return getClient(accountSid, sid, APPLICATION_JSON_TYPE);
    }

    @Path("/{sid}")
    @GET
    public Response getClientAsXml(@PathParam("accountSid") final String accountSid, @PathParam("sid") final String sid) {
        return getClient(accountSid, sid, APPLICATION_XML_TYPE);
    }

    @GET
    public Response getClients(@PathParam("accountSid") final String accountSid) {
        return getClients(accountSid, APPLICATION_XML_TYPE);
    }

    @POST
    public Response putClient(@PathParam("accountSid") final String accountSid, final MultivaluedMap<String, String> data) {
        return putClient(accountSid, data, APPLICATION_XML_TYPE);
    }

    @Path("/{sid}.json")
    @POST
    public Response updateClientAsJsonPost(@PathParam("accountSid") final String accountSid,
            @PathParam("sid") final String sid, final MultivaluedMap<String, String> data) {
        return updateClient(accountSid, sid, data, APPLICATION_JSON_TYPE);
    }

    @Path("/{sid}.json")
    @PUT
    public Response updateClientAsJsonPut(@PathParam("accountSid") final String accountSid, @PathParam("sid") final String sid,
            final MultivaluedMap<String, String> data) {
        return updateClient(accountSid, sid, data, APPLICATION_JSON_TYPE);
    }

    @Path("/{sid}")
    @POST
    public Response updateClientAsXmlPost(@PathParam("accountSid") final String accountSid, @PathParam("sid") final String sid,
            final MultivaluedMap<String, String> data) {
        return updateClient(accountSid, sid, data, APPLICATION_XML_TYPE);
    }

    @Path("/{sid}")
    @PUT
    public Response updateClientAsXmlPut(@PathParam("accountSid") final String accountSid, @PathParam("sid") final String sid,
            final MultivaluedMap<String, String> data) {
        return updateClient(accountSid, sid, data, APPLICATION_XML_TYPE);
    }
}
