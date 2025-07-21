package com.shifterwebapp.shifter.usercourseprogress;

import com.shifterwebapp.shifter.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCourseProgressRepository extends JpaRepository<User, Long> {
}
