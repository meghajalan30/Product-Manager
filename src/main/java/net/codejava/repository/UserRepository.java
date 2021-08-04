package net.codejava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import net.codejava.model.Product;
import net.codejava.model.UserInfo;

public interface UserRepository extends CrudRepository<UserInfo, String> {

}
