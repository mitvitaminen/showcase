package net.croware.showcase.backend.data;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.security.core.GrantedAuthority;

import net.croware.showcase.app.util.ShowcaseConstants;

@Entity
@Table(name = "role")
public class Role extends AbsEntity implements GrantedAuthority {
    public enum Type {
        ADMIN, USER
    }

    private static final long serialVersionUID = ShowcaseConstants.SERIAL_VERSION_UID;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Type type;

    public Role() {
    }

    public Role(Type type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Role that = (Role) o;
        return this.type.equals(that.type);
    }

    @Override
    public String getAuthority() {
        return this.type.toString();
    }

    public Type getType() {
        return this.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.type);
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(super.toString()).append("[role.type:").append(this.type.toString()).append("]").trimToSize();
        return sb.toString();
    }

}
