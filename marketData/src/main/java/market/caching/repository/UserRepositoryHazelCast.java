package market.caching.repository;

import java.util.List;
import java.util.Optional;

import market.caching.model.UserPayload;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryHazelCast implements UserRepository {

	@Autowired
    private List<UserPayload> payloadUsers;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRepositoryHazelCast.class);
    
    @Override
    @Cacheable("alluserscache")
    public List<UserPayload> fetchAllUsers() {

        LOGGER.info("Fetching all users using hazelcast");

        return payloadUsers;
    }

    @Override
    @Cacheable(cacheNames = "usercache",key = "#root.methodName")
    public UserPayload firstUser() {

        LOGGER.info("fetching firstUser using hazelcast");

        return payloadUsers.get(0);
    }

    @Override
    @Cacheable(cacheNames = "usercache",key = "{#firstName,#lastName}")
    public UserPayload userByFirstNameAndLastName(String firstName, String lastName) {
        LOGGER.info("fetching user by firstname and lastname using hazelcast");

        return payloadUsers.stream().filter(
                p-> p.getFirstName().equals(firstName)
                        &&p.getLastName().equals(lastName))
                .findFirst().orElse(null);

    }
}
