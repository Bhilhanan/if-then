package com.ifthen.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.NotFoundException;
import com.google.api.server.spi.response.UnauthorizedException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Nullable;
import javax.inject.Named;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * WARNING: This generated code is intended as a sample or starting point for using a
 * Google Cloud Endpoints RESTful API with an Objectify entity. It provides no data access
 * restrictions and no data validation.
 * <p>
 * DO NOT deploy this code unchanged as part of a real application to real users.
 */
@Api(
        name = "ifBeanApi",
        version = "v1",
        resource = "ifBean",
        namespace = @ApiNamespace(
                ownerDomain = "backend.ifthen.com",
                ownerName = "backend.ifthen.com",
                packagePath = ""
        )
)
public class IfBeanEndpoint {

    private static final Logger logger = Logger.getLogger(IfBeanEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(IfBean.class);
    }

    /**
     * Returns the {@link IfBean} with the corresponding ID.
     *
     * @param id the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code IfBean} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "ifBean/{id}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public IfBean get(@Named("id") Long id) throws NotFoundException {
        logger.info("Getting IfBean with ID: " + id);
        IfBean ifBean = ofy().load().type(IfBean.class).id(id).now();
        if (ifBean == null) {
            throw new NotFoundException("Could not find IfBean with ID: " + id);
        }
        return ifBean;
    }

    /**
     * Inserts a new {@code IfBean}.
     */
    @ApiMethod(
            name = "insert",
            path = "ifBean",
            httpMethod = ApiMethod.HttpMethod.POST)
    public IfBean insert(IfBean ifBean) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that ifBean.id has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(ifBean).now();
        logger.info("Created IfBean with ID: " + ifBean.getId());

        return ofy().load().entity(ifBean).now();
    }

    /**
     * Updates an existing {@code IfBean}.
     *
     * @param id     the ID of the entity to be updated
     * @param ifBean the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code IfBean}
     */
    @ApiMethod(
            name = "update",
            path = "ifBean/{id}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public IfBean update(@Named("id") Long id, IfBean ifBean) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(id);
        ofy().save().entity(ifBean).now();
        logger.info("Updated IfBean: " + ifBean);
        return ofy().load().entity(ifBean).now();
    }

    @ApiMethod(
            name = "updateCount",
            path = "ifBean/{id}/{count}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public IfBean updateCount(@Named("id") Long id, @Named("count") Integer count) throws NotFoundException {
        IfBean ifBean = checkExists(id);
        ifBean.setCount(count);
        ofy().save().entity(ifBean).now();
        return ofy().load().entity(ifBean).now();
    }

    /**
     * Deletes the specified {@code IfBean}.
     *
     * @param id the ID of the entity to delete
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code IfBean}
     */
    @ApiMethod(
            name = "remove",
            path = "ifBean/{id}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("id") Long id) throws NotFoundException {
        checkExists(id);
        ofy().delete().type(IfBean.class).id(id).now();
        logger.info("Deleted IfBean with ID: " + id);
    }

    /**
     * List all entities.
     *
     * @param cursor used for pagination to determine which page to return
     * @param limit  the maximum number of entries to return
     * @return a response that encapsulates the result list and the next page token/cursor
     */
    @ApiMethod(
            name = "list",
            path = "ifBean",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<IfBean> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<IfBean> query = ofy().load().type(IfBean.class).filter("isPublic =", true).order("-count").limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<IfBean> queryIterator = query.iterator();
        List<IfBean> ifBeanList = new ArrayList<IfBean>(limit);
        while (queryIterator.hasNext()) {
            ifBeanList.add(queryIterator.next());
        }
        return CollectionResponse.<IfBean>builder().setItems(ifBeanList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    @ApiMethod(
            name = "random",
            path = "randomIfBean",
            httpMethod = ApiMethod.HttpMethod.GET)
    public IfBean random(@Named("sessionId") String sessionId) throws UnauthorizedException {
        Collection<IfBean> ifList = listBySessionId(sessionId, null, null).getItems();
        int rand = (int) (Math.random() * ifList.size());
        Iterator<IfBean> iterator = ifList.iterator();
        while (rand - 1 >= 0) {
            iterator.next();
            rand--;
        }
        return iterator.next();
    }

    private CollectionResponse<IfBean> listBySessionId(String sessionId, String cursor, Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<IfBean> query = ofy().load().type(IfBean.class).filter("sessionId =", sessionId).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<IfBean> queryIterator = query.iterator();
        List<IfBean> ifBeanList = new ArrayList<IfBean>(limit);
        while (queryIterator.hasNext()) {
            ifBeanList.add(queryIterator.next());
        }
        return CollectionResponse.<IfBean>builder().setItems(ifBeanList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();

    }

    private IfBean checkExists(Long id) throws NotFoundException {
        try {
            return ofy().load().type(IfBean.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find IfBean with ID: " + id);
        }
    }
}