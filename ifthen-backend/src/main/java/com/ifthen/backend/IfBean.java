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
public class IfBean {
    @Id Long id;
    private String text;
    @Index private String sessionId;
    @Index private boolean isPublic;
    @Index private int count;
}
//    @ApiMethod(
//            name = "updateCount",
//            path = "ifBean/{id}/{count}",
//            httpMethod = ApiMethod.HttpMethod.PUT)
//    public IfBean updateCount(@Named("id") Long id, @Named("count") Integer count) throws NotFoundException {
//        IfBean ifBean = checkExists(id);
//        ifBean.setCount(count);
//        ofy().save().entity(ifBean).now();
//        return  ofy().load().entity(ifBean).now();
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
//            path = "ifBean",
//            httpMethod = ApiMethod.HttpMethod.GET)
//    public CollectionResponse<IfBean> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
//        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
//        Query<IfBean> query = ofy().load().type(IfBean.class).filter("isPublic =", true).limit(limit);
//        if (cursor != null) {
//            query = query.startAt(Cursor.fromWebSafeString(cursor));
//        }
//        QueryResultIterator<IfBean> queryIterator = query.iterator();
//        List<IfBean> ifBeanList = new ArrayList<IfBean>(limit);
//        while (queryIterator.hasNext()) {
//            ifBeanList.add(queryIterator.next());
//        }
//        return CollectionResponse.<IfBean>builder().setItems(ifBeanList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
//    }
//
//    @ApiMethod(
//            name = "random",
//            path = "randomIfBean",
//            httpMethod = ApiMethod.HttpMethod.GET)
//    public IfBean random(@Named("sessionId") String sessionId) throws UnauthorizedException {
//        Collection<IfBean> ifList = listBySessionId(sessionId, null, null).getItems();
//        int rand = (int) (Math.random() * ifList.size());
//        Iterator<IfBean> iterator = ifList.iterator();
//        while (rand - 1 >= 0) {
//            iterator.next();
//            rand--;
//        }
//        return iterator.next();
//    }
//
//    private CollectionResponse<IfBean> listBySessionId(String sessionId, String cursor, Integer limit) {
//        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
//        Query<IfBean> query = ofy().load().type(IfBean.class).filter("sessionId =", sessionId).limit(limit);
//        if (cursor != null) {
//            query = query.startAt(Cursor.fromWebSafeString(cursor));
//        }
//        QueryResultIterator<IfBean> queryIterator = query.iterator();
//        List<IfBean> ifBeanList = new ArrayList<IfBean>(limit);
//        while (queryIterator.hasNext()) {
//            ifBeanList.add(queryIterator.next());
//        }
//        return CollectionResponse.<IfBean>builder().setItems(ifBeanList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
//
//    }
//
//    private IfBean checkExists(Long id) throws NotFoundException {
//        try {
//            return ofy().load().type(IfBean.class).id(id).safe();
//        } catch (com.googlecode.objectify.NotFoundException e) {
//            throw new NotFoundException("Could not find IfBean with ID: " + id);
//        }
//    }
