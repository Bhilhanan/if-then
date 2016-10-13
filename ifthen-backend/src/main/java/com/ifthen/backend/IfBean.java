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
    private int count;
}
//    @ApiMethod(
//            name = "random",
//            path = "randomIfBean",
//            httpMethod = ApiMethod.HttpMethod.GET)
//    public IfBean random(@Named("sessoinId") String sessionId) throws UnauthorizedException {
//        Collection<IfBean> ifList = listBySessionId(sessionId,null, null).getItems();
//        int rand = (int) (Math.random() * ifList.size());
//        Iterator<IfBean> iterator = ifList.iterator();
//        while (rand - 1 >= 0) {
//            iterator.next();
//            rand--;
//        }
//        return iterator.next();
//    }
//
//    private CollectionResponse<IfBean> listBySessionId(String sessionId,String cursor, Integer limit) {
//        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
//        Query<IfBean> query = ofy().load().type(IfBean.class).filter("sessionId =",sessionId).limit(limit);
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
//    private void checkExists(Long id) throws NotFoundException {
//        try {
//            ofy().load().type(IfBean.class).id(id).safe();
//        } catch (com.googlecode.objectify.NotFoundException e) {
//            throw new NotFoundException("Could not find IfBean with ID: " + id);
//        }
//    }
