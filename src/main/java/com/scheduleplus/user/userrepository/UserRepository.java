package com.scheduleplus.user.userrepository;

import com.scheduleplus.user.userentity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
