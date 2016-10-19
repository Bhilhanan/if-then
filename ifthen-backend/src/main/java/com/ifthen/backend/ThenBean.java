package com.ifthen.backend;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by bjeyara on 9/22/16.
 */

@Getter
@Setter
@Entity
public class ThenBean {
    @Id
    Long id;
    private String text;
    @Index private String sessionId;
    @Index private boolean isPubic;
    @Index private int count;
}
//
//    @ApiMethod(
//            name = "updateCount",
//            path = "thenBean/{id}/{count}",
//            httpMethod = ApiMethod.HttpMethod.PUT)
//    public ThenBean updateCount(@Named("id") Long id, @Named("count") Integer count) throws NotFoundException {
//        ThenBean thenBean = checkExists(id);
//        thenBean.setCount(count);
//        ofy().save().entity(thenBean).now();
//        return ofy().load().entity(thenBean).now();
//    }
//    /**
//     * List all entities.
//     *
//     * @param cursor used for pagination to determine which page to return
//     * @param limit  the maximum number of entries to return
//     * @return a response that encapsulates the result list and the next page token/cursor
//     */
//    @ApiMethod(
//            name = "list",
//            path = "thenBean",
//            httpMethod = ApiMethod.HttpMethod.GET)
//    public CollectionResponse<ThenBean> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
//        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
//        Query<ThenBean> query = ofy().load().type(ThenBean.class).filter("isPubic =", true).limit(limit);
//        if (cursor != null) {
//            query = query.startAt(Cursor.fromWebSafeString(cursor));
//        }
//        QueryResultIterator<ThenBean> queryIterator = query.iterator();
//        List<ThenBean> thenBeanList = new ArrayList<ThenBean>(limit);
//        while (queryIterator.hasNext()) {
//            thenBeanList.add(queryIterator.next());
//        }
//        return CollectionResponse.<ThenBean>builder().setItems(thenBeanList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
//    }
//
//    @ApiMethod(
//            name = "random",
//            path = "randomThenBean",
//            httpMethod = ApiMethod.HttpMethod.GET)
//    public ThenBean random(@Named("sessionId") String sessionId) throws UnauthorizedException {
//        Collection<ThenBean> thenList = listBySessionId(sessionId, null, null).getItems();
//        int rand = (int) (Math.random() * thenList.size());
//        Iterator<ThenBean> iterator = thenList.iterator();
//        while (rand - 1 >= 0) {
//            iterator.next();
//            rand--;
//        }
//        return iterator.next();
//    }
//
//    private CollectionResponse<ThenBean> listBySessionId(String sessionId, String cursor, Integer limit) {
//        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
//        Query<ThenBean> query = ofy().load().type(ThenBean.class).filter("sessionId =", sessionId).limit(limit);
//        if (cursor != null) {
//            query = query.startAt(Cursor.fromWebSafeString(cursor));
//        }
//        QueryResultIterator<ThenBean> queryIterator = query.iterator();
//        List<ThenBean> thenBeanList = new ArrayList<ThenBean>(limit);
//        while (queryIterator.hasNext()) {
//            thenBeanList.add(queryIterator.next());
//        }
//        return CollectionResponse.<ThenBean>builder().setItems(thenBeanList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
//
//    }
//
//    private ThenBean checkExists(Long id) throws NotFoundException {
//        try {
//            return ofy().load().type(ThenBean.class).id(id).safe();
//        } catch (com.googlecode.objectify.NotFoundException e) {
//            throw new NotFoundException("Could not find ThenBean with ID: " + id);
//        }
//    }
