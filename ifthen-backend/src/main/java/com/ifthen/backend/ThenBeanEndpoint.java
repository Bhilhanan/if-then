package com.ifthen.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.NotFoundException;
import com.google.api.server.spi.response.UnauthorizedException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.google.appengine.api.users.User;
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
        name = "thenBeanApi",
        version = "v1",
        resource = "thenBean",
        namespace = @ApiNamespace(
                ownerDomain = "backend.ifthen.com",
                ownerName = "backend.ifthen.com",
                packagePath = ""
        )
//        ,
//        clientIds = {Constants.ANDROID_CLIENT_ID}
)
public class ThenBeanEndpoint {

    private static final Logger logger = Logger.getLogger(ThenBeanEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(ThenBean.class);
    }

    /**
     * Returns the {@link ThenBean} with the corresponding ID.
     *
     * @param id the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code ThenBean} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "thenBean/{id}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public ThenBean get(@Named("id") long id) throws NotFoundException, UnauthorizedException {
//        if (user == null) {
//            throw new UnauthorizedException("User is not valid");
//        }
        logger.info("Getting ThenBean with ID: " + id);
        ThenBean thenBean = ofy().load().type(ThenBean.class).id(id).now();
        if (thenBean == null) {
            throw new NotFoundException("Could not find ThenBean with ID: " + id);
        }
        return thenBean;
    }

    /**
     * Inserts a new {@code ThenBean}.
     */
    @ApiMethod(
            name = "insert",
            path = "thenBean",
            httpMethod = ApiMethod.HttpMethod.POST)
    public ThenBean insert(ThenBean thenBean) throws UnauthorizedException {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that thenBean.id has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
//        if (user == null) {
//            throw new UnauthorizedException("User is not valid");
//        }
        ofy().save().entity(thenBean).now();
        logger.info("Created ThenBean.");

        return ofy().load().entity(thenBean).now();
    }

    /**
     * Updates an existing {@code ThenBean}.
     *
     * @param id       the ID of the entity to be updated
     * @param thenBean the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code ThenBean}
     */
    @ApiMethod(
            name = "update",
            path = "thenBean/{id}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public ThenBean update(@Named("id") long id, ThenBean thenBean) throws NotFoundException, UnauthorizedException {
        // TODO: You should validate your ID parameter against your resource's ID here.
//        if (user == null) {
//            throw new UnauthorizedException("User is not valid");
//        }
        checkExists(id);
        ofy().save().entity(thenBean).now();
        logger.info("Updated ThenBean: " + thenBean);
        return ofy().load().entity(thenBean).now();
    }

    /**
     * Deletes the specified {@code ThenBean}.
     *
     * @param id the ID of the entity to delete
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code ThenBean}
     */
    @ApiMethod(
            name = "remove",
            path = "thenBean/{id}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("id") long id) throws NotFoundException, UnauthorizedException {
//        if (user == null) {
//            throw new UnauthorizedException("User is not valid");
//        }
        checkExists(id);
        ofy().delete().type(ThenBean.class).id(id).now();
        logger.info("Deleted ThenBean with ID: " + id);
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
            path = "thenBean",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<ThenBean> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) throws UnauthorizedException {
//        if (user == null) {
//            throw new UnauthorizedException("User is not valid");
//        }
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<ThenBean> query = ofy().load().type(ThenBean.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<ThenBean> queryIterator = query.iterator();
        List<ThenBean> thenBeanList = new ArrayList<ThenBean>(limit);
        while (queryIterator.hasNext()) {
            thenBeanList.add(queryIterator.next());
        }
        return CollectionResponse.<ThenBean>builder().setItems(thenBeanList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    @ApiMethod(
            name = "random",
            path = "randomThenBean",
            httpMethod = ApiMethod.HttpMethod.GET)
    public ThenBean random() throws UnauthorizedException {
//        if (user == null) {
//            throw new UnauthorizedException("User is not valid");
//        }
        Collection<ThenBean> thenList = list(null, null).getItems();
        int rand = (int) (Math.random() * thenList.size());
        Iterator<ThenBean> iterator = thenList.iterator();
        while (rand - 1 >= 0) {
            iterator.next();
            rand--;
        }
        return iterator.next();
    }

    private void checkExists(long id) throws NotFoundException {
        try {
            ofy().load().type(ThenBean.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find ThenBean with ID: " + id);
        }
    }
}