package net.croware.showcase.backend.data;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

import net.croware.showcase.app.util.ShowcaseConstants;

@Entity
@Table(name = "user")
public class User extends AbsEntity implements UserDetails {

    private static final long serialVersionUID = ShowcaseConstants.SERIAL_VERSION_UID;

    @NotNull
    @NotEmpty
    @Email
    @Size(max = 255)
    @Column(unique = true)
    private String email;

    @NotNull
    @NotEmpty
    @Size(min = 16, max = 255)
    private String password;

    @NotNull
    @NotEmpty
    @Size(max = 255)
    private String firstname;

    @NotNull
    @NotEmpty
    @Size(max = 255)
    private String lastname;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = {
            @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, updatable = false) }, inverseJoinColumns = {
                    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false, updatable = false) })
    private Set<Role> roles;

    private LocalDate accountExpireDate;

    private LocalDate credentialsExpireDate;

    private LocalDate lockDate;

    @Transient
    private boolean enabled;
    @Transient
    private boolean accountNonLocked;
    @Transient
    private boolean accountNonExpired;
    @Transient
    private boolean credentialsNonExpired;

    public User() {
    }

    public User(String email, String rawPassword, String firstName, String lastName, Set<Role> roles) {
        this(email, rawPassword, firstName, lastName, roles, null, null, null);
    }

    public User(String email, String rawPassword, String firstname, String lastname, Set<Role> roles,
            LocalDate accountExpireDate, LocalDate credentialsExpireDate, LocalDate lockDate) {
        this.email = email;
        this.roles = roles;
        setPassword(rawPassword);
        this.firstname = firstname;
        this.lastname = lastname;
        this.accountExpireDate = accountExpireDate;
        this.credentialsExpireDate = credentialsExpireDate;
        this.lockDate = lockDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        final User that = (User) o;
        return Objects.equals(email, that.email) && Objects.equals(firstname, that.firstname)
                && Objects.equals(lastname, that.lastname) && Objects.equals(roles, that.roles)
                && Objects.equals(accountExpireDate, that.accountExpireDate)
                && Objects.equals(credentialsExpireDate, that.credentialsExpireDate)
                && Objects.equals(lockDate, that.lockDate);
    }

    public LocalDate getAccountExpireDate() {
        return accountExpireDate;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    public LocalDate getCredentialsExpireDate() {
        return credentialsExpireDate;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public LocalDate getLockDate() {
        return lockDate;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public String getRolesAsString() {
        final StringBuilder sb = new StringBuilder();
        roles.forEach(r -> sb.append(r.getType().toString()).append(","));
        return sb.deleteCharAt(sb.lastIndexOf(",")).toString();
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), email, firstname, lastname, roles,
                accountExpireDate, credentialsExpireDate, lockDate);
    }

    @Override
    public boolean isAccountNonExpired() {
        accountNonExpired = Optional.ofNullable(accountExpireDate).map(date -> date.isAfter(LocalDate.now()))
                .orElse(true);
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        accountNonLocked = Optional.ofNullable(lockDate).map(date -> date.isAfter(LocalDate.now()))
                .orElse(true);
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        credentialsNonExpired = Optional.ofNullable(credentialsExpireDate)
                .map(date -> date.isAfter(LocalDate.now())).orElse(true);
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        enabled = isAccountNonLocked() && isAccountNonExpired();
        return enabled;
    }

    public void setAccountExpireDate(LocalDate accountExpireDate) {
        this.accountExpireDate = accountExpireDate;
    }

    public void setCredentialsExpireDate(LocalDate credentialsExpireDate) {
        this.credentialsExpireDate = credentialsExpireDate;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setLockDate(LocalDate lockDate) {
        this.lockDate = lockDate;
    }

    public void setPassword(String rawPassword) {
        final Argon2PasswordEncoder encoder = new Argon2PasswordEncoder();
        password = encoder.encode(rawPassword);
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("[").append(super.toString()).append(",").append("email:").append(email).append(",")
                .append("firstname:").append(firstname).append(",").append("lastname:").append(lastname)
                .append(",").append("roles:").append(getRolesAsString().toString()).append(",").append("expireDate:")
                .append(accountExpireDate.toString()).append(",").append("credentialsExpireDate:")
                .append(credentialsExpireDate.toString()).append(",").append("lockDate:")
                .append(lockDate.toString()).append("]").trimToSize();
        return sb.toString();
    }

    @PrePersist
    @PreUpdate
    private void prepareData() {
        email = email.toLowerCase();
    }

}
