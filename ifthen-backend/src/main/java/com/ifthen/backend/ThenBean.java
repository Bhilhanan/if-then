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
}

//    @ApiMethod(
//            name = "random",
//            path = "randomThenBean",
//            httpMethod = ApiMethod.HttpMethod.GET)
//    public ThenBean random(@Named("sessionId") String sessionId) throws UnauthorizedException {
//        Collection<ThenBean> thenList = list(null, null).getItems();
//        int rand = (int) (Math.random() * thenList.size());
//        Iterator<ThenBean> iterator = thenList.iterator();
//        while (rand - 1 >= 0) {
//            iterator.next();
//            rand--;
//        }
//        return iterator.next();
//    }
