package net.croware.showcase.backend.data;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.Transient;
import javax.persistence.Version;

import net.croware.showcase.app.util.ShowcaseConstants;

@MappedSuperclass
public abstract class AbsEntity implements Serializable {
    private static final long serialVersionUID = ShowcaseConstants.SERIAL_VERSION_UID;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Version
    protected int version;

    @Transient
    private boolean isNew = true;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AbsEntity that = (AbsEntity) o;
        return version == that.version && Objects.equals(id, that.id);
    }

    public Long getId() {
        return id;
    }

    public int getVersion() {
        return version;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, version);
    }

    public boolean isNew() {
        return isNew;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("[").append("id:").append(id).append(", version:").append(version).append("]").trimToSize();
        return sb.toString();
    }

    @PrePersist
    @PostLoad
    void markNotNew() {
        isNew = false;
    }

}
