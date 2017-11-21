package com.polytechcloudapi.userapi.repository;

import com.polytechcloudapi.userapi.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by JOYMANGUL Jensen Selwyn
 * on 08-Nov-17.
 */
public interface UserRepository extends MongoRepository<User, String> {
    @Override
    List<User> findAll();

    List<User> findByPositionNear(Point location);

    List<User> findAllByOrderByLastNameDesc(Pageable pageable);

}
