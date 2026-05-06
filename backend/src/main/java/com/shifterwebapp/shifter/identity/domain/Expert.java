package com.shifterwebapp.shifter.identity.domain;

import com.shifterwebapp.shifter.identity.domain.enums.AccountAuthority;
import com.shifterwebapp.shifter.collection.domain.bundle.CuratedBundle;
import com.shifterwebapp.shifter.catalog.domain.Course;
import com.shifterwebapp.shifter.collection.domain.learningpath.CuratedLearningPath;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

@Entity
@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
public class Expert extends Account {

    //---------MAPPINGS---------
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "expert_curated_learning_path",
            joinColumns = @JoinColumn(name = "expert_id"),
            inverseJoinColumns = @JoinColumn(name = "curated_learning_path_id")
    )
    private List<CuratedLearningPath> curatedLearningPaths;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "expert_curated_bundle",
            joinColumns = @JoinColumn(name = "expert_id"),
            inverseJoinColumns = @JoinColumn(name = "curated_bundle_id")
    )
    private List<CuratedBundle> curatedBundles;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "expert_course",
            joinColumns = @JoinColumn(name = "expert_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> courses;

    // USER DETAILS METHODS
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(AccountAuthority.ROLE_EXPERT.toString()));
    }

    @Override
    public String getPassword() {
        return passwordHash;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
