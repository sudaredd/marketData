package market.caching.repository;

import java.util.List;

import market.caching.model.UserPayload;

public interface UserRepository {

	List<UserPayload> fetchAllUsers();

    UserPayload firstUser();

    UserPayload userByFirstNameAndLastName(String firstName,String lastName);
}
